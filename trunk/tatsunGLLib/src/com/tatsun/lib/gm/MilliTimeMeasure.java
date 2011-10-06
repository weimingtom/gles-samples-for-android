package com.tatsun.lib.gm;

public class MilliTimeMeasure {
	private long lastTime;
	
	public MilliTimeMeasure() {
		reset();
	}
	
	private long getCurrent() {
		return System.currentTimeMillis();
	}
	
	public void reset() {
		lastTime = getCurrent();
	}
	
	public long getElapsed() {
		return getCurrent() - lastTime;
	}
}
