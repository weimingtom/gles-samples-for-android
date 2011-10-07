package com.tatsun.marisacorps.title;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.GGLIBinderObject;
import com.tatsun.lib.gles.GGLTexCoordBufferObject;
import com.tatsun.lib.gles.GGLTexture;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gles.helper.GGLCamera;
import com.tatsun.lib.gm.CameraPlayer;
import com.tatsun.lib.gm.GmService;
import com.tatsun.lib.gm.Layer;
import com.tatsun.marisacorps.GLResources;
import com.tatsun.marisacorps.MyGlobals;

import android.os.Bundle;

public class FightLayer implements Layer {
	public CameraPlayer cameraPlayer;
	GGLTexture texture;
	GGLVertexBufferObject floorVbo;
	GGLTexCoordBufferObject floorTbo;
	GGLIBinderObject floorIbo;
	FightManager fightManager;

	private FightUnit[] units = new FightUnit[FightConst.FIGHT_UNIT_COUNT * 2];
	
	public FightLayer() {
		for(int i= 0; i < units.length; ++i) {
			units[i] = new FightUnit();
		}
		fightManager = new FightManager();
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		texture = GLResources.textures[GLResources.TEXTURE_BACKGROUNDS_01];
		floorVbo = (GGLVertexBufferObject)GLResources.vbo[GLResources.VBO_FLOOR_01];
		floorTbo = (GGLTexCoordBufferObject)GLResources.vbo[GLResources.TBO_BACK_01];
		floorIbo = GLResources.vbo[GLResources.IBO_FLOOR_01];

	}
	
	public void fightInit() {
		fightManager.initUnits(units, FightManager.POSITIONS_LEFT, cameraPlayer.camera, 0, 30);
		fightManager.initUnits(units, FightManager.POSITIONS_RIGHT_UP, cameraPlayer.camera, 30, 30);
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
		GmService.projection.setFrustum(gl11, MyGlobals.PROJ_FRUS_01);
		gl11.glEnable(GL10.GL_DEPTH_TEST);
		gl11.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		cameraPlayer.applay(gl11);
		
		drawFloor(gl11);
		
		gl11.glEnable(GL10.GL_ALPHA_TEST);
		gl11.glAlphaFunc(GL10.GL_EQUAL, 1.0f);
		for(FightUnit unit : units) {
			if(unit.isActive)
				unit.draw(gl11, frame);
		}
		gl11.glDisable(GL10.GL_ALPHA_TEST);

		gl11.glDisable(GL10.GL_DEPTH_TEST);
	}

	
	private void drawFloor(GL11 gl11) {
		texture.bind(gl11);
		floorVbo.bind(gl11);
		floorTbo.bind(gl11, 7);
		for(int j = 0; j < 5; ++j) {
			float row = -8 + j * 4;
			for(int i = 0; i < 15; ++i) {
				float col = -29 + i * 4;
				gl11.glPushMatrix();
				{
					gl11.glTranslatef(col, 0, row);
					floorIbo.draw(gl11);
				}
				gl11.glPopMatrix();
			}
		}
		gl11.glPopMatrix();
		floorVbo.unbind(gl11);
		floorTbo.unbind(gl11);
		texture.unbind(gl11);
	}
}
