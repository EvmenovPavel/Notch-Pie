package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;

// Handles data related to notch.
public class SettingsManager {
	
	private SharedPreferences preferences;
	private static final String pref_name = "settings_preferences";
	private static final int pref_mode = 0;

	public SettingsManager(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		preferences.getBoolean("full_status", false);
		preferences.getBoolean("show_background", false);
		preferences.getString("background_color", "#000000");
		preferences.getBoolean("charging_animation", true);
		preferences.getBoolean("landscape_support", false);

	}
	
	
	
	// Getters
	// Return [string] the fullStatus setting
	public boolean isFullStatus() {
		
		return preferences.getBoolean("full_status", false);

	}

	// Return [string] the showBackground setting
	public boolean isShowBackground() {

		return preferences.getBoolean("show_background", false);

	}
	
	// Return [string] the backgroundColor setting
	public String getBackgroundColor() {
		
		return preferences.getString("background_color", "#000000");
		
	}
	
	// Return [string] the chargingAnimation setting
	public boolean isChargingAnimation() {

		return preferences.getBoolean("charging_animation", true);

	}

	// Return [string] the landscapeSupport setting
	public boolean isLandscapeSupport() {

		return preferences.getBoolean("landscape_support", false);

	}
	
	
	
	// Setters
	// Sets the FullStatus
	public void setFullStatus(boolean fullStatus, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("full_status", fullStatus);
		
		editor.apply();

	}

	// Sets the ShowBackground
	public void setShowBackground(boolean showBackground, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("show_background", showBackground);
		
		editor.apply();

	}

	// Sets the ChargingAnimation
	public void setChargingAnimation(boolean chargingAnimation, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("charging_animation", chargingAnimation);
		
		editor.apply();

	}

	// Sets the BackgroundColor
	public void setBackgroundColor(String backgroundColor, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putString("background_color", backgroundColor);
		
		editor.apply();

	}

	// Sets the LandscapeSupport
	public void setLandscapeSupport(boolean landscapeSupport, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("landscape_support", landscapeSupport);
		
		editor.apply();

	}
	
	
}
