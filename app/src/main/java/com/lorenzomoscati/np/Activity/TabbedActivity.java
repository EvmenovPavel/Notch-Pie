package com.lorenzomoscati.np.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
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

public class TabbedActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final String[] titles = {"Notch Settings", "Config Settings", "Battery Level Settings", "About"};
    BillingClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        createDirectories();
        if (!checkConfigs())
            createConfigs();
        init();
        makeServiceSnackbar();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionsPageAdapter(getSupportFragmentManager(), titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void createDirectories() {
        File folderInternal = new File(new ConstantHolder().getConfigFolderPathInternal());
        if (!folderInternal.exists())
            folderInternal.mkdirs();
        File folderExternal = new File(new ConstantHolder().getConfigFolderPathExternal());
        if (!folderExternal.exists())
            folderExternal.mkdirs();

    }

    private void createConfigs() {
        new SettingsManager().save();
        new BatteryConfigManager().save();
        new NotchManager().save();
    }

    private boolean checkConfigs() {
        return new File(new ConstantHolder().getConfigFilePathInternal()).exists() && new File(new ConstantHolder().getSettingsFilePathInternal()).exists() && new File(new ConstantHolder().getBatteryFilePathInternal()).exists();
    }

    private void makeServiceSnackbar() {
        if (!checkServiceOn()) {
            Snackbar bar = Snackbar.make(viewPager, "Please turn overlay service on!", Snackbar.LENGTH_INDEFINITE);
            bar.setAction("Enable", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), getString(R.string.popup_accessibility_toast), Toast.LENGTH_LONG).show();
                }
            });
            bar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeServiceSnackbar();
    }

    private boolean checkServiceOn() {
        return isAccessibilityServiceEnabled(getApplicationContext(), OverlayAccessibilityService.class);
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName()))
                return true;
        }

        return false;
    }
}
