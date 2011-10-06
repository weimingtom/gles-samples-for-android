package com.tatsun.marisacorps;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.tatsun.lib.gm.RenderSurfaceView2;

public class MyRenderSurfaceView extends RenderSurfaceView2 {

	public MyRenderSurfaceView(Context context) {
		super(context);
	}

	@Override
	protected void onSurfaceChanged(GL10 gl, int width, int height) {
		super.onSurfaceChanged(gl, width, height);
		
		GLResources.release(gl);
		GLResources.load(getContext(), gl);
	}
}
