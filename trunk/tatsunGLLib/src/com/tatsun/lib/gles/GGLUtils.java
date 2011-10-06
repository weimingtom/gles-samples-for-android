package com.tatsun.lib.gles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;

public class GGLUtils {
	public static final float one = 0.5f;
	// square
	public static final float[] squareVertices = {
			-one,  one, 0,
			-one, -one, 0,
			 one,  one, 0,
			 one, -one, 0,
		};
	public static final float[] squareTexCoords = {
		0, 0,
		0, 1,
		1, 0,
		1, 1,
	};
	public static final byte[] squareIndices = {
			0, 1, 2,
			2, 1, 3,
		};
	// cube
	public static final float[] cubeVertices = {
			-one,  one,  one,
	        -one, -one,  one,
	         one,  one,  one,
	         one, -one,  one,
	        -one,  one, -one,
	        -one, -one, -one,
	         one,  one, -one,
	         one, -one, -one,
       };
	public static final float[] cubeTexCoords = {
			0,0,  0,1, 1,0, 1,1,
			1,1,  1,0, 0,1, 0,0,
		};
	public static final byte[] cubeIndices = {
			0, 1, 2,   2, 1, 3,
			0, 1, 4,   4, 1, 5,
			4, 5, 6,   6, 5, 7,
			6, 7, 2,   2, 7, 3,
			4, 0, 6,   6, 0, 2,
			5, 1, 7,   7, 1, 3,
		};
	
	private GGLUtils() {
		// private for static class
	}

	/**
	 * 
	 * @param gl
	 * @param resources
	 * @param resId
	 * @return
	 */
	public static final int loadTexture(GL10 gl, Resources resources, int resId) {
		return loadTexture(gl, resources, resId, GL10.GL_NEAREST, 0);
	}
	
	/**
	 * 
	 * @param gl
	 * @param resources
	 * @param resId
	 * @param texture_filter
	 * @param mipmap
	 * @return
	 */
	public static final int loadTexture(GL10 gl, Resources resources, int resId, int texture_filter, int mipmap) {
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, resources, resId, GL10.GL_NEAREST, 0);
		
		return texture.textureId[0];
	}
	
	/**
	 * 
	 * @param gl
	 * @param bitmap
	 * @param texture_filter
	 * @param mipmap
	 * @return
	 */
	public static final int loadTexture(GL10 gl, Bitmap bitmap, int texture_filter, int mipmap) {
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, bitmap, GL10.GL_NEAREST, 0);
		
		return texture.textureId[0];
	}
	
	/**
	 * 
	 * @param array
	 * @param scale
	 * @return
	 */
	public static final float[] scale(float[] array, float scale) {
		for(int i = 0; i < array.length; ++i) {
			array[i] = array[i] * scale;
		}
		return array;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final int sizeof(int[] arr) {
		return arr.length * 4;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final int sizeof(float[] arr) {
		return arr.length * 4;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final int sizeof(byte[] arr) {
		return arr.length;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final int sizeof(short[] arr) {
		return arr.length * 2;
	}
	
	/**
	 * 
	 * @param len
	 * @return
	 */
	public static final int sizeofI(int len) {
		return len * 4;
	}
	
	/**
	 * 
	 * @param len
	 * @return
	 */
	public static final int sizeofF(int len) {
		return len * 4;
	}

	/**
	 * 
	 * @param len
	 * @return
	 */
	public static final int sizeofB(int len) {
		return len;
	}
	
	/**
	 * 
	 * @param len
	 * @return
	 */
	public static final int sizeofS(int len) {
		return len * 2;
	}

	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final FloatBuffer createFloatBuffer(float[] arr) {
		int len = sizeofF(arr.length);
		ByteBuffer bb = ByteBuffer.allocateDirect(len);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final ByteBuffer createByteBuffer(byte[] arr) {
		int len = sizeofB(arr.length);
		ByteBuffer bb = ByteBuffer.allocateDirect(len);
		bb.order(ByteOrder.nativeOrder());
		ByteBuffer fb = bb;
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public static final ShortBuffer createShortBuffer(short[] arr) {
		int len = sizeofS(arr.length);
		ByteBuffer bb = ByteBuffer.allocateDirect(len);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer fb = bb.asShortBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	
	/**
	 * 
	 * @param divides
	 * @param radius
	 * @param buffer
	 * @return
	 */
	public static final float[] calcCirclePoints(int divides, float radius, float[] buffer) {
		int idx = 0;
		for (int i = 0; i < divides; i++) {
			float th1 = (float)(2.0f / divides * i * Math.PI);
			float th2 = 2.0f / (float)divides * (float)(i+1) * (float)Math.PI;
		 
			buffer[idx++] = 0;
			buffer[idx++] = 0;
			buffer[idx++] = 0;
		 
			buffer[idx++] = (float)Math.cos((double)th1) * radius;
			buffer[idx++] = (float)Math.sin((double)th1) * radius;
			buffer[idx++] = 0;
		 
			buffer[idx++] = (float)Math.cos((double)th2) * radius;
			buffer[idx++] = (float)Math.sin((double)th2) * radius;
			buffer[idx++] = 0;
			Log.i("", "x:"+buffer[idx-9]+" y:"+buffer[idx-8]+" z:"+buffer[idx-7]
			    + ", " + "x:"+buffer[idx-6]+" y:"+buffer[idx-5]+" z:"+buffer[idx-4]
		        + ", " + "x:"+buffer[idx-3]+" y:"+buffer[idx-2]+" z:"+buffer[idx-1]);
		}
		return buffer;
	}
	

	/**
	 * 
	 * @param gl
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param texture
	 * @param u
	 * @param v
	 * @param tex_w
	 * @param tex_h
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public static final void drawTexture(GL10 gl, float x, float y, float width, float height, int texture, float u, float v, float tex_w, float tex_h, float r, float g, float b, float a) {
		float[] vertices = GGLTemporaryCache.getVerticesPool(8);
		vertices[0] = -0.5f * width + x; vertices[1] = -0.5f * height + y;
		vertices[2] =  0.5f * width + x; vertices[3] = -0.5f * height + y;
		vertices[4] = -0.5f * width + x; vertices[5] =  0.5f * height + y;
		vertices[6] =  0.5f * width + x; vertices[7] =  0.5f * height + y;
 
		float[] colors = GGLTemporaryCache.getColorsPool(16);
		for (int i = 0; i < 16; i++) {
			colors[i++] = r;
			colors[i++] = g;
			colors[i++] = b;
			colors[i]   = a;
		}
 
		float[] coords = GGLTemporaryCache.getTexCoordsPool(8);
		coords[0] = u; coords[1] = v + tex_h;
		coords[2] = u + tex_w; coords[3] = v + tex_h;
		coords[4] = u; coords[5] = v;
		coords[6] = u + tex_w; coords[7] = v;
	 
		FloatBuffer polygonVertices = GGLTemporaryCache.makeVertexBufferByPool(vertices);
		FloatBuffer polygonColors = GGLTemporaryCache.makeColorBufferByPool(colors);
		FloatBuffer texCoords = GGLTemporaryCache.makeTexCoordBufferByPool(coords);
	 
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, polygonVertices);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, polygonColors);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	 
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	 
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
}