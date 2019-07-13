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

    public int getAppCode() {
        return appCode;
    }

    public String getConfigFolderPathInternal() {
        return configFolderPathInternal;
    }

    public String getConfigFilePathInternal() {
        return configFilePathInternal;
    }

    public String getSettingsFilePathInternal() {
        return settingsFilePathInternal;
    }

    public String getConfigFolderPathExternal() {
        return configFolderPathExternal;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getConfigFetchUrl() {
        return configFetchUrl;
    }

    public String getBatteryFilePathInternal() {
        return batteryFilePathInternal;
    }
}
