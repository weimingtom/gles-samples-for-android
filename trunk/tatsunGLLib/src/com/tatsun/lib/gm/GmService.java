package com.tatsun.lib.gm;

import com.tatsun.lib.gles.GGLFont;
import com.tatsun.lib.gles.helper.GGLProjectionCache;

public class GmService {
	public static GGLFont font = new GGLFont();
	public static GGLProjectionCache projection = new GGLProjectionCache();
	public static ContentLoader contentLoader = new ContentLoader();
	public static SceneContainer sceneContainer = new SceneContainer();
	public static SceneController sceneController = new SceneController();
	public static MotionPool motionPool = new MotionPool();
}
