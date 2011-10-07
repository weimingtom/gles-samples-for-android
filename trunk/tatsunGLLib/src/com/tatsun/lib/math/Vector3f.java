package com.tatsun.lib.math;

public class Vector3f {
	public static final Vector3f LEFT  = new Vector3f(-1f, 0, 0);
	public static final Vector3f RIGHT = new Vector3f(1f, 0, 0);
	public static final Vector3f UP    = new Vector3f(0, -1f, 0);
	public static final Vector3f DOWN  = new Vector3f(0, 1f, 0);
	public static final Vector3f NEAR = new Vector3f(0, 0, 1f);
	public static final Vector3f FAR  = new Vector3f(0, 0, -1f);
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f() {
	}
	
	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(Vector3f vec) {
		this(vec.x, vec.y, vec.z);
	}
	
	public Vector3f(Vector3f from, Vector3f to) {
		this(to.x - from.x, to.y - from.y, to.z - from.z);
	}
	
	public Vector3f set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vector3f set(Vector3f vec) {
		this.set(vec.x, vec.y, vec.z);
		return this;
	}
	
	public Vector3f set(Vector3f from, Vector3f to) {
		this.set(to.x - from.x, to.y - from.y, to.z - from.z);
		return this;
	}
	
	public Vector3f normalize() {
		float len = length();
		x = x / len;
		y = y / len;
		z = z / len;
		return this;
	}
	
	public Vector3f offset(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public Vector3f offset(Vector3f offset) {
		offset(offset.x, offset.y, offset.z);
		return this;
	}
	
	public float dot(Vector3f vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}
	
		
	public Vector3f cross(Vector3f vec) {
		return cross(vec, new Vector3f());
	}
	
	public Vector3f cross(Vector3f vec, Vector3f ret) {
		ret.x = y*vec.z - vec.y*z;
		ret.y = z*vec.x - vec.z*x;
		ret.z = x*vec.y - vec.x*y;
		return ret;
	}
	
	public Vector3f times(float pow) {
		x *= pow;
		y *= pow;
		z *= pow;
		return this;
	}
	
	public float distance2(Vector3f to) {
		return length2(to.x - x, to.y - y, to.z - z);
	}

	public float distance(Vector3f to) {
		return (float)Math.sqrt(length2(to.x - x, to.y - y, to.z - z));
	}

	public boolean isEquals(Vector3f vec) {
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
