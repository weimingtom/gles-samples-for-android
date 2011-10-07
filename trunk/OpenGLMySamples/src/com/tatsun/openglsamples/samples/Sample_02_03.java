package com.tatsun.openglsamples.samples;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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
 * Sample_01_01. VBO (vertex buffer and coord buffer and index buffer)
 */
public class Sample_02_03 extends Activity {
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
		int[] vbo = new int[2];
		int texture;
		int verticesSize;
		@SuppressWarnings("unused")
		int verticesCount;
		int coordsSize;
		int indicesSize;
		int indicesCount;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

			// vbo
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, verticesSize);
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

			// draw
			// ibo
			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, vbo[1]);
			gl11.glDrawElements(GL10.GL_TRIANGLES, indicesCount,
					GL10.GL_UNSIGNED_SHORT, 0);
			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

			// unbind
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f,
					0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };
			float coords[] = { 0, 0, 0, 1, 1, 0, 1, 1, };
			short indices[] = { 0, 1, 2, 2, 1, 3, };
			verticesSize = positions.length * 4;
			verticesCount = positions.length / 3;
			coordsSize = coords.length * 4;
			indicesSize = indices.length * 2;
			indicesCount = indices.length;
			gl11.glGenBuffers(2, vbo, 0);
			FloatBuffer verticesBuffer = GGLTemporaryCache
					.makeVertexBufferByPool(positions);
			FloatBuffer coordsBuffer = GGLTemporaryCache
					.makeTexCoordBufferByPool(coords);
			ShortBuffer indicesBuffer = GGLTemporaryCache
					.makeIndexBuffer2ByPool(indices);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize + coordsSize,
					null, GL11.GL_STATIC_DRAW);
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize,
					verticesBuffer);
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, verticesSize,
					coordsSize, coordsBuffer);
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, vbo[1]);
			gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, indicesSize, null,
					GL11.GL_STATIC_DRAW);
			gl11.glBufferSubData(GL11.GL_ELEMENT_ARRAY_BUFFER, 0, indicesSize,
					indicesBuffer);
			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
