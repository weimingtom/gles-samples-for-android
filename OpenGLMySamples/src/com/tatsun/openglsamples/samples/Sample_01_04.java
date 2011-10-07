package com.tatsun.openglsamples.samples;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.tatsun.openglsamples.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;

/**
 * Sample_01_04. simple texture
 * 
 */
public class Sample_01_04 extends Activity {
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
	 * simple texture glBindTexture() GL10.GL_CULL_FACE GL10.GL_TRIANGLE_STRIP
	 * x2 glColor4f()
	 * 
	 * bind(転送)済みBitmapの破棄 gl.glDeleteTextures(1, texId, 0);
	 */
	private class RendererSample implements GLSurfaceView.Renderer {
		@SuppressWarnings("unused")
		private int screenWidth, screenHeight;
		private int[] textures = new int[1];

		public RendererSample() {
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			gl.glClearColor(0f, 0f, 0f, 1.0f);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

			{
				// u v
				float uv[] = { 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, };

				// ! OpenGLはビッグエンディアンではなく、CPUごとのネイティブエンディアンで数値を伝える必要がある。
				// ! そのためJavaヒープを直接的には扱えず、java.nio配下のクラスへ一度値を格納する必要がある。
				ByteBuffer bb = ByteBuffer.allocateDirect(uv.length * 4);
				bb.order(ByteOrder.nativeOrder());
				FloatBuffer fb = bb.asFloatBuffer();
				fb.put(uv);
				fb.position(0);

				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fb);
			}

			{
				// x y z
				float positions[] = { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f,
						0.5f, 0.5f, 0.0f, 0.5f, -0.5f, 0.0f, };

				// バッファ作成
				ByteBuffer bb = ByteBuffer.allocateDirect(positions.length * 4);
				bb.order(ByteOrder.nativeOrder());
				FloatBuffer fb = bb.asFloatBuffer();
				fb.put(positions);
				fb.position(0);

				gl.glEnableClientState(GL10.GL_VERTEX_ARRAY); // 頂点配列有効化
				gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb); // バッファ指定
			}

			gl.glEnable(GL10.GL_CULL_FACE); // culling

			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4); // 描画

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisable(GL10.GL_CULL_FACE);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glViewport(0, 0, width, height);
			screenWidth = width;
			screenHeight = height;

			Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.texture_02);

			// load texture
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glGenTextures(1, textures, 0);

			// ! テクスチャ情報の設定
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

			// 拡大・縮小時の処理を指定する。
			// GL10.GL_LINEAR / GL10.GL_NEARESTで指定する。
			// MINは縮小時、MAGは拡大時の処理に指定する。
			// リニアは高品位・低速、ニアレストは低品位・高速に動作する。
			// これを指定しない場合、正常にテクスチャが表示されない場合がある
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_NEAREST);

			// ! bitmapを破棄
			bitmap.recycle();
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		}
	}
}
