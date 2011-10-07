package com.tatsun.marisacorps;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;


import android.content.Context;

import com.tatsun.lib.gles.GGLIndexBufferObject;
import com.tatsun.lib.gles.GGLSpriteCoordFactory;
import com.tatsun.lib.gles.GGLTexCoordBufferObject;
import com.tatsun.lib.gles.GGLTexture;
import com.tatsun.lib.gles.GGLAbstractBufferObject;
import com.tatsun.lib.gles.GGLUtils;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.lib.gles.helper.GGLSpriteAnimater;

public class GLResources {
	public static final int TEXTURE_UNITS_01 = 1;
	public static final int TEXTURE_BACKGROUNDS_01 = 2;
	public static final int TEXTURE_ITEMS_01 = 3;
	public static final int TEXTURE_TREE_01 = 4;
	public static final int TEXTURE_TREE_02 = 5;
	public static final int TEXTURE_TREE_03 = 6;
	
	public static final int PATTERN_UNITS_01 = 1;
	public static final int PATTERN_UNITS_02 = 64;
	
	public static final int VBO_UNIT_01 = 1;
	public static final int IBO_UNIT_01 = 2;
	public static final int VBO_FLOOR_01 = 3;
	public static final int TBO_BACK_01 = 4;
	public static final int IBO_FLOOR_01 = 5;
	public static final int VBO_SKY_01 = 6;
	public static final int IBO_SKY_01 = 7;
	public static final int VBO_TREE_01 = 8;

	public static GGLTexture[] textures = new GGLTexture[256];
	public static GGLSpriteAnimater[] patterns = new GGLSpriteAnimater[256];
	public static GGLAbstractBufferObject[] vbo = new GGLAbstractBufferObject[256];
	
	public static final void load(Context context, GL10 gl) {
		GL11 gl11 = (GL11)gl;
		loadUnit(context, gl11);
		loadFloor(context, gl11); 
		loadBack(context, gl11);
	}

	private static final void loadUnit(Context context, GL11 gl) {
		float[] vertices = new float[]{-1,1,0, -1,-1,0, 1,1,0, 1,-1,0, };
		float[] coords = GGLSpriteCoordFactory.createDevidedSpriteCoords(16, 16);
		byte[] indices = new byte[]{0,1,2, 2,1,3,};
		
		GGLVertexCoordsBufferObject unitVbo = new GGLVertexCoordsBufferObject();
		unitVbo.bufferData(gl, vertices, coords);
		vbo[VBO_UNIT_01] = unitVbo;
		
		GGLIndexBufferObject unitIbo = new GGLIndexBufferObject();
		unitIbo.bufferData(gl, indices);
		vbo[IBO_UNIT_01] = unitIbo;
		
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, context.getResources(), R.drawable.units_01);
		textures[TEXTURE_UNITS_01] = texture;
		
		GGLSpriteAnimater[] sprites = new GGLSpriteAnimater[20];
		int idx = 0;
		for(int j = 0; j < 4; ++j) {
			int row = j * 16;
			for(int i = 0; i < 5; ++i) {
				int p = i * 3 + row;
				sprites[idx] = new GGLSpriteAnimater();
				sprites[idx].resistPatterns(new int[][]{{p, p+1, p+2, p+1}, {p+16, p+17, p+18, p+17}, {p+32, p+33, p+34, p+33}, {p+48, p+49, p+50, p+49}});
				idx++;
			}
		}
		
