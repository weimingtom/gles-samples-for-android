package com.tatsun.lib.gles;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class GGLIndexBufferObject extends GGLAbstractBufferObject {
	public int indicesCount;
	private int indicesType;
	private int drawType;


	public GGLIndexBufferObject() {
		bufferObject = new int[1];
	}

	/**
	 * 
	 * @param gl11
	 * @param indices
	 */
	public void bufferData(GL11 gl11, short[] indices) {
		bufferData(gl11, indices, GL10.GL_TRIANGLES);
	}
	
	/**
	 * 
	 * @param _gl
	 * @param vartices
	 * @param coords
	 */
	public void bufferData(GL11 gl11, short[] indices, int drawType) {
		gl11.glGenBuffers(1, bufferObject, 0);
		int indicesSize = GGLUtils.sizeofS(indices.length);
		this.indicesCount = indices.length;
		this.drawType = drawType;

		ShortBuffer indexBuffer = GGLTemporaryCache.makeIndexBuffer2ByPool(indices);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, indicesSize, null, GL11.GL_STATIC_DRAW);
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, indicesSize, indexBuffer);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		indicesType = GL10.GL_UNSIGNED_SHORT;
	}

	/**
	 * 
	 * @param gl11
	 * @param indices
	 */
	public void bufferData(GL11 gl11, byte[] indices) {
		bufferData(gl11, indices, GL10.GL_TRIANGLES);
	}
	
	/**
	 * 
	 * @param gl11
	 * @param indices
	 * @param drawType
	 */
	public void bufferData(GL11 gl11, byte[] indices, int drawType) {
		gl11.glGenBuffers(1, bufferObject, 0);
		int indicesSize = GGLUtils.sizeofB(indices.length);
		this.indicesCount = indices.length;
		this.drawType = drawType;

		ByteBuffer indexBuffer = GGLTemporaryCache.makeIndexBuffer1ByPool(indices);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, indicesSize, null, GL11.GL_STATIC_DRAW);
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, indicesSize, indexBuffer);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		indicesType = GL10.GL_UNSIGNED_BYTE;
	}
	
	/**
	 * 
	 * @param gl
	 */
	@Override
	public void bind(GL11 gl11) {
	}
	
	/**
	 * 
	 * @param gl
	 */
	@Override
	public void simpleBind(GL11 gl11) {
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void unbind(GL11 gl11) {
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
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferObject[0]);
		gl11.glDrawElements(drawType, indicesCount, indicesType, 0);
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
}
