package com.tatsun.lib.gles;


public class GGLSpriteCoordFactory {
	
//	public static DrawableSprite create(Context context, int[] resourceId, int totalFrame) {
//		return new DrawableMultiSprite(context, resourceId, totalFrame);
//	}
//
//	public static DrawableSprite create(Context context, int[] resourceId, int totalFrame, boolean nocache) {
//		return new DrawableMultiSprite(context, resourceId, totalFrame, nocache);
//	}
//
//	public static DrawableSprite create(Context context, int resourceId, int totalFrame, int cols, int rows) {
//		return new DrawableDevidedSprite(context, resourceId, totalFrame, cols, rows);
//	}
//
//	public static DrawableSprite create(Context context, int resourceId, int totalFrame, int cols, int rows, int startIdx, int chipCount) {
//		return new DrawableDevidedSprite(context, resourceId, totalFrame, cols, rows, startIdx, chipCount);
//	}
//
//	public static DrawableSprite create(Context context, int resourceId, int totalFrame, int cols, int rows, int[] chipPattern) {
//		return new DrawableDevidedSprite(context, resourceId, totalFrame, cols, rows, chipPattern);
//	}
//
//	public static DrawableSprite create(Context context, int resourceId, int totalFrame, Rect[] chips) {
//		return new DrawableDevidedSprite(context, resourceId, totalFrame, chips);
//	}
//
//	public static DrawableSprite create(Context context, int resourceId, int totalFrame, int cols, int rows, int[][] chipPatterns) {
//		return new DrawableListSprite(context, resourceId, totalFrame, cols, rows, chipPatterns);
//	}


	public static final float[] createDevidedSpriteCoords(int cols, int rows) {
		return createDevidedSpriteCoords(cols, rows, 0, 0, cols, rows);
	}
	
	public static final float[] createDevidedSpriteCoords(int cols, int rows, int minCol, int minRow, int maxCol, int maxRow) {
		float width = 1f / cols;
		float height = 1f / rows;
		
		int patternCount = cols * rows;
		float[] coords = GGLTemporaryCache.getTexCoordsPool(patternCount * 4 * 2);
		int idx = 0;
		
		for(int r = minRow; r < maxRow; ++r) {
			float v = r * height;
			for(int c = minCol; c < maxCol; ++c) {
				float u = c * width;
				coords[idx++] = u;
				coords[idx++] = v;
				coords[idx++] = u;
				coords[idx++] = v + height;
				coords[idx++] = u + width;
				coords[idx++] = v;
				coords[idx++] = u + width;
				coords[idx++] = v + height;
			}
		}
		return coords;
	}
	
}


