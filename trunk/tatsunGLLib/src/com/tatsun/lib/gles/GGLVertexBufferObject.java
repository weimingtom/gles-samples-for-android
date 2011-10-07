package com.tatsun.lib.gles;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class GGLVertexBufferObject extends GGLAbstractBufferObject {
	public int verticesCount;
	private int drawType;

	public GGLVertexBufferObject() {
		bufferObject = new int[1];
	}
	
	/**
	 * 
	 * @param gl11
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] vartices) {
		this.bufferData(gl11, vartices, GL10.GL_TRIANGLES);
	}
	
	/**
	 * 
	 * @param _gl
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] vartices, int drawType) {
		gl11.glGenBuffers(1, bufferObject, 0);
		this.drawType = drawType;
		int verticesSize = GGLUtils.sizeofF(vartices.length);
		verticesCount = vartices.length / 3;

		FloatBuffer verticesBuffer = GGLTemporaryCache.makeVertexBufferByPool(vartices);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize, null, GL11.GL_STATIC_DRAW);
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize, verticesBuffer);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void bind(GL11 gl11) {
		gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void simpleBind(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void unbind(GL11 gl11) {
		gl11.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void simpleUnbind(GL11 gl11) {
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void draw(GL11 gl11) {
		gl11.glDrawArrays(drawType, 0, verticesCount);
	}
}
