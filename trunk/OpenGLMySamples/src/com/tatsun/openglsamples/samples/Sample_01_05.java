package com.tatsun.openglsamples.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Sample_01_03. simple index
 * 
 */
public class Sample_01_05 extends Activity {
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
	 * simple rectangle GL10.GL_CULL_FACE GL10.GL_TRIANGLE_STRIP x2 glColor4f()
	 * 
	 */
	private class RendererSample implements GLSurfaceView.Renderer {

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClearColor(0f, 0f, 0f, 1.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f,
					0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };
			short indices[] = { 0, 1, 2, 2, 1, 3, };

			// vertex buffer
			{
				ByteBuffer bb = ByteBuffer.allocateDirect(positions.length * 4);
				bb.order(ByteOrder.nativeOrder());
				FloatBuffer fb = bb.asFloatBuffer();
				fb.put(positions);
				fb.position(0);

				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // 頂点配列有効化
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb); // バッファ指定
			}

			gl.glEnable(GL10.GL_CULL_FACE); // culling
			gl.glColor4f(0, 1, 0, 1); // 色指定

			// index buffer
			{
				ByteBuffer bb = ByteBuffer.allocateDirect(indices.length * 2);
				bb.order(ByteOrder.nativeOrder());
				ShortBuffer fb = bb.asShortBuffer();
				fb.put(indices);
				fb.position(0);

				gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 3,
						GL10.GL_UNSIGNED_SHORT, bb);
			}

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisable(GL10.GL_CULL_FACE);
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
