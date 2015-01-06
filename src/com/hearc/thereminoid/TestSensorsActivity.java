package com.hearc.thereminoid;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class TestSensorsActivity extends Activity implements SensorEventListener{

	private SensorManager mSensorManager;
	private Sensor light;
	private Sensor magnetic;
	private TextView viewForce,viewForceFirstValue,viewLight,viewLightFirstValue;
	private View layout;
	private float calibrateForce = 0;
	private float calibrateLight = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_sensors);

		initSensor();
		
		
		viewForce = (TextView)findViewById(R.id.textViewForce);
		viewForceFirstValue = (TextView)findViewById(R.id.textViewForceFirstValue);
		
		viewLight = (TextView)findViewById(R.id.TextViewProximity);
		viewLightFirstValue = (TextView)findViewById(R.id.textViewProximityFirstValue);
		
		layout = findViewById(R.id.layout);

	}

	// Différentes initialisation des capteurs
	private void initSensor() {
		mSensorManager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
		magnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // On récupère le capteur magnétique
		light = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // Puis celui de lumière (pas de proximité car capteur binaire)
		
		mSensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_FASTEST); // On définit les vitesse de changement de valeurs qu'on souhaite
		mSensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_UI);
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
		    
		    float force = (float) Math.sqrt( (x*x) + (y*y) + (z*z) ); // Formule du champ magnétique en fonction des 3 axes
		    
		    if(calibrateForce == 0)
		    {
		    	calibrateForce = force;
		    	viewForceFirstValue.setText(String.valueOf(calibrateForce));
		    }
		    
		    int value = getCalibrateValue(force,true);
		    
		    viewForce.setText(String.valueOf(value));
		}
		if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
			x = event.values[0];
			
		    if(calibrateLight == 0)
		    {
		    	calibrateLight = x;
		    	viewLightFirstValue.setText(String.valueOf(calibrateLight));
		    }
		    
		    int value = getCalibrateValue(x,false);
		    
			viewLight.setText(String.valueOf(value));
		}		
	}
	
	private int getCalibrateValue(float sensorValue,boolean bool)
	{
		int value;
		
		// si bool = 1 -> capteur magnétique
		if(bool)
		{
			// Calibrage du capteur magnétique
			value = (int) (103-(sensorValue/calibrateForce)*3.5);
			
		}
		else
		{
			// Calibrage du capteur de lumière
			value = (int) ((sensorValue/calibrateLight)*100);
		}
		
		// Car les valeurs des capteurs peuvent dépasser 100 et  < 0 on stop value le cas échéant
		if(value > 100)
			return 100;
		if(value <= 0)
			return 0;
		else
			return value;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
