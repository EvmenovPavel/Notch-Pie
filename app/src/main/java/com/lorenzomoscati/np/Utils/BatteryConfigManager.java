package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lorenzomoscati.np.Modal.ColorLevel;

import java.util.ArrayList;

public class BatteryConfigManager {
	
	private ArrayList<ColorLevel> colorLevels;
	private SharedPreferences preferences;
	private static final String pref_name = "battery_config_preferences";
	private static final int pref_mode = 0;
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
	private static final String[] colorDefinedDef = {   "#FF5555",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#FFB86C",
														"#50FA7B"};
	
	// Configs the default colors
	public BatteryConfigManager(Context context) {

		preferences = context.getSharedPreferences(pref_name, pref_mode);
		
		colorLevels = colorDefined();
		
		preferences.getBoolean("is_linear", false);
		preferences.getString("color_linear_start", "#50FA7B");
		preferences.getString("color_linear_end", "#FF5555");
		
		preferences.getBoolean("is_defined", true);
		
	}
	
	private ArrayList<ColorLevel> colorDefined() {
		
		colorLevels = new ArrayList<>();
		ColorLevel level;
		
		for (int j = 0; j < 11; j++) {
			
			level = new ColorLevel();
			level.setColor(preferences.getString(colorDefined[j], colorDefinedDef[j]));
			
			if (j == 0) {
				
				level.setStartLevel(j);
				level.setEndLevel(j + 5);
				
			}
			
			else if (j == 1) {
				
				level.setStartLevel(j + 5);
				level.setEndLevel(j * 10);
				
			}
			
			else {
				
				level.setStartLevel(((j - 1) * 10) + 1);
				level.setEndLevel(j * 10);
				
			}
			
			colorLevels.add(level);
			
		}
		
		return colorLevels;
		
	}
	
	
	
	// Getters
	// Returns [bool] if the color is set to linear
	public boolean isLinear() {

		return preferences.getBoolean("is_linear", false);

	}

	// Returns [bool] if the colors are defined
	public boolean isDefined() {

		return preferences.getBoolean("is_defined", true);

	}

	// Returns [string] the starting linear color
	public String getLinearStart() {

		return preferences.getString("color_linear_start", "#50FA7B");

	}

	// Returns [string] the ending linear color
	public String getLinearEnd() {

		return preferences.getString("color_linear_end", "#FF5555");

	}

	// Returns [array] the colors and the levels
	public ArrayList<ColorLevel> getColorLevels() {
		
		return colorDefined();

	}
	
	
	
	// Setters
	// Sets the isLinear property
	public void setLinear(boolean linear, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("is_linear", linear);
		
		editor.apply();

	}

	// Sets the isDefined property
	public void setDefined(boolean defined, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean("is_defined", defined);
		
		editor.apply();

	}

	// Sets the linearStart color
	public void setLinearStart(String linearStart, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putString("color_linear_start", linearStart);
		
		editor.apply();

	}

	// Sets the linearEnd color
	public void setLinearEnd(String linearEnd, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putString("color_linear_end", linearEnd);
		
		editor.apply();

	}

	// Sets the color level
	public void setColorLevels(ArrayList<ColorLevel> colorLevels, Context context) {
		
		preferences = context.getSharedPreferences(pref_name, pref_mode);
		SharedPreferences.Editor editor = preferences.edit();
		
		for (int i = 0; i < colorLevels.size(); i++) {
			
			ColorLevel item = colorLevels.get(i);
			
			editor.putString(colorDefined[i], item.getColor());
			
		}
		
		editor.apply();

	}
	
}
