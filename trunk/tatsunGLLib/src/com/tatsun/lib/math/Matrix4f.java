package com.tatsun.lib.math;

import android.opengl.Matrix;

public class Matrix4f {
	final public float[] mat;
    public Matrix4f() {
    	mat = new float[16];
        loadIdentity();
    }
    
    public Matrix4f(float[] mat) {
    	this.mat = new float[16];
    	if(mat != null)
    		System.arraycopy(mat, 0, this.mat, 0, 16);
    }
    
    public static final Matrix4f getInstance(float[] mat) {
    	if(mat == null || mat.length != 16)
    		return null;
    	return new Matrix4f(mat);
    }
    
    public float get(int row, int col) {
        return mat[row*4 + col];
    }

    public void set(int row, int col, float val) {
    	mat[row*4 + col] = val;
    }
    
    public void loadIdentity() {
    	Matrix.setIdentityM(mat, 0);
    }

    public Matrix4f copy(Matrix4f src) {
        System.arraycopy(src.mat, 0, mat, 0, 16);
        return this;
    }

    public Matrix4f copy(float[] src) {
        System.arraycopy(src, 0, mat, 0, 16);
        return this;
    }
    
    
    public void loadRotate(float rot, float x, float y, float z) {
    	Matrix.setRotateM(mat, 0, rot, x, y, z);
    }
    
    public void loadRotate(float x, float y, float z) {
    	Matrix.setRotateEulerM(mat, 0, x, y, z);
    }

    public void loadScale(float x, float y, float z) {
        loadIdentity();
        mat[0] = x;
        mat[5] = y;
        mat[10] = z;
    }

    public void loadTranslate(float x, float y, float z) {
        loadIdentity();
        mat[12] = x;
        mat[13] = y;
        mat[14] = z;
    }

    public void loadOrtho(float l, float r, float b, float t, float n, float f) {
        loadIdentity();
        mat[0] = 2 / (r - l);
        mat[5] = 2 / (t - b);
        mat[10]= -2 / (f - n);
        mat[12]= -(r + l) / (r - l);
        mat[13]= -(t + b) / (t - b);
        mat[14]= -(f + n) / (f - n);
    }

    public void loadFrustum(float l, float r, float b, float t, float n, float f) {
        loadIdentity();
        mat[0] = 2 * n / (r - l);
        mat[5] = 2 * n / (t - b);
        mat[8] = (r + l) / (r - l);
        mat[9] = (t + b) / (t - b);
        mat[10]= -(f + n) / (f - n);
        mat[11]= -1;
        mat[14]= -2*f*n / (f - n);
        mat[15]= 0;
    }
    
    /**
     * 
     * @param lhs
     * @param rhs
     */
    public void loadMultiply(Matrix4f lhs, Matrix4f rhs) {
    	Matrix.multiplyMM(mat, 0, lhs.mat, 0, rhs.mat, 0);
    }
    
