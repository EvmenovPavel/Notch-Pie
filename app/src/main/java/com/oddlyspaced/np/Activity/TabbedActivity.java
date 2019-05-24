package com.oddlyspaced.np.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.oddlyspaced.np.Constants.ConstantHolder;
import com.oddlyspaced.np.Adapter.SectionsPageAdapter;
import com.oddlyspaced.np.R;
import com.oddlyspaced.np.Utils.BatteryConfigManager;
import com.oddlyspaced.np.Utils.NotchManager;
import com.oddlyspaced.np.Utils.SettingsManager;

import java.io.File;

public class TabbedActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final String[] titles = {"Notch Settings", "Config Settings", "Battery Level Settings", "Manage Configs", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        createDirectories();
        if (!checkConfigs())
            createConfigs();
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionsPageAdapter(getSupportFragmentManager(), titles));
        tabLayout.setupWithViewPager(viewPager);
    }

    private void createDirectories() {
        File folder = new File(new ConstantHolder().getConfigFolderPathInternal());
        if (!folder.exists())
        folder.mkdirs();
    }

    private void createConfigs() {
        new SettingsManager().save();
        new BatteryConfigManager().save();
        new NotchManager().save();
    }

    private boolean checkConfigs() {
        return new File(new ConstantHolder().getConfigFilePathInternal()).exists() && new File(new ConstantHolder().getSettingsFilePathInternal()).exists() && new File(new ConstantHolder().getBatteryFilePathInternal()).exists();
    }

}
