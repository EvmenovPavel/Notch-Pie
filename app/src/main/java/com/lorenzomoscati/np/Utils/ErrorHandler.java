package com.lorenzomoscati.np.Utils;

import android.util.Log;

import com.lorenzomoscati.np.Constants.ConstantHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

// Saved the error to a file for proper analysis
public class ErrorHandler {

	private String tag;
	private Exception exception;
	private final String TAG = "ErrorHandler";

	// Method to set the TAG and the Exception
	public ErrorHandler(String tag, Exception exception) {

		// Sets the TAG
		this.tag = tag;
		// Sets the exception
		this.exception = exception;

	}

	// Writes the packet to the log file
	public void toFile() {

		try {

			// Dates
			DateFormat dateFormat = DateFormat.getDateTimeInstance();
			Date date = new Date();

			// Creates the log file
			File err = new File(new ConstantHolder().getConfigFolderPathExternal() + "/" + tag + "-" + dateFormat.format(date) + ".log");

			// Initializes the writer
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(err)));

			// Writes the exception
			writer.println("----------------------------------------");
			writer.println(exception.toString());
			writer.println("----------------------------------------");

			// Array where the errors are stored
			StackTraceElement[] log = exception.getStackTrace();

			//Loop to write the array of errors
			for (StackTraceElement element : log) {

				writer.println(element.toString());

			}

			// Closes the writer
			writer.close();

		}

		catch (Exception e) {

			// If the Handler can't write the log file, the error is written to the console log
			Log.e(TAG, e.toString());

		}

	}

}
