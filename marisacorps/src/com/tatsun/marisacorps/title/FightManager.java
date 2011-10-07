package com.tatsun.marisacorps.title;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tatsun.lib.gles.helper.GGLCamera;
import com.tatsun.lib.gm.CameraPlayer;
import com.tatsun.lib.math.Vector3f;
import com.tatsun.marisacorps.data.UnitData;

public class FightManager {
	public static final int POSITIONS_LEFT = 0;
	public static final int POSITIONS_RIGHT = 1;
	public static final int POSITIONS_LEFT_UP = 2;
	public static final int POSITIONS_RIGHT_UP = 3;
	public static final int POSITIONS_LEFT_SINGLE = 4;
	public static final int POSITIONS_RIGHT_SINGLE = 5;
	public static final int[] POSITION_DIRECTION = {
		FightConst.DIRECTION_RIGHT, FightConst.DIRECTION_LEFT, 
		FightConst.DIRECTION_RIGHT, FightConst.DIRECTION_LEFT, 
		FightConst.DIRECTION_RIGHT, FightConst.DIRECTION_LEFT, 
		};
	public static final List<List<Vector3f>> positions;
	
	private static final float l_l = -25;
	private static final float l_r = 25;
	private static final float l_t = -7.5f;
	private static final float w_o = 3;
	private static final float h = 1;
	private static final float fh = 5;
	private static final float sh = 3;
	
	static{
		positions = new ArrayList<List<Vector3f>>(6);
		List<Vector3f> ps0 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		List<Vector3f> ps1 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		List<Vector3f> ps2 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		List<Vector3f> ps3 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		List<Vector3f> ps4 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		List<Vector3f> ps5 = new ArrayList<Vector3f>(FightConst.FIGHT_UNIT_COUNT);
		positions.add(ps0);
		positions.add(ps1);
		positions.add(ps2);
		positions.add(ps3);
		positions.add(ps4);
		positions.add(ps5);
		for(int j = 0; j < FightConst.FIGHT_UNIT_ROW; ++j) {
			float z = l_t + j * w_o;
			for(int i = 0; i < FightConst.FIGHT_UNIT_COL; ++i) {
				ps0.add(new Vector3f(l_l + i * w_o, h, z));
				ps2.add(new Vector3f(l_r - i * w_o, fh, z));
			}
		}
		for(int j = 0; j < FightConst.FIGHT_UNIT_ROW; ++j) {
			float z = l_t + j * w_o;
			for(int i = 0; i < FightConst.FIGHT_UNIT_COL; ++i) {
				ps1.add(new Vector3f(l_r - i * w_o, h, z));
				ps3.add(new Vector3f(l_r - i * w_o, fh, z));
			}
		}
		
		ps4.add(new Vector3f(
				l_l + (w_o * FightConst.FIGHT_UNIT_COL) / 2,
				sh,
				l_t + (w_o * FightConst.FIGHT_UNIT_ROW) / 2));
		for(int i = 1; i < FightConst.FIGHT_UNIT_COUNT; ++i) {
			ps4.add(ps4.get(i-1));
		}
		ps5.add(new Vector3f(
				l_r - (w_o * FightConst.FIGHT_UNIT_COL) / 2,
				sh,
				l_t + (w_o * FightConst.FIGHT_UNIT_ROW) / 2));
		for(int i = 1; i < FightConst.FIGHT_UNIT_COUNT; ++i) {
			ps5.add(ps5.get(i-1));
		}
	}
	
	public FightManager() {
	}
	
	public void initManager() {
		
	}

	public void initUnits(FightUnit[] units, int positionPattern, GGLCamera camera, int offset, int count) {
		List<Vector3f> pos = positions.get(positionPattern);
		Collections.shuffle(pos);
		for(int j = 0; j < count; ++j) {
			FightUnit unit = units[j+offset];
			unit.position.set(pos.get(j));
			unit.isActive = true;
			unit.startDir = unit.patternDir = POSITION_DIRECTION[positionPattern];
			unit.camera = camera;
			unit.init();
		}
	}
	
	protected void setInitPosition(FightUnit unit, UnitData unitData) {
		
	}
}
