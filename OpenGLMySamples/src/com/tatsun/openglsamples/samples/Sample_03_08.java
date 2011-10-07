package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gles.helper.GGLSquareShape;
import com.tatsun.openglsamples.R;

/**
 * Sample_03_08. point sprite texture
 */
public class Sample_03_08 extends Activity {
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
		GGLVertexBufferObject vbo = new GGLVertexBufferObject();
		GGLSquareShape shape = new GGLSquareShape();
		int[] textures = new int[2];

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

			// background
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
			shape.bind(gl11);
			shape.draw(gl11);
			shape.unbind(gl11);

			// point sprite
			gl11.glEnable(GL11.GL_POINT_SPRITE_OES);
			gl.glPointSize(256.0f);
			gl11.glTexEnvi(GL11.GL_POINT_SPRITE_OES, GL11.GL_COORD_REPLACE_OES,
					GL11.GL_TRUE);

			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			vbo.bind(gl11);
			vbo.draw(gl11);
			vbo.unbind(gl11);

			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL11.GL_POINT_SPRITE_OES);
			gl.glDisable(GL10.GL_ALPHA_TEST);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f,
					0.5f, 0.0f, -0.5f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, };

			vbo.bufferData(gl11, positions, GL10.GL_POINTS);

			// load bitmap
			textures[0] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_01);
			textures[1] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_03);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
