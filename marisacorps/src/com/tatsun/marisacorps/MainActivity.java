package com.tatsun.marisacorps;

import javax.microedition.khronos.opengles.GL10;

import com.tatsun.lib.gm.GmService;
import com.tatsun.lib.gm.RenderSurfaceView;
import com.tatsun.lib.gm.RenderSurfaceView;
import com.tatsun.marisacorps.title.TitleScene;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
	private static final String TAG = "debugTask";
	private Handler handler = new Handler();
	private Runnable debugTask = new Runnable(){
		@Override
		public void run() {
			Runtime runtime = Runtime.getRuntime();
			Log.i("Runtime", "total:" + (int)(runtime.totalMemory()/1024)
					+ "kb, free:" + (int)(runtime.freeMemory()/1024)
					+ "kb, used:" + (int)( (runtime.totalMemory() - runtime.freeMemory())/1024)
					+ "kb, max:" + (int)(runtime.maxMemory()/1024));
			
			handler.postDelayed(debugTask, 2000);
		}
	};
	public RenderSurfaceView renderSurfaceView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		
		//renderSurfaceView = new RenderSurfaceView(this.getApplicationContext());
		//renderSurfaceView = new RenderSurfaceView(this.getApplicationContext());
		renderSurfaceView = new MyRenderSurfaceView(this.getApplicationContext());
		renderSurfaceView.setStartScene(TitleScene.class, null);
		
		this.setContentView(renderSurfaceView, new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }
    
    
    @Override
    protected void onDestroy() {
    	Log.i(TAG, "onDestroy");
    	super.onDestroy();
    	renderSurfaceView.onDestroy();
    }
    
    @Override
    protected void onStart() {
    	Log.i(TAG, "onStart");
    	super.onStart();
    }
    
    @Override
    protected void onRestart() {
    	Log.i(TAG, "onRestart");
    	super.onRestart();
    }
    
    @Override
    protected void onResume() {
    	Log.i(TAG, "onResume");
    	super.onResume();
    	renderSurfaceView.onResume();
    }
    
    @Override
    protected void onStop() {
    	Log.i(TAG, "onStop");
    	super.onStop();
    }
    
    @Override
    protected void onPause() {
    	Log.i(TAG, "onPause");
    	super.onPause();
    	renderSurfaceView.onPause();
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	Log.i(TAG, "onWindowFocusChanged:" + hasFocus);
    	super.onWindowFocusChanged(hasFocus);
    }
}