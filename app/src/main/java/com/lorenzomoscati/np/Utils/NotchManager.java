package com.lorenzomoscati.np.Utils;

import com.lorenzomoscati.np.Constants.ConstantHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

// Handles data related to notch.
public class NotchManager {

	private final String TAG = "NotchManager";
	private int height = 0,
				width = 0,
				notchSize = 0,
				topRadius = 0,
				bottomRadius = 0,
				xPositionPortrait = 0,
				yPositionPortrait = 0,
				xPositionLandscape = 0,
				yPositionLandscape = 0;
	private String filePath;

	// Requests to the ConstantHolder the path to where the file where the settings are written
	public NotchManager() {

		filePath = new ConstantHolder().getConfigFilePathInternal();

	}

	public NotchManager(String filePath) {

		this.filePath = filePath;

	}

	// Reads the config file and returns true if read was successful
	public boolean read() {

		try {

			// Prepares to read the file
			File dataFile = new File(filePath);

			BufferedReader reader = new BufferedReader(new FileReader(dataFile));

			while (true) {

				// Initialize the line reader
				String line = reader.readLine();

				// If the line is empty (which means that the file is empty) breaks the loop and returns false (which means that the read of the config file is unsuccessful)
				if (line == null) {

					break;

				}

				// Reads the height
				else if (line.startsWith("H:")) {

					height = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the width
				else if (line.startsWith("W:")) {

					width = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the notchSize
				else if (line.startsWith("NS:")) {

					notchSize = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the topRadius
				else if (line.startsWith("TR:")) {

					topRadius = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the bottomRadius
				else if (line.startsWith("BR:")) {

					bottomRadius = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the xPosition in portrait mode
				else if (line.startsWith("XP:")) {

					xPositionPortrait = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the yPosition in portrait mode
				else if (line.startsWith("YP:")) {

					yPositionPortrait = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the xPosition in landscape mode
				else if (line.startsWith("XL:")) {

					xPositionLandscape = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

				// Reads the yPosition in landscape mode
				else if (line.startsWith("YL:")) {

					yPositionLandscape = Integer.parseInt(line.substring(line.indexOf(":") + 1));

				}

			}

			// Closes the reader
			reader.close();

			return true;

		} catch (Exception e) {

			// If an error is thrown the error is handled by the ErrorHandler util

			// The TAG and the Exception are sets
			ErrorHandler handler = new ErrorHandler(TAG, e);
			// The prepared packet is written to the log file
			handler.toFile();

			return false;

		}

	}

	// This method saves the file
	public boolean save() {

		try {

			// Prepares to write the file
			File dataFile = new File(filePath);

			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));

			// Writes the height
			writer.println("H:" + height);
			// Writes the width
			writer.println("W:" + width);
			// Writes the notchSize
			writer.println("NS:" + notchSize);
			// Writes the topRadius
			writer.println("TR:" + topRadius);
			// Writes the bottomRadius
			writer.println("BR:" + bottomRadius);
			// Writes the xPosition in portrait mode
			writer.println("XP:" + xPositionPortrait);
			// Writes the yPosition in portrait mode
			writer.println("YP:" + yPositionPortrait);
			// Writes the xPosition in landscape mode
			writer.println("XL:" + xPositionLandscape);
			// Writes the yPosition in landscape mode
			writer.println("YL:" + yPositionLandscape);

			// Closes the writer
			writer.close();

			return true;

		} catch (Exception e) {

			// If an error is thrown the error is handled by the ErrorHandler util

			// The TAG and the Exception are sets
			ErrorHandler handler = new ErrorHandler(TAG, e);
			// The prepared packet is written to the log file
			handler.toFile();

			return false;

		}

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

	// Sets the path to the file where the settings are saved
	public void setFilePath(String filePath) {

		this.filePath = filePath;

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

	// Returns [string] the TAG for the errorHandler
	public String getTAG() {

		return TAG;

	}

	// Returns [string] the path to the file where the settings are saved
	public String getFilePath() {

		return filePath;

	}

}
