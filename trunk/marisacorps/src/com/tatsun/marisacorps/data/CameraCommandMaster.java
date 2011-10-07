package com.tatsun.marisacorps.data;

import com.tatsun.lib.gm.CameraCommand;

public class CameraCommandMaster {
	public static final CameraCommand[][] commands = new CameraCommand[][]{
		{ 
			new CameraCommand("30"),
			new CameraCommand("30, 1 0.6 0.2 0.2"),
			new CameraCommand("120"),
			new CameraCommand("60, 1 -0.6 0 0"),
			new CameraCommand("120"),
			new CameraCommand("30, 1 0.6 -0.2 -0.2"),
			new CameraCommand("30"),
		},
		{
			
		},
	};
}
