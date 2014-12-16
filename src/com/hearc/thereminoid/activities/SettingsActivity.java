package com.hearc.thereminoid.activities;

import com.hearc.thereminoid.fragments.SettingsFragment;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	public static final String ANIMATIONS_ENABLE = "animations_enable";
	public static final String ANIMATIONS_HUI = "animations_hui";
	public static final String ANIMATIONS_PARTICLES = "animations_particles";
	public static final String SENSORS_LUMINOSITY = "sensors_luminosity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}
}