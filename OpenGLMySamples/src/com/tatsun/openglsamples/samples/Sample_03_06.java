package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.lib.gles.helper.GGLSquareShape;
import com.tatsun.lib.gm.FPSChecker;
import com.tatsun.openglsamples.R;

/**
 * Sample_03_05. save backbaffer texture
 */
public class Sample_03_06 extends Activity {
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
		GGLSquareShape svbo = new GGLSquareShape();
		int[] textures = new int[2];
		int[] animationPattern = { 0, 1, 2, 1, };
		int patternset = 0;
		int frame = 0;
		FPSChecker fps = new FPSChecker();
		GGLFont font = new GGLFont();

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			++frame;
			if (frame % 120 == 0)
				patternset = (patternset + 1) % 4;

			// alpha blend alpha_test. アルファ値が1.0f以外の部分は透明にする
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glAlphaFunc(GL10.GL_EQUAL, 1.0f);

			// background
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
			svbo.bind(gl11);
			svbo.draw(gl11);
			svbo.unbind(gl11);

			// sprite
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			vbo.bind(gl11, patternset * 3 + animationPattern[frame / 15 % 4]);
			vbo.draw(gl11);
			vbo.unbind(gl11);

			gl.glDisable(GL10.GL_TEXTURE_2D);
			gl.glDisable(GL10.GL_ALPHA_TEST);
			fps.update();
			font.drawText(gl11, "" + fps.getFPS(), -0.8f, 0.8f, 0.1f);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			gl.glViewport(0, 0, width, height);

			float w = 1f / 16f, h = 1f / 16f;
			float positions[] = { -w, h, 0.0f, -w, -h, 0.0f, w, h, 0.0f, w, -h,
					0.0f, };
			int texCoordsCount = 12 * 4 * 2, idx = 0;
			float coords[] = GGLTemporaryCache.getTexCoordsPool(texCoordsCount);
			for (int i = 0; i < 12; ++i) {
				float l = i * w, t = 0;
				coords[idx++] = l;
				coords[idx++] = t;
				coords[idx++] = l;
				coords[idx++] = t + h;
				coords[idx++] = l + w;
				coords[idx++] = t;
				coords[idx++] = l + w;
				coords[idx++] = t + h;
			}

			vbo.bufferData(gl11, positions, coords, GL11.GL_TRIANGLE_STRIP);

			svbo.create(gl11, -1f, 1f, 1f, -1f, true);

			// load bitmap
			textures[0] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_02);
			textures[1] = GGLUtils.loadTexture(gl11, getResources(),
					R.drawable.texture_03);

			font.load(gl11);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
