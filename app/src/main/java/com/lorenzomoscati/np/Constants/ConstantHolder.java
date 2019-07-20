package com.lorenzomoscati.np.Constants;

import android.content.Context;

// Sample file holding constant data for app
public class ConstantHolder {
	
	// Returns [string] the package name
	public String getPackageName() {
		
		return "com.lorenzomoscati.np";

	}



	// Returns [string] the path of the internal folder where configurations are saved
	public String getConfigFolderPathInternal(Context context) {

		return context.getFilesDir().getPath();

	}



	// Returns [string] the path to the file where the configurations are saved
	public String getConfigFilePathInternal(Context context) {
		
		return context.getFilesDir().getPath() + "/config";

	}

	// Returns [string] the path to the file where the settings are saved
	public String getSettingsFilePathInternal(Context context) {
		
		return context.getFilesDir().getPath() + "/settings";

	}

	// Returns [string] the path to the file where the battery settings are saved
	public String getBatteryFilePathInternal(Context context) {
		
		return context.getFilesDir().getPath() + "/battery";

	}



	// Returns [string] the path of the external folder
	public String getConfigFolderPathExternal(Context context) {

		return context.getFilesDir().getPath() + "/logs";

	}
	
	
}
