package com.lorenzomoscati.np.Modal;

public class ColorLevel {

	private int startLevel, endLevel;
	private String color;

	// Returns [string] the first percentage
	public int getStartLevel() {

		return startLevel;

	}

	// Returns [string] the last percentage
	public int getEndLevel() {

		return endLevel;

	}

	// Returns [string] the color code
	public String getColor() {

		return color;

	}



	// Sets the first percentage
	public void setStartLevel(int startLevel) {

		this.startLevel = startLevel;

	}

	// Sets the second percentage
	public void setEndLevel(int endLevel) {

		this.endLevel = endLevel;

	}

	// Sets the color code
	public void setColor(String color) {

		this.color = color;

	}

}
