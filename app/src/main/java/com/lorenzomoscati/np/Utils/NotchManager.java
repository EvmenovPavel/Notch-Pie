package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;

// Handles data related to notch.
public class NotchManager {
	
	private int height,
				width,
				notchSize,
				topRadius,
				bottomRadius,
				xPositionPortrait,
				yPositionPortrait,
				xPositionLandscape,
				yPositionLandscape;
	private SharedPreferences preferences;
	private static final String pref_name = "notch_preferences";
	private static final int pref_mode = 0;

	public NotchManager(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		height = preferences.getInt("height", 90);
		width = preferences.getInt("width", 1);
		notchSize = preferences.getInt("notch_size", 80);
		topRadius = preferences.getInt("top_radius", 0);
		bottomRadius = preferences.getInt("bottom_radius", 100);
		xPositionPortrait = preferences.getInt("x_pos_port", 0);
		yPositionPortrait = preferences.getInt("y_pos_port", 0);
		xPositionLandscape = preferences.getInt("x_pos_land", 0);
		yPositionLandscape = preferences.getInt("y_pos_land", 0);

	}
	
	// Reads the config file and returns true if read was successful
	public void read(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		height = preferences.getInt("height", 90);
		width = preferences.getInt("width", 1);
		notchSize = preferences.getInt("notch_size", 80);
		topRadius = preferences.getInt("top_radius", 0);
		bottomRadius = preferences.getInt("bottom_radius", 100);
		xPositionPortrait = preferences.getInt("x_pos_port", 0);
		yPositionPortrait = preferences.getInt("y_pos_port", 0);
		xPositionLandscape = preferences.getInt("x_pos_land", 0);
		yPositionLandscape = preferences.getInt("y_pos_land", 0);
		
	}

	// This method saves the file
	public void save(Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("height", height);
		editor.putInt("width", width);
		editor.putInt("notch_size", notchSize);
		editor.putInt("top_radius", topRadius);
		editor.putInt("bottom_radius", bottomRadius);
		editor.putInt("x_pos_port", xPositionPortrait);
		editor.putInt("y_pos_port", yPositionPortrait);
		editor.putInt("x_pos_land", xPositionLandscape);
		editor.putInt("y_pos_land", yPositionLandscape);
		
		editor.apply();

	}



	// Setters
	// Sets the height
	public void setHeight(int height) {

		this.height = height;

	}

	// Sets the width
	public void setWidth(int width) {

		this.width = width;

	}

	// Sets the notchSize
	public void setNotchSize(int notchSize) {

		this.notchSize = notchSize;

	}

	// Sets the topRadius
	public void setTopRadius(int topRadius) {

		this.topRadius = topRadius;

	}

	// Sets the bottomRadius
	public void setBottomRadius(int bottomRadius) {

		this.bottomRadius = bottomRadius;

	}

	// Sets the xPosition in portrait mode
	public void setxPositionPortrait(int xPositionPortrait) {

		this.xPositionPortrait = xPositionPortrait;

	}

	// Sets the yPosition in portrait mode
	public void setyPositionPortrait(int yPositionPortrait) {

		this.yPositionPortrait = yPositionPortrait;

	}

	// Sets the xPosition in landscape mode
	public void setxPositionLandscape(int xPositionLandscape) {

		this.xPositionLandscape = xPositionLandscape;

	}

	// Sets the yPosition in landscape mode
	public void setyPositionLandscape(int yPositionLandscape) {

		this.yPositionLandscape = yPositionLandscape;

	}
	
	
	// Getters
	// Returns [int] the height
	public int getHeight() {

		return height;

	}

	// Returns [int] the width
	public int getWidth() {

		return width;

	}

	// Returns [int] the notchSize
	public int getNotchSize() {

		return notchSize;

	}

	// Returns [int] the topRadius
	public int getTopRadius() {

		return topRadius;

	}

	// Returns [int] the bottomRadius
	public int getBottomRadius() {

		return bottomRadius;

	}

	// Returns [int] the xPosition in portrait mode
	public int getxPositionPortrait() {

		return xPositionPortrait;

	}

	// Returns [int] the yPosition in portrait mode
	public int getyPositionPortrait() {

		return yPositionPortrait;

	}

	// Returns [int] the xPosition in landscape mode
	public int getxPositionLandscape() {

		return xPositionLandscape;

	}

	// Returns [int] the yPosition in landscape mode
	public int getyPositionLandscape() {

		return yPositionLandscape;

	}
	
}
