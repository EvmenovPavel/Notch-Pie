package com.lorenzomoscati.np.Utils;

import com.lorenzomoscati.np.Constants.ConstantHolder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

// Handles data related to notch.
public class NotchManager {

    private final String TAG = "NotchManager";
    private int height = 0, width = 0, notchSize = 0, topRadius = 0, bottomRadius = 0, xPositionPortrait = 0, yPositionPortrait = 0, xPositionLandscape = 0, yPositionLandscape = 0;
    private String filePath;

    public NotchManager() {
        filePath = new ConstantHolder().getConfigFilePathInternal();
    }

    public NotchManager(String filePath) {
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
                } else if (line.startsWith("H:")) {
                    height = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("W:")) {
                    width = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("NS:")) {
                    notchSize = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("TR:")) {
                    topRadius = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("BR:")) {
                    bottomRadius = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("XP:")) {
                    xPositionPortrait = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("YP:")) {
                    yPositionPortrait = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                }
                else if (line.startsWith("XL:")) {
                    xPositionLandscape = Integer.parseInt(line.substring(line.indexOf(":") + 1));
                } else if (line.startsWith("YL:")) {
                    yPositionLandscape = Integer.parseInt(line.substring(line.indexOf(":") + 1));
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
            writer.println("H:" + height);
            writer.println("W:" + width);
            writer.println("NS:" + notchSize);
            writer.println("TR:" + topRadius);
            writer.println("BR:" + bottomRadius);
            writer.println("XP:" + xPositionPortrait);
            writer.println("YP:" + yPositionPortrait);
            writer.println("XL:" + xPositionLandscape);
            writer.println("YL:" + yPositionLandscape);

            writer.close();
            return true;
        } catch (Exception e) {
            ErrorHandler handler = new ErrorHandler(TAG, e);
            handler.toFile();
            return false;
        }
    }

    // setters
    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setNotchSize(int notchSize) {
        this.notchSize = notchSize;
    }

    public void setTopRadius(int topRadius) {
        this.topRadius = topRadius;
    }

    public void setBottomRadius(int bottomRadius) {
        this.bottomRadius = bottomRadius;
    }

    public void setxPositionPortrait(int xPositionPortrait) {
        this.xPositionPortrait = xPositionPortrait;
    }

    public void setyPositionPortrait(int yPositionPortrait) {
        this.yPositionPortrait = yPositionPortrait;
    }

    public void setxPositionLandscape(int xPositionLandscape) {
        this.xPositionLandscape = xPositionLandscape;
    }

    public void setyPositionLandscape(int yPositionLandscape) {
        this.yPositionLandscape = yPositionLandscape;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    // getters
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNotchSize() {
        return notchSize;
    }

    public int getTopRadius() {
        return topRadius;
    }

    public int getBottomRadius() {
        return bottomRadius;
    }

    public String getTAG() {
        return TAG;
    }

    public int getxPositionPortrait() {
        return xPositionPortrait;
    }

    public int getyPositionPortrait() {
        return yPositionPortrait;
    }

    public int getxPositionLandscape() {
        return xPositionLandscape;
    }

    public int getyPositionLandscape() {
        return yPositionLandscape;
    }

    public String getFilePath() {
        return filePath;
    }
}
