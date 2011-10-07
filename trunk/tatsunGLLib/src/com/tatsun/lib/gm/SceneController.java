package com.tatsun.lib.gm;

import java.util.Stack;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;


public class SceneController {
	protected LoadingScene loadingScene;
	protected Scene currentScene;
	private Scene nextScene;
	protected Stack<Scene> hideStack;
	protected boolean hasHideStack;

	/**
	 * 
	 * @param context
	 * @param sceneContainer
	 */
	public SceneController() {
		hideStack = new Stack<Scene>();
		hasHideStack = false;
	}

	/**
	 * 
	 * @param gl
	 * @param bundle
	 */
	public void onSurfaceChanged(Context context, GL11 gl, Bundle bundle) {
		LoadingScene loadingScene = (LoadingScene)GmService.sceneContainer.getLoadingScene(context, bundle);
		loadingScene.onLoad(gl, bundle);
		this.loadingScene = loadingScene;
	}
	
	/**
	 * 
	 * @return
	 */
	public LoadingScene getLoadingScene() {
		return this.loadingScene;
	}
	
	/**
	 * 
	 * @return
	 */
	public Scene getCurrentScene() {
		return currentScene;
	}
	
	/**
	 * 
	 * @param gl
	 * @param sceneId
	 * @param bundle
	 */
	@SuppressWarnings("rawtypes")
	public void setScene(GL11 gl, Class sceneClazz, Bundle bundle) {
		hasHideStack = false;
		if(currentScene != null)
			currentScene.onPause();
		
		loadingScene.onFocus(gl, bundle);
		currentScene = loadingScene;
		loadingScene.loadScene(gl, sceneClazz, bundle);
		
		clearHideStack();
	}
	
	/**
	 * 
	 * @param newScene
	 */
	@SuppressWarnings("rawtypes")
	public void pushScene(GL11 gl, Class sceneClazz, Bundle bundle) {
		if(currentScene != null) {
			currentScene.onPause();
			hideStack.push(currentScene);
			hasHideStack = true;
		}
		
		loadingScene.onFocus(gl, bundle);
		currentScene = loadingScene;
		loadingScene.loadScene(gl, sceneClazz, bundle);
	}
	
	/**
	 * 
	 */
	public void popScene(GL11 gl, Bundle bundle) {
		if(currentScene != null) {
			currentScene.onPause();
		}
		
		loadingScene.onFocus(gl, bundle);
		currentScene = loadingScene;
		loadingScene.loadSceneByInstance(gl, hideStack.pop(), bundle);
		
		if(this.hideStack.size() == 0)
			hasHideStack = false;
	}
	
	/**
	 * clearStack
	 */
	private void clearHideStack() {
		hasHideStack = false;
		
		this.hideStack.clear();
	}
	
	/**
	 * 
	 * @param scene
	 */
	public void registChangeScene(Scene scene) {
		this.nextScene = scene;
	}
	
	/**
	 * change scene
	 */
	public void update() {
		if(nextScene != null) {
			GmService.motionPool.clear();
			currentScene = nextScene;
			nextScene = null;
		}
			
	}
	
	public void reflesh() {
		
	}
}
