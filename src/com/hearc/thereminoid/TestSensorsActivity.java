package com.hearc.thereminoid;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TestSensorsActivity extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private Sensor proximity;
	private Sensor magnetic;
	private TextView viewForce,viewProximity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_sensors);

		mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		proximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		mSensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
		
		
		viewForce = (TextView)findViewById(R.id.textViewForce);
		viewProximity = (TextView)findViewById(R.id.TextViewProximity);

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_sensors, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x,y,z;
		
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
		    x = event.values[0];
		    y = event.values[1];
		    z = event.values[2];
		    
		    float force = (float) Math.sqrt( (x*x) + (y*y) + (z*z) );
		
		    
		    viewForce.setText(String.valueOf(force));
		}
		if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
			x = event.values[0];
			
			viewProximity.setText(String.valueOf(x));
		}
		
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
