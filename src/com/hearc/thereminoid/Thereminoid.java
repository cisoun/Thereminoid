package com.hearc.thereminoid;

import com.hearc.thereminoid.utils.Waves;

/**
 * Thereminoid main class.<br/>
 * Contains some general stuff.
 * 
 * @author Cyriaque Skrapits
 * @author Eddy Strambini
 */
public class Thereminoid {
	// Theremin configuration
	public static final float MIN_FREQUENCY = 20.0f;
	public static final float MAX_FREQUENCY = 20000.0f;
	
	public static boolean mute = false;
	public static int signalType = Waves.SINUS;
}