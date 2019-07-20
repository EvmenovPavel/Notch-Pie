package com.lorenzomoscati.np.Utils;

import android.content.Context;

import com.lorenzomoscati.np.Constants.ConstantHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

// Handles data related to notch.
public class SettingsManager {

	private final String TAG = "SettingsManager";
	private boolean fullStatus = false,
					showBackground = false,
					chargingAnimation = false,
					landscapeSupport = false;
	private String backgroundColor = "#000000";
	private final String filePath;

	// Requests to the ConstantHolder the path to where the file where the settings are written
	public SettingsManager(Context context) {

		filePath = new ConstantHolder().getSettingsFilePathInternal(context);

	}


	// Reads the config file and returns true if read was successful
	public boolean read(Context context) {

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

				} else {

					// Variables to recognize the true statement about a config
					boolean True = line.substring(line.indexOf(":") + 1).equals("T");

					// Checks if the FullStatus is set to true
					if (line.startsWith("FS:")) {

						fullStatus = True;

					}

					// Checks if the background is active, and also checks the color set for it
					else if (line.startsWith("SB:")) {

						showBackground = line.substring(line.indexOf(":") + 1, line.lastIndexOf(":")).equals("T");
						backgroundColor = line.substring(line.lastIndexOf(":") + 1);

					}

					// Checks if the  charging animation is active
					else if (line.startsWith("CA:")) {

						chargingAnimation = True;

					}

					// Checks if the LandscapeSupport is set to true
					else if (line.startsWith("LS:")) {

						landscapeSupport = True;

					}

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
			handler.toFile(context);

			return false;

		}

	}

	// This method saves the file
	public void save(Context context) {

		try {

			// Prepares to read the file
			File dataFile = new File(filePath);

			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));

			// Writes whether the FullStatus is true or false
			if (fullStatus) {

				writer.println("FS:T");

			}

			else {

				writer.println("FS:F");

			}

			// Writes whether the ShowBackground is true or false
			if (showBackground) {

				writer.println("SB:T:" + backgroundColor);

			}

			else {

				writer.println("SB:F:" + backgroundColor);

			}

			// Writes whether the ChargingAnimation is true or false
			if (chargingAnimation) {

				writer.println("CA:T");

			}

			else {

				writer.println("CA:F");

			}

			// Writes whether the LandscapeSupport is true or false
			if (landscapeSupport) {

				writer.println("LS:T");

			}

			else {

				writer.println("LS:F");

			}

			// Closes the reader
			writer.close();
			
		} catch (Exception e) {

			// If an error is thrown the error is handled by the ErrorHandler util

			// The TAG and the Exception are sets
			ErrorHandler handler = new ErrorHandler(TAG, e);
			// The prepared packet is written to the log file
			handler.toFile(context);
			
		}

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
