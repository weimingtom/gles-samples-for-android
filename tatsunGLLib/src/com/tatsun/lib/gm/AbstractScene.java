package com.tatsun.lib.gm;

import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public abstract class AbstractScene implements Scene {
	private static final String TAG = "AbstractScene";
	protected Context context;
	protected int frame;
	
	public void onCreate(Context context, Bundle bundle) {
		this.context = context;
		frame = 0;
	}
	
	
	@Override
	public void onSceneChangedFromMe(GL11 gl, Bundle bundle, Scene to) {
		
	}
	
	@Override
	public void onSceneChangedToMe(GL11 gl, Bundle bundle, Scene from) {
		
	}
	
	@Override
	public SceneType getSceneType() {
		return SceneType.STAGE;
	}

	/**
	 * 
	 */
	@Override
	public void onDoubleTap(float x, float y) {
		Log.v(TAG, "onDoubleTap [x:" + x + ",y:" + y + "]");
	}

	@Override
	public void onSingleTapConfirmed(float x, float y) {
		Log.v(TAG, "onSingleTapConfirmed [x:" + x + ",y:" + y + "]");
	}

	@Override
	public void onDown(float x, float y) {
		Log.v(TAG, "onDown [x:" + x + ",y:" + y + "]");
	}

	@Override
	public void onFling(float x, float y, float x2, float y2, float x3, float y3) {
		Log.v(TAG, "onFling f[x:" + x + ",y:" + y + "] t[x:" + x2 + ",y:" + y2 + "] v[x:" + x3 + ",y:" + y3 + "]");
	}

	@Override
	public void onLongPress(float x, float y) {
		Log.v(TAG, "onLongPress [x:" + x + ",y:" + y + "]");
	}

	@Override
	public void onScroll(float x, float y, float x2, float y2, float x3, float y3) {
		Log.v(TAG, "onScroll f[x:" + x + ",y:" + y + "] t[x:" + x2 + ",y:" + y2 + "] d[x:" + x3 + ",y:" + y3 + "]");
	}

	@Override
	public void onSingleTapUp(float x, float y) {
		Log.v(TAG, "onSingleTapUp [x:" + x + ",y:" + y + "]");
	}
}
