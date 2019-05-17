package com.oddlyspaced.np.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.oddlyspaced.np.Fragments.Adapter.SectionsPageAdapter;
import com.oddlyspaced.np.R;

import java.util.ArrayList;

public class TabbedActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private final String[] titles = {"Notch Settings", "Config Settings", "Manage Configs", "About"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        init();
    }

    private void init() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SectionsPageAdapter(getSupportFragmentManager(), titles));
        tabLayout.setupWithViewPager(viewPager);
    }

}
