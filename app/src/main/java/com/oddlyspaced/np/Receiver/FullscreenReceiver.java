package com.oddlyspaced.np.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.oddlyspaced.np.Interface.OnFullscreen;

// Broadcast Receiver for receiving status bar changes
public class FullscreenReceiver extends BroadcastReceiver {

    // Interface
    private OnFullscreen onFullscreen;

    // Constructor
    public FullscreenReceiver(OnFullscreen onFullscreen) {
        this.onFullscreen = onFullscreen;
    }

    // here onReceive is called when status bar visibility is modified
    @Override
    public void onReceive(Context context, Intent intent) {
    }

}


