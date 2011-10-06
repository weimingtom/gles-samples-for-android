package com.tatsun.lib.gm;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;

import com.tatsun.lib.gles.helper.GGLProjectionCache;

public class LoadingScene extends AbstractScene {
	private static final String TAG = "LoadingScene";
	public static String LOADING_STRING = "now loading";
	public static float FONT_SIZE = 0.1f;
	private float loadingStringSize;
	private LoadingView loadingView;
	
	public LoadingScene() {
	}

	public void loadScene(final GL11 gl, final Class sceneClazz, final Bundle bundle, final SceneController sceneController) {
		// TBD
//		ContentLoader.Future future = GmService.contentLoader.offer(new Runnable(){
//			@Override
//			public void run() {
//				Scene scene = GmService.sceneContainer.get(sceneClazz, context, bundle);
//				scene.onLoad(gl, bundle);
//				scene.onFocus(gl, bundle);
//				sceneController.registChangeScene(scene);
//			}
//		});
		Scene scene = GmService.sceneContainer.get(sceneClazz, context, bundle);
		scene.onLoad(gl, bundle);
		scene.onFocus(gl, bundle);
		sceneController.registChangeScene(scene);
	}

	public void loadSceneByInstance(final GL11 gl, final Scene scene, final Bundle bundle, final SceneController sceneController) {
		ContentLoader.Future future = GmService.contentLoader.offer(new Runnable(){
			@Override
			public void run() {
				scene.onLoad(gl, bundle);
				scene.onFocus(gl, bundle);
				sceneController.registChangeScene(scene);
			}
		});
	}

	@Override
	public void update(GL11 gl) {
		// check load finish
		
		// move scene
		
	}

	@Override
	public void draw(GL11 gl11) {
		// draw now loading
		GGLProjectionCache.OrthParam orthParam = GmService.projection.getOrthPram(0);
		
		GmService.projection.setOrth(gl11, 0);
		GmService.font.drawText(gl11, "now loading", orthParam.r - loadingStringSize, orthParam.b + FONT_SIZE, FONT_SIZE);
	}

	public void setLoadingView(LoadingView loadingView) {
		this.loadingView = loadingView;
	}
	
	@Override
	public void onCreate(Context context, Bundle bundle) {
		super.onCreate(context, bundle);
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		loadingStringSize = Misc.calcCharactorSize(LOADING_STRING) * FONT_SIZE;
		// load resources
	}

	@Override
	public void onFocus(GL11 gl, Bundle bundle) {
	}
	
	@Override
	public void onPause() {
	}

	@Override
	public void onDestroy(GL11 gl) {
		// delete load resources
		
	}

	@Override
	public SceneType getSceneType() {
		return SceneType.LOADING;
	}
}
