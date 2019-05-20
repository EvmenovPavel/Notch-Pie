package com.oddlyspaced.np.Utils;

import android.util.Log;

import com.oddlyspaced.np.Constants.ConstantHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

// saved the error to a file for proper analysis
public class ErrorHandler {

    private String tag;
    private Exception exception;
    private final String TAG = "ErrorHandler";

    public ErrorHandler(String tag, Exception exception) {
        this.tag = tag;
        this.exception = exception;
    }

    public void toFile() {
        try {
            DateFormat dateFormat = DateFormat.getDateTimeInstance();
            Date date = new Date();
            File err = new File(new ConstantHolder().getConfigFolderPathExternal() + "/" + tag + "-" + dateFormat.format(date) + ".log");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(err)));
            writer.println("----------------------------------------");
            writer.println(exception.toString());
            writer.println("----------------------------------------");
            StackTraceElement[] log = exception.getStackTrace();
            for (StackTraceElement element : log) {
                writer.println(element.toString());
            }
            writer.close();
        }
        catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
