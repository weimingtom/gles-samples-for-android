package com.tatsun.openglsamples.samples;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.openglsamples.R;

/**
 * Sample_01_01. save backbaffer texture
 */
public class Sample_03_04 extends Activity {
	private GLSurfaceView glSurfaceView = null;
	private boolean isShot = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(this.getClass().getSimpleName());

		glSurfaceView = new GLSurfaceView(this) {
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i("", "action down");
					break;
				case MotionEvent.ACTION_UP:
					isShot = true;
					Toast.makeText(Sample_03_04.this, "save", Toast.LENGTH_LONG)
							.show();
					break;
				}
				return super.onTouchEvent(event);
			}
		};
		glSurfaceView.setRenderer(new RendererSample());
		glSurfaceView.setClickable(true);

		setContentView(glSurfaceView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

	/**
	 * renderer
	 * 
	 */
	private class RendererSample implements GLSurfaceView.Renderer {
		int mWidth;
		int mHeight;
		int[] vbo = new int[1];
		int texture;
		int verticesSize;
		int verticesCount;
		int coordsSize;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// bind
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			// texture
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
			gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, verticesSize);

			// draw
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, verticesCount);

			// unbind
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);

			if (isShot) {
				isShot = false;
				saveScreen(gl);
			}
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			mWidth = width;
			mHeight = height;
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f,
					0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };
			float coords[] = { 0, 0, 0, 1, 1, 0, 1, 1, };
			verticesSize = positions.length * 4;
			verticesCount = positions.length / 3;
			coordsSize = coords.length * 4;
			gl11.glGenBuffers(1, vbo, 0);
			FloatBuffer verticesBuffer = GGLTemporaryCache
					.makeVertexBufferByPool(positions);
			FloatBuffer coordsBuffer = GGLTemporaryCache
					.makeTexCoordBufferByPool(coords);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize + coordsSize,
					null, GL11.GL_STATIC_DRAW);
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize,
					verticesBuffer);
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, verticesSize,
					coordsSize, coordsBuffer);
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}

		protected void saveScreen(GL10 gl) {
			Log.i("", "save screen start");
			gl.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 1);

			// Read Pixels
			ByteBuffer pixels = ByteBuffer.allocateDirect(mWidth * mHeight * 4);
			gl.glReadPixels(0, 0, mWidth, mHeight, GL10.GL_RGBA,
					GL10.GL_UNSIGNED_BYTE, pixels);

			// Create Bitmap
			Bitmap bmp = Bitmap.createBitmap(mWidth, mHeight,
					Bitmap.Config.ARGB_8888);
			bmp.copyPixelsFromBuffer(pixels);

			// Save Bitmap
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(
						Environment.getExternalStorageDirectory()
								+ "/sample_test_001.jpg");
				bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			// Recycle Bitmap
			bmp.recycle();
			bmp = null;

			gl.glPixelStorei(GL10.GL_UNPACK_ALIGNMENT, 4);
		}
	}
}
