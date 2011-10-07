package com.tatsun.lib.gm;

import android.os.Bundle;

public final class GmStatus {
	private GmStatus() {
		// not instanciate
	}
	
	static boolean isInitialized = false;
	
	static Class startClazz;
	static Bundle startBundle;
}
