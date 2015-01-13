package com.hearc.thereminoid.utils;

import com.hearc.thereminoid.Thereminoid;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class Audio {

	private final static int SAMPLE_RATE = 44100;
	private final static int BUFFER_SIZE = 1024;

	private static AudioTrack track;
	private static Thread thread;
	private static float frequency;
	private static float amplitude;

	public static void initAudio(Context context) {
		track = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE, AudioTrack.MODE_STREAM);
		track.play();
	}

	public static void start() {
		thread = new Thread(new Runnable() {
			float[] buffer;

			@Override
			public void run() {
				while (true) {
					buffer = Waves.make(Thereminoid.signalType, BUFFER_SIZE, frequency, amplitude);
					track.write(Utils.floatToShort(buffer), 0, BUFFER_SIZE);
				}
			}
		});
		thread.start();
		
		System.out.println("Audio::start()");
	}

	public static void update(float frequency, float amplitude) {
		Audio.frequency = frequency;
		Audio.amplitude = amplitude;
	}

	public static void stop()
	{
		track.stop();
	}
}
