package com.tatsun.openglsamples.samples;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.GGLTemporaryCache;

/**
 * Sample_07_03. draw text2
 */
public class Sample_07_03 extends Activity {
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
		GGLFont font = new GGLFont();

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			GL11 gl11 = (GL11) gl;
			// set render status
			float yoffset = 1.35f;
			font.drawText(gl11, "TEXTURE FONT SAMPLE", -0.9f, yoffset, 0.08f);
			font.drawText(gl11, "! \" # $ % & ' ( ) * + , - . /", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "0 1 2 3 4 5 6 7 8 9 : ; < = > ?", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "@ A B C D E F G H I J K L M N O", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "P Q R S T U V W X Y Z [ \\ ] ^ |", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "` a b c d e f g h i j k l m n o", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "p q r s t u v w x y z { | } ~", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "あ い う え お か き く け こ さ し す せ そ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "ア イ ウ エ オ カ キ ク ケ コ サ シ ス セ ソ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "Android is a software stack for ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "mobile devices that includes an ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "What is Android?", -0.9f, yoffset -= 0.15f,
					0.08f);
			font.drawText(gl11, "Android is a software stack for ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "mobile devices that includes an ", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, "operating system, middleware and", -0.9f,
					yoffset -= 0.15f, 0.08f);
			font.drawText(gl11, " key applications.", -0.9f, yoffset -= 0.15f,
					0.08f);
			// font.drawBaseTexture(gl11);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			GL11 gl11 = (GL11) gl;
			GGLTemporaryCache.clearCache();
			// w : h を 2 : 3 で表示
			int wr = width > height ? 3 : 2;
			int hr = width > height ? 2 : 3;
			int w = 0, h = 0;
			while (w < width && h < height) {
				w += wr;
				h += hr;
			}
			gl.glViewport((width - w) / 2, (height - h) / 2, w, h);

			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glOrthof(-wr / 2f, wr / 2f, -hr / 2f, hr / 2f, 0.5f, -0.5f);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			font.load(gl11);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
