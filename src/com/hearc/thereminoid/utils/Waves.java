package com.hearc.thereminoid.utils;

/**
 * Waves class
 * 
 * This class creates many different types of signal.
 * These signals are defined by a frequency, an amplitude and a length. Once generated,
 * they can be stored in an array.
 * 
 * @author Cyriaque Skrapits
 *
 */
public class Waves {
	public final static int SINUS = 0;
	public final static int SQUARE = 1;
	public final static int SAW = 2;

	public static float[] make(int signalType, int bufferSize, float frequency, float amplitude)
	{
		float buffer[];

		switch (signalType) {
		case SINUS:
			buffer = sinus(bufferSize, frequency, amplitude);
			break;
		case SQUARE:
			buffer = square(bufferSize, frequency, amplitude);
			break;
		case SAW:
			buffer = saw(bufferSize, frequency, amplitude);
			break;
		default:
			buffer = sinus(bufferSize, frequency, amplitude);
			break;
		}
		
		return buffer;
	}
	
	public static float[] sinus(int bufferSize, float frequency, float amplitude) {
		float buffer[] = new float[bufferSize];
		float degrees = 0.0f;
		float delta = (frequency * 360.0f) / bufferSize;
		
		for (int i = 0; i < bufferSize; i++) {
			buffer[i] = (float) Math.sin(Math.toRadians(degrees)) * amplitude;
			degrees += delta;
		}

		return buffer;
	}

	public static float[] square(int bufferSize, float frequency, float amplitude) {
		float buffer[] = new float[bufferSize];
		int direction = -1;
		int samplesPerPeak = (int) (bufferSize / frequency) / 2;
		
		for (int i = 0; i < bufferSize; i++) {
			if (i % samplesPerPeak == 0)
				direction *= -1;
			buffer[i] = direction * amplitude;
		}

		return buffer;
	}

	public static float[] saw(int bufferSize, float frequency, float amplitude) {
		float buffer[] = new float[bufferSize];
		float samplesPerFrequence = bufferSize / frequency;
		float offset = samplesPerFrequence / 2;
		
		float delta = amplitude * 2.0f / samplesPerFrequence;
		
		for (int i = 0; i < bufferSize; i++) {
			buffer[i] = amplitude - ((i + offset) % samplesPerFrequence) * delta; //((float)i + offset % samplesPerFrequence);
		}
		
		return buffer;
	}
}