package com.tatsun.lib.gm;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MotionPool extends SimpleOnGestureListener {
	private static final String TAG = "MotionPool";
	private static final int FLIP_SIZE = 2;
	private static final int POOL_SIZE = 64;

	private static final int MOTION_COMMAND_onDoubleTap = 10001;
	private static final int MOTION_COMMAND_onSingleTapConfirmed = 10002;
	private static final int MOTION_COMMAND_onDown = 10003;
	private static final int MOTION_COMMAND_onFling = 10004;
	private static final int MOTION_COMMAND_onLongPress = 10005;
	private static final int MOTION_COMMAND_onScroll = 10006;
	private static final int MOTION_COMMAND_onSingleTapUp = 10007;
	
	private MotionCommand[][] pool;
	private int currentSide;
	private int currentIndex;
	
	public MotionPool() {
		pool = new MotionCommand[FLIP_SIZE][POOL_SIZE];
		currentSide = 0;
		currentIndex = 0;
	}
	
	public MotionCommand[] getMotions() {
		synchronized (pool) {
			MotionCommand[] ret = pool[currentSide++];
			currentSide %= FLIP_SIZE;
			currentIndex = 0;
			clearPool(currentSide);
			
			return ret;
		}
	}
	
	private void putMotion(MotionCommand command) {
		if(command == null)
			return;
		pool[currentSide][currentIndex++] = command;
	}
	
	public void clear() {
		synchronized (pool) {
			clearPool(currentSide);
			currentIndex = 0;
		}
	}
	
	private void clearPool(int side) {
		for(int i = 0; i < POOL_SIZE; ++i) {
			if(pool[side][i] == null)
				break;
			pool[side][i].isRecycled = true;
			pool[side][i] = null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public final boolean onDoubleTap(MotionEvent e) {
		synchronized (pool) {
			putMotion(MotionCommand.obtain(MOTION_COMMAND_onDoubleTap, e.getX(), e.getY()));
		}
		Log.v(TAG, "onDoubleTap e.x:" + e.getX() + ", e.y:" + e.getY());
		return true;
	}

	@Override
	public final boolean onSingleTapConfirmed(MotionEvent e) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onSingleTapConfirmed, e.getX(), e.getY()));
		}
		Log.v(TAG, "onSingleTapConfirmed e.x:" + e.getX() + ", e.y:" + e.getY());
		return true;
	}

	@Override
	public final boolean onDown(MotionEvent e) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onDown, e.getX(), e.getY()));
		}
		Log.v(TAG, "onDown [x:" + e.getX() + ",y:" + e.getY() + "]");
		return true;
	}

	@Override
	public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onFling, e1.getX(), e1.getY(), e2.getX(), e2.getY(), velocityX, velocityY));
		}
		Log.v(TAG, "onFling f[x:" + e1.getX() + ",y:" + e1.getY() + "] t[x:" + e2.getX() + ",y:" + e2.getY() + "] v[x:" + velocityX + ",y:" + velocityX + "]");
		return true;
	}

	@Override
	public final void onLongPress(MotionEvent e) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onLongPress, e.getX(), e.getY()));
		}
		Log.v(TAG, "onLongPress [x:" + e.getX() + ",y:" + e.getY() + "]");
	}

	@Override
	public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onScroll, e1.getX(), e1.getY(), e2.getX(), e2.getY(), distanceX, distanceY));
		}
		Log.v(TAG, "onScroll f[x:" + e1.getX() + ",y:" + e1.getY() + "] t[x:" + e2.getX() + ",y:" + e2.getY() + "] d[x:" + distanceX + ",y:" + distanceY + "]");
		return true;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		synchronized (pool) {
			if(currentIndex < POOL_SIZE)
				putMotion(MotionCommand.obtain(MOTION_COMMAND_onSingleTapUp, e.getX(), e.getY()));
		}
		Log.v(TAG, "onSingleTapUp [x:" + e.getX() + ",y:" + e.getY() + "]");
		return false;
	}
	
	/**
	 * 
	 * @param scene
	 */
	public void checkTapEvents(Scene scene) {
		MotionCommand[] commands = getMotions();
		if(scene == null)
			return;
		                  
		for(int i = 0; i < commands.length; ++i) {
			if(commands[i] == null)
				break;
			checkTapEvent(scene, commands[i]);
			commands[i].isRecycled = true;
			commands[i] = null;
		}
	}
	
	private void checkTapEvent(Scene scene, MotionCommand mc) {
		
		switch(mc.commandType) {
		case MOTION_COMMAND_onDoubleTap:
			scene.onDoubleTap(mc.x, mc.y);
			break;
		case MOTION_COMMAND_onSingleTapConfirmed:
			scene.onSingleTapConfirmed(mc.x, mc.y);
			break;
		case MOTION_COMMAND_onDown:
			scene.onDown(mc.x, mc.y);
			break;
		case MOTION_COMMAND_onFling:
			scene.onFling(mc.x, mc.y, mc.x2, mc.y2, mc.x3, mc.y3);
			break;
		case MOTION_COMMAND_onLongPress:
			scene.onLongPress(mc.x, mc.y);
			break;
		case MOTION_COMMAND_onScroll:
			scene.onScroll(mc.x, mc.y, mc.x2, mc.y2, mc.x3, mc.y3);
			break;
		case MOTION_COMMAND_onSingleTapUp:
			scene.onSingleTapUp(mc.x, mc.y);
			break;
		}
	}
}
