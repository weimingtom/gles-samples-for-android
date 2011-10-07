package com.tatsun.lib.gm;



public class FPSChecker {

	private MilliTimeMeasure milliTimer;
	private int fpsCount;
	private int tickCount;
	
	public FPSChecker() {
		milliTimer = new MilliTimeMeasure();
	}
	
	public void update() {
		if(milliTimer.getElapsed() > 1000) {
			milliTimer.reset();
			fpsReset();
			//Log.i("FPS", "fps:" + fpsCount);
		}
		tickCount++;
	}
	
	public int getFPS() {
		return fpsCount;
	}
	
	public void fpsReset() {
		fpsCount = tickCount;
		tickCount = 0;
	}
}
