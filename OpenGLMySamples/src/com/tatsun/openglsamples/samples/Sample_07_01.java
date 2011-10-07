package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.helper.GGLCircleShape;
import com.tatsun.lib.gles.helper.GGLSquareShape;
import com.tatsun.openglsamples.R;

/**
 * Sample_07_01. draw square, circle
 */
public class Sample_07_01 extends Activity {
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
		GGLSquareShape squreVbo = new GGLSquareShape();
		GGLCircleShape circleVbo = new GGLCircleShape();
		int texture;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status

			// square
			squreVbo.bind(gl11);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
			gl.glPushMatrix();
			{
				gl.glScalef(0.5f, 0.33f, 1);
				gl.glTranslatef(-0.6f, 0, 0);
				squreVbo.draw(gl11);
			}
			gl.glPopMatrix();
			gl.glDisable(GL10.GL_TEXTURE_2D);
			squreVbo.unbind(gl11);

			// circle
			circleVbo.bind(gl11);
			gl.glColor4f(1, 1, 1, 1);
			gl.glPushMatrix();
			{
				gl.glScalef(0.5f, 0.33f, 1);
				gl.glTranslatef(0.6f, 0, 0);
				circleVbo.draw(gl11);
			}
			gl.glPopMatrix();
			circleVbo.unbind(gl11);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			squreVbo.create(gl11, true);
			circleVbo.create(gl11, 32, 0.5f);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
