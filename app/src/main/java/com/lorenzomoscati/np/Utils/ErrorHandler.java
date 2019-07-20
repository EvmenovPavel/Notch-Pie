package com.lorenzomoscati.np.Utils;

import android.content.Context;
import android.util.Log;

import com.lorenzomoscati.np.Constants.ConstantHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

// Saved the error to a file for proper analysis
class ErrorHandler {

	private String tag;
	private Exception exception;
	
	// Method to set the TAG and the Exception
	ErrorHandler(String tag, Exception exception) {

		// Sets the TAG
		this.tag = tag;
		// Sets the exception
		this.exception = exception;

	}

	// Writes the packet to the log file
	void toFile(Context context) {

		try {

			// Dates
			DateFormat dateFormat = DateFormat.getDateTimeInstance();
			Date date = new Date();

			// Creates the log file
			File err = new File(new ConstantHolder().getConfigFolderPathExternal(context) + "/" + tag + "-" + dateFormat.format(date) + ".log");

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
			String TAG = "ErrorHandler";
			Log.e(TAG, e.toString());

		}

	}

}
