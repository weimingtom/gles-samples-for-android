package com.tatsun.lib.gles;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL11;

/**
 * 
 *	TODO guarded object
 */
public class GGLResourceCache {
	
	private Map<Integer, GGLResource> pool = new Hashtable<Integer, GGLResource>();
 
	public final boolean containsResource(int key) {
		return pool.containsKey(key);
	}
	
	public final GGLResource getResource(int key) {
		if(pool.containsKey(key)) {
			return pool.get(key);
		}
		return null;
	}
	
	public final void addResource(int key, GGLResource resource) {
		pool.put(key, resource);
	}
 
	public final void deleteResource(GL11 gl11, int key) {
		if (!pool.containsKey(key))
			return;
			
		GGLResource resource = pool.get(key);
		pool.remove(key);
		resource.delete(gl11);
	}
	
	public final void deleteAll(GL11 gl11) {
		if(pool.size() == 0)
			return;
		
		List<GGLResource> values = new ArrayList<GGLResource>(pool.values());
		pool.clear();
		for (GGLResource resource : values) {
			resource.delete(gl11);
		}
	}
}

