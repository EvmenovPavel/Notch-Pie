package com.lorenzomoscati.np.Constants;

import android.os.Environment;

// Sample file holding constant data for app
public class ConstantHolder {

	private final int appCode = 7;
	private final String packageName = "com.lorenzomoscati.np";

	private final String configFolderPathInternal = Environment.getExternalStorageDirectory() + "/Android/data/com.lorenzomoscati.np";

	private final String configFilePathInternal = configFolderPathInternal + "/config";
	private final String settingsFilePathInternal = configFolderPathInternal + "/settings";
	private final String batteryFilePathInternal = configFolderPathInternal + "/battery";

	private final String configFolderPathExternal = Environment.getExternalStorageDirectory() + "/NotchPie";

	private final String configFetchUrl = "https://raw.githubusercontent.com/oddlyspaced/NotchPie-Data/master/";


	// Returns [string] the application code
	public int getAppCode() {

		return appCode;

	}

	// Returns [string] the package name
	public String getPackageName() {

		return packageName;

	}



	// Returns [string] the path of the internal folder where configurations are saved
	public String getConfigFolderPathInternal() {

		return configFolderPathInternal;

	}



	// Returns [string] the path to the file where the configurations are saved
	public String getConfigFilePathInternal() {

		return configFilePathInternal;

	}

	// Returns [string] the path to the file where the settings are saved
	public String getSettingsFilePathInternal() {

		return settingsFilePathInternal;

	}

	// Returns [string] the path to the file where the battery settings are saved
	public String getBatteryFilePathInternal() {

		return batteryFilePathInternal;

	}



	// Returns [string] the path of the external folder
	public String getConfigFolderPathExternal() {

		return configFolderPathExternal;

	}



	// Returns the URL where the configuration are hosted
	public String getConfigFetchUrl() {

		return configFetchUrl;

	}

}
