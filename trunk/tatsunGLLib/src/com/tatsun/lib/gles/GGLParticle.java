package com.tatsun.lib.gles;
//package com.tatsun.lib.gles;
//
//import javax.microedition.khronos.opengles.GL10;
//
//public class GGLParticle {
//
//	public float mX;
//	public float mY;
//	public float mSize;
//	public boolean mIsActive;
//	public float mMoveX;//1フレームあたりのX軸方向の移動量
//	public float mMoveY;//1フレームあたりのY軸方向の移動量
//	public int mFrameNumber;//生成からの時間(フレーム数)
//	public int mLifeSpan;//寿命(フレーム数)
//	
//	public GGLParticle() {
//		this.mX = 0.0f;
//		this.mY = 0.0f;
//		this.mSize = 0.0f;
//		this.mIsActive = false;
//		this.mMoveX = 0.0f;
//		this.mMoveY = 0.0f;
//		this.mFrameNumber = 0;
//		this.mLifeSpan = 60;//初期状態では寿命を60フレームにする
//	}
//	
//	public void reset(){
//		
//	}
//	
//	public void draw(GL10 gl, int texture) {
//		if (mIsActive) {
//			//現在のフレームが、寿命の間のどの位置にあるのかを計算する
//			float lifePercentage = (float)mFrameNumber / (float)mLifeSpan;
//			float alpha = 1.0f;
//			if (lifePercentage <= 0.5f) {//寿命が半分以上残っている場合
//				alpha = lifePercentage * 2.0f;
//			} else {
//				alpha = 1.0f - lifePercentage;
//			}
//			GGLUtils.drawTexture(gl, mX, mY, mSize, mSize, texture, 1.0f, 1.0f, 1.0f, alpha);
//		}
//	}
//	
//	public void update() {
//		mFrameNumber++;//フレームをカウントする
//		if (mFrameNumber >= mLifeSpan) mIsActive = false;//寿命に達していたら非アクティブにする
//		
//		mX += mMoveX;
//		mY += mMoveY;
//	}
//}
