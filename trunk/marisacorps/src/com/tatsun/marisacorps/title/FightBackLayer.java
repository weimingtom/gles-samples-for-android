package com.tatsun.marisacorps.title;

import javax.microedition.khronos.opengles.GL11;

import android.os.Bundle;

import com.tatsun.lib.gles.GGLIBinderObject;
import com.tatsun.lib.gles.GGLTexCoordBufferObject;
import com.tatsun.lib.gles.GGLTexture;
import com.tatsun.lib.gles.GGLVertexBufferObject;
import com.tatsun.lib.gm.CameraPlayer;
import com.tatsun.lib.gm.GmService;
import com.tatsun.lib.gm.Layer;
import com.tatsun.marisacorps.GLResources;
import com.tatsun.marisacorps.MyGlobals;

public class FightBackLayer implements Layer {
	public CameraPlayer cameraPlayer;
	GGLTexture texture;
	GGLVertexBufferObject backVbo;
	GGLTexCoordBufferObject backTbo;
	GGLIBinderObject backIbo;
	
	@Override
	public void update(GL11 gl, int frame) {
		
	}

	@Override
	public void onLoad(GL11 gl, Bundle bundle) {
		texture = GLResources.textures[GLResources.TEXTURE_BACKGROUNDS_01];
		backVbo = (GGLVertexBufferObject)GLResources.vbo[GLResources.VBO_SKY_01];
		backTbo = (GGLTexCoordBufferObject)GLResources.vbo[GLResources.TBO_BACK_01];
		backIbo = GLResources.vbo[GLResources.IBO_SKY_01];		
	}

	@Override
	public void draw(GL11 gl11, int frame) {
		GmService.projection.setFrustum(gl11, MyGlobals.PROJ_ORTH_01);
		drawBack(gl11);
	}

	private void drawBack(GL11 gl11) {
		texture.bind(gl11);
		backVbo.bind(gl11);
		backTbo.bind(gl11, 32);
		for(int j = 0; j < 5; ++j) {
			float row = 1 + j * 4;
			for(int i = 0; i < 15; ++i) {
				float col = -29 + i * 4;
				gl11.glPushMatrix();
				{
					gl11.glTranslatef(col, row, 0);
					backIbo.draw(gl11);
				}
				gl11.glPopMatrix();
			}
		}
		backVbo.unbind(gl11);
		backTbo.unbind(gl11);
		texture.unbind(gl11);

	}
}
