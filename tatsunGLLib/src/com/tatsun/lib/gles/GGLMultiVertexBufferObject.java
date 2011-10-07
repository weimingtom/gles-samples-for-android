package com.tatsun.lib.gles;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


public class GGLMultiVertexBufferObject extends GGLAbstractBufferObject {
	private int drawType;
	public int verticesCount;
	public int texCoordsOffset;
	public int normalsOffset;
	public int colorsOffset;
	private int colorUnitSize;

	public GGLMultiVertexBufferObject() {
		bufferObject = new int[1];
	}
	
	public void bufferData(GL11 gl11, float[] vartices, float[] texCoords, float[] normals, float[] colors) {
		bufferData(gl11, vartices, texCoords, normals, colors, GL10.GL_TRIANGLES, 4);
	}
	
	/**
	 * 
	 * @param _gl
	 * @param vartices
	 */
	public void bufferData(GL11 gl11, float[] vartices, float[] texCoords, float[] normals, float[] colors, int drawType, int colorUnitSize) {
		gl11.glGenBuffers(1, bufferObject, 0);
		this.drawType = drawType;
		this.colorUnitSize = colorUnitSize;
		verticesCount = 0;
		texCoordsOffset = 0;
		normalsOffset = 0;
		colorsOffset = 0;
		
		int verticesSize = (vartices == null ? 0 : GGLUtils.sizeofF(vartices.length));
		verticesCount = (vartices == null ? 0 : vartices.length / 3);
		int texCoordsSize = (texCoords == null ? 0 : GGLUtils.sizeofF(texCoords.length));
		int normalsSize = (normals == null ? 0 : GGLUtils.sizeofF(normals.length));
		int colorsSize = (colors == null ? 0 : GGLUtils.sizeofF(colors.length));
		int pointerOffset = 0;

		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glBufferData(GL11.GL_ARRAY_BUFFER, verticesSize + texCoordsSize + normalsSize + colorsSize, null, GL11.GL_STATIC_DRAW);
		if(verticesSize != 0) {
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, verticesSize, GGLTemporaryCache.makeVertexBufferByPool(vartices));
			pointerOffset += verticesSize;
		}
		if(texCoordsSize != 0) {
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, pointerOffset, texCoordsSize, GGLTemporaryCache.makeTexCoordBufferByPool(texCoords));
			texCoordsOffset = pointerOffset;
			pointerOffset += texCoordsSize;
		}
		if(normalsSize != 0) {
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, pointerOffset, normalsSize, GGLTemporaryCache.makeTexCoordBufferByPool(normals));
			normalsOffset = pointerOffset;
			pointerOffset += normalsSize;
		}
		if(colorsSize != 0) {
			gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, pointerOffset, colorsSize, GGLTemporaryCache.makeColorBufferByPool(colors));
			colorsOffset = pointerOffset;
			pointerOffset += colorsSize;
		}
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void bind(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		// bind vertices
		if(verticesCount != 0) {
			gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		}
		// bind texcoords
		if(texCoordsOffset != 0) {
			gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsOffset);
		}
		// bind normals
		if(normalsOffset != 0) {
			gl11.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl11.glNormalPointer(GL10.GL_FLOAT, 0, normalsOffset);
		}
		// bind colors
		if(colorsOffset != 0) {
			gl11.glEnableClientState(GL10.GL_COLOR_ARRAY);
			gl11.glColorPointer(colorUnitSize, GL10.GL_FLOAT, 0, colorsOffset);
		}
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void simpleBind(GL11 gl11) {
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		// bind vertices
		if(verticesCount != 0) {
			gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		}
		// bind texcoords
		if(texCoordsOffset != 0) {
			gl11.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsOffset);
		}
		// bind normals
		if(normalsOffset != 0) {
			gl11.glNormalPointer(GL10.GL_FLOAT, 0, normalsOffset);
		}
		// bind colors
		if(colorsOffset != 0) {
			gl11.glColorPointer(colorUnitSize, GL10.GL_FLOAT, 0, colorsOffset);
		}
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
	}
	
	/**
	 * 
	 * @param _gl
	 */
	@Override
	public void unbind(GL11 gl11) {
		if(verticesCount != 0) {
			gl11.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
		// bind texcoords
		if(texCoordsOffset != 0) {
			gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
		// bind normals
		if(normalsOffset != 0) {
			gl11.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		}
		// bind colors
		if(colorsOffset != 0) {
			gl11.glDisableClientState(GL10.GL_COLOR_ARRAY);
		}
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