		System.arraycopy(sprites, 0, patterns, PATTERN_UNITS_01, 20); 
	}
	
	
	private static final void loadFloor(Context context, GL11 gl) {
		//float[] vertices = new float[]{-30,0,-9, -30,0,9, 30,0,-9, 30,0,9,};
		float[] vertices = new float[]{-2,0,-2, -2,0,2, 2,0,-2, 2,0,2,};
		float[] texCoords =  GGLSpriteCoordFactory.createDevidedSpriteCoords(8, 8);
		byte[] indices = new byte[]{0,1,2, 2,1,3,};
		
		GGLVertexBufferObject floorVbo = new GGLVertexBufferObject();
		floorVbo.bufferData(gl, vertices);
		vbo[VBO_FLOOR_01] = floorVbo;
		
		GGLTexCoordBufferObject tbo = new GGLTexCoordBufferObject();
		tbo.bufferData(gl, texCoords, 8);
		vbo[TBO_BACK_01] = tbo;
		
		GGLIndexBufferObject floorIbo = new GGLIndexBufferObject();
		floorIbo.bufferData(gl, indices);
		vbo[IBO_FLOOR_01] = floorIbo;
		
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, context.getResources(), R.drawable.backgrounds_01);
		textures[TEXTURE_BACKGROUNDS_01] = texture;
	}
	
	private static final void loadItem(Context context, GL11 gl) {
		float[] vertices = new float[]{-1,1,0, -1,-1,0, 1,1,0, 1,-1,0, };
		float[] coords = GGLSpriteCoordFactory.createDevidedSpriteCoords(16, 16);
		byte[] indices = new byte[]{0,1,2, 2,1,3,};
		
		GGLVertexCoordsBufferObject unitVbo = new GGLVertexCoordsBufferObject();
		unitVbo.bufferData(gl, vertices, coords);
		vbo[VBO_UNIT_01] = unitVbo;
		
		GGLIndexBufferObject unitIbo = new GGLIndexBufferObject();
		unitIbo.bufferData(gl, indices);
		vbo[IBO_UNIT_01] = unitIbo;
		
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, context.getResources(), R.drawable.units_01);
		textures[TEXTURE_UNITS_01] = texture;
		
		GGLSpriteAnimater[] sprites = new GGLSpriteAnimater[20];
		int idx = 0;
		for(int j = 0; j < 4; ++j) {
			int row = j * 16;
			for(int i = 0; i < 5; ++i) {
				int p = i * 3 + row;
				sprites[idx] = new GGLSpriteAnimater();
				sprites[idx].resistPatterns(new int[][]{{p, p+1, p+2, p+1}, {p+16, p+17, p+18, p+17}, {p+32, p+33, p+34, p+33}, {p+48, p+49, p+50, p+49}});
				idx++;
			}
		}
		
		System.arraycopy(sprites, 0, patterns, PATTERN_UNITS_01, 20); 		
	}
	
	private static final void loadBack(Context context, GL11 gl) {
		{
			float[] vertices = new float[]{-2,-2,-10, -2,2,-10, 2,-2,-10, 2,2,-10,};
			byte[] indices = new byte[]{0,1,2, 2,1,3,};
			
			GGLVertexBufferObject vb = new GGLVertexBufferObject();
			vb.bufferData(gl, vertices);
			vbo[VBO_SKY_01] = vb;
			
			GGLIndexBufferObject ibo = new GGLIndexBufferObject();
			ibo.bufferData(gl, indices);
			vbo[IBO_SKY_01] = ibo;
		}
		
		{
			float positions[] = { 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f,
					0.5f, 0.0f, -0.5f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, };

			GGLVertexBufferObject vb = new GGLVertexBufferObject();
			vb.bufferData(gl, positions, GL10.GL_POINTS);
			vbo[VBO_TREE_01] = vb;
		}
		
		textures[TEXTURE_TREE_01] = loadTexture(gl, context, R.drawable.tree_00);
		textures[TEXTURE_TREE_02] = loadTexture(gl, context, R.drawable.tree_01);
		textures[TEXTURE_TREE_03] = loadTexture(gl, context, R.drawable.tree_02);
	}
	
	public static void release(GL10 gl) {
		GL11 gl11 = (GL11)gl;
		for(int i = 0; i < textures.length; ++i) {
			if(textures[i] == null)
				continue;
			textures[i].delete(gl11);
			textures[i] = null;
		}
		for(int i= 0; i < patterns.length; ++i) {
			patterns[i] = null;
		}
		for(int i= 0; i < vbo.length; ++i) {
			if(vbo[i] == null)
				continue;
			vbo[i].delete(gl11);
			vbo[i] = null;
		}
	}
	
	private static GGLTexture loadTexture(GL10 gl, Context context, int id) {
		GGLTexture texture = new GGLTexture();
		texture.loadTexture(gl, context.getResources(), id);

		return texture;
	}
}
