package com.lorenzomoscati.np.Interface;

// Interface for handling battery level change
public interface OnBatteryLevelChanged {
	
	// Battery percentage changes
	void onChanged(int battery);
	
	// Battery is charging
	void onChargingConnected();
	
	// Battery is no longer charging
	void oncChargingDisconnected(int battery);
	
}
