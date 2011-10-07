package com.tatsun.marisacorps.scenes;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gm.AbstractScene;
import com.tatsun.lib.gm.GmService;
import com.tatsun.marisacorps.title.TitleScene;

public class FightScene extends AbstractScene {
	private static final String TAG = "FightScene";
	
	public FightScene() {
	}

	@Override
	public void update(GL11 gl) {
		++frame;
		if(frame == 60)
			GmService.sceneController.setScene(gl, TitleScene.class, null);
		
	}

	@Override
	public void draw(GL11 gl) {
		Log.i(TAG, "draw");
		gl.glClearColor(0, 0.5f, 0f, 0.5f);
	}

	@Override
	public void onCreate(Context context, Bundle bundle) {
		super.onCreate(context, bundle);
		
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		// TODO Auto-generated method stub
		GL11 gl11 = (GL11)gl;
		
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
		// TODO Auto-generated method stub
		
	}
}
