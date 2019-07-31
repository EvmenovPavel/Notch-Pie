package com.lorenzomoscati.np.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import com.lorenzomoscati.np.Adapter.SectionsPageAdapter;
import com.lorenzomoscati.np.R;
import com.lorenzomoscati.np.Service.OverlayAccessibilityService;

import java.util.List;
import java.util.Objects;

public class TabbedActivity extends AppCompatActivity {
	
	private ViewPager viewPager;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tabbed);
		
		mContext = this;
		
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
			
			// Sets the title and length (infinite)
			Snackbar bar = Snackbar.make(viewPager, getString(R.string.battery_optimization), Snackbar.LENGTH_INDEFINITE);
			
			// Sets the button to disable it
			bar.setAction(getString(R.string.disable), new View.OnClickListener() {
				
				@SuppressLint("BatteryLife")
				@Override
				public void onClick(View v) {
					
					// Creates the intent to the settings page
					Intent intent = new Intent();
					intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
					intent.setData(Uri.parse("package:" + getPackageName()));
					startActivity(intent);
					
				}
				
			});
			
			// Shows the bar
			bar.show();
			
		}
		
		// If the app is running on MIUI a snackBar is shown
		else if (checkMIUI(getApplicationContext())) {
			
			SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", 0);
			
			final SharedPreferences.Editor editor = preferences.edit();
			
			if (!preferences.getBoolean("already_appeared_miui", false)) {
				
				// Sets the title and length (infinite)
				Snackbar bar = Snackbar.make(viewPager, getString(R.string.miui_note), Snackbar.LENGTH_INDEFINITE);
				
				bar.setAction(getString(R.string.more_info), new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						new AlertDialog.Builder(mContext)
								.setTitle(getString(R.string.overlay_disappearing))
								.setMessage(getString(R.string.overlay_disappearing_desc))
								.setPositiveButton(getString(R.string.understood), new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										
										editor.putBoolean("already_appeared_miui", true);
										editor.apply();
										
									}
							
								})
								.show();
						
					}
					
				});
				
				// Shows the bar
				bar.show();
				
			}
		
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
	
	private boolean isIntentResolved(Context ctx, Intent intent ){
		
		return (intent!=null && ctx.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null);
		
	}
	
	private boolean checkMIUI(Context ctx) {
		
		return isIntentResolved(ctx, new Intent("miui.intent.action.OP_AUTO_START").addCategory(Intent.CATEGORY_DEFAULT))
				|| isIntentResolved(ctx, new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")))
				|| isIntentResolved(ctx, new Intent("miui.intent.action.POWER_HIDE_MODE_APP_LIST").addCategory(Intent.CATEGORY_DEFAULT))
				|| isIntentResolved(ctx, new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.powercenter.PowerSettings")));
		
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
