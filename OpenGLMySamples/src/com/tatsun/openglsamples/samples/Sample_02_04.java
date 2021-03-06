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
 * Sample_01_01. VBO (vertex buffer and coord buffer sepalate vbo)
 */
public class Sample_02_04 extends Activity {
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
		int verticesCount;
		int coordsSize;
		int coordsUnitSize;
		int frame;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// vbo[0] vertex buffer
			{
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
				gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}

			// vbo[1] coord buffer
			{
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[1]);
				if (frame % 60 < 30) {
					gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, 0);
				} else {
					gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, coordsUnitSize);
				}
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}

			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);

			// draw
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, verticesCount);

			// unbind
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

			gl11.glGenBuffers(2, vbo, 0);
			// vertex
			{
				verticesSize = positions.length * 4;
				verticesCount = positions.length / 3;
				FloatBuffer verticesBuffer = GGLTemporaryCache
						.makeVertexBufferByPool(positions);

				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[0]);
				gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize, null,
						GL11.GL_STATIC_DRAW);
				gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize,
						verticesBuffer);
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}
			// coord
			{
				coordsSize = coords.length * 4;
				coordsUnitSize = coordsSize / 2;
				FloatBuffer coordsBuffer = GGLTemporaryCache
						.makeTexCoordBufferByPool(coords);

				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, vbo[1]);
				gl11.glBufferData(GL11.GL_ARRAY_BUFFER, coordsSize, null,
						GL11.GL_STATIC_DRAW);
				gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, coordsSize,
						coordsBuffer);
				gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}

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
