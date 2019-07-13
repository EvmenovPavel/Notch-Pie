package com.lorenzomoscati.np.Utils;

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

import androidx.appcompat.app.AppCompatDialogFragment;

import com.lorenzomoscati.np.Interface.ColorPickerListener;
import com.lorenzomoscati.np.R;

// https://www.youtube.com/watch?v=ARezg1D9Zd0
// Fragment class for ColorPicker dialog
public class ColorPicker extends AppCompatDialogFragment {

    // UI widgets
    private SeekBar red;
    private SeekBar green;
    private SeekBar blue;
    // the listener for handling color picker changes
    public ColorPickerListener listener;
    // the color preview in the dialog
    private View dColor;
    // text box for showing the color code
    private EditText editTextColor;
    // internal variable to store generated color
    public String color = "#FFFFFF";


    public ColorPicker(ColorPickerListener listener) {
        this.listener = listener;
    }

    // on creation of fragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    // on loading up of dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // load the layout to show in dialog
        View view = inflater.inflate(R.layout.dialog_color_picker, null);
        builder.setView(view); // setting view
        builder.setCancelable(false);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onColorSet(color);
            }
        });
        // initialising items
        dColor = view.findViewById(R.id.viewDialogColor);
        red = view.findViewById(R.id.sbRed);
        red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColorString();
                updateColorView(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        green = view.findViewById(R.id.sbGreen);
        green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColorString();
                updateColorView(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        blue = view.findViewById(R.id.sbBlue);
        blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateColorString();
                updateColorView(color);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        editTextColor = view.findViewById(R.id.editTextColor);
        editTextColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    dColor.setBackgroundColor(Color.parseColor(s.toString()));
                    color = s.toString();
                    updateColorSeekbar(color);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Invalid Color!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        updateColorSeekbar(color);
        // return the created builder dialog
        return builder.create();
    }

    // generate color string from the progress values
    private void updateColorString() {
        String r = Integer.toHexString(red.getProgress());
        if (r.length() == 1)
            r = "0" + r;
        String g = Integer.toHexString(green.getProgress());
        if (g.length() == 1)
            g = "0" + g;
        String b = Integer.toHexString(blue.getProgress());
        if (b.length() == 1)
            b = "0" + b;
        color = "#" + r + g + b;
    }

    // update the color view
    private void updateColorView(String c) {
        dColor.setBackgroundColor(Color.parseColor(c));
        editTextColor.setText(c);
    }

    // update values of seekbars
    private void updateColorSeekbar(String c) {
        red.setProgress(Integer.parseInt(c.substring(1, 3), 16));
        green.setProgress(Integer.parseInt(c.substring(3, 5), 16));
        blue.setProgress(Integer.parseInt(c.substring(5), 16));
    }
}
