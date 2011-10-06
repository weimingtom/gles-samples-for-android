package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Sample_01_01. clear by color
 * 
 */
public class Sample_01_01 extends Activity {
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
	 * stub
	 * 
	 */
	private class RendererSample implements GLSurfaceView.Renderer {
		float r, g, b;

		public RendererSample() {
			r = (float) Math.random();
			g = (float) Math.random();
			b = (float) Math.random();
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClearColor(r, g, b, 1.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			r = (r + 0.01f) % 1f;
			g = (g + 0.01f) % 1f;
			b = (b + 0.01f) % 1f;
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glViewport(0, 0, width, height);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
