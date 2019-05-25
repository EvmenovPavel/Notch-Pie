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
        final TextView txXPosition = main.findViewById(R.id.txXPosition);
        final TextView txYPosition = main.findViewById(R.id.txYPosition);

        // Seek bars
        int[] heightLimit = {50, 500};
        int[] widthLimit = {1, 500};
        int[] notchSizeLimit = {1, 500};
        int[] notchRadiusTopLimit = {0, 100};
        int[] notchRadiusBottomLimit = {0, 100};
        int[] xPositionLimit = {-100, 100};
        int[] yPositionLimit = {-100, 100};

        final SeekBar height, width, notchSize, topRadius, bottomRadius, xPosition, yPosition;
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

        xPosition = main.findViewById(R.id.sbXPosition);
        xPosition.setMin(xPositionLimit[0]);
        xPosition.setMax(xPositionLimit[1]);
        xPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txXPosition.setText(String.valueOf(progress));
                manager.setxPosition(progress);
                manager.save();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        yPosition = main.findViewById(R.id.sbYPosition);
        yPosition.setMin(yPositionLimit[0]);
        yPosition.setMax(yPositionLimit[1]);
        yPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txYPosition.setText(String.valueOf(progress));
                manager.setyPosition(progress);
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

        FloatingActionButton xPositionIncrease = main.findViewById(R.id.fabXPositionIncrease);
        xPositionIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPosition.setProgress(xPosition.getProgress() + 1);
            }
        });

        FloatingActionButton xPositionDecrease = main.findViewById(R.id.fabXPositionDecrease);
        xPositionDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xPosition.setProgress(xPosition.getProgress() - 1);
            }
        });

        FloatingActionButton yPositionIncrease = main.findViewById(R.id.fabYPositionIncrease);
        yPositionIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPosition.setProgress(yPosition.getProgress() + 1);
            }
        });

        FloatingActionButton yPositionDecrease = main.findViewById(R.id.fabYPositionDecrease);
        yPositionDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yPosition.setProgress(yPosition.getProgress() - 1);
            }
        });

        // read the file
        if (manager.read()) {
            height.setProgress(manager.getHeight());
            width.setProgress(manager.getWidth());
            topRadius.setProgress(manager.getTopRadius());
            bottomRadius.setProgress(manager.getBottomRadius());
            notchSize.setProgress(manager.getNotchSize());
            xPosition.setProgress(manager.getxPosition());
            yPosition.setProgress(manager.getyPosition());
        }

        return main;
    }


}
