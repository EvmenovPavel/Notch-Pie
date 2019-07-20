package com.lorenzomoscati.np.Utils;

import android.content.Context;

import com.lorenzomoscati.np.Constants.ConstantHolder;
import com.lorenzomoscati.np.Modal.ColorLevel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BatteryConfigManager {

	private final String TAG = "BatteryConfigManager";
	private boolean isLinear = false, isDefined = true;
	private String linearStart = "#FF0000", linearEnd = "#0000FF";
	private ArrayList<ColorLevel> colorLevels;
	private String filePath;


	// Configs the default colors
	public BatteryConfigManager(Context context) {

		filePath = new ConstantHolder().getBatteryFilePathInternal(context);
		ColorLevel level = new ColorLevel();

		colorLevels = new ArrayList<>();
		level.setColor("#FF5555");
		level.setStartLevel(0);
		level.setEndLevel(5);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(6);
		level.setEndLevel(10);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(11);
		level.setEndLevel(20);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(21);
		level.setEndLevel(30);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(31);
		level.setEndLevel(40);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(41);
		level.setEndLevel(50);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(51);
		level.setEndLevel(60);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(61);
		level.setEndLevel(70);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(71);
		level.setEndLevel(80);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#ffb86c");
		level.setStartLevel(81);
		level.setEndLevel(90);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor("#50fa7b");
		level.setStartLevel(91);
		level.setEndLevel(100);
		colorLevels.add(level);

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

				}

				// If the line if LL: means that the line tells about linear color settings
				if (line.startsWith("LL:")) {

					// Checks if the setting is set to true
					String s = line.substring(line.indexOf(":") + 1);
					isLinear = s.startsWith("T");


					s = s.substring(s.indexOf(":") + 1);
					// Retrieves the StartColor
					linearStart = s.substring(0, s.indexOf(":"));
					// Retrieves the EndColor
					linearEnd = s.substring(s.indexOf(":") + 1);

				}

				if (line.startsWith("DL:")) {

					isDefined = line.substring(line.indexOf(":") + 1).equals("T");

				}

				// The list of defined colors is starting
				else if (line.startsWith("ColorStart")) {

					// Initializes an array to contain every defined color
					colorLevels = new ArrayList<>();

					while (true) {

						// Initializes the line reader
						String s = reader.readLine();

						// If the list is done the loop is break
						if (s.equals("ColorEnd")) {

							break;

						}

						// Sets where the color code ends and when the percentages starts
						StringTokenizer stringTokenizer = new StringTokenizer(s, ",");

						// Variable to contain the color level
						ColorLevel cl = new ColorLevel();

						//Sets the color code
						cl.setColor(stringTokenizer.nextToken());
						// Sets the first percentage
						cl.setStartLevel(Integer.parseInt(stringTokenizer.nextToken()));
						// Sets the second percentage
						cl.setEndLevel(Integer.parseInt(stringTokenizer.nextToken()));

						// Adds the color variable to the array of defined colors
						colorLevels.add(cl);

					}

				}

			}

			//Closes the reader
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

	// this method saves the file
	public void save(Context context) {

		try {

			// Prepares to write the file
			File dataFile = new File(filePath);

			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));

			// Sets the LinearColor attribute to true or false
			if (isLinear) {

				writer.println("LL:T:" + linearStart + ":" + linearEnd);

			}

			else {

				writer.println("LL:F:" + linearStart + ":" + linearEnd);

			}

			// Sets the DefinedColor attribute to true or false
			if (isDefined) {

				writer.println("DL:T");

			}

			else {

				writer.println("DL:F");

			}

			// Writes down the statement which tells that the color list is starting
			writer.println("ColorStart");

			// Loop to write the array of colors
			for (ColorLevel item : colorLevels) {

				writer.println(item.getColor() + "," + item.getStartLevel() + "," + item.getEndLevel());

			}

			// Writes down the statement which tells that the color list is ending
			writer.println("ColorEnd");

			// Closes the writer
			writer.close();
			
		} catch (Exception e) {

			// If an error is thrown the error is handled by the ErrorHandler util

			// The TAG and the Exception are sets
			ErrorHandler handler = new ErrorHandler(TAG, e);
			// The prepared packet is written to the log file
			handler.toFile(context);
			
		}

	}


	// Returns [bool] if the color is set to linear
	public boolean isLinear() {

		return isLinear;

	}

	// Returns [bool] if the colors are defined
	public boolean isDefined() {

		return isDefined;

	}

	// Returns [string] the starting linear color
	public String getLinearStart() {

		return linearStart;

	}

	// Returns [string] the ending linear color
	public String getLinearEnd() {

		return linearEnd;

	}

	// Returns [array] the colors and the levels
	public ArrayList<ColorLevel> getColorLevels() {

		return colorLevels;

	}
	
	// Sets the isLinear property
	public void setLinear(boolean linear) {

		isLinear = linear;

	}

	// Sets the isDefined property
	public void setDefined(boolean defined) {

		isDefined = defined;

	}

	// Sets the linearStart color
	public void setLinearStart(String linearStart) {

		this.linearStart = linearStart;

	}

	// Sets the linearEnd color
	public void setLinearEnd(String linearEnd) {

		this.linearEnd = linearEnd;

	}

	// Sets the color level
	public void setColorLevels(ArrayList<ColorLevel> colorLevels) {

		this.colorLevels = colorLevels;

	}
	
}
