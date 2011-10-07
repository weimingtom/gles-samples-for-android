package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLMultiVertexBufferObject;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.openglsamples.R;

/**
 * Sample_02_07. VBO (use same vbo for vertex, uv, normal, color and index)
 */
public class Sample_02_07 extends Activity {
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
		GGLMultiVertexBufferObject vbo = new GGLMultiVertexBufferObject();
		GGLIndexBufferObject ibo = new GGLIndexBufferObject();
		int texture;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status
			gl.glEnable(GL10.GL_TEXTURE_2D);

			// bind
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
			vbo.bind(gl11);

			// draw
			ibo.draw(gl11);

			// unbind
			vbo.unbind(gl11);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -1f, 1f, 0.0f, -1f, -1f, 0.0f, 1f, 1f, 0.0f,
					1f, -1f, 0.0f, };
			float coords[] = { 0, 0, 0, 1f, 1, 0, 1, 1f, };
			float normals[] = { 0, 0, 1, };
			float colors[] = { 1f, 0.8f, 0.8f, 1f, 0.8f, 1f, 0.8f, 1f, 0.8f,
					0.8f, 1f, 1f, 0.8f, 1f, 1f, 1f, };
			byte indices[] = { 0, 1, 2, 2, 1, 3, };

			// vertex
			vbo.bufferData(gl11, positions, coords, normals, colors);
			// coord
			ibo.bufferData(gl11, indices);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
