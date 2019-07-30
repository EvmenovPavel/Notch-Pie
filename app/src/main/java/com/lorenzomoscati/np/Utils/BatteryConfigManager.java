package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.lorenzomoscati.np.Modal.ColorLevel;

import java.util.ArrayList;

public class BatteryConfigManager {

	private boolean isLinear, isDefined;
	private String linearStart, linearEnd;
	private ArrayList<ColorLevel> colorLevels;
	private SharedPreferences preferences;
	private static final String[] colorDefined = {  "color_05",
													"color_610",
													"color_1120",
													"color_2130",
													"color_3140",
													"color_4150",
													"color_5160",
													"color_6170",
													"color_7180",
													"color_8190",
													"color_91100"};
	
	// Configs the default colors
	public BatteryConfigManager(Context context) {

		preferences = context.getSharedPreferences("preferences", 0);
		
		colorLevels = new ArrayList<>();
		ColorLevel level;
		
		isLinear = preferences.getBoolean("is_linear", false);
		linearStart = preferences.getString("color_linear_start", "#50FA7B");
		linearEnd = preferences.getString("color_linear_end", "#FF5555");
		
		isDefined = preferences.getBoolean("is_defined", true);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[0], "#FF5555"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[0], "#FF5555"));
		level.setStartLevel(0);
		level.setEndLevel(5);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[1], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[1], "#FFB86C"));
		level.setStartLevel(6);
		level.setEndLevel(10);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[2], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[2], "#FFB86C"));
		level.setStartLevel(11);
		level.setEndLevel(20);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[3], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[3], "#FFB86C"));
		level.setStartLevel(21);
		level.setEndLevel(30);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[4], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[4], "#FFB86C"));
		level.setStartLevel(31);
		level.setEndLevel(40);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[5], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[5], "#FFB86C"));
		level.setStartLevel(41);
		level.setEndLevel(50);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[6], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[6], "#FFB86C"));
		level.setStartLevel(51);
		level.setEndLevel(60);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[7], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[7], "#FFB86C"));
		level.setStartLevel(61);
		level.setEndLevel(70);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[8], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[8], "#FFB86C"));
		level.setStartLevel(71);
		level.setEndLevel(80);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[9], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[9], "#FFB86C"));
		level.setStartLevel(81);
		level.setEndLevel(90);
		colorLevels.add(level);

		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[10], "#50FA7B"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[10], "#50FA7B"));
		level.setStartLevel(91);
		level.setEndLevel(100);
		colorLevels.add(level);

	}
	
	// Reads the config file and returns true if read was successful
	public boolean read(Context context) {
		
		preferences = context.getSharedPreferences("preferences", 0);
		colorLevels = new ArrayList<>();
		ColorLevel level;
		
		isLinear = preferences.getBoolean("is_linear", false);
		linearStart = preferences.getString("color_linear_start", "#50FA7B");
		linearEnd = preferences.getString("color_linear_end", "#FF5555");
		
		isDefined = preferences.getBoolean("is_defined", true);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[0], "#FF5555"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[0], "#FF5555"));
		level.setStartLevel(0);
		level.setEndLevel(5);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[1], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[1], "#FFB86C"));
		level.setStartLevel(6);
		level.setEndLevel(10);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[2], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[2], "#FFB86C"));
		level.setStartLevel(11);
		level.setEndLevel(20);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[3], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[3], "#FFB86C"));
		level.setStartLevel(21);
		level.setEndLevel(30);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[4], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[4], "#FFB86C"));
		level.setStartLevel(31);
		level.setEndLevel(40);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[5], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[5], "#FFB86C"));
		level.setStartLevel(41);
		level.setEndLevel(50);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[6], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[6], "#FFB86C"));
		level.setStartLevel(51);
		level.setEndLevel(60);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[7], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[7], "#FFB86C"));
		level.setStartLevel(61);
		level.setEndLevel(70);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[8], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[8], "#FFB86C"));
		level.setStartLevel(71);
		level.setEndLevel(80);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[9], "#FFB86C"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[9], "#FFB86C"));
		level.setStartLevel(81);
		level.setEndLevel(90);
		colorLevels.add(level);
		
		level = new ColorLevel();
		level.setColor(preferences.getString(colorDefined[10], "#50FA7B"));
		Log.e("TAG", "Color: " + preferences.getString(colorDefined[10], "#50FA7B"));
		level.setStartLevel(91);
		level.setEndLevel(100);
		colorLevels.add(level);
		
		return true;

	}

	// this method saves the file
	public void save(Context context) {
		
		preferences = context.getSharedPreferences("preferences", 0);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("is_linear", isLinear);
		editor.putString("color_linear_start", linearStart);
		editor.putString("color_linear_end", linearEnd);
		
		editor.putBoolean("is_defined", isDefined);
		
		for (int i = 0; i < colorLevels.size(); i++) {
			
			ColorLevel item = colorLevels.get(i);
			
			editor.putString(colorDefined[i], item.getColor());
			
		}
		
		editor.apply();

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
