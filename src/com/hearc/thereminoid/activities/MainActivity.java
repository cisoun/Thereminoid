package com.hearc.thereminoid.activities;

import com.hearc.thereminoid.R;
import com.hearc.thereminoid.R.id;
import com.hearc.thereminoid.R.layout;
import com.hearc.thereminoid.R.menu;
import com.hearc.thereminoid.Thereminoid;
import com.hearc.thereminoid.utils.Waves;
import com.hearc.thereminoid.views.VisualizerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private LinearLayout layout;
	private VisualizerView visualizer;
	private MenuItem menuSinus;
	private MenuItem menuMute;
	
	private float tmpFrequency = 0.0f;
	private boolean fd = false;

	Thread threadFrequency;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		visualizer = new VisualizerView(this);

		layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(visualizer);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

		threadFrequency = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				// while (true) {
				if (tmpFrequency < 1.0f || tmpFrequency >= 10.0f)
					fd = !fd;
				tmpFrequency += (fd ? 1.0f : -1.0f);

				visualizer.makeWave(Waves.makeSaw(tmpFrequency, visualizer.getWidth(), 0.5f));
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// }
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menuMute = (MenuItem) menu.findItem(R.id.action_mute);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_wave_sin:
			visualizer.makeWave(Waves.makeSinus(6, visualizer.getWidth(), 0.5f));
			return true;
		case R.id.action_wave_sqr:
			visualizer.makeWave(Waves.makeSquare(6, visualizer.getWidth(), 0.5f));
			return true;
		case R.id.action_wave_saw:
			visualizer.makeWave(Waves.makeSaw(6, visualizer.getWidth(), 0.5f));
			return true;
		case R.id.action_settings:
			settings();
			return true;
		case R.id.action_about:
			about();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void record() {
		// TODO Auto-generated method stub
		
	}

	public void mute() {
		boolean mute = Thereminoid.mute;
		Thereminoid.mute = !mute;
		menuMute.setIcon(mute ? R.drawable.ic_action_mute : R.drawable.ic_action_unmute);
	}
	
	private void settings() {
		Intent intent = new Intent(this, SettingsActivity.class);
	    startActivity(intent);
	}
	
	public void about() {
		Intent intent = new Intent(this, AboutActivity.class);
	    startActivity(intent);
	}
}
