package com.tatsun.openglsamples.samples;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLTemporaryCache;

/**
 * Sample_02_05. VBO (vertex buffer and color buffer and normal buffer)
 */
public class Sample_02_05 extends Activity {
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
		int verticesSize;
		int verticesCount;
		int colorsSize;
		int normalsSize;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

			// vbo[0] vertex buffer
			{
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
				gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
				gl11.glNormalPointer(GL11.GL_FLOAT, 0, verticesSize);
				gl11.glColorPointer(4, GL11.GL_FLOAT, 0, verticesSize
						+ normalsSize);
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}

			// draw
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, verticesCount);

			// unbind
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f,
					0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };
			float normals[] = { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, };
			float colors[] = { 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, };

			gl11.glGenBuffers(1, vbo, 0);
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
			// vertex
			{
				verticesSize = positions.length * 4;
				verticesCount = positions.length / 3;
				normalsSize = normals.length * 4;
				colorsSize = colors.length * 4;

				FloatBuffer vertexBuffer = GGLTemporaryCache
						.makeVertexBufferByPool(positions);
				gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize
						+ normalsSize + colorsSize, null, GL11.GL_STATIC_DRAW);
				gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize,
						vertexBuffer);
			}
			// normals
			{
				FloatBuffer normalBuffer = GGLTemporaryCache
						.makeNormalBufferByPool(normals);
				gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, verticesSize,
						normalsSize, normalBuffer);
			}
			// colors
			{
				FloatBuffer colorBuffer = GGLTemporaryCache
						.makeColorBufferByPool(colors);
				gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, verticesSize
						+ normalsSize, colorsSize, colorBuffer);
			}
			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
