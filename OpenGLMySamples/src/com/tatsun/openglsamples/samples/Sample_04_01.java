package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.openglsamples.R;

/**
 * Sample_04_01. view port 2D
 */
public class Sample_04_01 extends Activity {
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
		GGLVertexCoordsBufferObject vbo = new GGLVertexCoordsBufferObject();
		int texture;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			// texture 1
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

			// bind
			vbo.bind(gl11);

			// draw
			vbo.draw(gl11);

			// unbind
			vbo.unbind(gl11);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// w : h を 2 : 3 で表示
			int wr = width > height ? 3 : 2;
			int hr = width > height ? 2 : 3;
			int w = 0, h = 0;
			while (w < width && h < height) {
				w += wr;
				h += hr;
			}
			gl.glViewport((width - w) / 2, (height - h) / 2, w, h);

			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(-wr / 2f, wr / 2f, -hr / 2f, hr / 2f, 0.5f, -0.5f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float vartices[] = { -1f, 1f, 0.0f, -1f, -1f, 0.0f, 1f, 1f, 0.0f,
					1f, -1f, 0.0f, };
			float coords[] = { 0, 0, 0, 1, 1, 0, 1, 1, };
			vbo.bufferData(gl11, vartices, coords, GL10.GL_TRIANGLE_STRIP);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
