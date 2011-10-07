package com.tatsun.openglsamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SecondActivity extends Activity implements
		AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
	private static class ListItem {
		@SuppressWarnings("rawtypes")
		public Class clazz;
		public String explain;

		public ListItem(String explain,
				@SuppressWarnings("rawtypes") Class clazz) {
			this.clazz = clazz;
			this.explain = explain;
		}
	}

	private static final ListItem[][] activities = new ListItem[][] {
			// foundation
			{
					new ListItem("clear by color",
							com.tatsun.openglsamples.samples.Sample_01_01.class),
					new ListItem("simple GL_TRIANGLES",
							com.tatsun.openglsamples.samples.Sample_01_02.class),
					new ListItem("simple GL_TRIANGLE_STRIP",
							com.tatsun.openglsamples.samples.Sample_01_03.class),
					new ListItem("simple texture",
							com.tatsun.openglsamples.samples.Sample_01_04.class),
					new ListItem("simple index drawElements",
							com.tatsun.openglsamples.samples.Sample_01_05.class),
			// glPolygonStipple
			},
			// VBO
			{
					new ListItem("vertex only",
							com.tatsun.openglsamples.samples.Sample_02_01.class),
					new ListItem("vertex, uv",
							com.tatsun.openglsamples.samples.Sample_02_02.class),
					new ListItem("vertex, uv, index",
							com.tatsun.openglsamples.samples.Sample_02_03.class),
					new ListItem("vertex, uv (separate)",
							com.tatsun.openglsamples.samples.Sample_02_04.class),
					new ListItem("vertex, color, normal",
							com.tatsun.openglsamples.samples.Sample_02_05.class),
					new ListItem("use vbo class for vertex, uv, index",
							com.tatsun.openglsamples.samples.Sample_02_06.class),
					new ListItem(
							"use same vbo for vertex, uv, normal, color and index",
							com.tatsun.openglsamples.samples.Sample_02_07.class),
			// vbo animate
			},
			// texture
			{
					new ListItem("tile pattern texture",
							com.tatsun.openglsamples.samples.Sample_03_01.class),
					new ListItem("brend GL_ONE_MINUS_SRC_ALPHA texture",
							com.tatsun.openglsamples.samples.Sample_03_02.class),
					new ListItem("brend GL_ONE texture",
							com.tatsun.openglsamples.samples.Sample_03_03.class),
					new ListItem("save backbaffer texture",
							com.tatsun.openglsamples.samples.Sample_03_04.class),
					new ListItem("alpha test GL_ALPHA_TEST",
							com.tatsun.openglsamples.samples.Sample_03_05.class),
					new ListItem("texture sprite draw",
							com.tatsun.openglsamples.samples.Sample_03_06.class),
					// matrixMode GL_TEXTURE
					new ListItem("texture sprite2 draw",
							com.tatsun.openglsamples.samples.Sample_03_07.class),
					new ListItem("point sprite",
							com.tatsun.openglsamples.samples.Sample_03_08.class),
			// glTexSubImage2D
			// http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20050610
			// gltexsubimage
			// texture object
			// texture unit
			// cube map
			// multi texture
			// set rendering target
			// http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20050923
			// mip map
			// http://msdn.microsoft.com/ja-jp/library/bb932345.aspx
			// http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20050614
			// http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20050615
			// http://marina.sys.wakayama-u.ac.jp/~tokoi/?date=20050616
			// http://wiki.livedoor.jp/mikk_ni3_92/d/%C8%AF%C5%B8%CA%D401%3A%3A%A5%DE%A5%EB%A5%C1%A5%C6%A5%AF%A5%B9%A5%C1%A5%E3
			},
			// viewport, normal and matrix
			{
					new ListItem("2D view (glOrthof)",
							com.tatsun.openglsamples.samples.Sample_04_01.class),
					new ListItem("3D view (gluPerspective)",
							com.tatsun.openglsamples.samples.Sample_04_02.class),
					new ListItem("3D view (glFrustum)",
							com.tatsun.openglsamples.samples.Sample_04_03.class),
					new ListItem("camera view (gluLookat)",
							com.tatsun.openglsamples.samples.Sample_04_04.class),
					new ListItem("camera view (matrix)",
							com.tatsun.openglsamples.samples.Sample_04_05.class),
					new ListItem("bill board",
							com.tatsun.openglsamples.samples.Sample_04_06.class),
					new ListItem("bill board2",
							com.tatsun.openglsamples.samples.Sample_04_07.class),
			// depth test gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
			// GL10.GL_DEPTH_BUFFER_BIT);
			// surface normal, vertex normal
			// collision
			// culling
			// rendering target to texture
			// http://angra.blog31.fc2.com/blog-entry-11.html
			},
			// lighting, normal, stencil
			{
			// anbient
			// defuse
			// bump map
			// normal map
			// grow shading
			// cube map
			// spot light
			// gross
			// HDR
			// shadow
			// stencil
			},
			// effect, shader
			{
			// fog
			// smoke
			// fire
			// thunder
			// ice
			// water
			// gravity hole
			},
			// etc
			{
					new ListItem("draw square, circle",
							com.tatsun.openglsamples.samples.Sample_07_01.class),
					new ListItem("draw font",
							com.tatsun.openglsamples.samples.Sample_07_02.class),
					new ListItem("draw font2",
							com.tatsun.openglsamples.samples.Sample_07_03.class),
			// particle
			// custom surface view
			// metasecoia
			}, };
	private Integer activitySelect;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.app_name);
		Intent intent = getIntent();
		activitySelect = intent.getIntExtra(MainActivity.INTENT_KEY, 0);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		for (ListItem li : activities[activitySelect]) {
			adapter.add(li.clazz.getSimpleName() + "\n" + li.explain);
		}

		ListView listView = new ListView(this.getApplicationContext());
		listView.setAdapter(adapter);
		setContentView(listView);

		// item clicked callback
		listView.setOnItemClickListener(this);
		// item selected callback
		listView.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListView listView = (ListView) parent;
		String item = (String) listView.getItemAtPosition(position);
		Log.i("onItemClick", item);
		Intent intent = new Intent(this,
				activities[activitySelect][position].clazz);
		startActivity(intent);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		ListView listView = (ListView) parent;
		Log.i("onItemSelected", (String) listView.getItemAtPosition(position));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
}