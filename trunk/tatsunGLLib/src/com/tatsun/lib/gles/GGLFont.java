package com.tatsun.lib.gles;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;

import com.tatsun.lib.gles.helper.GGLSquareShape;

public class GGLFont {
	private static final String characters = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^|`abcdefghijklmnopqrstuvwxyz{|}~ " +
			"ぁあぃいぅうぇえぉおかがきぎくぐけげこごさざしじすずせぜそぞただちぢつづてでとどなにぬねのはばぱひびぴふぶぷへべぺほぼぽまみむめもゃやゅゆょよらりるれろわゐゑをん　　　　　　　　　　　　　　　" +
			"ァアィイゥウェエォオカガキギクグケゲコゴサザシジスズセゼソゾタダチヂツヅテデトドナニヌネノハバパヒビピフブプヘベペホボポマミムメモャヤュユョヨラリルレロワヰヱヲン　　　　　　　　　　　　　　　";
	private static final int TEXTURE_WIDTH = 256;
	private static final int TEXTURE_HEIGHT = 256;
	private static final int FONT_WIDTH = 12;//18;
	private static final int FONT_HEIGHT = 16;//25;
	private static final float FONT_ASPECT = 0.75f;
	private static final int BYTECODE_OFFSET = 0x2fc1;
	private static final int CHARACTOER_KINDS = characters.length();
	private GGLSquareShape vbo = new GGLSquareShape();
	private GGLVertexCoordsBufferObject uvbo = new GGLVertexCoordsBufferObject();
	private int texture;

	/**
	 * 
	 * @param gl
	 */
	public GGLFont() {
	}
	
	public void load(GL11 gl11) {
		Bitmap bitmap = Bitmap.createBitmap(TEXTURE_WIDTH, TEXTURE_HEIGHT, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		createFontTexture(canvas);
		
		texture = GGLUtils.loadTexture(gl11, bitmap, GL10.GL_LINEAR, 0);
		bitmap.recycle();
		
		createTexCoordMap(gl11);
		
		vbo.create(gl11, true);
	}
	
	/**
	 * 
	 * @param paint
	 */
	private void createFontTexture(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setTextSize(FONT_HEIGHT-3);
		FontMetrics fontMetrics = paint.getFontMetrics();
		
		int cols = TEXTURE_WIDTH / FONT_WIDTH;
		float fontWOffset = FONT_WIDTH / 2f;
		float fontHOffset = FONT_HEIGHT - fontMetrics.bottom;
		
		canvas.drawColor(Color.TRANSPARENT);
		
		for(int i= 0; i < characters.length(); ++i) {
			if(i == 97) {
				paint.setTextSize(FONT_HEIGHT-4);
				fontWOffset -= 1;
			}
			int col = i % cols;
			int row = i / cols;
			String c = ""+characters.charAt(i);
			canvas.drawText(c, fontWOffset+FONT_WIDTH*col, fontHOffset+FONT_HEIGHT*row, paint);
		}
	}
	
	private void createTexCoordMap(GL11 gl11) {
		int cols = TEXTURE_WIDTH / FONT_WIDTH;
		int rows = TEXTURE_HEIGHT / FONT_HEIGHT;
		int vertexCount = 3 * 4;
		float[] vertices = GGLTemporaryCache.getVerticesPool(vertexCount);
		int vidx = 0;
		float bw = 0.5f*FONT_ASPECT, bh = 0.5f;
		vertices[vidx++] = -bw;
		vertices[vidx++] = bh;
		vertices[vidx++] = 0;
		vertices[vidx++] = -bw;
		vertices[vidx++] = -bh;
		vertices[vidx++] = 0;
		vertices[vidx++] = bw;
		vertices[vidx++] = bh;
		vertices[vidx++] = 0;
		vertices[vidx++] = bw;
		vertices[vidx++] = -bh;
		vertices[vidx++] = 0;
		
		
		int coordsCount = cols * rows * 2 * 4;
		float[] texCoords = GGLTemporaryCache.getTexCoordsPool(coordsCount);
		float textureWidth = (float)FONT_WIDTH / (float)TEXTURE_WIDTH;
		float textureHEIGHT = (float)FONT_HEIGHT / (float)TEXTURE_HEIGHT;
		int idx = 0;
		
		for(int j = 0; j < rows; ++j) {
			float v = j * textureHEIGHT;
			for(int i = 0; i < cols; ++i) {
				float u = i * textureWidth;
				texCoords[idx++] = u;
				texCoords[idx++] = v;
				texCoords[idx++] = u;
				texCoords[idx++] = v+textureHEIGHT;
				texCoords[idx++] = u+textureWidth;
				texCoords[idx++] = v;
				texCoords[idx++] = u+textureWidth;
				texCoords[idx++] = v+textureHEIGHT;
			}
		}
		
		uvbo.bufferData(gl11, vertices, texCoords, GL10.GL_TRIANGLE_STRIP);
	}
	
	/**
	 * 
	 * @param gl
	 */
	public void drawBaseTexture(GL10 gl) {
		GL11 gl11 = (GL11)gl;
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		vbo.bind(gl11);
		vbo.draw(gl11);
		vbo.unbind(gl11);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	/**
	 * 
	 * @param gl11
	 * @param text
	 * @param x
	 * @param y
	 * @param unitSize
	 */
	public void drawText(GL11 gl11, String text, float x, float y, float unitSize) {
		if(text == null || text.length() == 0)
			return;
		
		gl11.glEnable(GL10.GL_TEXTURE_2D);
		gl11.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
		gl11.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, uvbo.bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		
		for(int i = 0; i < text.length(); ++i) {
			int ch = ((int)text.charAt(i) - 32) % BYTECODE_OFFSET % CHARACTOER_KINDS;
			gl11.glPushMatrix();
			{
				gl11.glTranslatef(x + i * unitSize * FONT_ASPECT, y, 0);
				gl11.glScalef(unitSize, unitSize, unitSize);
				gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, uvbo.texCoordsOffset + ch*uvbo.uvUnitSize);
				uvbo.draw(gl11);
			}
			gl11.glPopMatrix();
		}
		
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
		gl11.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		gl11.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl11.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	/**
	 * 
	 * @param gl11
	 */
	public void delete(GL11 gl11) {
		vbo.delete(gl11);
		uvbo.delete(gl11);
	}
}
