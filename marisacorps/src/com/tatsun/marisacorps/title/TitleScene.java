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
	FightLayer fightLayer = new FightLayer();
	FightBackLayer backLayer = new FightBackLayer();
	
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
		
		fightLayer.update(gl11, frame);
		backLayer.update(gl11, frame);
	}

	@Override
	public void draw(GL11 gl) {
		//gl.glEnable(GL10.GL_CULL_FACE);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		backLayer.draw(gl, frame);
		fightLayer.draw(gl, frame);
		
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
	@Override
	public void onCreate(Context context, Bundle bundle) {
		super.onCreate(context, bundle);
		resetCamera();
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		fightLayer.cameraPlayer = cameraPlayer;
		fightLayer.onLoad(gl, bundle);
		backLayer.cameraPlayer = cameraPlayer;
		backLayer.onLoad(gl, bundle);
	}
	

	@Override
	public void onFocus(GL11 gl, Bundle bundle) {
		frame = 0;
		fightLayer.fightInit();
		cameraPlayer.setCommands(CameraCommandMaster.commands[0]);
		cameraPlayer.start();
	}
	
	@Override
	public void onPause() {
		
	}
	
	@Override
	public void onDestroy(GL11 gl) {
		
	}
	
	public void resetCamera() {
		camera.loadIdentity();
		camera.translate(0, 0, -20);
		camera.rotate(30, 1, 0, 0);
	}
}
