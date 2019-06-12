package com.oddlyspaced.np.Fragments;

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

import com.oddlyspaced.np.Interface.ColorPickerListener;
import com.oddlyspaced.np.Modal.ColorLevel;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Utils.ColorPicker;
import com.oddlyspaced.np.Utils.SettingsManager;

import org.w3c.dom.Text;

public class ConfigSettingsFragment extends Fragment {

    SettingsManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_config_settings, container, false);
        init();
        return attach(main);
    }

    private void init() {
        manager = new SettingsManager();
    }

    private View attach(View parent) {
        Switch fullStatus = parent.findViewById(R.id.switchFullStatus);
        final Switch showBackground = parent.findViewById(R.id.switchShowBackground);
        Switch chargingAnimation = parent.findViewById(R.id.switchCharging);
        Switch fillOverlay = parent.findViewById(R.id.switchFillOverlay);
        Switch landscapeSupprt = parent.findViewById(R.id.switchLandscape);
        View showBackgroundColor = parent.findViewById(R.id.viewTouchShowBackground);
        final TextView backgroundColorText = parent.findViewById(R.id.txBackgroundColor);

        if (manager.read()) {
            fullStatus.setChecked(manager.isFullStatus());
            showBackground.setChecked(manager.isShowBackground());
            chargingAnimation.setChecked(manager.isChargingAnimation());
            fillOverlay.setChecked(manager.isFillOverlay());
            backgroundColorText.setText(new String("Current Color : " + manager.getBackgroundColor()));
        }

        fullStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setFullStatus(isChecked);
                manager.save();
            }
        });
        showBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setShowBackground(isChecked);
                manager.save();
            }
        });
        chargingAnimation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setChargingAnimation(isChecked);
                manager.save();
            }
        });
        fillOverlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setFillOverlay(isChecked);
                manager.save();
            }
        });
        landscapeSupprt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                manager.setLandscapeSupport(isChecked);
                manager.save();
            }
        });
        showBackgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPicker colorPicker = new ColorPicker(new ColorPickerListener() {
                    @Override
                    public void onColorSet(String color) {
                        manager.setBackgroundColor(color);
                        manager.save();
                        backgroundColorText.setText(new String("Current Color : " + color));
                    }
                });
                colorPicker.color = manager.getBackgroundColor();
                colorPicker.show(getFragmentManager(), "color picker");
            }
        });
        return parent;
    }


}
