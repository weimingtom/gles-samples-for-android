package com.tatsun.marisacorps.title;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.GGLIBinderObject;
import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLSpriteCoordFactory;
import com.tatsun.lib.gles.GGLTexCoordBufferObject;
import com.tatsun.lib.gles.GGLTexture;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.lib.gles.helper.GGLCamera;
import com.tatsun.lib.gles.helper.GGLSpriteAnimater;
import com.tatsun.lib.gm.AbstractScene;
import com.tatsun.lib.gm.CameraPlayer;
import com.tatsun.lib.gm.GmService;
import com.tatsun.lib.math.Matrix4f;
import com.tatsun.marisacorps.GLResources;
import com.tatsun.marisacorps.MyGlobals;
import com.tatsun.marisacorps.data.CameraCommandMaster;
import com.tatsun.marisacorps.scenes.FightScene;
import com.tatsun.marisacorps.R;

public class TitleScene extends AbstractScene {
	private static final String TAG = "TitleScene";
	
	CameraPlayer cameraPlayer;
	GGLCamera camera = new GGLCamera();
	FightManager fightManager = new FightManager();
	
	public TitleScene() {
		cameraPlayer = new CameraPlayer(camera);
	}

	@Override
	public void update(GL11 gl11) {
		++frame;
		if(frame % 600 == 599) {
			cameraPlayer.reset(0);
			cameraPlayer.start();
		}

		cameraPlayer.update();
		
		fightManager.update(gl11, frame);
	}

	@Override
	public void draw(GL11 gl) {
		//gl.glEnable(GL10.GL_CULL_FACE);
		
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-1.5f, 1.5f, -1, 1, 1f, 100);
		
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		drawBack(gl);
		
		cameraPlayer.applay(gl);
	
		drawFloor(gl);
		drawUnits(gl);
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_DEPTH_TEST);
	}

	private void drawBack(GL11 gl11) {
		GGLTexture texture = GLResources.textures[GLResources.TEXTURE_BACKGROUNDS_01];
		GGLVertexBufferObject vbo = (GGLVertexBufferObject)GLResources.vbo[GLResources.VBO_SKY_01];
		GGLTexCoordBufferObject tbo = (GGLTexCoordBufferObject)GLResources.vbo[GLResources.TBO_BACK_01];
		GGLIBinderObject ibo = GLResources.vbo[GLResources.IBO_SKY_01];
		texture.bind(gl11);
		vbo.bind(gl11);
		tbo.bind(gl11, 32);
		for(int j = 0; j < 5; ++j) {
			float row = 1 + j * 4;
			for(int i = 0; i < 15; ++i) {
				float col = -29 + i * 4;
				gl11.glPushMatrix();
				{
					gl11.glTranslatef(col, row, 0);
					ibo.draw(gl11);
				}
				gl11.glPopMatrix();
			}
		}
		vbo.unbind(gl11);
		tbo.unbind(gl11);
		texture.unbind(gl11);

	}
	
	private void drawFloor(GL11 gl11) {
		GGLTexture texture = GLResources.textures[GLResources.TEXTURE_BACKGROUNDS_01];
		GGLVertexBufferObject floorVbo = (GGLVertexBufferObject)GLResources.vbo[GLResources.VBO_FLOOR_01];
		GGLTexCoordBufferObject tbo = (GGLTexCoordBufferObject)GLResources.vbo[GLResources.TBO_BACK_01];
		GGLIBinderObject floorIbo = GLResources.vbo[GLResources.IBO_FLOOR_01];
		texture.bind(gl11);
		floorVbo.bind(gl11);
		tbo.bind(gl11, 7);
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
		tbo.unbind(gl11);
		texture.unbind(gl11);
	}
	
	private void drawUnits(GL11 gl11) {
		fightManager.draw(gl11, frame);
//		GGLTexture texture = GLResources.textures[GLResources.TEXTURE_UNITS_01];
//		GGLVertexCoordsBufferObject vbo = (GGLVertexCoordsBufferObject)GLResources.vbo[GLResources.VBO_UNIT_01];
//		GGLIBinderObject ibo = GLResources.vbo[GLResources.IBO_UNIT_01];
//		GGLSpriteAnimater sprite1 = GLResources.patterns[GLResources.PATTERN_UNITS_01 + 2]; 
//		GGLSpriteAnimater sprite2 = GLResources.patterns[GLResources.PATTERN_UNITS_01 + 9]; 
//		gl11.glEnable(GL10.GL_ALPHA_TEST);
//		gl11.glAlphaFunc(GL10.GL_EQUAL, 1.0f);
//		texture.bind(gl11);
//		vbo.bind(gl11, sprite1.getCurrentUnit(frame, 2));
//		{
//			float sx = -25f, sy = 1f, sz = -7.5f;
//			for(int j = 0; j < 6; ++j) {
//				float z = sz + 3f * j;
//				for(int i = 0; i < 5; ++i) {
//					float x = sx + 2f * i;
//					gl11.glPushMatrix();
//					{
//						gl11.glTranslatef(x, sy, z);
//						camera.applayBillboard(gl11);
//						//gl11.glRotatef(-30, 1, 0, 0);
//						//gl11.glScalef(1, 1, 1);
//						ibo.draw(gl11);
//					}
//					gl11.glPopMatrix();
//				}
//			}
//		}
//		vbo.unbind(gl11);
//		vbo.bind(gl11, sprite2.getCurrentUnit(frame, 1));
//		{
//			float sx = 25f, sy = 1f, sz = -7.5f;
//			for(int j = 0; j < 6; ++j) {
//				float z = sz + 3f * j;
//				for(int i = 0; i < 5; ++i) {
//					float x = sx - 2f * i;
//					gl11.glPushMatrix();
//					{
//						gl11.glTranslatef(x, sy, z);
//						camera.applayBillboard(gl11);
//						//gl11.glRotatef(-30, 1, 0, 0);
//						//gl11.glScalef(1, 1, 1);
//						ibo.draw(gl11);
//					}
//					gl11.glPopMatrix();
//				}
//			}
//		}
//		vbo.unbind(gl11);
//		texture.unbind(gl11);
//		gl11.glDisable(GL10.GL_ALPHA_TEST);
	}
	
	@Override
	public void onCreate(Context context, Bundle bundle) {
		super.onCreate(context, bundle);
		
		Matrix4f m = new Matrix4f();
		m.loadIdentity();
		m.mat[2] = 10;
		m.mat[8] = 8;
		m.mat[9] = -20;
		m.mat[11] = 1.5f;
		m.mat[14] = 1;
		m.inverse();
		
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		camera.loadIdentity();
		camera.translate(0, 0, -20);
		camera.rotate(30, 1, 0, 0);
		cameraPlayer.setCommands(CameraCommandMaster.commands[0]);
		cameraPlayer.start();
		fightManager.camera = camera;
		fightManager.onLoad(gl, bundle);
	}
	

	@Override
	public void onFocus(GL11 gl, Bundle bundle) {
		frame = 0;
	}
	
	@Override
	public void onPause() {
		
	}
	
	@Override
	public void onDestroy(GL11 gl) {
		
	}
}
