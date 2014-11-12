package com.hearc.thereminoid.utils;

public class Waves {
	public static float[] makeSinus(float frequency, int samples, float amplitude) {
		float[] sinus;
		double degrees = 0.0;
		double delta;
		double max;

		max = frequency * 360;

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

	public static float[] makeSquare(float frequency, int samples, float amplitude) {
		float[] square = new float[samples];
		int direction = -1;
		int samplesPerPeak = (int) (samples / frequency); //samples / (int) frequency / 2;
		System.out.println(samplesPerPeak + " - " + samples);

		/*
		 * square[0] = 0; for (int i = 1; i < samples; i++) { square[i] =
		 * amplitude * direction; if (i % samplesPerPeak == 0) direction =
		 * direction * -1; }
		 */

		for (int i = 0; i < samples; i++) {
			if (i % samplesPerPeak == 0)
				direction *= -1;
			square[i] = (float)direction * amplitude;
		}

		return square;
	}

	/*public static float[] makeSaw(float frequency, int samples, float amplitude) {
		
	}*/
}
