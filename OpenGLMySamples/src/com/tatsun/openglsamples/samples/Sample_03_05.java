package com.tatsun.openglsamples.samples;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.openglsamples.R;

/**
 * Sample_03_05. save backbaffer texture
 */
public class Sample_03_05 extends Activity {
	private GLSurfaceView glSurfaceView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(this.getClass().getSimpleName());

		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(new RendererSample());

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
		int[] vbo = new int[1];
		int[] textures = new int[2];
		int verticesSize;
		int verticesCount;
		int coordsSize;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			// alpha blend alpha_test. アルファ値が1.0f以外の部分は透明にする
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glAlphaFunc(GL10.GL_EQUAL, 1.0f);

			// set render status
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// bind
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			// texture 1
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, verticesSize);

			// draw
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, verticesCount);

			// texture2
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);

			// draw
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, verticesCount);

			// unbind
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL10.GL_ALPHA_TEST);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -1f, 1f, 0.0f, -1f, -1f, 0.0f, 1f, 1f, 0.0f,
					1f, -1f, 0.0f, };
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
			textures[0] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_03);
			textures[1] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
