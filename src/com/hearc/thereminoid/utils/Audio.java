package com.hearc.thereminoid.utils;

import com.hearc.thereminoid.Thereminoid;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * Audio class
 * 
 * Handles an audio thread which plays a defined signal.
 * 
 * @author Cyriaque Skrapits
 *
 */
public class Audio {

	private final static int SAMPLE_RATE = 44100;
	private final static int BUFFER_SIZE = 1024;

	private static AudioTrack track;
	private static Thread thread;
	private static float frequency;
	private static float amplitude;
	
	private static boolean running;

	public static void initAudio(Context context) {
		track = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE, AudioTrack.MODE_STREAM);
	}

	/**
	 * Starts the audio thread.
	 */
	public static void start() {
		System.out.println("Audio::start()");

		running = true; // Allows the thread to run.
		track.play(); // Start the audio track.
		
		// Audio thread
		thread = new Thread(new Runnable() {
			float[] buffer;

			@Override
			public void run() {
				while (running) {
					// Get the signal.
					buffer = Waves.make(Thereminoid.signalType, BUFFER_SIZE, frequency, amplitude);
					
					// Write the signal to the audio track (speaker).
					track.write(Utils.floatToShort(buffer), 0, BUFFER_SIZE);
				}
			}
		});
		thread.start();
	}

	/**
	 * Update the frequency and the amplitude.
	 * @param frequency Signal frequency
	 * @param amplitude Signal amplitude
	 */
	public static void update(float frequency, float amplitude) {
		Audio.frequency = frequency;
		Audio.amplitude = amplitude;
	}

	/**
	 * Stops the signal.
	 */
	public static void stop()
	{
		System.out.println("Audio::stop()");
		
		running = false; // Stop the thread.
		track.stop(); // Stop writing to the audio track.
	}
}
