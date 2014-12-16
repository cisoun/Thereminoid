package com.hearc.thereminoid.utils;

public class Waves {
	public static float[] makeSinus(float frequency, int samples, float amplitude) {
		float[] sinus;
		double degrees = 0.0;
		double delta;
		double max;

		max = frequency * 360.0;

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
		int samplesPerPeak = (int) (samples / frequency) / 2 + 1;

		for (int i = 0; i < samples; i++) {
			if (i % samplesPerPeak == 0)
				direction *= -1;
			square[i] = (float)direction * amplitude;
		}

		return square;
	}

	public static float[] makeSaw(float frequency, int samples, float amplitude) {
		float[] saw = new float[samples];
		float samplesPerFrequence = samples / frequency;
		float offset = samplesPerFrequence / 2;
		
		float delta = amplitude * 2.0f / samplesPerFrequence;
		
		for (int i = 0; i < samples; i++) {
			saw[i] = amplitude - (((float)i + offset) % samplesPerFrequence) * delta; //((float)i + offset % samplesPerFrequence);
		}
		
		return saw;
	}
}