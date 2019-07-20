package com.lorenzomoscati.np.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lorenzomoscati.np.Fragments.AboutFragment;
import com.lorenzomoscati.np.Fragments.BatterySettingsFragment;
import com.lorenzomoscati.np.Fragments.ConfigSettingsFragment;
import com.lorenzomoscati.np.Fragments.NotchSettingsFragment;

public class SectionsPageAdapter extends FragmentPagerAdapter {

	private final String[] titles;

	public SectionsPageAdapter(FragmentManager fm, String[] titles) {

		super(fm);

		// Sets the title passed
		this.titles = titles;

	}

	// Depending on the position given, returns the right fragment
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
				return new AboutFragment();

			default:
				return null;

		}

	}



	// Returns the number of fragments
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
