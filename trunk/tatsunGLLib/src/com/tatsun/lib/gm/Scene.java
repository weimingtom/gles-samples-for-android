package com.tatsun.lib.gm;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;

public interface Scene {
	enum SceneType {
		LOADING,
		STAGE,
	}
	
	SceneType getSceneType();
	
	void update(GL11 gl);

	void draw(GL11 gl);

	/**
	 * on create
	 * 
	 * @param context
	 * @param bundle
	 */
	void onCreate(Context context, Bundle bundle);
	
	/**
	 * for load gl resource
	 * 
	 * @param gl
	 * @param bundle
	 */
	void onLoad(GL11 gl, Bundle bundle);
	
	/**
	 * on focus
	 * 
	 * @param gl
	 * @param bundle
	 */
	void onFocus(GL11 gl, Bundle bundle);
	
	/**
	 * change page event from target
	 * 
	 * @param gl
	 * @param bundle
	 * @param to
	 */
	void onSceneChangedFromMe(GL11 gl, Bundle bundle, Scene to);

	/**
	 * change page event to target
	 * 
	 * @param gl
	 * @param bundle
	 * @param from
	 */
	void onSceneChangedToMe(GL11 gl, Bundle bundle, Scene from);

	/**
	 * pause event
	 */
	void onPause();
	
	/**
	 * destroy event
	 * 
	 * @param gl
	 */
	void onDestroy(GL11 gl);
	
	void onDoubleTap(float x, float y);

	void onSingleTapConfirmed(float x, float y);

	void onDown(float x, float y);

	void onFling(float fx, float fy, float tx, float ty, float vx, float vy);

	void onLongPress(float x, float y);

	void onScroll(float fx, float fy, float tx, float ty, float dx, float dy);
	
	void onSingleTapUp(float x, float y);
}
