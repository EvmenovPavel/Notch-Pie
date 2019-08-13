package com.lorenzomoscati.np.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.lorenzomoscati.np.Interface.OnBatteryLevelChanged;

// Receiver for receiving battery updates
public class BatteryBroadcastReceiver extends BroadcastReceiver {

	// Interface
	private final OnBatteryLevelChanged onBatteryChangedInterface;

	// Constructor
	public BatteryBroadcastReceiver(OnBatteryLevelChanged onBatteryChangedInterface) {
		
		this.onBatteryChangedInterface = onBatteryChangedInterface;
		
	}

	// On receiving the broadcast intent
	@Override
	public void onReceive(Context context, Intent intent) {
		
		// Getting data from intent
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		
		onBatteryChangedInterface.onChanged(level);
		
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		
		boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||status == BatteryManager.BATTERY_STATUS_FULL;
		
		if (isCharging) {
			
			onBatteryChangedInterface.onChargingConnected();
			
		}
		
		else {
			
			onBatteryChangedInterface.oncChargingDisconnected(level);
			
		}
		
	}

}
