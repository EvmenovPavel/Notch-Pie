package com.oddlyspaced.np.Utils;

import com.oddlyspaced.np.Constants.ConstantHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

// Handles data related to notch.
public class SettingsManager {

    private final String TAG = "SettingsManager";
    private boolean fullStatus = false, showBackground = false, fillOverlay = false;
    private String backgroundColor = "#000000";
    private String filePath;
    private boolean chargingAnimation1 = false, chargingAnimation2 = false, chargingAnimation3 = false;

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
                } else if (line.startsWith("CA1:")) {
                    chargingAnimation1 = line.substring(line.indexOf(":") + 1).equals("T");
                }
                else if (line.startsWith("CA2:")) {
                    chargingAnimation2 = line.substring(line.indexOf(":") + 1).equals("T");
                }
                else if (line.startsWith("CA3:")) {
                    chargingAnimation3 = line.substring(line.indexOf(":") + 1).equals("T");
                }else if (line.startsWith("FO:")) {
                    fillOverlay = line.substring(line.indexOf(":") + 1).equals("T");
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
            if (chargingAnimation1) {
                writer.println("CA1:T");
            } else {
                writer.println("CA1:F");
            }
            if (chargingAnimation2) {
                writer.println("CA2:T");
            } else {
                writer.println("CA2:F");
            }
            if (chargingAnimation3) {
                writer.println("CA3:T");
            } else {
                writer.println("CA3:F");
            }
            if (fillOverlay) {
                writer.println("FO:T");
            } else {
                writer.println("FO:F");
            }
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

    public boolean isChargingAnimation1() {
        return chargingAnimation1;
    }

    public boolean isChargingAnimation2() {
        return chargingAnimation2;
    }

    public boolean isChargingAnimation3() {
        return chargingAnimation3;
    }

    public boolean isFillOverlay() {
        return fillOverlay;
    }

    public String getBackgroundColor() {
        return backgroundColor;
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

    public void setChargingAnimation1(boolean chargingAnimation1) {
        this.chargingAnimation1 = chargingAnimation1;
    }

    public void setChargingAnimation2(boolean chargingAnimation2) {
        this.chargingAnimation2 = chargingAnimation2;
    }

    public void setChargingAnimation3(boolean chargingAnimation3) {
        this.chargingAnimation3 = chargingAnimation3;
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
}
