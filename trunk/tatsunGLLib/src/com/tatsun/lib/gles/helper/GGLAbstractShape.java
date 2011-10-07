package com.tatsun.lib.gles.helper;

import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.GGLIBinderObject;

public abstract class GGLAbstractShape implements GGLIBinderObject {

	public GGLAbstractShape() {
	}
	
	public abstract void create(GL11 gl11);
	
}
