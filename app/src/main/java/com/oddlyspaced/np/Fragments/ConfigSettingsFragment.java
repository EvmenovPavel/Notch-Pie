package com.oddlyspaced.np.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Utils.SettingsManager;

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
        Switch showBackground = parent.findViewById(R.id.switchShowBackground);
        Switch chargingAnimation = parent.findViewById(R.id.switchCharging1);
        Switch fillOverlay = parent.findViewById(R.id.switchFillOverlay);

        if (manager.read()) {
            fullStatus.setChecked(manager.isFullStatus());
            showBackground.setChecked(manager.isShowBackground());
            chargingAnimation.setChecked(manager.isChargingAnimation1());
            fillOverlay.setChecked(manager.isFillOverlay());
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
                manager.setChargingAnimation1(isChecked);
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
        return parent;
    }


}
