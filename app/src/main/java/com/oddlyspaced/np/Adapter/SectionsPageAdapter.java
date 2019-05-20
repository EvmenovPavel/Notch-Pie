package com.oddlyspaced.np.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.oddlyspaced.np.Fragments.BatterySettingsFragment;
import com.oddlyspaced.np.Fragments.BlankFragment;
import com.oddlyspaced.np.Fragments.ConfigSettingsFragment;
import com.oddlyspaced.np.Fragments.NotchSettingsFragment;

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private String[] titles;

    public SectionsPageAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NotchSettingsFragment();
            case 1:
                return new ConfigSettingsFragment();
            case 2:
                return new BatterySettingsFragment();
            case 3:
                return new BlankFragment();
            case 4:
                return new BlankFragment();
            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
