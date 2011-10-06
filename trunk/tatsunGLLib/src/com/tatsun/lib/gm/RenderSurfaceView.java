package com.tatsun.lib.gm;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.tatsun.lib.gles.GGLTemporaryCache;

public class RenderSurfaceView extends GLSurfaceView implements Renderer {
	
	private GestureDetector gestureDetector;
	private FPSChecker fpsChecker = new FPSChecker();
	MilliTimeMeasure milliTimeMesure = new MilliTimeMeasure();
	private int millisPerFrame;
	private Handler handler = new Handler();
	private Runnable delayTask = new Runnable() {
			@Override
			public void run() {
				requestRender();
			}
		};
		
	public RenderSurfaceView(Context context) {
		super(context);
		// debagging. call before setRenderer method.
		//setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
		
		int fps = 60;
		millisPerFrame = 1000 / fps;

		gestureDetector = new GestureDetector(GmService.motionPool);

		setRenderer(this);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
		setFocusable(true);
		setClickable(true);
		
		requestFocus();
	}
	
	public void setStartScene(Class sceneClazz, Bundle bundle) {
		GmStatus.startClazz = sceneClazz;
		GmStatus.startBundle = bundle;
	}
	
	/**
	 * pause
	 */
	@Override
	public void onPause() {
		super.onPause();

		GmService.contentLoader.onPause();
	}

	@Override
	public void setRenderer(Renderer renderer) {
		super.setRenderer(renderer);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFps() {
		return fpsChecker.getFPS();
	}

	protected void delayRequestRender() {
		long currentDelayTime = millisPerFrame - (int)milliTimeMesure.getElapsed();
		//Log.v("task", "currentDelay:"+currentDelayTime+", fps:"+fpsChecker.getFPS());
		if(currentDelayTime < 0) {
			currentDelayTime = 0;
		}
		handler.postDelayed(delayTask, currentDelayTime);
		milliTimeMesure.reset();
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		milliTimeMesure.reset();
		GL11 gl11 = (GL11)gl;
		// delete caches
		GGLTemporaryCache.clearCache();
		if(GmService.font != null)
			GmService.font.delete(gl11);
		
		// content loader
		GmService.contentLoader.onSurfaceChanged();
		
		// camera
		GmService.projection.onSurfaceChanged(gl, width, height);
		GmService.projection.registDefaultOrth(0);
		GmService.projection.registDefaultFrustum(0);
		GmService.projection.registDefaultPerspective(0);
		GmService.projection.registDefaultLookAt(0);
		
		// loader onload
		GmService.sceneController.onSurfaceChanged(getContext().getApplicationContext(), gl11, null);
		
		// set scene
		if(!GmStatus.isInitialized) {
			GmService.sceneController.setScene(gl11, GmStatus.startClazz, GmStatus.startBundle);
			GmStatus.isInitialized = true;
		}
		
		// font
		GmService.font.load(gl11);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		GL11 gl11 = (GL11)gl;
		fpsChecker.update();
		update(gl11);
		draw(gl11);

		delayRequestRender();
	}
	
	protected void update(GL11 gl11){
		// change scene
		GmService.sceneController.update();
		
		Scene scene = GmService.sceneController.getCurrentScene();
		// process event
		GmService.motionPool.checkTapEvents(scene);
		
		// scene update
		scene.update(gl11);
	}
	
	protected void draw(GL11 gl11) {
		Scene scene = GmService.sceneController.getCurrentScene();
		gl11.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl11.glClearColor(0, 0, 0, 0);
		GmService.projection.setOrth(gl11, 0);
		GmService.font.drawText(gl11, "fps:"+fpsChecker.getFPS(), -1.4f, 0.9f, 0.2f);
		
		scene.draw(gl11);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(gestureDetector.onTouchEvent(event))
			return true;
		
		return super.onTouchEvent(event);
	}
}
