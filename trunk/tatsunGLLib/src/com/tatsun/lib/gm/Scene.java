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

	void onCreate(Context context, Bundle bundle);
	
	void onLoad(GL11 gl, Bundle bundle);
	
	void onFocus(GL11 gl, Bundle bundle);
	
	void onPause();
	
	void onDestroy(GL11 gl);
	
	void onDoubleTap(float x, float y);

	void onSingleTapConfirmed(float x, float y);

	void onDown(float x, float y);

	void onFling(float fx, float fy, float tx, float ty, float vx, float vy);

	void onLongPress(float x, float y);

	void onScroll(float fx, float fy, float tx, float ty, float dx, float dy);
	
	void onSingleTapUp(float x, float y);
}
