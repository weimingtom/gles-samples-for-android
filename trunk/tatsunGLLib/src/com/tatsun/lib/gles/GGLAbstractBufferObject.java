package com.tatsun.lib.gles;

import javax.microedition.khronos.opengles.GL11;

public abstract class GGLAbstractBufferObject implements GGLIBinderObject {
	protected int[] bufferObject;
	
	/**
	 * 
	 */
	public int[] getBufferObject() {
		return bufferObject;
	}
	
	/**
	 * 
	 * @param _gl
	 */
	public abstract void bind(GL11 gl11);

	/**
	 * 
	 * @param gl
	 */
	public abstract void simpleBind(GL11 gl11);

	/**
	 * 
	 * @param _gl
	 */
	public abstract void unbind(GL11 gl11);

	/**
	 * 
	 * @param gl
	 */
	public abstract void simpleUnbind(GL11 gl11);

	/**
	 * 
	 * @param _gl
	 */
	public abstract void draw(GL11 gl11);
	
	/**
	 * 
	 * @param gl
	 */
	public void delete(GL11 gl11) {
		if(bufferObject != null)
			gl11.glDeleteBuffers(bufferObject.length, bufferObject, 0);
	}
}
