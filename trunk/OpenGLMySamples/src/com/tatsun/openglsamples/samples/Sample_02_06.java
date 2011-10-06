package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLTexCoordBufferObject;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.openglsamples.R;

/**
 * Sample_02_06. VBO (use vbo class for vertex and coord)
 */
public class Sample_02_06 extends Activity {
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
		GGLTexCoordBufferObject tbo = new GGLTexCoordBufferObject();
		GGLIndexBufferObject ibo = new GGLIndexBufferObject();
		int texture;
		int frame;

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
			if (frame % 60 < 30) {
				tbo.bind(gl11, 0);
			} else {
				tbo.bind(gl11, 1);
			}

			ibo.draw(gl11);

			// unbind
			vbo.unbind(gl11);
			tbo.unbind(gl11);
			gl.glDisable(GL10.GL_TEXTURE_2D);

			++frame;
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f,
					0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };
			float coords[] = { 0, 0, 0, 0.5f, 1, 0, 1, 0.5f, 0, 0.5f, 0, 1, 1,
					0.5f, 1, 1, };
			byte indices[] = { 0, 1, 2, 2, 1, 3, };

			// vertex
			vbo.bufferData(gl11, positions);
			// coord
			tbo.bufferData(gl11, coords, 8);
			// index
			ibo.bufferData(gl11, indices);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
			frame = 0;
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