    /**
     * 
     * @param rot
     * @param x
     * @param y
     * @param z
     */
    public Matrix4f rotate(float rot, float x, float y, float z) {
    	Matrix.rotateM(mat, 0, rot, x, y, z);
    	return this;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z
     */
    public Matrix4f scale(float x, float y, float z) {
    	Matrix.scaleM(mat, 0, x, y, z);
    	return this;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param z
     */
    public Matrix4f translate(float x, float y, float z) {
    	Matrix.translateM(mat, 0, x, y, z);
    	return this;
    }
    
    /**
     * 	TODO modify
     * @param rhs
     */
    public void multiply(Matrix4f rhs) {
        Matrix4f tmp = new Matrix4f();
        tmp.loadMultiply(this, rhs);
        copy(tmp);
    }
    
    /**
     * 
     * @param vec
     */
    public Vector3f multiply(Vector3f vec) {
    	float x = vec.x * get(0,0) + vec.y * get(0,1) + vec.z * get(0,2);
    	float y = vec.x * get(1,0) + vec.y * get(1,1) + vec.z * get(1,2);
    	float z = vec.x * get(2,0) + vec.y * get(2,1) + vec.z * get(2,2);
    	//float w = 0;
    	vec.set(x, y, z);
    	return vec;
    }
    
    /**
     * 
     * @param vec
     */
    public Vector4f multiply(Vector4f vec) {
    	float x = vec.x * get(0,0) + vec.y * get(0,1) + vec.z * get(0,2) + vec.w * get(0,3);
    	float y = vec.x * get(1,0) + vec.y * get(1,1) + vec.z * get(1,2) + vec.w * get(1,3);
    	float z = vec.x * get(2,0) + vec.y * get(2,1) + vec.z * get(2,2) + vec.w * get(2,3);
    	float w = vec.x * get(3,0) + vec.y * get(3,1) + vec.z * get(3,2) + vec.w * get(3,3);
    	vec.set(x, y, z, w);
    	return vec;
    }
    
    /**
     * 
     */
    public boolean inverse() {
		double val1, val2;
		double[][] mat8x4 = new double[4][8];
		boolean flag = true;
		
		//set value to 8x4
		int idx = 0;
		for(int i = 0; i < 4; i++) {
		    for(int j = 0; j < 4; j++) { 
		    	mat8x4[i][j] = (double)mat[idx++];
		    }
		    for(int j = 0; j < 4; j++) {
		        if(i == j) {
		        	mat8x4[i][j+4] = 1.0;
		        } else {
		        	mat8x4[i][j+4] = 0.0;
		        }
		    }
		}
		
		for(int loop = 0; loop < 4; loop++) {
		    val1 = mat8x4[loop][loop];
		    if(val1 != 1.0) {
		        if(val1 == 0.0) {
		        	int i = 0;
		            for(i = loop + 1; i < 4; i++) {
		                val1 = mat8x4[i][loop];
		                if(val1 != 0.0) break;
		            }
		            if(i >= 4) {
		                flag = false;
		                break;
		            }
		            //
		            int j = 0;
		            for(j = 0; j < 8; j++) {
		                val1 = mat8x4[i][j];
		                mat8x4[i][j] = mat8x4[loop][j];
		                mat8x4[loop][j] = val1;
		            }
		            val1 = mat8x4[loop][loop];
		        }
		
		        for(int i = 0; i < 8; i++)
		        	mat8x4[loop][i] /= val1;
		    }
		    for(int i = 0; i < 4; i++) {
		        if(i != loop) {
		            val1 = mat8x4[i][loop];
		            if(val1 != 0.0f) {
		                for(int j = 0; j < 8; j++) {
		                    val2 = mat8x4[loop][j] * val1;
		                    mat8x4[i][j] -= val2;
		                }
		            }
		        }
		    }
		}
		
		if(flag){
			for(int i = 0; i < 4; ++i) {
				for(int j = 0; j < 4; ++j) {
					mat[i*4+j] = (float)mat8x4[i][j+4];
				}
			}
		}
		
		return flag;
    }
}

//public class Matrix4f {
//	final public float[] mat;
//    public Matrix4f() {
//    	mat = new float[16];
//        loadIdentity();
//    }
//    
//    public float get(int row, int col) {
//        return mat[row*4 + col];
//    }
//
//    public void set(int row, int col, float val) {
//    	mat[row*4 + col] = val;
//    }
//    
//    public void loadIdentity() {
//    	mat[0] = 1;
//    	mat[1] = 0;
//    	mat[2] = 0;
//    	mat[3] = 0;
//
//    	mat[4] = 0;
//    	mat[5] = 1;
//    	mat[6] = 0;
//    	mat[7] = 0;
//
//    	mat[8] = 0;
//    	mat[9] = 0;
//    	mat[10] = 1;
//    	mat[11] = 0;
//
//    	mat[12] = 0;
//        mat[13] = 0;
//        mat[14] = 0;
//        mat[15] = 1;
//    }
//
//    public Matrix4f copy(Matrix4f src) {
//        System.arraycopy(src.mat, 0, mat, 0, 16);
//        return this;
//    }
//
//    public void loadRotate(float rot, float x, float y, float z) {
//        float c, s;
//        mat[3] = 0;
//        mat[7] = 0;
//        mat[11]= 0;
//        mat[12]= 0;
//        mat[13]= 0;
//        mat[14]= 0;
//        mat[15]= 1;
//        rot *= (float)(Math.PI / 180.0f);
//        c = (float)Math.cos(rot);
//        s = (float)Math.sin(rot);
//
//        float len = (float)Math.sqrt(x*x + y*y + z*z);
//        if (!(len != 1)) {
//            float recipLen = 1.f / len;
//            x *= recipLen;
//            y *= recipLen;
//            z *= recipLen;
//        }
//        float nc = 1.0f - c;
//        float xy = x * y;
//        float yz = y * z;
//        float zx = z * x;
//        float xs = x * s;
//        float ys = y * s;
//        float zs = z * s;
//        mat[ 0] = x*x*nc +  c;
//        mat[ 4] =  xy*nc - zs;
//        mat[ 8] =  zx*nc + ys;
//        mat[ 1] =  xy*nc + zs;
//        mat[ 5] = y*y*nc +  c;
//        mat[ 9] =  yz*nc - xs;
//        mat[ 2] =  zx*nc - ys;
//        mat[ 6] =  yz*nc + xs;
//        mat[10] = z*z*nc +  c;
//    }
//
//    public void loadScale(float x, float y, float z) {
//        loadIdentity();
//        mat[0] = x;
//        mat[5] = y;
//        mat[10] = z;
//    }
//
//    public void loadTranslate(float x, float y, float z) {
//        loadIdentity();
//        mat[12] = x;
//        mat[13] = y;
//        mat[14] = z;
//    }
//
//    public void loadOrtho(float l, float r, float b, float t, float n, float f) {
//        loadIdentity();
//        mat[0] = 2 / (r - l);
//        mat[5] = 2 / (t - b);
//        mat[10]= -2 / (f - n);
//        mat[12]= -(r + l) / (r - l);
//        mat[13]= -(t + b) / (t - b);
//        mat[14]= -(f + n) / (f - n);
//    }
//
//    public void loadFrustum(float l, float r, float b, float t, float n, float f) {
//        loadIdentity();
//        mat[0] = 2 * n / (r - l);
//        mat[5] = 2 * n / (t - b);
//        mat[8] = (r + l) / (r - l);
//        mat[9] = (t + b) / (t - b);
//        mat[10]= -(f + n) / (f - n);
//        mat[11]= -1;
//        mat[14]= -2*f*n / (f - n);
//        mat[15]= 0;
//    }
//    
//    /**
//     * 
//     * @param lhs
//     * @param rhs
//     */
//    public void loadMultiply(Matrix4f lhs, Matrix4f rhs) {
//        for (int i=0 ; i<4 ; i++) {
//            float ri0 = 0;
//            float ri1 = 0;
//            float ri2 = 0;
//            float ri3 = 0;
//            for (int j=0 ; j<4 ; j++) {
//                float rhs_ij = rhs.get(i,j);
//                ri0 += lhs.get(j,0) * rhs_ij;
//                ri1 += lhs.get(j,1) * rhs_ij;
//                ri2 += lhs.get(j,2) * rhs_ij;
//                ri3 += lhs.get(j,3) * rhs_ij;
//            }
//            set(i,0, ri0);
//            set(i,1, ri1);
//            set(i,2, ri2);
//            set(i,3, ri3);
//        }
//    }
//    
//    /**
//     * 
//     * @param rot
//     * @param x
//     * @param y
//     * @param z
//     */
//    public void rotate(float rot, float x, float y, float z) {
//        Matrix4f tmp = new Matrix4f();
//        tmp.loadRotate(rot, x, y, z);
//        multiply(tmp);
//    }
//    
//    /**
//     * 
//     * @param x
//     * @param y
//     * @param z
//     */
//    public void scale(float x, float y, float z) {
//        Matrix4f tmp = new Matrix4f();
//        tmp.loadScale(x, y, z);
//        multiply(tmp);
//    }
//    
//    /**
//     * 
//     * @param x
//     * @param y
//     * @param z
//     */
//    public void translate(float x, float y, float z) {
//        Matrix4f tmp = new Matrix4f();
//        tmp.loadTranslate(x, y, z);
//        multiply(tmp);
//    }
//    
//    /**
//     * 	TODO modify
//     * @param rhs
//     */
//    public void multiply(Matrix4f rhs) {
//        Matrix4f tmp = new Matrix4f();
//        tmp.loadMultiply(this, rhs);
//        copy(tmp);
//    }
//    
//    /**
//     * 
//     * @param vec
//     */
//    public Vector3f multiply(Vector3f vec) {
//    	float x = vec.x * get(0,0) + vec.y * get(0,1) + vec.z * get(0,2);
//    	float y = vec.x * get(1,0) + vec.y * get(1,1) + vec.z * get(1,2);
//    	float z = vec.x * get(2,0) + vec.y * get(2,1) + vec.z * get(2,2);
//    	//float w = 0;
//    	vec.set(x, y, z);
//    	return vec;
//    }
//    
//    /**
//     * 
//     * @param vec
//     */
//    public Vector4f multiply(Vector4f vec) {
//    	float x = vec.x * get(0,0) + vec.y * get(0,1) + vec.z * get(0,2) + vec.w * get(0,3);
//    	float y = vec.x * get(1,0) + vec.y * get(1,1) + vec.z * get(1,2) + vec.w * get(1,3);
//    	float z = vec.x * get(2,0) + vec.y * get(2,1) + vec.z * get(2,2) + vec.w * get(2,3);
//    	float w = vec.x * get(3,0) + vec.y * get(3,1) + vec.z * get(3,2) + vec.w * get(3,3);
//    	vec.set(x, y, z, w);
//    	return vec;
//    }
//    
//    /**
//     * 
//     */
//    public boolean inverse() {
//		double val1, val2;
//		double[][] mat8x4 = new double[4][8];
//		boolean flag = true;
//		
//		//set value to 8x4
//		int idx = 0;
//		for(int i = 0; i < 4; i++) {
//		    for(int j = 0; j < 4; j++) { 
//		    	mat8x4[i][j] = (double)mat[idx++];
//		    }
//		    for(int j = 0; j < 4; j++) {
//		        if(i == j) {
//		        	mat8x4[i][j+4] = 1.0;
//		        } else {
//		        	mat8x4[i][j+4] = 0.0;
//		        }
//		    }
//		}
//		
//		for(int loop = 0; loop < 4; loop++) {
//		    val1 = mat8x4[loop][loop];
//		    if(val1 != 1.0) {
//		        if(val1 == 0.0) {
//		        	int i = 0;
//		            for(i = loop + 1; i < 4; i++) {
//		                val1 = mat8x4[i][loop];
//		                if(val1 != 0.0) break;
//		            }
//		            if(i >= 4) {
//		                flag = false;
//		                break;
//		            }
//		            //
//		            int j = 0;
//		            for(j = 0; j < 8; j++) {
//		                val1 = mat8x4[i][j];
//		                mat8x4[i][j] = mat8x4[loop][j];
//		                mat8x4[loop][j] = val1;
//		            }
//		            val1 = mat8x4[loop][loop];
//		        }
//		
//		        for(int i = 0; i < 8; i++)
//		        	mat8x4[loop][i] /= val1;
//		    }
//		    for(int i = 0; i < 4; i++) {
//		        if(i != loop) {
//		            val1 = mat8x4[i][loop];
//		            if(val1 != 0.0f) {
//		                for(int j = 0; j < 8; j++) {
//		                    val2 = mat8x4[loop][j] * val1;
//		                    mat8x4[i][j] -= val2;
//		                }
//		            }
//		        }
//		    }
//		}
//		
//		if(flag){
//			for(int i = 0; i < 4; ++i) {
//				for(int j = 0; j < 4; ++j) {
//					mat[i*4+j] = (float)mat8x4[i][j+4];
//				}
//			}
//		}
//		
//		return flag;
//    }
//}
