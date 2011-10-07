package com.tatsun.lib.gm;

import javax.microedition.khronos.opengles.GL10;

import com.tatsun.lib.gles.helper.GGLCamera;

public class CameraPlayer {
	private int idx;
	private int frame;
	private boolean isActive;
	public CameraCommand[] commands;
	public GGLCamera camera;
	private CameraCommand currentCommand;
	
	public CameraPlayer(GGLCamera camera) {
		this.camera = camera;
		this.isActive = false;
	}
	
	public void update() {
		if(isActive && commands != null) {
			if(frame <= 0) {
				if(idx >= commands.length) {
					isActive = false;
					return;
				}
				currentCommand = commands[idx++];
				frame = currentCommand.tickFrame;
			}
			currentCommand.play(camera);
			--frame;
		} else {
			isActive = false;
		}
	}
	
	public void applay(GL10 gl) {
		camera.applayMatrix(gl);
	}
	
	public void setCammera(GGLCamera camera) {
		this.camera = camera;
	}
	
	public void setCommands(CameraCommand[] commands) {
		this.commands = commands;
		reset(0);
	}
	
	public void start() {
		isActive = true;
	}
	
	public void stop() {
		isActive = false;
	}
	
	public void reset(int pos) {
		frame = 0;
		idx = pos;
	}
}
