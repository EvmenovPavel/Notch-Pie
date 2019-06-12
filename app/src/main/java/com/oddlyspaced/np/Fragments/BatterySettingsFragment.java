package com.oddlyspaced.np.Fragments;

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

import com.oddlyspaced.np.Adapter.BatteryColorAdapter;
import com.oddlyspaced.np.Interface.ColorPickerListener;
import com.oddlyspaced.np.Interface.OnTouchColorLevel;
import com.oddlyspaced.np.Modal.ColorLevel;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Utils.BatteryConfigManager;
import com.oddlyspaced.np.Utils.ColorPicker;

import java.util.ArrayList;

public class BatterySettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_battery_settings, container, false);
        init();
        main = attach(main);
        return main;
    }

    private RecyclerView recyclerView;
    private BatteryColorAdapter batteryColorAdapter;
    private ArrayList<ColorLevel> list;
    private BatteryConfigManager manager;
    private int positionTouch = -1;

    private void init() {
        manager = new BatteryConfigManager();
    }

    // takes the parent view and attaches the listeners to it
    private View attach(final View main) {
        final CheckBox linearAnimation = main.findViewById(R.id.cbLinearAnimation);
        final CheckBox definedAnimation = main.findViewById(R.id.cbDefinedAnimation);
        final TextView linearStart = main.findViewById(R.id.txLinearColorStart);
        final TextView linearEnd = main.findViewById(R.id.txLinearColorEnd);
        final View linearStartBorder = main.findViewById(R.id.viewLinearColorStart);
        final View linearEndBorder = main.findViewById(R.id.viewLinearColorEnd);

        linearAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setLinear(isChecked);
                definedAnimation.setChecked(!isChecked);
                manager.setDefined(!isChecked);
                manager.save();
            }
        });

        linearStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {
                    @Override
                    public void onColorSet(String color) {
                        linearStart.setText(color);
                        manager.setLinearStart(color);
                        manager.save();
                        linearStartBorder.setBackgroundColor(Color.parseColor(color));
                    }
                });
                colorPicker.show(getFragmentManager(), "color picker");
            }
        });

        linearEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {
                    @Override
                    public void onColorSet(String color) {
                        linearEnd.setText(color);
                        manager.setLinearEnd(color);
                        manager.save();
                        linearEndBorder.setBackgroundColor(Color.parseColor(color));

                    }
                });
                colorPicker.show(getFragmentManager(), "color picker");
            }
        });

        definedAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setDefined(isChecked);
                linearAnimation.setChecked(!isChecked);
                manager.setDefined(!isChecked);
                manager.save();
            }
        });

        recyclerView = main.findViewById(R.id.rvBatteryLevel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = manager.getColorLevels();
        if (getContext() == null) {
            Log.e("why", "why");
        }
        batteryColorAdapter = new BatteryColorAdapter(getContext(), list, new OnTouchColorLevel() {
            @Override
            public void onTouchItem(int position) {
                positionTouch = position;
                ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {
                    @Override
                    public void onColorSet(String color) {
                        ColorLevel prev = list.get(positionTouch);
                        prev.setColor(color);
                        list.set(positionTouch, prev);
                        batteryColorAdapter.notifyItemChanged(positionTouch);
                        manager.setColorLevels(list);
                        manager.save();
                    }
                });
                colorPicker.color = list.get(position).getColor();
                colorPicker.show(getFragmentManager(), "color picker");
            }
        });
        recyclerView.setAdapter(batteryColorAdapter);

        if (manager.read()) {
            linearAnimation.setChecked(manager.isLinear());
            definedAnimation.setChecked(manager.isDefined());
            linearStart.setText(manager.getLinearStart());
            linearEnd.setText(manager.getLinearEnd());
            linearStartBorder.setBackgroundColor(Color.parseColor(manager.getLinearStart()));
            linearEndBorder.setBackgroundColor(Color.parseColor(manager.getLinearEnd()));
        }

        return main;
    }

}
