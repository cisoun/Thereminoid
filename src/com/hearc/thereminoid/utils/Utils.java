package com.hearc.thereminoid.utils;

/**
 * Utils class
 * 
 * This class contains some utility methods.
 * 
 * @author Cyriaque Skrapits
 *
 */
public class Utils {
	/**
	 * Convert an array of floats to an array of shorts.
	 * @param array Array of floats
	 * @return Array of shorts
	 */
	public static short[] floatToShort(float[] array)
	{
		short[] newArray = new short[array.length];
		for (int i = 0; i < array.length; i++)
			newArray[i] = (short)(Short.MAX_VALUE * array[i]);
		return newArray;
	}
}
