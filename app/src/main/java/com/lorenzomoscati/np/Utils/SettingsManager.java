package com.lorenzomoscati.np.Utils;

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
    private boolean fullStatus = false, showBackground = false, chargingAnimation = false, fillOverlay = false, landscapeSupport = false;
    private String backgroundColor = "#000000";
    private String filePath;

    public SettingsManager() {
        filePath = new ConstantHolder().getSettingsFilePathInternal();
    }

    public SettingsManager(String filePath) {
        this.filePath = filePath;
    }

    // reads the config file and returns true if read was successful
    public boolean read() {
        try {
            File dataFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else if (line.startsWith("FS:")) {
                    fullStatus = line.substring(line.indexOf(":") + 1).equals("T");
                } else if (line.startsWith("SB:")) {
                    showBackground = line.substring(line.indexOf(":") + 1, line.lastIndexOf(":")).equals("T");
                    backgroundColor = line.substring(line.lastIndexOf(":") + 1);
                } else if (line.startsWith("CA:")) {
                    chargingAnimation = line.substring(line.indexOf(":") + 1).equals("T");
                } else if (line.startsWith("FO:")) {
                    fillOverlay = line.substring(line.indexOf(":") + 1).equals("T");
                } else if (line.startsWith("LS:")) {
                    landscapeSupport = line.substring(line.indexOf(":") + 1).equals("T");
                }
            }
            reader.close();
            return true;
        } catch (Exception e) {
            ErrorHandler handler = new ErrorHandler(TAG, e);
            handler.toFile();
            return false;
        }
    }

    // this method saves the file
    public boolean save() {
        try {
            File dataFile = new File(filePath);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(dataFile)));
            if (fullStatus) {
                writer.println("FS:T");
            } else {
                writer.println("FS:F");
            }
            if (showBackground) {
                writer.println("SB:T:" + backgroundColor);
            } else {
                writer.println("SB:F:" + backgroundColor);
            }
            if (chargingAnimation) {
                writer.println("CA:T");
            } else {
                writer.println("CA:F");
            }
            if (fillOverlay) {
                writer.println("FO:T");
            } else {
                writer.println("FO:F");
            }
            writer.println("LS:" + ((landscapeSupport)?"T":"F"));
            writer.close();
            return true;
        } catch (Exception e) {
            ErrorHandler handler = new ErrorHandler(TAG, e);
            handler.toFile();
            return false;
        }
    }

    public String getTAG() {
        return TAG;
    }

    public boolean isFullStatus() {
        return fullStatus;
    }

    public boolean isShowBackground() {
        return showBackground;
    }

    public boolean isChargingAnimation() {
        return chargingAnimation;
    }

    public boolean isFillOverlay() {
        return fillOverlay;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isLandscapeSupport() {
        return landscapeSupport;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFullStatus(boolean fullStatus) {
        this.fullStatus = fullStatus;
    }

    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }

    public void setChargingAnimation(boolean chargingAnimation) {
        this.chargingAnimation = chargingAnimation;
    }

    public void setFillOverlay(boolean fillOverlay) {
        this.fillOverlay = fillOverlay;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setLandscapeSupport(boolean landscapeSupport) {
        this.landscapeSupport = landscapeSupport;
    }
}
