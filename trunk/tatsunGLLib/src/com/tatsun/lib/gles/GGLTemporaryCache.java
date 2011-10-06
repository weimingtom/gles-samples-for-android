package com.tatsun.lib.gles;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Hashtable;

/**
 * GGLTemporaryCache.
 * 
 * TODO 必要なら、threadlocal対応
 *
 */
public class GGLTemporaryCache {
	private static final Hashtable<Integer, float[]> verticesPool = new Hashtable<Integer, float[]>();
	private static final Hashtable<Integer, byte[]> indicesPool1 = new Hashtable<Integer, byte[]>();
	private static final Hashtable<Integer, short[]> indicesPool2 = new Hashtable<Integer, short[]>();
	private static final Hashtable<Integer, float[]> texCoordsPool = new Hashtable<Integer, float[]>();
	private static final Hashtable<Integer, float[]> colorsPool = new Hashtable<Integer, float[]>();
	private static final Hashtable<Integer, float[]> normalsPool = new Hashtable<Integer, float[]>();

	private static final Hashtable<Integer, FloatBuffer> vertexBufferPool = new Hashtable<Integer, FloatBuffer>();
	private static final Hashtable<Integer, ByteBuffer> indexBufferPool1 = new Hashtable<Integer, ByteBuffer>();
	private static final Hashtable<Integer, ShortBuffer> indexBufferPool2 = new Hashtable<Integer, ShortBuffer>();
	private static final Hashtable<Integer, FloatBuffer> texCoordBufferPool = new Hashtable<Integer, FloatBuffer>();
	private static final Hashtable<Integer, FloatBuffer> colorBufferPool = new Hashtable<Integer, FloatBuffer>();
	private static final Hashtable<Integer, FloatBuffer> normalBufferPool = new Hashtable<Integer, FloatBuffer>();
	
	//private static final List<Integer> autoReleaseBufferObjectsPool = new LinkedList<Integer>();
	

	/**
	 * 
	 * @param gl11
	 */
	public static final void clearCache() {
		verticesPool.clear();
		indicesPool1.clear();
		indicesPool2.clear();
		colorsPool.clear();
		texCoordsPool.clear();

		vertexBufferPool.clear();
		indexBufferPool1.clear();
		indexBufferPool2.clear();
		colorBufferPool.clear();
		texCoordBufferPool.clear();
		
		//clearAllBufferObjects(gl11);
	}
	
//	public static final void addBufferObjectToPool(int[] bufferObjects) {
//		if(bufferObjects == null || bufferObjects.length == 0)
//			return;
//		for(int id : bufferObjects) {
//			if(id == 0)
//				continue;
//			autoReleaseBufferObjectsPool.add(id);
//		}
//	}
//	
//	public static final void removeBufferObjectFromPool(int id) {
//		ListIterator<Integer> iter = autoReleaseBufferObjectsPool.listIterator();
//		while(iter.hasNext()) {
//			Integer i = iter.next();
//			if(i.intValue() == id) {
//				iter.remove();
//				return;
//			}
//		}
//	}
//	
//	public static final void clearAllBufferObjects(GL11 gl11) {
//		int[] deleteList = new int[autoReleaseBufferObjectsPool.size()];
//		
//		int idx = 0;
//		for(int id : autoReleaseBufferObjectsPool) {
//			deleteList[idx++] = id;
//		}
//		autoReleaseBufferObjectsPool.clear();
//		gl11.glDeleteBuffers(deleteList.length, deleteList, 0);
//	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static float[] getVerticesPool(int n) {
		if (verticesPool.containsKey(n)) {
			return verticesPool.get(n);
		}
		float[] vertices = new float[n];
		verticesPool.put(n, vertices);
		return vertices;
	}

	public static float[] getCloneFromVerticesPool(float[] base) {
		float[] ret = getVerticesPool(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static byte[] getIndicesPool1(int n) {
		if (indicesPool1.containsKey(n)) {
			return indicesPool1.get(n);
		}
		byte[] indexces = new byte[n];
		indicesPool1.put(n, indexces);
		return indexces;
	}

	public static byte[] getCloneFromIndicesPool1(byte[] base) {
		byte[] ret = getIndicesPool1(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static short[] getIndicesPool2(int n) {
		if (indicesPool2.containsKey(n)) {
			return indicesPool2.get(n);
		}
		short[] indexces = new short[n];
		indicesPool2.put(n, indexces);
		return indexces;
	}
	
	public static short[] getCloneFromIndicesPool2(short[] base) {
		short[] ret = getIndicesPool2(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static float[] getColorsPool(int n) {
		if (colorsPool.containsKey(n)) {
			return colorsPool.get(n);
		}
		float[] colors = new float[n];
		colorsPool.put(n, colors);
		return colors;
	}
 
	public static float[] getCloneFromColorsPool(float[] base) {
		float[] ret = getColorsPool(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static float[] getTexCoordsPool(int n) {
		if (texCoordsPool.containsKey(n)) {
			return texCoordsPool.get(n);
		}
		float[] coords = new float[n];
		texCoordsPool.put(n, coords);
		return coords;
	}

	public static float[] getCloneFromTexCoordsPool(float[] base) {
		float[] ret = getTexCoordsPool(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public static float[] getNormalsPool(int n) {
		if (normalsPool.containsKey(n)) {
			return normalsPool.get(n);
		}
		float[] vertices = new float[n];
		normalsPool.put(n, vertices);
		return vertices;
	}
	
	public static float[] getCloneFromNormalsPool(float[] base) {
		float[] ret = getNormalsPool(base.length);
		for(int i = 0; i < base.length; ++i) {
			ret[i] = base[i];
		}
		return ret;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final FloatBuffer makeVertexBufferByPool(float[] arr) {
		FloatBuffer fb = null;
		if (vertexBufferPool.containsKey(arr.length)) {
			fb = vertexBufferPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createFloatBuffer(arr);
		vertexBufferPool.put(arr.length, fb);
		return fb;
	}
 
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final ByteBuffer makeIndexBuffer1ByPool(byte[] arr) {
		ByteBuffer fb = null;
		if (indexBufferPool1.containsKey(arr.length)) {
			fb = indexBufferPool1.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createByteBuffer(arr);
		indexBufferPool1.put(arr.length, fb);
		return fb;
	}

	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final ShortBuffer makeIndexBuffer2ByPool(short[] arr) {
		ShortBuffer fb = null;
		if (indexBufferPool2.containsKey(arr.length)) {
			fb = indexBufferPool2.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createShortBuffer(arr);
		indexBufferPool2.put(arr.length, fb);
		return fb;
	}

	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final FloatBuffer makeColorBufferByPool(float[] arr) {
		FloatBuffer fb = null;
		if (colorBufferPool.containsKey(arr.length)) {
			fb = colorBufferPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createFloatBuffer(arr);
		colorBufferPool.put(arr.length, fb);
		return fb;
	}
 
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final FloatBuffer makeTexCoordBufferByPool(float[] arr) {
		FloatBuffer fb = null;
		if (texCoordBufferPool.containsKey(arr.length)) {
			fb = texCoordBufferPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createFloatBuffer(arr);
		texCoordBufferPool.put(arr.length, fb);
		return fb;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final FloatBuffer makeNormalBufferByPool(float[] arr) {
		FloatBuffer fb = null;
		if (normalBufferPool.containsKey(arr.length)) {
			fb = normalBufferPool.get(arr.length);
			fb.clear();
			fb.put(arr);
			fb.position(0);
			return fb;
		}
		fb = GGLUtils.createFloatBuffer(arr);
		normalBufferPool.put(arr.length, fb);
		return fb;
	}
}
