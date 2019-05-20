package com.oddlyspaced.np.Constants;

import android.os.Environment;

// Sample file holding constant data for app
public class ConstantHolder {

    private final int appCode = 7;
    private final String configFolderPathInternal = Environment.getExternalStorageDirectory() + "/Android/data/com.oddlyspaced.np";
    private final String configFilePathInternal = configFolderPathInternal + "/config";
    private final String settingsFilePathInternal = configFolderPathInternal + "/settings";
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

    public String getConfigFetchUrl() {
        return configFetchUrl;
    }
}
