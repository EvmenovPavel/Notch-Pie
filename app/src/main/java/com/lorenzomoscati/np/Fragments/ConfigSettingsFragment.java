package com.lorenzomoscati.np.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lorenzomoscati.np.Interface.ColorPickerListener;
import com.lorenzomoscati.np.R;
import com.lorenzomoscati.np.Utils.ColorPicker;
import com.lorenzomoscati.np.Utils.SettingsManager;

import java.util.Objects;

public class ConfigSettingsFragment extends Fragment {

	// Settings manager object
	private SettingsManager manager;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View main = inflater.inflate(R.layout.fragment_config_settings, container, false);

		init();

		return attach(main);

	}


	// Initializes the method to call the settings manager's methods
	private void init() {

		manager = new SettingsManager(Objects.requireNonNull(getContext()));

	}

	// Takes the parent view and attaches the listeners to it
	private View attach(View parent) {

		// Switches
		Switch fullStatus = parent.findViewById(R.id.switchFullStatus);
		final Switch showBackground = parent.findViewById(R.id.switchShowBackground);
		Switch chargingAnimation = parent.findViewById(R.id.switchCharging);
		Switch landscapeSupport = parent.findViewById(R.id.switchLandscape);

		// Views
		View showBackgroundColor = parent.findViewById(R.id.viewTouchShowBackground);

		// TextViews
		final TextView backgroundColorText = parent.findViewById(R.id.txBackgroundColor);

		fullStatus.setChecked(manager.isFullStatus());
		showBackground.setChecked(manager.isShowBackground());
		chargingAnimation.setChecked(manager.isChargingAnimation());
		backgroundColorText.setText(String.format("%s %s", getString(R.string.current_color), manager.getBackgroundColor()));


		// Listener for fullStatus
		fullStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Changes the status
				manager.setFullStatus(isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});

		// Listener for showBackground
		showBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Changes the status
				manager.setShowBackground(isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});

		// Listener for chargingAnimation
		chargingAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Changes the status
				manager.setChargingAnimation(isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});

		// Listener for landscapeSupport
		landscapeSupport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Changes the status
				manager.setLandscapeSupport(isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});

		// Listener for backgroundColor
		showBackgroundColor.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Creates a colorPicker
				ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {

					@Override
					public void onColorSet(String color) {

						// Sets the background color
						manager.setBackgroundColor(color);
						// Saves the configuration
						manager.save(getContext());
						// Updates the string shown in the fragment
						backgroundColorText.setText(String.format("%s%s", getString(R.string.current_color), color));

					}

				});

				// Sets the color to the one read from the configuration file
				colorPicker.color = manager.getBackgroundColor();

				// Shows the colorPicker
				colorPicker.show(Objects.requireNonNull(getFragmentManager()), "color picker");

			}

		});

		return parent;

	}

}
