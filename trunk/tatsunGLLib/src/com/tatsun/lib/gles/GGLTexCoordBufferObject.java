package com.tatsun.lib.gles;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class GGLTexCoordBufferObject extends GGLAbstractBufferObject {
	public int texCoordsUnitSize;

	public GGLTexCoordBufferObject() {
		bufferObject = new int[1];
	}
	
	/**
	 * 
	 * @param _gl
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] texCoords, int texCoordsUnit) {
		gl11.glGenBuffers(1, bufferObject, 0);
		int texCoordsSize = GGLUtils.sizeofF(texCoords.length);
		this.texCoordsUnitSize = GGLUtils.sizeofF(texCoordsUnit);

		FloatBuffer texCoordBuffer = GGLTemporaryCache.makeTexCoordBufferByPool(texCoords);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, texCoordsSize, null, GL11.GL_STATIC_DRAW);
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, texCoordsSize, texCoordBuffer);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void bind(GL11 gl11) {
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param gl
	 * @param unitNumber
	 */
	public void bind(GL11 gl11, int unitNumber) {
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, unitNumber * texCoordsUnitSize);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param gl
	 */
	@Override
	public void simpleBind(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param gl11
	 * @param unitNumber
	 */
	public void simpleBind(GL11 gl11, int unitNumber) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, unitNumber * texCoordsUnitSize);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void unbind(GL11 gl11) {
		gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
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
	}
}