/*
class DrawableDevidedSprite implements GGLSpriteCoordFactory{
	private Bitmap bitmap;
	private Rect[] srcRect;
	private RectF workRect = new RectF();

	private int width;
	private int height;
	private float hx;
	private float hy;
	private int totalFrame;
	private float perFrame;
	private int bitmapCount;

	public DrawableDevidedSprite(Context context, int resourceId, int totalFrame, int cols, int rows) {
		this.totalFrame = totalFrame;
		this.bitmapCount = cols * rows;
		this.perFrame = (float) totalFrame / (float)bitmapCount;
		
		bitmap = BitmapLoader.loadBitmap(context, resourceId);

		setSize(bitmap.getWidth() / cols, bitmap.getHeight() / rows);
		srcRect = new Rect[bitmapCount];
		
		int idx = 0;
		for(int j = 0; j < rows; ++j) {
			for(int i = 0; i < cols; ++i) {
				int x = i * width, y = j * height;
				srcRect[idx++] = new Rect(x, y, x + width, y + height);
			}
		}
	}

	public DrawableDevidedSprite(Context context, int resourceId, int totalFrame, int cols, int rows, int startIdx, int chipCount) {
		this.totalFrame = totalFrame;
		this.bitmapCount = chipCount;
		this.perFrame = (float) totalFrame / (float)bitmapCount;
		
		bitmap = BitmapLoader.loadBitmap(context, resourceId);

		setSize(bitmap.getWidth() / cols, bitmap.getHeight() / rows);
		srcRect = new Rect[bitmapCount];
		
		int idx = 0;
		int chipIdx = 0;
		for(int j = 0; j < rows; ++j) {
			for(int i = 0; i < cols; ++i) {
				if(chipIdx < startIdx || chipIdx > startIdx + chipCount) {
					chipIdx++;
					continue;
				}
				int x = i * width, y = j * height;
				srcRect[idx++] = new Rect(x, y, x + width, y + height);
				chipIdx++;
			}
		}
	}
	
	public DrawableDevidedSprite(Context context, int resourceId, int totalFrame, int cols, int rows, int[] chipPattern) {
		this.totalFrame = totalFrame;
		this.bitmapCount = chipPattern.length;
		this.perFrame = (float) totalFrame / (float)bitmapCount;
		
		bitmap = BitmapLoader.loadBitmap(context, resourceId);

		setSize(bitmap.getWidth() / cols, bitmap.getHeight() / rows);
		srcRect = new Rect[bitmapCount];
		
		for(int i = 0; i < chipPattern.length; ++i) {
			int pos = chipPattern[i];
			int col = pos % cols, row = pos / cols;
			int x = col * width, y = row * height;
			srcRect[i] = new Rect(x, y, x + width, y + height);
		}
	}
	
	public DrawableDevidedSprite(Context context, int resourceId, int totalFrame, Rect[] chips) {
		this.totalFrame = totalFrame;
		this.bitmapCount = chips.length;
		this.perFrame = (float) totalFrame / (float)bitmapCount;
		
		bitmap = BitmapLoader.loadBitmap(context, resourceId);

		setSize(chips[0].width(), chips[0].height());
		srcRect = new Rect[chips.length];
		
		for(int i = 0; i < chips.length; ++i) {
			Rect r = chips[i];
			srcRect[i] = new Rect(r.left, r.top, r.right, r.bottom);
		}
	}
	
	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		setHotSpot(width / 2f, height / 2f);
	}
	
	@Override
	public void setHotSpot(float hx, float hy) {
		this.hx = hx;
		this.hy = hy;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	public int getTotalFrame() {
		return totalFrame;
	}

	private Rect getCurrentRect(int frameCount) {
		int idx = 0;
		idx = (int) (frameCount / perFrame) % bitmapCount;
		if(srcRect[idx] == null)
			Log.i("tatshlib", "srcRect is null.frame:"+frameCount+", idx:"+idx+", perFrame:"+perFrame+", bitmapCount:"+bitmapCount);
		return srcRect[idx];
	}

	@Override
	public void draw(Canvas canvas, int frameCount, float posx, float posy,
			Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx - hx, t = posy - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount), workRect, paint);
	}

	@Override
	public void draw(Canvas canvas, int frameCount, RectF dstRect, Paint paint) {
		if(paint == null)
			paint = defaultPaint;

		canvas.drawBitmap(bitmap, getCurrentRect(frameCount), dstRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount), workRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, Matrix matrix, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		canvas.drawBitmap(bitmap, matrix, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			Paint paint) {
		float l = pos.x - hx, t = pos.y - hy;
		if(paint == null)
			paint = defaultPaint;

		canvas.save();
		canvas.rotate(degree, pos.x, pos.y);
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount), workRect, paint);
		canvas.restore();
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			PointF center, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;

		canvas.save();
		canvas.rotate(degree, center.x, center.y);
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount), workRect, paint);
		canvas.restore();
	}

	@Override
	public void draw(Canvas canvas, int frameCount, float posx, float posy,
			Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx - hx, t = posy - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, srcRect[setNumber % bitmapCount], workRect, paint);
	}

	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, Paint paint,
			int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, srcRect[setNumber % bitmapCount], workRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, pos.x, pos.y);
		canvas.drawBitmap(bitmap, srcRect[setNumber % bitmapCount], workRect, paint);
		canvas.restore();
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			PointF center, Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, center.x, center.y);
		canvas.drawBitmap(bitmap, srcRect[setNumber % bitmapCount], workRect, paint);
		canvas.restore();
	}

	@Override
	public void drawFromPosition(Canvas canvas, int frameCount, PointF pos, RectF dest, Paint paint) {
		throw new RuntimeException("unsupported.");
	}

	@Override
	public void drawFromPosition(Canvas canvas, int frameCount, float left, float top, RectF dest, Paint paint) {
		throw new RuntimeException("unsupported.");
	}

	@Override
	public void drawFromZero(Canvas canvas, int frameCount,  float posx, float posy, Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx, t = posy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, srcRect[setNumber % bitmapCount], workRect, paint);
	}
}


class DrawableListSprite implements GGLSpriteCoordFactory {
	private Bitmap bitmap;
	private Rect[][] srcRects;
	private RectF workRect = new RectF();

	private int width;
	private int height;
	private float hx;
	private float hy;
	private int totalFrame;
	private float perFrame;
	private int bitmapCount;
	private int setCount;

	
	public DrawableListSprite(Context context, int resourceId, int totalFrame, int cols, int rows, int[][] chipPattern) {
		this.totalFrame = totalFrame;
		this.setCount = chipPattern.length;
		this.bitmapCount = chipPattern[0].length;
		this.perFrame = (float) totalFrame / (float)bitmapCount;
		
		bitmap = BitmapLoader.loadBitmap(context, resourceId);

		setSize(bitmap.getWidth() / cols, bitmap.getHeight() / rows);
		srcRects = new Rect[setCount][bitmapCount];
		
		for(int j = 0; j < setCount; ++j) {
			for(int i = 0; i < bitmapCount; ++i) {
				int pos = chipPattern[j][i];
				int col = pos % cols, row = pos / cols;
				int x = col * width, y = row * height;
				srcRects[j][i] = new Rect(x, y, x + width, y + height);
			}
		}
	}
	
	private void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		setHotSpot(width / 2f, height / 2f);
	}
	
	@Override
	public void setHotSpot(float hx, float hy) {
		this.hx = hx;
		this.hy = hy;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	public int getTotalFrame() {
		return totalFrame;
	}

	private Rect getCurrentRect(int frameCount, int setNumber) {
		int idx = 0;
		idx = (int) (frameCount / perFrame) % bitmapCount;
		if(srcRects[setNumber][idx] == null)
			Log.i("tatshlib", "srcRect is null.frame:"+frameCount+", idx:"+idx+", perFrame:"+perFrame+", bitmapCount:"+bitmapCount+", setNumber:"+setNumber);
		return srcRects[setNumber][idx];
	}

	@Override
	public void draw(Canvas canvas, int frameCount, float posx, float posy,
			Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx - hx, t = posy - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, 0), workRect, paint);
	}

	@Override
	public void draw(Canvas canvas, int frameCount, RectF dstRect, Paint paint) {
		if(paint == null)
			paint = defaultPaint;

		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, 0), dstRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, 0), workRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, Matrix matrix, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		canvas.drawBitmap(bitmap, matrix, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, pos.x, pos.y);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, 0), workRect, paint);
		canvas.restore();
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			PointF center, Paint paint) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, center.x, center.y);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, 0), workRect, paint);
		canvas.restore();
	}

	@Override
	public void draw(Canvas canvas, int frameCount, float posx, float posy,
			Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx - hx, t = posy - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, setNumber), workRect, paint);
	}

	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, Paint paint,
			int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, setNumber), workRect, paint);
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, pos.x, pos.y);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, setNumber), workRect, paint);
		canvas.restore();
	}
	
	@Override
	public void draw(Canvas canvas, int frameCount, PointF pos, float degree,
			PointF center, Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = pos.x - hx, t = pos.y - hy;
		workRect.set(l, t, l+width, t+height);

		canvas.save();
		canvas.rotate(degree, center.x, center.y);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, setNumber), workRect, paint);
		canvas.restore();
	}
	
	@Override
	public void drawFromPosition(Canvas canvas, int frameCount, PointF pos, RectF dest, Paint paint) {
		throw new RuntimeException("unsupported.");
	}

	@Override
	public void drawFromPosition(Canvas canvas, int frameCount, float left, float top, RectF dest, Paint paint) {
		throw new RuntimeException("unsupported.");
	}

	@Override
	public void drawFromZero(Canvas canvas, int frameCount,  float posx, float posy, Paint paint, int setNumber) {
		if(paint == null)
			paint = defaultPaint;
		float l = posx, t = posy;
		workRect.set(l, t, l+width, t+height);
		canvas.drawBitmap(bitmap, getCurrentRect(frameCount, setNumber), workRect, paint);
	}
}
*/
