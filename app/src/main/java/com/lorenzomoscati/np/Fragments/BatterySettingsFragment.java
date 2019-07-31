package com.lorenzomoscati.np.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lorenzomoscati.np.Adapter.BatteryColorAdapter;
import com.lorenzomoscati.np.Interface.ColorPickerListener;
import com.lorenzomoscati.np.Interface.OnTouchColorLevel;
import com.lorenzomoscati.np.Modal.ColorLevel;
import com.lorenzomoscati.np.R;
import com.lorenzomoscati.np.Utils.BatteryConfigManager;
import com.lorenzomoscati.np.Utils.ColorPicker;

import java.util.ArrayList;
import java.util.Objects;

public class BatterySettingsFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View main = inflater.inflate(R.layout.fragment_battery_settings, container, false);

		init();
		
		return attach(main);

	}

	private BatteryColorAdapter batteryColorAdapter;
	private ArrayList<ColorLevel> list;
	private BatteryConfigManager manager;
	private int positionTouch;

	// Initializes the method to call the battery configuration manager's methods
	private void init() {

		manager = new BatteryConfigManager(Objects.requireNonNull(getContext()));

	}

	// Takes the parent view and attaches the listeners to it
	private View attach(final View main) {

		// Checkboxes
		final CheckBox linearAnimation = main.findViewById(R.id.cbLinearAnimation);
		final CheckBox definedAnimation = main.findViewById(R.id.cbDefinedAnimation);

		// TextViews
		final TextView linearStart = main.findViewById(R.id.txLinearColorStart);
		final TextView linearEnd = main.findViewById(R.id.txLinearColorEnd);

		// Views
		final View linearStartBorder = main.findViewById(R.id.viewLinearColorStart);
		final View linearEndBorder = main.findViewById(R.id.viewLinearColorEnd);

		// Applying listener to linear colors
		linearAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Updates the configuration
				manager.setLinear(isChecked);
				// Updates the opposite checkbox
				definedAnimation.setChecked(!isChecked);
				// Updates the configuration
				manager.setDefined(!isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});

		// Applying the listener to the first linear color
		linearStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Initializes a color picker
				ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {

					@Override
					public void onColorSet(String color) {

						// Updates the text view
						linearStart.setText(color);
						// Updates the configuration
						manager.setLinearStart(color);
						// Saves the configuration
						manager.save(getContext());
						// Updates the color preview
						linearStartBorder.setBackgroundColor(Color.parseColor(color));

					}

				});

				// Creates the color picker
				colorPicker.show(Objects.requireNonNull(getFragmentManager()), "color picker");

			}

		});

		// Applying the listener to the last linear color
		linearEnd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// Initializes a color picker
				ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {

					@Override
					public void onColorSet(String color) {

						// Updates the text view
						linearEnd.setText(color);
						// Updates the configuration
						manager.setLinearEnd(color);
						// Saves the configuration
						manager.save(getContext());
						// Updates the color preview
						linearEndBorder.setBackgroundColor(Color.parseColor(color));

					}

				});

				// Creates the color picker
				colorPicker.show(Objects.requireNonNull(getFragmentManager()), "color picker");

			}

		});

		// Applying listener to defined colors
		definedAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				// Updates the configuration
				manager.setDefined(isChecked);
				// Updates the opposite checkbox
				linearAnimation.setChecked(!isChecked);
				// Updates the configuration
				manager.setDefined(!isChecked);
				// Saves the configuration
				manager.save(getContext());

			}

		});



		// Recycler view for the colors and battery level
		RecyclerView recyclerView = main.findViewById(R.id.rvBatteryLevel);
		// Sets the recycler to have a fixed size
		recyclerView.setHasFixedSize(true);
		// Applies a layout manager to it
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		// Fills the array with the color levels from the settings
		list = manager.getColorLevels();

		// ###DEBUG###
		if (getContext() == null) {

			Log.e("why", "why");

		}
		// ###DEBUG###

		// Applying a listener to get when the color is touched, so that a color picker can be summoned
		batteryColorAdapter = new BatteryColorAdapter(getContext(), list, new OnTouchColorLevel() {

			@Override
			public void onTouchItem(int position) {

				// Retrieves the color that needs to be modified
				positionTouch = position;

				// Initializes a color picker
				ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {

					@Override
					public void onColorSet(String color) {

						// Gets the previous color
						ColorLevel prev = list.get(positionTouch);
						// Sets the new color
						prev.setColor(color);
						// Sets the new color into the list
						list.set(positionTouch, prev);
						// Notifies the manager that a color has changed
						batteryColorAdapter.notifyItemChanged(positionTouch);
						// Updates the configuration
						manager.setColorLevels(list);
						// Saves the configuration
						manager.save(getContext());

					}

				});

				// Gets the color to be shown in preview
				colorPicker.color = list.get(position).getColor();
				// Creates a color picker
				colorPicker.show(Objects.requireNonNull(getFragmentManager()), "color picker");

			}

		});

		// Applies the adapter to the recycler view
		recyclerView.setAdapter(batteryColorAdapter);
		
		linearAnimation.setChecked(manager.isLinear());
		definedAnimation.setChecked(manager.isDefined());
		linearStart.setText(manager.getLinearStart());
		linearEnd.setText(manager.getLinearEnd());
		linearStartBorder.setBackgroundColor(Color.parseColor(manager.getLinearStart()));
		linearEndBorder.setBackgroundColor(Color.parseColor(manager.getLinearEnd()));

		return main;

	}

}
