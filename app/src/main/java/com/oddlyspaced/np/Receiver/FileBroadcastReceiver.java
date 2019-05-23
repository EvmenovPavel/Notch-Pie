package com.oddlyspaced.np.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.oddlyspaced.np.Interface.OnBatteryConfigEdited;
import com.oddlyspaced.np.Interface.OnNotchConfigEdited;
import com.oddlyspaced.np.Interface.OnSettingsConfigEdited;

// Broadcast Receiver for receiving FileUpdates
public class FileBroadcastReceiver extends BroadcastReceiver {

    // Interface
    private OnBatteryConfigEdited onBatteryConfigEdited;
    private OnNotchConfigEdited onNotchConfigEditedInterface;
    private OnSettingsConfigEdited onSettingsConfigEditedInterface;

    public FileBroadcastReceiver(OnBatteryConfigEdited onBatteryConfigEdited) {
        this.onBatteryConfigEdited = onBatteryConfigEdited;
    }

    public FileBroadcastReceiver(OnNotchConfigEdited onNotchConfigEditedInterface) {
        this.onNotchConfigEditedInterface = onNotchConfigEditedInterface;
    }

    public FileBroadcastReceiver(OnSettingsConfigEdited onSettingsConfigEditedInterface) {
        this.onSettingsConfigEditedInterface = onSettingsConfigEditedInterface;
    }

    // here onReceive is called when file is modified
    @Override
    public void onReceive(Context context, Intent intent) {
        if (onBatteryConfigEdited != null) {
            onBatteryConfigEdited.onEdited();
        }
        else if (onNotchConfigEditedInterface != null) {
            onNotchConfigEditedInterface.onEdited();
        }
        else if (onSettingsConfigEditedInterface != null) {
            onSettingsConfigEditedInterface.onEdited();
        }

    }
}


