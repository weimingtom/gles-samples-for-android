package com.tatsun.lib.math;

public class Vector4f {
	public static final Vector4f LEFT  = new Vector4f(-1f, 0, 0, 0);
	public static final Vector4f RIGHT = new Vector4f(1f, 0, 0, 0);
	public static final Vector4f UP    = new Vector4f(0, -1f, 0, 0);
	public static final Vector4f DOWN  = new Vector4f(0, 1f, 0, 0);
	public static final Vector4f NEAR = new Vector4f(0, 0, 1f, 0);
	public static final Vector4f FAR  = new Vector4f(0, 0, -1f, 0);
	
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector4f() {
	}
	
	public Vector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4f(Vector4f vec) {
		this(vec.x, vec.y, vec.z, vec.w);
	}
	
	public Vector4f(Vector3f vec) {
		this(vec.x, vec.y, vec.z, 0);	// TBD
	}
	
	public Vector4f(Vector4f from, Vector4f to) {
		this(to.x - from.x, to.y - from.y, to.z - from.z, to.w - from.w);
	}
	
	public Vector4f set(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}
	
	public Vector4f set(Vector4f from, Vector4f to) {
		this.set(to.x - from.x, to.y - from.y, to.z - from.z, to.w - from.w);
		return this;
	}
	
	public Vector4f normalize() {
		float len = length();
		x = x / len;
		y = y / len;
		z = z / len;
		return this;
	}
	
	public Vector4f offset(float x, float y, float z, float w) {
		this.x += x;
		this.y += y;
		this.z += z;
		this.w += w;
		return this;
	}

	public Vector4f offset(Vector4f offset) {
		offset(offset.x, offset.y, offset.z, offset.w);
		return this;
	}
	
	public float dot(Vector4f vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}
	
		
	public Vector4f cross(Vector4f vec) {
		return cross(vec, new Vector4f());
	}
	
	public Vector4f cross(Vector4f vec, Vector4f ret) {
		ret.x = y*vec.z - vec.y*z;
		ret.y = z*vec.x - vec.z*x;
		ret.z = x*vec.y - vec.x*y;
		return ret;
	}
	
	public Vector4f times(float pow) {
		x *= pow;
		y *= pow;
		z *= pow;
		return this;
	}
	
	public float distance2(Vector4f to) {
		return length2(to.x - x, to.y - y, to.z - z);
	}

	public float distance(Vector4f to) {
		return (float)Math.sqrt(length2(to.x - x, to.y - y, to.z - z));
	}

	public boolean isEquals(Vector4f vec) {
		if(vec == null)
			return false;
		if(x == vec.x && y == vec.y && z == vec.z)
			return true;
		return false;
	}
	
	public float length2() {
		return x * x + y * y + z * z;
	}
	
	public float length() {
		return (float)Math.sqrt(length2());
	}
	
	public static float length2(float x, float y, float z) {
		return x * x + y * y + z * z;
	}

	public static float length(float x, float y, float z) {
		return (float)Math.sqrt(length2(x, y, z));
	}
}
