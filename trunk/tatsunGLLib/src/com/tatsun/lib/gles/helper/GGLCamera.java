package com.tatsun.lib.gles.helper;

import javax.microedition.khronos.opengles.GL10;

import com.tatsun.lib.math.Matrix4f;

public class GGLCamera {
	private Matrix4f multMatrix, rotate, billboard;
	
	/**
	 * 
	 */
	public GGLCamera() {
		multMatrix = new Matrix4f();
		rotate = new Matrix4f();
		billboard = new Matrix4f();
	}

	/**
	 * 
	 * @param gl
	 */
	public void applayMatrix(GL10 gl) {
		gl.glMultMatrixf(multMatrix.mat, 0);
	}

	public GGLCamera loadIdentity() {
		multMatrix.loadIdentity();
		rotate.loadIdentity();
		billboard.loadIdentity();
		return this;
	}
	
	public GGLCamera rotate(float rot, float x, float y, float z) {
		multMatrix.rotate(rot, x, y, z);
		rotate.rotate(rot, x, y, z);
		billboard.copy(rotate).inverse();
		return this;
	}
	
	public GGLCamera translate(float x, float y, float z) {
		multMatrix.translate(x, y, z);
		return this;
	}
	
	public GGLCamera scale(float x, float y, float z) {
		multMatrix.scale(x, y, z);
		return this;
	}
	
	public GGLCamera loadFlustum(float l, float r, float b, float t, float n, float f) {
		multMatrix.loadFrustum(l, r, b, t, n, f);
		
		return this;
	}
	
	public void applayBillboard(GL10 gl) {
		gl.glMultMatrixf(billboard.mat, 0);
	}
	
	public Matrix4f getBillboardMatrix() {
		return billboard;
	}
}
