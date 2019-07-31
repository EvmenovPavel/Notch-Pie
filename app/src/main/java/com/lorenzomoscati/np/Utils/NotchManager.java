package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;

// Handles data related to notch.
public class NotchManager {
	
	private SharedPreferences preferences;
	private static final String pref_name = "notch_preferences";
	private static final int pref_mode = 0;

	public NotchManager(Context context) {

		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		preferences.getInt("height", 90);
		preferences.getInt("width", 1);
		preferences.getInt("notch_size", 80);
		preferences.getInt("top_radius", 0);
		preferences.getInt("bottom_radius", 100);
		preferences.getInt("x_pos_port", 0);
		preferences.getInt("y_pos_port", 0);
		preferences.getInt("x_pos_land", 0);
		preferences.getInt("y_pos_land", 0);

	}
	
	
	
	// Getters
	// Returns [int] the height
	public int getHeight() {
		
		return preferences.getInt("height", 90);
		
	}
	
	// Returns [int] the width
	public int getWidth() {
		
		return  preferences.getInt("width", 1);
		
	}
	
	// Returns [int] the notchSize
	public int getNotchSize() {
		
		return preferences.getInt("notch_size", 80);
		
	}
	
	// Returns [int] the topRadius
	public int getTopRadius() {
		
		return preferences.getInt("top_radius", 0);
		
	}
	
	// Returns [int] the bottomRadius
	public int getBottomRadius() {
		
		return preferences.getInt("bottom_radius", 100);
		
	}
	
	// Returns [int] the xPosition in portrait mode
	public int getxPositionPortrait() {
		
		return preferences.getInt("x_pos_port", 0);
		
	}
	
	// Returns [int] the yPosition in portrait mode
	public int getyPositionPortrait() {
		
		return preferences.getInt("y_pos_port", 0);
		
	}
	
	// Returns [int] the xPosition in landscape mode
	public int getxPositionLandscape() {
		
		return preferences.getInt("x_pos_land", 0);
		
	}
	
	// Returns [int] the yPosition in landscape mode
	public int getyPositionLandscape() {
		
		return preferences.getInt("y_pos_land", 0);
		
	}



	// Setters
	// Sets the height
	public void setHeight(int height, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("height", height);
		
		editor.apply();

	}

	// Sets the width
	public void setWidth(int width, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("width", width);
		
		editor.apply();

	}

	// Sets the notchSize
	public void setNotchSize(int notchSize, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("notch_size", notchSize);
		
		editor.apply();

	}

	// Sets the topRadius
	public void setTopRadius(int topRadius, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("top_radius", topRadius);
		
		editor.apply();

	}

	// Sets the bottomRadius
	public void setBottomRadius(int bottomRadius, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("bottom_radius", bottomRadius);
		
		editor.apply();

	}

	// Sets the xPosition in portrait mode
	public void setxPositionPortrait(int xPositionPortrait, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("x_pos_port", xPositionPortrait);
		
		editor.apply();

	}

	// Sets the yPosition in portrait mode
	public void setyPositionPortrait(int yPositionPortrait, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("y_pos_port", yPositionPortrait);
		
		editor.apply();

	}

	// Sets the xPosition in landscape mode
	public void setxPositionLandscape(int xPositionLandscape, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("x_pos_land", xPositionLandscape);
		
		editor.apply();

	}

	// Sets the yPosition in landscape mode
	public void setyPositionLandscape(int yPositionLandscape, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("y_pos_land", yPositionLandscape);
		
		editor.apply();

	}
	
}
