package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.openglsamples.R;

/**
 * Sample_04_05. camera view matrix
 */
public class Sample_04_05 extends Activity {
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
		GGLIndexBufferObject ibo = new GGLIndexBufferObject();
		int texture;
		float angle;

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			// view
			gl.glLoadIdentity();
			gl11.glTranslatef(0, 0, -2);
			gl11.glRotatef(angle, 0.0f, 1.0f, 0.0f);

			// set render status
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glAlphaFunc(GL10.GL_EQUAL, 1.0f);
			// gl.glColor4f(1, 1, 1, 1);

			// bind
			gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
			vbo.bind(gl11);

			// left
			gl11.glPushMatrix();
			{
				gl11.glTranslatef(-1, 0, 0);
				ibo.draw(gl11);
			}
			gl11.glPopMatrix();

			// right
			gl11.glPushMatrix();
			{
				gl11.glTranslatef(1, 0, 0);
				ibo.draw(gl11);
			}
			gl11.glPopMatrix();

			// up
			gl11.glPushMatrix();
			{
				gl11.glTranslatef(0, -1, 0);
				ibo.draw(gl11);
			}
			gl11.glPopMatrix();

			// down
			gl11.glPushMatrix();
			{
				gl11.glTranslatef(0, 1, 0);
				ibo.draw(gl11);
			}
			gl11.glPopMatrix();

			// unbind
			vbo.unbind(gl11);
			gl.glDisable(GL10.GL_TEXTURE_2D);

			angle += 1.2f;
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			// projection
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();

			// view port
			gl.glViewport(0, 0, width, height);
			// perspective
			gl.glFrustumf(-1, 1, -1.5f, 1.5f, 1, 10);

			// model view
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			// vertex coords
			vbo.bufferData(gl11, GGLUtils.cubeVertices, GGLUtils.cubeTexCoords);
			// indices
			ibo.bufferData(gl11, GGLUtils.cubeIndices);

			// load bitmap
			texture = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
