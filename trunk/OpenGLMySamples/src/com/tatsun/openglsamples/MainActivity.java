package com.tatsun.openglsamples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements
		AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
	public static final String INTENT_KEY = "MENU_POSITION";
	private static final String[] listMenus = { "foundation", "buffer object",
			"texture", "viewport, normal and matrix", "lighting",
			"effect, shader", "etc", };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(R.string.app_name);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1);
		for (String c : listMenus) {
			adapter.add(c);
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
		Intent intent = new Intent(this, SecondActivity.class);
		intent.putExtra(INTENT_KEY, position);
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