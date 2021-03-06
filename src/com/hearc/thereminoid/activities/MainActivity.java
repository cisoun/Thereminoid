package com.hearc.thereminoid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hearc.thereminoid.R;
import com.hearc.thereminoid.Thereminoid;
import com.hearc.thereminoid.utils.Audio;
import com.hearc.thereminoid.utils.ISensorsListener;
import com.hearc.thereminoid.utils.Sensors;
import com.hearc.thereminoid.utils.Waves;
import com.hearc.thereminoid.views.VisualizerView;

/**
 * Main activity
 * 
 * @author Cyriaque Skrapits
 * @author Eddy Strambini
 *
 */
public class MainActivity extends Activity implements ISensorsListener {

	private static int orientation = 0;
	private LinearLayout layout;
	private VisualizerView visualizer;
	private MenuItem menuMute;

	private float frequency = 0.0f;
	private float amplitude = 0.5f;

	Thread threadFrequency;

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

		Sensors.initSensors(this, this);

		threadFrequency = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					redrawSignal();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		threadFrequency.start();

		Audio.initAudio(this);
		Audio.start();
	}

	@Override
	protected void onPause() {
		Audio.stop();
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();
		Audio.start();
	}

	@Override
	protected void onDestroy() {
		Audio.stop();
		super.onDestroy();
	}

	private synchronized void redrawSignal() {
		visualizer.drawWave(Thereminoid.signalType, frequency, amplitude);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menuMute = menu.findItem(R.id.action_mute);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_mute:
			mute();
			return true;
		case R.id.action_list:
			// list();
			return true;
		case R.id.action_reset:
			resetSensors();
			return true;
		case R.id.action_wave_sin:
			// visualizer.drawWave(Waves.makeSinus(6, visualizer.getWidth(),
			// 0.5f));
			Thereminoid.signalType = Waves.SINUS;
			System.out.println("Main::Signal set to SINUS");
			return true;
		case R.id.action_wave_sqr:
			// visualizer.drawWave(Waves.makeSquare(6, visualizer.getWidth(),
			// 0.5f));
			Thereminoid.signalType = Waves.SQUARE;
			System.out.println("Main::Signal set to SQUARE");
			return true;
		case R.id.action_wave_saw:
			// visualizer.drawWave(Waves.makeSaw(6, visualizer.getWidth(),
			// 0.5f));
			Thereminoid.signalType = Waves.SAW;
			System.out.println("Main::Signal set to SAW");
			return true;
		case R.id.action_orientation:
			changeOrientation();
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

	/*
	 * private void list() { Intent intent = new Intent(this,
	 * RecordsActivity.class); startActivity(intent); }
	 */

	public void mute() {
		boolean mute = Thereminoid.mute;
		Thereminoid.mute = !mute;
		menuMute.setIcon(mute ? R.drawable.ic_action_mute
				: R.drawable.ic_action_unmute);
	}

	private void changeOrientation() {
		if (orientation == 0) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
			orientation = 1;
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			orientation = 0;
		}
	}

	private void settings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		visualizer.update();
	}

	public void about() {
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	public void resetSensors() {
		Sensors.resetSensors();

		frequency = 0.0f;
		amplitude = 0.5f;
	}

	@Override
	public void onSensorChanged(int sensorType, float value) {

		if (sensorType == Sensor.TYPE_MAGNETIC_FIELD) {
			frequency = 1000.0f - (10.0f * value);

			// Set to 10.0f to avoid the frequency 0 when
			// we approach the magnet.			
			if (frequency == 0.0f)
				frequency = 10.0f;

		} else if (sensorType == Sensor.TYPE_LIGHT) {
			amplitude = 1.0f - (value / 100.0f);
		}

		// Set amplitude and frequency to zero if muted.
		if (!Thereminoid.mute) {
			frequency = 0.0f;
			amplitude = 0.0f;
		}

		Audio.update(frequency, amplitude);
	}
}