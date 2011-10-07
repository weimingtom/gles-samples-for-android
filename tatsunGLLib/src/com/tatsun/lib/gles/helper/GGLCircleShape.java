package com.tatsun.lib.gles.helper;

import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.GGLTemporaryCache;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;

public class GGLCircleShape extends GGLAbstractShape {
	private GGLVertexBufferObject vbo = new GGLVertexBufferObject();

	@Override
	public int[] getBufferObject() {
		return vbo.getBufferObject();
	}
	
	@Override
	public void create(GL11 gl11) {
		float[] vertices = GGLTemporaryCache.getVerticesPool(16 * 3 * 3);
		GGLUtils.calcCirclePoints(16, 0.5f, vertices);
		vbo.bufferData(gl11, vertices);
	}
	
	public void create(GL11 gl11, int devides, float radius) {
		float[] vertices = GGLTemporaryCache.getVerticesPool(devides * 3 * 3);
		GGLUtils.calcCirclePoints(devides, radius, vertices);
		vbo.bufferData(gl11, vertices);
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
