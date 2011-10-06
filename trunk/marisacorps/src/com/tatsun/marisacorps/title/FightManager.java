package com.tatsun.marisacorps.title;

import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.helper.GGLCamera;
import com.tatsun.lib.gm.Layer;

import android.os.Bundle;

public class FightManager implements Layer {
	public GGLCamera camera;
	
	private FightUnit[] units = new FightUnit[FightConst.FIGHT_UNIT_COUNT * 2];
	
	public FightManager() {
		for(int i= 0; i < units.length; ++i) {
			units[i] = new FightUnit();
		}
	}
	
	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		int idx = 0;
		{
			float sx = -25f, sy = 1f, sz = -7.5f;
			for(int j = 0; j < 6; ++j) {
				float z = sz + 3f * j;
				for(int i = 0; i < 5; ++i) {
					float x = sx + 2f * i;
					FightUnit unit = units[idx++];
					unit.position.set(x, sy, z);
					unit.isActive = true;
					unit.startDir = unit.patternDir = FightConst.DIRECTION_RIGHT;
					unit.camera = camera;
					unit.init();
				}
			}
		}

		{
			float sx = 25f, sy = 1f, sz = -7.5f;
			for(int j = 0; j < 6; ++j) {
				float z = sz + 3f * j;
				for(int i = 0; i < 5; ++i) {
					float x = sx - 2f * i;
					FightUnit unit = units[idx++];
					unit.position.set(x, sy, z);
					unit.isActive = true;
					unit.startDir = unit.patternDir = FightConst.DIRECTION_LEFT;
					unit.camera = camera;
					unit.init();
				}
			}
		}

	}
	
	@Override
	public void update(GL11 gl11, int frame) {
		for(FightUnit unit : units) {
			if(unit.isActive)
				unit.update(frame);
		}
	}
	
	@Override
	public void draw(GL11 gl11, int frame) {
		for(FightUnit unit : units) {
			if(unit.isActive)
				unit.draw(gl11, frame);
		}
	}


}
