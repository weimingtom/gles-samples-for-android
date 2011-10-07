package com.tatsun.lib.math;

import android.graphics.PointF;

public class Vector2f extends PointF {
	public static final Vector2f LEFT  = new Vector2f(-1f, 0);
	public static final Vector2f UP    = new Vector2f(0, -1f);
	public static final Vector2f RIGHT = new Vector2f(1f, 0);
	public static final Vector2f DOWN  = new Vector2f(0, 1f);
	
	public Vector2f() {
		super();
	}
	
	public Vector2f(float x, float y) {
		super(x, y);
	}
	
	public Vector2f(PointF vec) {
		super(vec.x, vec.y);
	}
	
	public Vector2f(Vector2f vec) {
		super(vec.x, vec.y);
	}
	
	public Vector2f(PointF from, PointF to) {
		super(to.x - from.x, to.y - from.y);
	}
	
	public Vector2f set(PointF from, PointF to) {
		this.set(to.x - from.x, to.y - from.y);
		return this;
	}
	
	public Vector2f normalize() {
		float len = length();
		x = x / len;
		y = y / len;
		return this;
	}

	public void offset(PointF offset) {
		offset(offset.x, offset.y);
	}
	
	public float dot(PointF vec) {
		return x * vec.x + y * vec.y;
	}
	
	public Vector2f times(float pow) {
		x = x * pow;
		y = y * pow;
		return this;
	}
	
	public float distance(PointF to) {
		return length(to.x - x, to.y - y);
	}
	
	public static float distance(PointF from, PointF to) {
		return length(to.x - from.x, to.y - from.y);
	}
	
	public Vector2f add(float ax, float ay) {
		x += ax;
		y += ay;
		return this;
	}
	
	public Vector2f add(PointF add) {
		return this.add(add.x, add.y);
	}
	
	public boolean isEquals(Vector2f vec) {
		if(vec == null)
			return false;
		if(x == vec.x && y == vec.y)
			return true;
		return false;
	}
	
}
