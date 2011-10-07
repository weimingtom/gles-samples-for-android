package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.lib.gm.FPSChecker;
import com.tatsun.lib.gm.Misc;
import com.tatsun.openglsamples.R;

/**
 * Sample_04_06. 2D + 3D
 */
public class Sample_04_06 extends Activity {
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
		private static final int UNIT_COUNT = 60;
		private static final int CAMERA_ANGLE = 30;
		float sqrt3 = (float) Math.sqrt(3);
		float aspect;
		GGLVertexCoordsBufferObject unitVbo = new GGLVertexCoordsBufferObject();
		GGLVertexCoordsBufferObject floorVbo = new GGLVertexCoordsBufferObject();
		GGLIndexBufferObject unitIbo = new GGLIndexBufferObject();
		GGLIndexBufferObject floorIbo = new GGLIndexBufferObject();
		int[] textures = new int[2];
		GGLFont font = new GGLFont();
		FPSChecker fps = new FPSChecker();
		float[][] positions = new float[UNIT_COUNT][];

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glClearColor(1, 1, 1, 1);
			GL11 gl11 = (GL11) gl;
			fps.update();

			// 3D
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl11, 45, aspect, 0.01f, 100f);
			GLU.gluLookAt(gl, 0, 10 / sqrt3, 10, 0, 0, 0.0f, 0.0f, 1.0f, 0.0f);

			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			// bind
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
			floorVbo.bind(gl11);
			// left
			gl11.glPushMatrix();
			{
				floorIbo.draw(gl11);
			}
			gl11.glPopMatrix();
			// unbind
			floorVbo.unbind(gl11);

			// units
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glAlphaFunc(GL10.GL_EQUAL, 1.0f);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			unitVbo.bind(gl11);

			for (int i = 0; i < UNIT_COUNT; ++i) {
				gl11.glPushMatrix();
				{
					gl.glTranslatef(positions[i][0], positions[i][1],
							positions[i][2]);
					gl11.glRotatef(-CAMERA_ANGLE, 1f, 0f, 0f);
					unitIbo.draw(gl11);
				}
				gl11.glPopMatrix();
			}

			// unbind
			unitVbo.unbind(gl11);

			gl.glDisable(GL10.GL_ALPHA_TEST);
			gl.glDisable(GL10.GL_DEPTH_TEST);
			gl.glDisable(GL10.GL_TEXTURE_2D);

			// 2D
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(-1, 1f, -1.5f, 1.5f, 0.5f, -0.5f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

			// draw
			font.drawText(gl11, "fps:" + fps.getFPS(), -0.9f, 1.4f, 0.1f);

			gl.glDisable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			// projection
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			aspect = (float) width / (float) height;
			// view port
			gl.glViewport(0, 0, width, height);

			// model view
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			// unit
			float vs = 1f / 8f;
			float ts = 1f / 16f;
			float[] unitVertices = { -vs, vs, 0, -vs, -vs, 0, vs, vs, 0, vs,
					-vs, 0 };
			float[] unitCoords = { 0, 0, 0, ts, ts, 0, ts, ts };
			byte[] unitIndices = { 0, 1, 2, 2, 1, 3 };

			unitVbo.bufferData(gl11, unitVertices, unitCoords);
			unitIbo.bufferData(gl11, unitIndices);

			for (int i = 0; i < UNIT_COUNT; ++i) {
				positions[i] = new float[] { Misc.getRangeRand(-1.8f, 1.8f),
						vs, Misc.getRangeRand(-0.9f, 0.9f) };
			}

			// floor
			float[] floorVertices = { -2, 0, 1, -2, 0, -1, 2, 0, 1, 2, 0, -1 };
			float[] floorCoords = { 0, 0, 0, 10, 10, 0, 10, 10 };
			byte[] floorIndices = { 0, 1, 2, 2, 1, 3 };

			// vertex coords
			floorVbo.bufferData(gl11, floorVertices, floorCoords);
			floorIbo.bufferData(gl11, floorIndices);

			// load bitmap
			textures[0] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
			textures[1] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_03);

			font.load(gl11);
			fps.fpsReset();
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
