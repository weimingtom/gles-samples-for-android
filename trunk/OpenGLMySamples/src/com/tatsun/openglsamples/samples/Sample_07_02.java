package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.helper.GGLSquareShape;

/**
 * Sample_07_02. draw text1
 */
public class Sample_07_02 extends Activity {
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
		int texture;
		GGLSquareShape vbo = new GGLSquareShape();

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;

			gl11.glEnable(GL10.GL_TEXTURE_2D);
			gl11.glBindTexture(GL10.GL_TEXTURE_2D, texture);

			vbo.bind(gl11);
			vbo.draw(gl11);
			vbo.unbind(gl11);

			gl11.glDisable(GL10.GL_TEXTURE_2D);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			GGLTemporaryCache.clearCache();
			gl.glViewport(0, 0, width, height);

			Bitmap bitmap = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
			String text = "TEST DRAW TEXT";
			createFontTexture(gl11, text, bitmap);

			texture = GGLUtils.loadTexture(gl11, bitmap, GL10.GL_NEAREST, 0);
			bitmap.recycle();

			vbo.create(gl11, true);
		}

		private void createFontTexture(GL11 gl11, String text, Bitmap bitmap) {
			Canvas canvas = new Canvas(bitmap);

			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setColor(Color.YELLOW);
			paint.setStyle(Style.FILL);
			paint.setTextAlign(Paint.Align.LEFT);
			paint.setTextSize(20);
			paint.setShadowLayer(2, 0, 0, Color.BLACK);
			FontMetrics fontMetrics = paint.getFontMetrics();

			float fontHOffset = 40 - fontMetrics.bottom;

			canvas.drawColor(Color.BLUE);
			canvas.drawText(text, 0, fontHOffset, paint);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
