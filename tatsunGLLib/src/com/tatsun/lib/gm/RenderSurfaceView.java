package com.tatsun.lib.gm;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.tatsun.lib.gles.GGLTemporaryCache;

public class RenderSurfaceView extends GGLSurfaceView {
	
	private LoadingView loadingView;
	private GestureDetector gestureDetector;
	private FPSChecker fpsChecker = new FPSChecker();
		
	public RenderSurfaceView(Context context) {
		super(context);
		// debagging. call before setRenderer method.
		//setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);

		gestureDetector = new GestureDetector(GmService.motionPool);
		loadingView = new LoadingView(context);
		//GmService.sceneController.getLoadingScene().setLoadingView(loadingView);

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
	
	/**
	 * 
	 * @return
	 */
	public int getFps() {
		return fpsChecker.getFPS();
	}
	
	@Override
	protected void onSurfaceChanged(GL10 gl, int width, int height) {
		GL11 gl11 = (GL11)gl;
		
		// delete caches
		GGLTemporaryCache.clearCache();

		// font
		if(GmService.font != null)
			GmService.font.delete(gl11);
		GmService.font.load(gl11);
		
		// content loader
		GmService.contentLoader.onSurfaceChanged();
		
		// camera
		GmService.projection.onSurfaceChanged(gl11, width, height);
		GmService.projection.registDefaultOrth(0);
		GmService.projection.registDefaultFrustum(0);
		GmService.projection.registDefaultPerspective(0);
		GmService.projection.registDefaultLookAt(0);
		
//		// resource load
//		if(GmService.glResources != null) {
//			GmService.glResources.load(getContext().getApplicationContext(), gl11);
//		}
		
		// loader onload
		GmService.sceneController.onSurfaceChanged(getContext().getApplicationContext(), gl11, null);
		
		// set scene
		if(!GmStatus.isInitialized) {
			GmService.sceneController.setScene(gl11, GmStatus.startClazz, GmStatus.startBundle);
			GmStatus.isInitialized = true;
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	}
	
	@Override
	public void onRepaint() {
		GL11 gl11 = (GL11)gl10;
		fpsChecker.update();
		update(gl11);
		draw(gl11);

		swapBuffers();
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
