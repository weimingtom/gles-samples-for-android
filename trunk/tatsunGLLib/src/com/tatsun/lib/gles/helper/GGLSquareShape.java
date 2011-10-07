package com.tatsun.lib.gles.helper;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.GGLAbstractBufferObject;
import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;

public class GGLSquareShape extends GGLAbstractShape {
	private GGLAbstractBufferObject vbo = new GGLVertexCoordsBufferObject();

	@Override
	public int[] getBufferObject() {
		return vbo.getBufferObject();
	}
	
	@Override
	public void create(GL11 gl11) {
		create(gl11, false);
	}
	
	public void create(GL11 gl11, boolean isCreateUV) {
		vbo = createBufferObject(gl11, GGLUtils.squareVertices, isCreateUV ? GGLUtils.squareTexCoords : null);
	}

	public void create(GL11 gl11, float l, float t, float r, float b) {
		create(gl11, l, t, r, b, true);
	}
	
	public void create(GL11 gl11, float l, float t, float r, float b, boolean isCreateUV) {
		float[] vertices = GGLTemporaryCache.getVerticesPool(12);
		int pos = 0;
		vertices[pos++] = l; vertices[pos++] = t; vertices[pos++] = 0;
		vertices[pos++] = l; vertices[pos++] = b; vertices[pos++] = 0;
		vertices[pos++] = r; vertices[pos++] = t; vertices[pos++] = 0;
		vertices[pos++] = r; vertices[pos++] = b; vertices[pos++] = 0;
		
		vbo = createBufferObject(gl11, vertices, isCreateUV ? GGLUtils.squareTexCoords : null);
	}

	public void create(GL11 gl11, float l, float t, float r, float b, float ul, float vt, float ur, float vb) {
		float[] vertices = GGLTemporaryCache.getVerticesPool(12);
		int pos = 0;
		vertices[pos++] = l; vertices[pos++] = t; vertices[pos++] = 0;
		vertices[pos++] = l; vertices[pos++] = b; vertices[pos++] = 0;
		vertices[pos++] = r; vertices[pos++] = t; vertices[pos++] = 0;
		vertices[pos++] = r; vertices[pos++] = b; vertices[pos++] = 0;
		
		float[] coords = GGLTemporaryCache.getTexCoordsPool(8);
		pos = 0;
		coords[pos++] = ul; coords[pos++] = vt;
		coords[pos++] = ul; coords[pos++] = vb;
		coords[pos++] = ur; coords[pos++] = vt;
		coords[pos++] = ur; coords[pos++] = vb;
		
		vbo = createBufferObject(gl11, vertices, coords);
	}

	private GGLAbstractBufferObject createBufferObject(GL11 gl11, float[] vertices, float[] texCoords) {
		if(texCoords != null) {
			GGLVertexCoordsBufferObject v = new GGLVertexCoordsBufferObject();
			v.bufferData(gl11, vertices, texCoords, GL10.GL_TRIANGLE_STRIP);
			return v;
		} else {
			GGLVertexBufferObject v = new GGLVertexBufferObject();
			v.bufferData(gl11, vertices, GL10.GL_TRIANGLE_STRIP);
			return v;
		}		
	}
	
	@Override
	public void draw(GL11 gl11) {
		vbo.draw(gl11);
	}

	@Override
	public void bind(GL11 gl11) {
		vbo.bind(gl11);
	}

	@Override
	public void unbind(GL11 gl11) {
		vbo.unbind(gl11);
	}

	@Override
	public void simpleBind(GL11 gl11) {
		vbo.simpleBind(gl11);
	}

	@Override
	public void simpleUnbind(GL11 gl11) {
		vbo.simpleUnbind(gl11);
	}

	@Override
	public void delete(GL11 gl11) {
		vbo.delete(gl11);
	}
	

}
