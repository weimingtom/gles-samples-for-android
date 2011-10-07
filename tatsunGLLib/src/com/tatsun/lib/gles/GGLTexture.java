package com.tatsun.lib.gles;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class GGLTexture implements GGLResource {
	private static final BitmapFactory.Options options = new BitmapFactory.Options();
	static {
		options.inScaled = false;
		options.inPreferredConfig = Config.ARGB_8888;
	}
	public static int TEXTURE_FILTER = GL10.GL_NEAREST;	// GL_NEAREST, GL_LINEAR
	
	public int[] textureId = new int[1];
	public int resourceId;
	
	/**
	 * 
	 * @param gl
	 * @param resources
	 * @param resId
	 * @return
	 */
	public int loadTexture(GL10 gl, Resources resources, int resourceId) {
		return loadTexture(gl, resources, resourceId, TEXTURE_FILTER, 0);
	}
	
	/**
	 * 
	 * @param gl
	 * @param resources
	 * @param resId
	 * @param mipMapLevel
	 * @return
	 */
	public int loadTexture(GL10 gl, Resources resources, int resourceId, int texture_filter, int mipMapLevel) {
		this.resourceId = 0;
		
		Bitmap bmp = BitmapFactory.decodeResource(resources, resourceId, options);
		if (bmp == null) {
			textureId[0] = 0;
			return 0;
		}
		
		try {
			textureId[0] = loadTexture(gl, bmp, texture_filter, mipMapLevel);
		} finally {
			bmp.recycle();
		}
		
		return textureId[0];
	}
	
	/**
	 * this method is not cache bitmap
	 * @param gl
	 * @param bmp
	 * @param texture_filter
	 * @param level
	 * @return
	 */
	public int loadTexture(GL10 gl, Bitmap bmp, int texture_filter, int level) {
		gl.glGenTextures(1, textureId, 0);
		// bind
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bmp, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, texture_filter);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, texture_filter);
		// unbind
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		return textureId[0];
	}

	/**
	 * 
	 * @param gl11
	 */
	public void bind(GL11 gl11) {
		gl11.glBindTexture(GL10.GL_TEXTURE_2D, textureId[0]);
	}
	
	/**
	 * 
	 * @param gl11
	 */
	public void unbind(GL11 gl11) {
		gl11.glBindTexture(GL10.GL_TEXTURE_2D, 0);
	}
	
	/**
	 * 
	 * @param gl
	 */
	@Override
	public void delete(GL11 gl11) {
		gl11.glDeleteTextures(1, textureId, 0);
	}
}
