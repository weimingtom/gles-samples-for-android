package com.tatsun.lib.gles.helper;

import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

public class GGLProjectionCache {
	public static final int DEFAULT_WRATIO = 3;
	public static final int DEFAULT_HRATIO = 2;
	private int wRatio = 3;
	private int hRatio = 2;
	private boolean isStretch = false;
	public int width;
	public int height;
	public int offsetX;
	public int offsetY;
	
	private HashMap<Integer, OrthParam> orths = new HashMap<Integer, OrthParam>();
	private HashMap<Integer, FrustumParam> frumstums = new HashMap<Integer, FrustumParam>();
	private HashMap<Integer, PerspectiveParam> perspectives = new HashMap<Integer, PerspectiveParam>();
	private HashMap<Integer, LookAtParam> lookAts = new HashMap<Integer, LookAtParam>();
	
	/**
	 * 
	 */
	public GGLProjectionCache() {
	}
	
	/**
	 * 
	 * @param wRatio
	 * @param hRatio
	 * @param isStretch
	 */
	public GGLProjectionCache(int wRatio, int hRatio, boolean isStretch) {
		this.wRatio = wRatio;
		this.hRatio = hRatio;
		this.isStretch = isStretch;
	}
	
	/**
	 * 
	 * @param gl
	 * @param width
	 * @param height
	 */
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;
		if (isStretch) {
			this.offsetX = 0;
			this.offsetY = 0;
		} else {
			int w = 0, h = 0;
			while (w < width && h < height) {
				w += wRatio;
				h += hRatio;
			}
			this.width = w;
			this.height = h;
			this.offsetX = (width - w) / 2;
			this.offsetY = (height - h) / 2;
		}
		setViewport(gl);
	}
	
	/*
	 * 
	 */
	public void setViewport(GL10 gl) {
		gl.glViewport(offsetX, offsetY, width, height);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void registDefaultOrth(int key) {
		registOrth(key, -wRatio * 0.5f, wRatio * 0.5f, -hRatio * 0.5f, hRatio * 0.5f, 0.5f, -0.5f);
	}
	
	public void registOrth(int key, float l, float r, float b, float t, float near, float far) {
		orths.put(key, new OrthParam(l, r, b, t, near, far));
	}
	
	public OrthParam getOrthPram(int key) {
		return orths.get(key);
	}

	public void setOrth(GL10 gl, int key) {
		OrthParam orth = orths.get(key);
		setOrth(gl, orth);	
	}
		
	public void setOrth(GL10 gl, OrthParam orth) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(orth.l, orth.r, orth.b, orth.t, orth.near, orth.far);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	/**
	 * 
	 * @param key
	 */
	public void registDefaultFrustum(int key) {
		registFrustum(key, -wRatio * 0.1f, wRatio * 0.1f, -hRatio * 0.1f, hRatio * 0.1f, 5f, 100f);
	}
	
	public void registFrustum(int key, float l, float r, float b, float t, float near, float far) {
		frumstums.put(key, new FrustumParam(l, r, b, t, near, far));
	}

	public FrustumParam getFrustumParam(int key) {
		return frumstums.get(key);
	}
	
	public void setFrustum(GL10 gl, int key) {
		FrustumParam frustum = frumstums.get(key);
		setFrustum(gl, frustum);
	}
	
	public void setFrustum(GL10 gl, FrustumParam frustum) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(frustum.l, frustum.r, frustum.b, frustum.t, frustum.near, frustum.far);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	/**
	 * 
	 * @param key
	 */
	public void registDefaultPerspective(int key) {
		registPerspective(key, 45, (float)width / (float)height, 0.01f, 100f);
	}
	
	public void registPerspective(int key, float fovy, float aspect, float near, float far) {
		perspectives.put(key, new PerspectiveParam(fovy, aspect, near, far));
	}

	public PerspectiveParam getPerspectiveParam(int key) {
		return perspectives.get(key);
	}
	
	/**
	 * 
	 * @param key
	 */
	public void registDefaultLookAt(int key) {
		registLookAt(key, 0, 0, 10, 0, 0, 0.0f, 0.0f, 1.0f, 0.0f);
	}
	
	public void registLookAt(int key, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
		lookAts.put(key, new LookAtParam(eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ));
	}
	
	public LookAtParam getLookAtParam(int key) {
		return lookAts.get(key);
	}
	
	/**
	 * 
	 * @param gl
	 * @param perspectiveKey
	 * @param lookAtKey
	 */
	public void setPerspective(GL10 gl, int perspectiveKey, int lookAtKey) {
		PerspectiveParam pers = perspectives.get(perspectiveKey);
		LookAtParam looks = lookAts.get(lookAtKey);
		setPerspective(gl, pers, looks);
	}
	
	public void setPerspective(GL10 gl, PerspectiveParam pers, LookAtParam looks) {
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, pers.fovy, pers.aspect, pers.near, pers.far);
		GLU.gluLookAt(gl, looks.eyeX, looks.eyeY, looks.eyeZ, looks.centerX, looks.centerY, looks.centerZ, looks.upX, looks.upY, looks.upZ);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
	}
	

	public float getGLX(float x) {
		return ((x - offsetX) / (float) width) * wRatio - (wRatio*0.5f);
	}

	public float getGLY(float y) {
		return ((y - offsetY) / (float) height) * hRatio - (hRatio*0.5f);
	}
	
	/**
	 * 
	 * @param target
	 *   all: -1, orths: 0, frustum: 1, perspective: 2, lookuat: 3 
	 */
	public void clearRegistParams(int target) {
		switch(target) {
		case -1:
			orths.clear();
			frumstums.clear();
			perspectives.clear();
			lookAts.clear();
			break;
		case 0:
			orths.clear();
			break;
		case 1:
			frumstums.clear();
			break;
		case 2:
			perspectives.clear();
			break;
		case 3:
			lookAts.clear();
			break;
		}
	}
	
	public static final class OrthParam {
		public float l, r, b, t;
		public float near, far;
		public OrthParam(float l, float r, float b, float t, float near, float far) {
			this.l = l; this.r = r; this.b = b; this.t = t;
			this.near = near; this.far = far;
		}
	}
	public static final class FrustumParam {
		public float l, r, t, b;
		public float near, far;
		public FrustumParam(float l, float r, float b, float t, float near, float far) {
			this.l = l; this.r = r; this.b = b; this.t = t;
			this.near = near; this.far = far;
		}
	}
	public static final class PerspectiveParam {
		public float fovy, aspect, near, far;
		public PerspectiveParam(float fovy, float aspect, float near, float far) {
			this.fovy = fovy; this.aspect = aspect;
			this.near = near; this.far = far;
		}
	}
	public static final class LookAtParam {
		public float eyeX, eyeY, eyeZ;
		public float centerX, centerY, centerZ;
		public float upX, upY, upZ;
		public LookAtParam(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
			this.eyeX = eyeX; this.eyeY = eyeY; this.eyeZ = eyeZ;
			this.centerX = centerX; this.centerY = centerY; this.centerZ = centerZ;
			this.upX = upX; this.upY = upY; this.upZ = upZ;
		}
	}
}
