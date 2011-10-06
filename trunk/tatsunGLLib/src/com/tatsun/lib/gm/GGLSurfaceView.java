package com.tatsun.lib.gm;



import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GGLSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "RenderSurfaceView";

	private MilliTimeMeasure milliTimeMesure = new MilliTimeMeasure();
	private int millisPerFrame;
	private Handler handler = new Handler();
	private boolean isRepaint = true;
	private Runnable delayTask = new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		};
    private SurfaceHolder holder;
    private EGL10 egl;
    private EGLContext eglContext = null;
    private EGLDisplay eglDisplay = null;
    private EGLSurface eglSurface = null;
    private EGLConfig eglConfig = null;
    protected GL10 gl10 = null;
    private int windowWidth; 
    private int windowHeight;
		
	public GGLSurfaceView(Context context) {
		super(context);

		int fps = 60;
		millisPerFrame = 1000 / fps;
		
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
        getHolder().addCallback(this);
	}

    /**
     * 
     */
    public void initializeGL() {
        Log.i(TAG, "initializeGL");
        egl = (EGL10) EGLContext.getEGL();

        //! 描画先ディスプレイ確保
        eglDisplay = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

        // egl initialize
        {
            int[] version = { -1, -1 };
            if (!egl.eglInitialize(eglDisplay, version)) {
                Log.w(TAG, "egl version error");
                return;
            }
        }

        //! コンフィグ取得
        {
            EGLConfig[] configs = new EGLConfig[1];
            int[] num = new int[1];

            //! この配列でGLの性能を指定する。
            //! ディスプレイの色深度、Z深度もここで指定するが、
            //! 基本的に2D描画する場合はデフォルトのままでも特に問題ない。
            //! specに対応していない値を入れても初期化が失敗する。
            int[] spec = { EGL10.EGL_NONE //! 終端にはEGL_NONEを入れる
            };
            if (!egl.eglChooseConfig(eglDisplay, spec, configs, 1, num)) {
                Log.i(TAG, "eglChooseConfig error");
                return;
            }

            eglConfig = configs[0];
        }

        //! レンダリングコンテキスト作成
        {
            //レンダリングコンテキスト作成
            eglContext = egl.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, null);
            if (eglContext == EGL10.EGL_NO_CONTEXT) {
            	Log.i(TAG, "glContext == EGL10.EGL_NO_CONTEXT");
                return;
            }
        }
        //! 描画先サーフェイスを作成する
        {
            //! SurfaceHolderに結びつける
            eglSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig, holder, null);

            if (eglSurface == EGL10.EGL_NO_SURFACE) {
            	Log.i(TAG, "glSurface == EGL10.EGL_NO_SURFACE");
                return;
            }
        }

        //! GLESインターフェース取得
        {
            gl10 = (GL10) eglContext.getGL();
        }

        //! サーフェイスとコンテキストを結びつける
        {
            if (!egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext)) {
                Log.i(TAG, "!eglMakeCurrent");
                return;
            }
        }

        {
            windowWidth = holder.getSurfaceFrame().width();
            windowHeight = holder.getSurfaceFrame().height();

            gl10.glViewport(0, 0, windowWidth, windowHeight);
        }
    }
	
    /**
     * 
     */
    public void pauseGL() {
        Log.i(TAG, "pauseGL");
        if (eglSurface != null) {
            egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, eglContext);
            egl.eglDestroySurface(eglDisplay, eglSurface);
            eglSurface = null;
        }
    }

    /**
     * 
     */
    public void resumeGL() {
        Log.i(TAG, "resumeGL");
        eglSurface = egl.eglCreateWindowSurface(eglDisplay, eglConfig, holder, null);
        egl.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);
    }

    /**
     * OpenGL終了処理を行う。
     */
    public void finalizeGL() {
        //サーフェイス破棄
        if (eglSurface != null) {
            egl.eglMakeCurrent(eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            egl.eglDestroySurface(eglDisplay, eglSurface);
            eglSurface = null;
        }

        //レンダリングコンテキスト破棄
        if (eglContext != null) {
            egl.eglDestroyContext(eglDisplay, eglContext);
            eglContext = null;
        }

        //ディスプレイコネクション破棄
        if (eglDisplay != null) {
            egl.eglTerminate(eglDisplay);
            eglDisplay = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //!
        if (gl10 == null) {
        	Log.i(TAG, "initialize start");
            this.holder = holder;
            initializeGL();
            onSurfaceChanged(gl10, width, height);
        } else {
        	Log.i(TAG, "resume");
            resumeGL();
            onSurfaceChanged(gl10, width, height);
        }
        milliTimeMesure.reset();
        
        isRepaint = true;
        repaint();
    }
    
    protected void onSurfaceChanged(GL10 gl, int width, int height) {
    	
    }
    
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	onSurfaceCreated(gl10, eglConfig);
    }
    
    protected void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
    	
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        onSurfaceDestroyed(gl10);
    }
	
    protected void onSurfaceDestroyed(GL10 gl) {
    	
    }
    
    /**
     * 再描画を行う。
     */
    protected void repaint() {
		onRepaint();
		delayRequestRender();
    }
    
    public void onRepaint() {
    	
    }

    public void swapBuffers() {
        egl.eglSwapBuffers(eglDisplay, eglSurface);
    }
    
    public void onPause() {
    	isRepaint = false;
    	handler.removeCallbacks(delayTask);
    	pauseGL();
    }
    
    public void onResume() {
    	//resumeGL();
    }
    
    public void onDestroy() {
    	finalizeGL();
    }
    
	protected void delayRequestRender() {
		if(!isRepaint)
			return;
		
		long currentDelayTime = millisPerFrame - (int)milliTimeMesure.getElapsed();
		//Log.v("task", "currentDelay:"+currentDelayTime+", fps:"+fpsChecker.getFPS());
		if(currentDelayTime < 0) {
			currentDelayTime = 0;
		}
		handler.postDelayed(delayTask, currentDelayTime);
		milliTimeMesure.reset();
	}
}
