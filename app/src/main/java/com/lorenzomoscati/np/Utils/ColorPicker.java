package com.lorenzomoscati.np.Utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.lorenzomoscati.np.Interface.ColorPickerListener;
import com.lorenzomoscati.np.R;

import java.util.Objects;

// Fragment class for ColorPicker dialog
public class ColorPicker extends AppCompatDialogFragment {

	// RGB SeekBars
	private SeekBar red;
	private SeekBar green;
	private SeekBar blue;

	// Listener to handle color picker changes
	private final ColorPickerListener listener;

	// Color preview View
	private View dColor;

	// Text box to show the color code
	private EditText editTextColor;

	// Internal variable to store generated color
	public String color = "#FFFFFF";


	// Method to call the listener
	public ColorPicker(ColorPickerListener listener) {

		this.listener = listener;

	}

	@Override
	public void onAttach(Context context) {

		super.onAttach(context);

	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();

		// Load the layout to show in dialog
		@SuppressLint("InflateParams")
		View view = inflater.inflate(R.layout.dialog_color_picker, null);

		builder.setView(view);

		builder.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// Advises the listener that the colors are set
				listener.onColorSet(color);

			}

		});

		builder.setCancelable(false);

		// Initialising items

		// Preview
		dColor = view.findViewById(R.id.viewDialogColor);

		// Red
		red = view.findViewById(R.id.sbRed);
		// Listener for Red
		red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				// Updates the string according to the level
				updateColorString();
				// Updates the preview according to the level
				updateColorView(color);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		});

		// Green
		green = view.findViewById(R.id.sbGreen);
		// Listener for Green
		green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				// Updates the string according to the level
				updateColorString();
				// Updates the preview according to the level
				updateColorView(color);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		});

		// Blue
		blue = view.findViewById(R.id.sbBlue);
		// Listener for Blue
		blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

				// Updates the string according to the level
				updateColorString();
				// Updates the preview according to the level
				updateColorView(color);

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

		});

		// Color String
		editTextColor = view.findViewById(R.id.editTextColor);
		// Listener for Color String
		editTextColor.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				// Try to change the color according to the given string
				try {

					// Updates the preview
					dColor.setBackgroundColor(Color.parseColor(s.toString()));

					// Updates the seekBars
					color = s.toString();
					updateColorSeekbar(color);

				} catch (Exception e) {

					// If the color string is not valid, the user is notified
					Toast.makeText(getContext(), R.string.color_invalid, Toast.LENGTH_SHORT).show();

				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}

		});

		updateColorSeekbar(color);

		// Returns the created builder dialog
		return builder.create();

	}

	// Method to generate the color string from the seekBars
	private void updateColorString() {

		//  Gets the value of the red seekBar
		String r = Integer.toHexString(red.getProgress());
		if (r.length() == 1) {
			r = "0" + r;
		}

		//  Gets the value of the green seekBar
		String g = Integer.toHexString(green.getProgress());
		if (g.length() == 1) {
			g = "0" + g;
		}

		//  Gets the value of the blue seekBar
		String b = Integer.toHexString(blue.getProgress());
		if (b.length() == 1) {
			b = "0" + b;
		}

		// Creates the string according to the seekBars
		color = "#" + r + g + b;

	}

	// Method to update the color preview
	private void updateColorView(String c) {

		// Sets the color preview
		dColor.setBackgroundColor(Color.parseColor(c));

		// Sets the string
		editTextColor.setText(c);

	}

	// Method to update the values of the seekBars
	private void updateColorSeekbar(String c) {

		// Retrieves red amount
		red.setProgress(Integer.parseInt(c.substring(1, 3), 16));
		// Retrieves green amount
		green.setProgress(Integer.parseInt(c.substring(3, 5), 16));
		// Retrieves blue amount
		blue.setProgress(Integer.parseInt(c.substring(5), 16));

	}

}
