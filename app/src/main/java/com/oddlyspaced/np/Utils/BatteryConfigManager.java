package com.oddlyspaced.np.Utils;

import android.util.Log;

import com.oddlyspaced.np.Constants.ConstantHolder;
import com.oddlyspaced.np.Modal.ColorLevel;

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


    public BatteryConfigManager() {
        filePath = new ConstantHolder().getBatteryFilePathInternal();
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

    public BatteryConfigManager(String filePath) {
        this.filePath = filePath;
        ColorLevel level = new ColorLevel();

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

    // reads the config file and returns true if read was successful
    public boolean read() {
        try {
            File dataFile = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("LL:")) {
                    String s = line.substring(line.indexOf(":") + 1);
                    isLinear = s.startsWith("T");
                    s = s.substring(s.indexOf(":") + 1);
                    linearStart = s.substring(0, s.indexOf(":"));
                    linearEnd = s.substring(s.indexOf(":") + 1);
                }
                if (line.startsWith("DL:")) {
                    isDefined = line.substring(line.indexOf(":") + 1).equals("T");
                }
                else if (line.startsWith("ColorStart")) {
                    colorLevels = new ArrayList<>();
                    while (true) {
                        String s = reader.readLine();
                        if (s.equals("ColorEnd"))
                            break;
                        StringTokenizer stringTokenizer = new StringTokenizer(s, ",");
                        ColorLevel cl = new ColorLevel();
                        cl.setColor(stringTokenizer.nextToken());
                        cl.setStartLevel(Integer.parseInt(stringTokenizer.nextToken()));
                        cl.setEndLevel(Integer.parseInt(stringTokenizer.nextToken()));
                        colorLevels.add(cl);
                    }
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
            if (isLinear) {
                writer.println("LL:T:" + linearStart + ":" + linearEnd);
            }
            else {
                writer.println("LL:F:" + linearStart + ":" + linearEnd);
            }

            if (isDefined) {
                writer.println("DL:T");
            }
            else {
                writer.println("DL:F");
            }

            writer.println("ColorStart");
            for (ColorLevel item : colorLevels) {
                writer.println(item.getColor() + "," + item.getStartLevel() + "," + item.getEndLevel());
            }
            writer.println("ColorEnd");
            writer.close();
            return true;
        } catch (Exception e) {
            ErrorHandler handler = new ErrorHandler(TAG, e);
            handler.toFile();
            return false;
        }
    }


    public boolean isLinear() {
        return isLinear;
    }

    public boolean isDefined() {
        return isDefined;
    }

    public String getLinearStart() {
        return linearStart;
    }

    public String getLinearEnd() {
        return linearEnd;
    }

    public ArrayList<ColorLevel> getColorLevels() {
        return colorLevels;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setLinear(boolean linear) {
        isLinear = linear;
    }

    public void setDefined(boolean defined) {
        isDefined = defined;
    }

    public void setLinearStart(String linearStart) {
        this.linearStart = linearStart;
    }

    public void setLinearEnd(String linearEnd) {
        this.linearEnd = linearEnd;
    }

    public void setColorLevels(ArrayList<ColorLevel> colorLevels) {
        this.colorLevels = colorLevels;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
