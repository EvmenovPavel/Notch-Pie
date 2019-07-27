package com.lorenzomoscati.np.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lorenzomoscati.np.Constants.ConstantHolder;
import com.lorenzomoscati.np.Adapter.SectionsPageAdapter;
import com.lorenzomoscati.np.R;
import com.lorenzomoscati.np.Service.OverlayAccessibilityService;
import com.lorenzomoscati.np.Utils.BatteryConfigManager;
import com.lorenzomoscati.np.Utils.NotchManager;
import com.lorenzomoscati.np.Utils.SettingsManager;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class TabbedActivity extends AppCompatActivity {
	
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tabbed);

		// Creates the configuration directories
		createDirectories();

		// Creates the config files, if not already present
		if (!checkConfigs()) {

			createConfigs();

		}

		init();

		makeServiceSnackbar();

	}

	private void init() {
		
		final String[] titles = {getString(R.string.title_notch), getString(R.string.title_config), getString(R.string.title_battery), getString(R.string.title_about)};
		
		TabLayout tabLayout = findViewById(R.id.tabs);
		viewPager = findViewById(R.id.view_pager);
		viewPager.setAdapter(new SectionsPageAdapter(getSupportFragmentManager(), titles));
		tabLayout.setupWithViewPager(viewPager);

	}

	private void createDirectories() {

		// Creates the internal folder, if it is not already present
		File folderInternal = new File(new ConstantHolder().getConfigFolderPathInternal(getApplicationContext()));
		boolean success;
		if (!folderInternal.exists()) {
			
			success = folderInternal.mkdirs();
			
			if (!success) {
				
				Toast.makeText(getApplicationContext(), R.string.folderCreationUnsucc, Toast.LENGTH_SHORT).show();
				
			}

		}

		// Creates the external folder, if it is not already present
		File folderExternal = new File(new ConstantHolder().getConfigFolderPathExternal(getApplicationContext()));
		if (!folderExternal.exists()) {
			
			success = folderExternal.mkdirs();
			
			if (!success) {
				
				Toast.makeText(getApplicationContext(), R.string.folderCreationUnsucc, Toast.LENGTH_SHORT).show();
				
			}

		}

	}

	// Creates the config files for each section, based on the results from the setting manager
	private void createConfigs() {

		new SettingsManager(getApplicationContext()).save(getApplicationContext());
		new BatteryConfigManager(getApplicationContext()).save(getApplicationContext());
		new NotchManager(getApplicationContext()).save(getApplicationContext());

	}

	// Checks if the config files are existing
	private boolean checkConfigs() {

		return new File(new ConstantHolder().getConfigFilePathInternal(getApplicationContext())).exists() && new File(new ConstantHolder().getSettingsFilePathInternal(getApplicationContext())).exists() && new File(new ConstantHolder().getBatteryFilePathInternal(getApplicationContext())).exists();

	}

	// Checks if the accessibility service is turned on
	private void makeServiceSnackbar() {

		// If the service is not on a snackBar is shown
		if (!checkServiceOn()) {

			// Sets the title and length (infinite)
			Snackbar bar = Snackbar.make(viewPager, getString(R.string.turnServiceOn), Snackbar.LENGTH_INDEFINITE);

			// Sets the button to enable it
			bar.setAction(getString(R.string.enable), new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					// Creates the intent to the settings page
					Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
					startActivity(intent);

					// Makes a toast to notify the user about what setting to turn on
					Toast.makeText(getApplicationContext(), getString(R.string.popup_accessibility_toast), Toast.LENGTH_LONG).show();

				}

			});

			// Shows the bar
			bar.show();

		}
		
		// If Doze is on for the app a snackBar is shown
		else if (checkDozeOn()) {
			
			Snackbar bar = Snackbar.make(viewPager, "Please turn off battery optimization", Snackbar.LENGTH_INDEFINITE);
			
			bar.setAction("Disable", new View.OnClickListener() {
				
				@SuppressLint("BatteryLife")
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent();
					intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
					intent.setData(Uri.parse("package:" + getPackageName()));
					startActivity(intent);
					
				}
				
			});
			
			bar.show();
			
		}

	}

	@Override
	protected void onResume() {

		super.onResume();

		// Checks again if the accessibility service is enabled
		makeServiceSnackbar();

	}

	// Method that returns true if the accessibility service is turned on
	private boolean checkServiceOn() {

		return isAccessibilityServiceEnabled(getApplicationContext());

	}
	
	private boolean checkDozeOn() {
		
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		return !Objects.requireNonNull(pm).isIgnoringBatteryOptimizations(getPackageName());
	
	}

	// Method that checks the status of the accessibility service
	private static boolean isAccessibilityServiceEnabled(Context context) {

		// Defines an array in which are contained the services turned on
		AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
		List<AccessibilityServiceInfo> enabledServices = Objects.requireNonNull(am).getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

		// Loops that cycles through the array to check if our service is turned on
		for (AccessibilityServiceInfo enabledService : enabledServices) {

			// Targets the service
			ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;

			// Checks if the service is our service
			if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(OverlayAccessibilityService.class.getName())) {

				return true;

			}

		}

		return false;

	}

}
