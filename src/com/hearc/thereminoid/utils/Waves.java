package com.hearc.thereminoid.utils;

public class Waves {
	public static int SINUS = 0;
	public static int SQUARE = 1;
	public static int SAW = 2;
	
	private static int bufferSize;
	private static float[] buffer;
	
	public static void initBuffer(int samples) {
		bufferSize = samples > 0 ? samples : 1;
		buffer = new float[samples];
		System.out.println("INITBUFFER : " + String.valueOf(samples));
	}
	
	public static float[] makeSinus(float frequency, float amplitude) {
		float degrees = 0.0f;
		float delta = (frequency * 360.0f) / (float) bufferSize;
		
		for (int i = 0; i < bufferSize; i++) {
			buffer[i] = (float) Math.sin(Math.toRadians(degrees)) * amplitude;
			degrees += delta;
		}

		return buffer;
	}

	public static float[] makeSquare(float frequency, float amplitude) {
		int direction = -1;
		//int samplesPerPeak = (int) (bufferSize / frequency) / 2 + 1;
		int samplesPerPeak = (int) (bufferSize / frequency) / 2;
		
		for (int i = 0; i < bufferSize; i++) {
			if (i % samplesPerPeak == 0)
				direction *= -1;
			buffer[i] = (float)direction * amplitude;
		}

		return buffer;
	}

	public static float[] makeSaw(float frequency, float amplitude) {
		//float[] saw = new float[samples];
		float samplesPerFrequence = bufferSize / frequency;
		float offset = samplesPerFrequence / 2;
		
		float delta = amplitude * 2.0f / samplesPerFrequence;
		
		for (int i = 0; i < bufferSize; i++) {
			buffer[i] = amplitude - (((float)i + offset) % samplesPerFrequence) * delta; //((float)i + offset % samplesPerFrequence);
		}
		
		return buffer;
	}
}