package com.hearc.thereminoid.activities;

import com.hearc.thereminoid.R;
import com.hearc.thereminoid.R.id;
import com.hearc.thereminoid.R.layout;
import com.hearc.thereminoid.R.menu;
import com.hearc.thereminoid.views.VisualizerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LinearLayout layout;
	private VisualizerView visualizer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		visualizer = new VisualizerView(this);
		
		layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(visualizer);
		
		getWindow().setFlags(
			    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
			    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
}
