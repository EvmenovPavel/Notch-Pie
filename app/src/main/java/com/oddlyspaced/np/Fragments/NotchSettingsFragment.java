package com.oddlyspaced.np.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Utils.NotchManager;

public class NotchSettingsFragment extends Fragment {

    // notch manager object
    private NotchManager manager;
    private boolean isPortrait = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View main = inflater.inflate(R.layout.fragment_notch_settings, container, false);
        init();
        return attach(main);
    }

    private void init() {
        manager = new NotchManager();
    }

    // takes the parent view and attaches the listeners to it
    private View attach(View main) {
        // Text views
        final TextView txHeight = main.findViewById(R.id.txHeight);
        final TextView txWidth = main.findViewById(R.id.txWidth);
        final TextView txNotchSize = main.findViewById(R.id.txNotchSize);
        final TextView txTopRadius = main.findViewById(R.id.txTopRadius);
        final TextView txBottomRadius = main.findViewById(R.id.txBottomRadius);
        final TextView txXPositionP = main.findViewById(R.id.txXPositionPortrait);
        final TextView txYPositionP = main.findViewById(R.id.txYPositionPortrait);
        final TextView txXPositionL = main.findViewById(R.id.txXPositionLandscape);
        final TextView txYPositionL = main.findViewById(R.id.txYPositionLandscape);

        // Seek bars
        int[] heightLimit = {50, 500};
        int[] widthLimit = {1, 500};
        int[] notchSizeLimit = {1, 500};
        int[] notchRadiusTopLimit = {0, 100};
        int[] notchRadiusBottomLimit = {0, 100};
        int[] xPositionLimit = {-100, 100};
        int[] yPositionLimit = {-100, 100};

        final SeekBar height, width, notchSize, topRadius, bottomRadius, xPositionP, yPositionP, xPositionL, yPositionL;
        height = main.findViewById(R.id.sbHeight);
        height.setMin(heightLimit[0]);
        height.setMax(heightLimit[1]);
        height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txHeight.setText(String.valueOf(progress));
                manager.setHeight(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        width = main.findViewById(R.id.sbWidth);
        width.setMin(widthLimit[0]);
        width.setMax(widthLimit[1]);
        width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txWidth.setText(String.valueOf(progress));
                manager.setWidth(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        notchSize = main.findViewById(R.id.sbNotchSize);
        notchSize.setMin(notchSizeLimit[0]);
        notchSize.setMax(notchSizeLimit[1]);
        notchSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txNotchSize.setText(String.valueOf(progress));
                manager.setNotchSize(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        topRadius = main.findViewById(R.id.sbTopRadius);
        topRadius.setMin(notchRadiusTopLimit[0]);
        topRadius.setMax(notchRadiusTopLimit[1]);
        topRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txTopRadius.setText(String.valueOf(progress));
                manager.setTopRadius(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bottomRadius = main.findViewById(R.id.sbBottomRadius);
        bottomRadius.setMin(notchRadiusBottomLimit[0]);
        bottomRadius.setMax(notchRadiusBottomLimit[1]);
        bottomRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txBottomRadius.setText(String.valueOf(progress));
                manager.setBottomRadius(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        xPositionP = main.findViewById(R.id.sbXPositionPortrait);
        xPositionP.setMin(xPositionLimit[0]);
        xPositionP.setMax(xPositionLimit[1]);
        xPositionP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txXPositionP.setText(String.valueOf(progress));
                manager.setxPositionPortrait(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        yPositionP = main.findViewById(R.id.sbYPositionPortrait);
        yPositionP.setMin(yPositionLimit[0]);
        yPositionP.setMax(yPositionLimit[1]);
        yPositionP.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txYPositionP.setText(String.valueOf(progress));
                manager.setyPositionPortrait(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        xPositionL = main.findViewById(R.id.sbXPositionLandscape);
        xPositionL.setMin(xPositionLimit[0]);
        xPositionL.setMax(xPositionLimit[1]);
        xPositionL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txXPositionL.setText(String.valueOf(progress));
                manager.setxPositionLandscape(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        yPositionL = main.findViewById(R.id.sbYPositionLandscape);
        yPositionL.setMin(yPositionLimit[0]);
        yPositionL.setMax(yPositionLimit[1]);
        yPositionL.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txYPositionL.setText(String.valueOf(progress));
                manager.setyPositionLandscape(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Floating Action Buttons
        FloatingActionButton heightIncrease = main.findViewById(R.id.fabHeightIncrease);
        heightIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height.setProgress(height.getProgress() + 1);
            }
        });

        FloatingActionButton heightDecrease = main.findViewById(R.id.fabHeightDecrease);
        heightDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                height.setProgress(height.getProgress() - 1);
            }
        });

        FloatingActionButton widthIncrease = main.findViewById(R.id.fabWidthIncrease);
        widthIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                width.setProgress(width.getProgress() + 1);
            }
        });

        FloatingActionButton widthDecrease= main.findViewById(R.id.fabWidthDecrease);
        widthDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                width.setProgress(width.getProgress() - 1);
            }
        });

        FloatingActionButton notchSizeIncrease = main.findViewById(R.id.fabNotchSizeIncrease);
        notchSizeIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notchSize.setProgress(notchSize.getProgress() + 1);
            }
        });

        FloatingActionButton notchSizeDecrease = main.findViewById(R.id.fabNotchSizeDecrease);
        notchSizeDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notchSize.setProgress(notchSize.getProgress() - 1);
            }
        });

        FloatingActionButton topRadiusIncrease = main.findViewById(R.id.fabTopRadiusIncrease);
        topRadiusIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRadius.setProgress(topRadius.getProgress() + 1);
            }
        });

        FloatingActionButton topRadiusDecrease = main.findViewById(R.id.fabTopRadiusDecrease);
        topRadiusDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topRadius.setProgress(topRadius.getProgress() - 1);
            }
        });

        FloatingActionButton bottomRadiusIncrease = main.findViewById(R.id.fabBottomRadiusIncrease);
        bottomRadiusIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomRadius.setProgress(bottomRadius.getProgress() + 1);
            }
        });

        FloatingActionButton bottomRadiusDecrease = main.findViewById(R.id.fabBottomRadiusDecrease);
        bottomRadiusDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomRadius.setProgress(bottomRadius.getProgress() - 1);
            }
        });

        FloatingActionButton xPositionIncreaseP = main.findViewById(R.id.fabXPositionPortraitIncrease);
        xPositionIncreaseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPositionP.setProgress(xPositionP.getProgress() + 1);
            }
        });

        FloatingActionButton xPositionDecreaseP = main.findViewById(R.id.fabXPositionPortraitDecrease);
        xPositionDecreaseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPositionP.setProgress(xPositionP.getProgress() - 1);
            }
        });

        FloatingActionButton yPositionIncreaseP = main.findViewById(R.id.fabYPositionPortraitIncrease);
        yPositionIncreaseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPositionP.setProgress(yPositionP.getProgress() + 1);
            }
        });

        FloatingActionButton yPositionDecreaseP = main.findViewById(R.id.fabYPositionPortraitDecrease);
        yPositionDecreaseP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPositionP.setProgress(yPositionP.getProgress() - 1);
            }
        });

        FloatingActionButton xPositionIncreaseL = main.findViewById(R.id.fabXPositionLandscapeIncrease);
        xPositionIncreaseL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPositionL.setProgress(xPositionL.getProgress() + 1);
            }
        });

        FloatingActionButton xPositionDecreaseL = main.findViewById(R.id.fabXPositionLandscapeDecrease);
        xPositionDecreaseL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPositionL.setProgress(xPositionL.getProgress() - 1);
            }
        });

        FloatingActionButton yPositionIncreaseL = main.findViewById(R.id.fabYPositionLandscapeIncrease);
        yPositionIncreaseL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPositionL.setProgress(yPositionL.getProgress() + 1);
            }
        });

        FloatingActionButton yPositionDecreaseL = main.findViewById(R.id.fabYPositionLandscapeDecrease);
        yPositionDecreaseL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPositionL.setProgress(yPositionL.getProgress() - 1);
            }
        });

        // read the file
        if (manager.read()) {
            height.setProgress(manager.getHeight());
            width.setProgress(manager.getWidth());
            topRadius.setProgress(manager.getTopRadius());
            bottomRadius.setProgress(manager.getBottomRadius());
            notchSize.setProgress(manager.getNotchSize());
            xPositionP.setProgress(manager.getxPositionPortrait());
            yPositionP.setProgress(manager.getyPositionPortrait());
            xPositionL.setProgress(manager.getxPositionLandscape());
            yPositionL.setProgress(manager.getyPositionLandscape());
        }

        return main;
    }


}
