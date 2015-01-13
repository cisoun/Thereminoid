package com.hearc.thereminoid.utils;

public class Utils {
	public static short[] floatToShort(float[] array)
	{
		short[] newArray = new short[array.length];
		for (int i = 0; i < array.length; i++)
			newArray[i] = (short)(Short.MAX_VALUE * array[i]);
		return newArray;
	}
}
