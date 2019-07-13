package com.lorenzomoscati.np.Interface;

// Interface for handling battery level change
public interface OnBatteryLevelChanged {
    void onChanged(int battery);
    void onChargingConnected(int battery);
    void oncChargingDisconnected(int battery);
}
