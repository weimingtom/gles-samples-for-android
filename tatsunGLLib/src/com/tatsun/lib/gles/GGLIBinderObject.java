package com.tatsun.lib.gles;

import javax.microedition.khronos.opengles.GL11;

public interface GGLIBinderObject extends GGLResource {
	public int[] getBufferObject();
	public void bind(GL11 gl11);
	public void unbind(GL11 gl11);
	
	public void simpleBind(GL11 gl11);
	public void simpleUnbind(GL11 gl11);
	
	public void draw(GL11 gl11);
}
