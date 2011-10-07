package com.tatsun.lib.gm;

import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class SceneContainer {
	private static final String TAG = "SceneContainer";
	protected Map<String, Scene> scenes;
	protected LoadingScene loadingScene;
	
	public SceneContainer() {
		scenes = new TreeMap<String, Scene>();
	}
	
	@SuppressWarnings("rawtypes")
	public Scene get(Class clazz, Context context, Bundle bundle) {
		if(!scenes.containsKey(clazz.getName())) {
			Scene scene = createStage(clazz);
			scene.onCreate(context, bundle);
			scenes.put(clazz.getName(), scene);

			return scene;
		}
		return scenes.get(clazz.getName());
	}
	
	public LoadingScene getLoadingScene(Context context, Bundle bundle) {
		if(loadingScene == null) {
			loadingScene = new LoadingScene();
			loadingScene.onCreate(context, bundle);
		}
		return loadingScene;
	}
	
	@SuppressWarnings("rawtypes")
	protected Scene createStage(Class clazz) {
		try {
			return (Scene) clazz.newInstance();
		} catch (IllegalAccessException e) {
			Log.w(TAG, e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			Log.w(TAG, e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}
