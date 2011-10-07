package com.tatsun.lib.gm;

import javax.microedition.khronos.opengles.GL11;

import android.os.Bundle;

public interface Layer {
	public void update(GL11 gl, int frame);
	public void onLoad(GL11 gl, Bundle bundle);
	public void draw(GL11 gl, int frame);
}
