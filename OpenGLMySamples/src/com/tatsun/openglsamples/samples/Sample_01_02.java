package com.tatsun.openglsamples.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Sample_01_02. simple GL_TRIANGLES
 * 
 */
public class Sample_01_02 extends Activity {
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
	 * simple triangle GL10.GL_TRIANGLES
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
					0.5f, 0.0f, };

			// バッファ作成
			ByteBuffer bb = ByteBuffer.allocateDirect(positions.length * 4);
			bb.order(ByteOrder.nativeOrder());
			FloatBuffer fb = bb.asFloatBuffer();
			fb.put(positions);
			fb.position(0);

			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // 頂点配列有効化
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb); // バッファ指定
			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3); // 描画
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
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
