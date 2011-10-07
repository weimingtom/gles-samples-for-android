package com.tatsun.lib.gles;
//package com.tatsun.lib.gles;
//
//import java.lang.reflect.Array;
//import java.nio.FloatBuffer;
//
//import javax.microedition.khronos.opengles.GL10;
//
//
//public abstract class GGLParticleSystem<T extends GGLParticle> {
//
//	public final int mPapacity;
//	
//	// パーティクル
//	private GGLParticle[] mParticles;
//	
//	public GGLParticleSystem(int capacity, int particleLifeSpan) {
//		this.mPapacity = capacity;
//		mParticles = new GGLParticle[mPapacity];
//		
//		GGLParticle[] particles = mParticles;
//		for (int i = 0; i < mPapacity; i++) {
//			particles[i] = create();
//			particles[i].mLifeSpan = particleLifeSpan;
//		}	
//	}
//	
//	abstract protected T create();
//	
//	public void add(float x, float y, float size, float moveX, float moveY) {
//		GGLParticle[] particles = mParticles;
//		for (int i = 0; i < mPapacity; i++) {
//			if (!particles[i].mIsActive) {//非アクティブのパーティクルを探す
//				particles[i].mIsActive = true;
//				particles[i].mX = x;
//				particles[i].mY = y;
//				particles[i].mSize = size;
//				particles[i].mMoveX = moveX;
//				particles[i].mMoveY = moveY;
//				particles[i].mFrameNumber = 0;
//				break;
//			}
//		}
//	}
//	
//	public void draw(GL10 gl, int texture) {
//		GGLParticle[] particles = mParticles;
//		//頂点の配列
//		//1つのパーティクルあたり6頂点×2要素
//		float[] vertices = GGLTemporaryCache.getVerticesPool(6 * 2 * mPapacity);
//		
//		//色の配列
//		//1つのパーティクルあたりの6頂点×要素(r,g,b,a)×最大のパーティクル数
//		float[] colors = GGLTemporaryCache.getColorsPool(6 * 4 * mPapacity);
//		
//		//テクスチャマッピングの配列
//		//1つのパーティクルあたり6頂点×2要素(x,y)×最大のパーティクル数
//		float[] coords = GGLTemporaryCache.getTexCoordsPool(6 * 2 * mPapacity);
//		
//		//アクティブなパーティクルのカウント
//		int vertexIndex = 0;
//		int colorIndex = 0;
//		int texCoordIndex = 0;
//		
//		int activePaticleCount = 0;
//		
//		for (int i = 0; i < mPapacity; i++) {
//			// 　状態がアクティブのパーティクルのみ描画します
//			if (particles[i].mIsActive) {
//				//頂点座標を追加します
//				float centerX = particles[i].mX;
//				float centerY = particles[i].mY;
//				float size = particles[i].mSize;
//				float vLeft = -0.5f * size + centerX;
//				float vRight = 0.5f * size + centerX;
//				float vTop = 0.5f * size + centerY;
//				float vBottom = -0.5f* size + centerY;
//				
//				//ポリゴン1
//				vertices[vertexIndex++] = vLeft;
//				vertices[vertexIndex++] = vTop;
//				vertices[vertexIndex++] = vRight;
//				vertices[vertexIndex++] = vTop;
//				vertices[vertexIndex++] = vLeft;
//				vertices[vertexIndex++] = vBottom;
//				
//				//ポリゴン2
//				vertices[vertexIndex++] = vRight;
//				vertices[vertexIndex++] = vTop;
//				vertices[vertexIndex++] = vLeft;
//				vertices[vertexIndex++] = vBottom;
//				vertices[vertexIndex++] = vRight;
//				vertices[vertexIndex++] = vBottom;
//				
//				//色
//				float lifePercentage = (float)particles[i].mFrameNumber/(float)particles[i].mLifeSpan;
//				float alpha;
//				if (lifePercentage <= 0.5f) {
//					alpha = lifePercentage * 2.0f;
//				} else {
//					alpha = 1.0f - lifePercentage;
//				}
//				
//				for (int j = 0; j < 6; j++) {
//					colors[colorIndex++] = 1.0f;
//					colors[colorIndex++] = 1.0f;
//					colors[colorIndex++] = 1.0f;
//					colors[colorIndex++] = alpha;
//				}
//				
//				//マッピング座標
//				//ポリゴン1
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 1.0f;
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 1.0f;
//				//ポリゴン2
//				coords[texCoordIndex++] = 1.0f;
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 0.0f;
//				coords[texCoordIndex++] = 1.0f;
//				coords[texCoordIndex++] = 1.0f;
//				coords[texCoordIndex++] = 1.0f;
//				
//				//アクティブパーティクルの数を数えます
//				activePaticleCount++;
//			}
//		}
//		
//		FloatBuffer verticesBuffer = GGLTemporaryCache.makeVertexBufferByPool(vertices);
//		FloatBuffer colorBuffer = GGLTemporaryCache.makeColorBufferByPool(colors);
//		FloatBuffer coordBuffer = GGLTemporaryCache.makeTexCoordBufferByPool(coords);
//		
//		gl.glEnable(GL10.GL_TEXTURE_2D);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
//		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, verticesBuffer);
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//		
//		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, coordBuffer);
//		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		
//		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, activePaticleCount * 6);
//		
//		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(GL10.GL_TEXTURE_2D);
//	}
//	
//	public void update() {
//		GGLParticle[] particles = mParticles;
//		for (int i = 0; i < mPapacity; i++) {
//			if (particles[i].mIsActive) {//アクティブのパーティクルを更新する
//				particles[i].update();
//			}
//		}
//	}
//}
