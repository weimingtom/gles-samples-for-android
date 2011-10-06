package com.tatsun.lib.gm;

import com.tatsun.lib.gles.helper.GGLCamera;

public class CameraCommand {
	public static final int WAIT_COMMAND = 0;
	public static final int TRANSLATE_COMMAND = 1;
	public static final int ROTATE_COMMAND = 2;
	public static final int SCALE_COMMAND = 3;
	public static final int QUARTANION_COMMAND = 4;
	public int tickFrame;
	public ListCommand[] list = null;
	
	public CameraCommand() {
		
	}
	
	public CameraCommand(String line) {
		int i = 0;
		String[] commands = line.split(",");
		tickFrame = Integer.parseInt(commands[i++]);
		
		if(commands.length <= 1) {
			list = new ListCommand[0];
			return;
		}
		list = new ListCommand[commands.length - 1];


		for(; i < commands.length; ++i) {
			int idx = 0;
			String[] values = commands[i].trim().split(" "); 
			
			int type = Integer.parseInt(values[idx++]);
			switch(type) {
			case WAIT_COMMAND:
				list[i-1] = new ListCommand(type, null);
				break;
			case TRANSLATE_COMMAND:
				list[i-1] = new ListCommand(type, new float[]{ Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), });
				break;
			case ROTATE_COMMAND:
				list[i-1] = new ListCommand(type, new float[]{ Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), });
				break;
			case SCALE_COMMAND:
				list[i-1] = new ListCommand(type, new float[]{ Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), });
				break;
			case QUARTANION_COMMAND:
				list[i-1] = new ListCommand(type, new float[]{
							Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]),
							Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), 
							Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), 
							Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), Float.parseFloat(values[idx++]), 
						});
			}
		}
	}
	
	public void play(GGLCamera camera) {
		for(ListCommand cs : list) {
			float[] p = cs.p;
			switch((int)cs.type) {
			case TRANSLATE_COMMAND:
				camera.translate(p[0], p[1], p[2]);
				break;
			case ROTATE_COMMAND:
				camera.rotate(p[0], p[1], p[2], p[3]);
				break;
			case SCALE_COMMAND:
				camera.scale(p[0], p[1], p[2]);
				break;
//			case QUARTANION_COMMAND:
//				camera.multiply(p);
//				break;
			}
		}
	}
	
	private static final class ListCommand {
		int type;
		float[] p;
		
		public ListCommand(int type, float[] p) {
			this.type = type;
			this.p = p;
		}
	}
}
