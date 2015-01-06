package com.hearc.thereminoid.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Sensors {
	private static ISensorsListener sensorsListener;
	private static SensorManager sensorManager;
	private static SensorEventListener eventListener;
	private static Sensor light;
	private static Sensor magnetic;
	private static float calibrateForce = 0.0f;
	private static float calibrateLight = 0.0f;

	public static void initSensors(Context context, ISensorsListener listener) {
		sensorsListener = listener;
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

		initListener();

		sensorManager.registerListener(eventListener, magnetic, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(eventListener, light, SensorManager.SENSOR_DELAY_UI);
	}

	private static void initListener() {
		eventListener = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				float x, y, z;

				if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
					x = Math.abs(event.values[0]);
					y = Math.abs(event.values[1]);
					z = Math.abs(event.values[2]);

					// Magnetic field force formula.
					float force = (float) Math.sqrt((x * x) + (y * y) + (z * z));

					if (calibrateForce == 0)
						calibrateForce = force;

					int value = getCalibrateValue(force, true);
					sensorsListener.onSensorChanged(Sensor.TYPE_MAGNETIC_FIELD, value);
				}
				if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
					x = event.values[0];

					if (calibrateLight == 0)
						calibrateLight = x;

					int value = getCalibrateValue(x, false);
					sensorsListener.onSensorChanged(Sensor.TYPE_LIGHT, value);
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		};
	}

	private static int getCalibrateValue(float sensorValue, boolean bool) {
		int value;

		// If bool is true, magnetic sensor.
		if (bool) {
			// Calibrate the magnetic sensor.
			value = (int) (103 - (sensorValue / calibrateForce) * 3.5);
		} else {
			// Calibrate the light sensor.
			value = (int) ((sensorValue / calibrateLight) * 100);
		}

		// Values must be between 0 and 100.
		if (value > 100)
			return 100;
		if (value <= 0)
			return 0;
		else
			return value;
	}
}
