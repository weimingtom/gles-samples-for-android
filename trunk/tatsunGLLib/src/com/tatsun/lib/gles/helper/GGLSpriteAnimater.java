package com.tatsun.lib.gles.helper;

import com.tatsun.lib.gles.GGLConst;

public class GGLSpriteAnimater {
	private float patternInterval;
	private int[][] patterns;
	private int patternLength;
	
	public GGLSpriteAnimater() {
	}
	
	public void registPattern(int[] pattern) {
		int[][] ptn = new int[1][];
		ptn[0] = pattern;
		resistPatterns(ptn);
	}
	
	public void resistPatterns(int[][] patterns) {
		this.patterns = patterns;
		this.patternLength = patterns[0].length;
		this.patternInterval = (float)patterns[0].length / (float)GGLConst.FPS;
	}
	
	/**
	 *
	 * @param frame
	 * @return
	 */
	public int getCurrentUnit(int frame) {
		return patterns[0][((int)(frame * patternInterval)) % patternLength];
	}
	
	public int getCurrentUnit(int frame, int patternSet) {
		return patterns[patternSet][((int)(frame * patternInterval)) % patternLength];
	}

}
