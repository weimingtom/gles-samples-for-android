package com.tatsun.marisacorps;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.tatsun.lib.gm.GmService;
import com.tatsun.lib.gm.RenderSurfaceView;

public class MyRenderSurfaceView extends RenderSurfaceView {

	public MyRenderSurfaceView(Context context) {
		super(context);
	}

	@Override
	protected void onSurfaceChanged(GL10 gl, int width, int height) {
		GLResources.release(gl);
		GLResources.load(getContext(), gl);
		
		super.onSurfaceChanged(gl, width, height);
		
		GmService.projection.registOrth(MyGlobals.PROJ_ORTH_01, -1, 1, -1, 1, -5, 5);
		GmService.projection.registFrustum(MyGlobals.PROJ_FRUS_01, -1.5f, 1.5f, -1, 1, 1f, 100);
	}
}
