package com.hearc.thereminoid.utils;

public class Waves {
	public static float[] makeSinus(float hertz, int samples, float amplitude) {
		float[] sinus;
		double degrees = 0.0;
		double delta;
		double max;

		max = hertz * 360;

		if (samples > max)
			samples = (int) max;

		sinus = new float[samples];

		delta = (double) max / (double) samples;
		for (int i = 0; i < samples; i++) {
			sinus[i] = (float) Math.sin(Math.toRadians(degrees)) * amplitude;
			degrees += delta;
		}

		return sinus;
	}

	public static float[] makeSquare(float hertz, int samples, float amplitude) {
		float[] square = new float[samples];
		int direction = -1;
		int samplesPerPeak = samples / (int) hertz / 2;
		System.out.println(samplesPerPeak + " - " + samples);
		
		square[0] = 0;
		for (int i = 1; i < samples; i++) {
			square[i] = amplitude * direction;
			if (i % samplesPerPeak == 0)
				direction = direction * -1;
		}

		return square;
	}
}
