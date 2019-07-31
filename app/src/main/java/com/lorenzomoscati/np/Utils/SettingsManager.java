package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;

// Handles data related to notch.
public class SettingsManager {
	
	private boolean fullStatus,
					showBackground,
					chargingAnimation,
					landscapeSupport;
	private String backgroundColor;
	
	private SharedPreferences preferences;
	private static final String pref_name = "settings_preferences";
	private static final int pref_mode = 0;

	public SettingsManager(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		fullStatus = preferences.getBoolean("full_status", false);
		showBackground = preferences.getBoolean("show_background", false);
		backgroundColor = preferences.getString("background_color", "#000000");
		chargingAnimation = preferences.getBoolean("charging_animation", true);
		landscapeSupport = preferences.getBoolean("landscape_support", false);

	}


	// Reads the config file and returns true if read was successful
	public void read(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		fullStatus = preferences.getBoolean("full_status", false);
		showBackground = preferences.getBoolean("show_background", false);
		backgroundColor = preferences.getString("background_color", "#000000");
		chargingAnimation = preferences.getBoolean("charging_animation", true);
		landscapeSupport = preferences.getBoolean("landscape_support", false);
		
	}

	// This method saves the file
	public void save(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("full_status", fullStatus);
		editor.putBoolean("show_background", showBackground);
		editor.putString("background_color", backgroundColor);
		editor.putBoolean("charging_animation", chargingAnimation);
		editor.putBoolean("landscape_support", landscapeSupport);
		
		editor.apply();

	}
	
	
	// Return [string] the fullStatus setting
	public boolean isFullStatus() {

		return fullStatus;

	}

	// Return [string] the showBackground setting
	public boolean isShowBackground() {

		return showBackground;

	}

	// Return [string] the chargingAnimation setting
	public boolean isChargingAnimation() {

		return chargingAnimation;

	}

	// Return [string] the backgroundColor setting
	public String getBackgroundColor() {

		return backgroundColor;

	}

	// Return [string] the landscapeSupport setting
	public boolean isLandscapeSupport() {

		return landscapeSupport;

	}
	
	
	// Sets the FullStatus
	public void setFullStatus(boolean fullStatus) {

		this.fullStatus = fullStatus;

	}

	// Sets the ShowBackground
	public void setShowBackground(boolean showBackground) {

		this.showBackground = showBackground;

	}

	// Sets the ChargingAnimation
	public void setChargingAnimation(boolean chargingAnimation) {

		this.chargingAnimation = chargingAnimation;

	}

	// Sets the BackgroundColor
	public void setBackgroundColor(String backgroundColor) {

		this.backgroundColor = backgroundColor;

	}

	// Sets the LandscapeSupport
	public void setLandscapeSupport(boolean landscapeSupport) {

		this.landscapeSupport = landscapeSupport;

	}
	
	
}
