package com.tatsun.lib.gles;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class GGLVertexCoordsBufferObject extends GGLAbstractBufferObject {
	public int verticesCount;
	public int texCoordsOffset;
	public int uvUnitSize;
	private int drawType;

	public GGLVertexCoordsBufferObject() {
		bufferObject = new int[1];
	}
	
	/**
	 * 
	 * @param gl11
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] vertices, float[] texCoords) {
		this.bufferData(gl11, vertices, texCoords, GL10.GL_TRIANGLES);
	}
	
	/**
	 * 
	 * @param gl11
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] vertices, float[] texCoords, int drawType) {
		bufferData(gl11, vertices, texCoords, -1, drawType);
	}
	
	public void bufferData(GL11 gl11, float[] vertices, float[] texCoords, int uvUnitCount, int drawType) {
		gl11.glGenBuffers(1, bufferObject, 0);
		this.drawType = drawType;
		int verticesSize = GGLUtils.sizeofF(vertices.length);
		this.verticesCount = vertices.length / 3;
		int texCoordsSize = GGLUtils.sizeofF(texCoords.length);
		if(uvUnitCount == -1) {
			this.uvUnitSize = GGLUtils.sizeofF(verticesCount * 2);
		} else {
			this.uvUnitSize = GGLUtils.sizeofF(uvUnitCount * 2);
		}
		
		FloatBuffer verticesBuffer = GGLTemporaryCache.makeVertexBufferByPool(vertices);
		FloatBuffer texCoordsBuffer = GGLTemporaryCache.makeTexCoordBufferByPool(texCoords);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize + texCoordsSize, null, GL11.GL_STATIC_DRAW);
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize, verticesBuffer);
		texCoordsOffset = verticesSize;
		gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, texCoordsOffset, texCoordsSize, texCoordsBuffer);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param gl11
	 */
	@Override
	public void bind(GL11 gl11) {
		gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordsOffset);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	public void bind(GL11 gl11, int uvUnitNumber) {
		gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordsOffset + uvUnitNumber*uvUnitSize);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	@Override
	public void simpleBind(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordsOffset);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	public void simpleBind(GL11 gl11, int uvUnitNumber) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordsOffset + uvUnitNumber*uvUnitSize);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	public void bindBegin(GL11 gl11) {
		gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
	}
		
	/**
	 * 
	 * @param uvUnitNumber
	 */
	public void bindUnit(GL11 gl11, int uvUnitNumber) {
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordsOffset + uvUnitNumber*uvUnitSize);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	public void bindEnd(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	@Override
	public void unbind(GL11 gl11) {
		gl11.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	@Override
	public void simpleUnbind(GL11 gl11) {
	}
	
	/**
	 * 
	 * @param gl11
	 */
	@Override
	public void draw(GL11 gl11) {
		gl11.glDrawArrays(drawType, 0, verticesCount);
	}
}
