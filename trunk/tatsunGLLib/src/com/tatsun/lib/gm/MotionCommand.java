package com.tatsun.lib.gm;

public class MotionCommand {
	private static final int STOCK_SIZE = 256;
	private static final MotionCommand[] stock;
	private static int currentIndex = 0;
	static {
		stock = new MotionCommand[STOCK_SIZE];
		for(int i = 0; i < STOCK_SIZE; ++i) {
			stock[i] = new MotionCommand();
		}
	}
	
	private static MotionCommand searchReadyCommand() {
		int idx = currentIndex;
		for(int i = 0; i < STOCK_SIZE; ++i) {
			if(stock[idx].isRecycled) {
				stock[idx].isRecycled = false;
				idx = (idx + 1) % STOCK_SIZE;
				return stock[idx];
			}
			idx = (idx + 1) % STOCK_SIZE;
		}
		
		return null;
	}

	public int commandType;
	public boolean isRecycled;
	public float x, y;
	public float x2, y2;
	public float x3, y3;
	
	
	private MotionCommand() {
		isRecycled = true;
	}
	
	public static MotionCommand obtain(MotionCommand motionCommand) {
		MotionCommand lhs = searchReadyCommand();
		if(lhs == null)
			return lhs;
		
		lhs.commandType = motionCommand.commandType;
		lhs.x = motionCommand.x; lhs.y = motionCommand.y;
		lhs.x2 = motionCommand.x2; lhs.y2 = motionCommand.y2;
		lhs.x3 = motionCommand.x3; lhs.y3 = motionCommand.y3;
		return lhs;
	}
	
	public static MotionCommand obtain(int commandType, float x, float y) {
		MotionCommand lhs = searchReadyCommand();
		if(lhs == null)
			return lhs;
		
		lhs.commandType = commandType;
		lhs.x = x; lhs.y = y;
		return lhs;
	}
	
	public static MotionCommand obtain(int commandType, float x, float y, float x2, float y2) {
		MotionCommand lhs = searchReadyCommand();
		if(lhs == null)
			return lhs;
		
		lhs.commandType = commandType;
		lhs.x = x; lhs.y = y;
		lhs.x2 = x2; lhs.y2 = y2;
		return lhs;
	}

	public static MotionCommand obtain(int commandType, float x, float y, float x2, float y2, float x3, float y3) {
		MotionCommand lhs = searchReadyCommand();
		if(lhs == null)
			return lhs;
		
		lhs.commandType = commandType;
		lhs.x = x; lhs.y = y;
		lhs.x2 = x2; lhs.y2 = y2;
		lhs.x3 = x3; lhs.y3 = y3;
		return lhs;
	}
}
