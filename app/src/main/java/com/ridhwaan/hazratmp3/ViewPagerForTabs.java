package com.ridhwaan.hazratmp3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ridhwaan.hazratmp3.Fragment.DriveFragment;
import com.ridhwaan.hazratmp3.Fragment.PlayerFragment;

/**
 * Created by Ridhwaan on 6/20/17.
 */

public class ViewPagerForTabs extends FragmentPagerAdapter {

    static final String drive = "Drive ";
    static final String local = "Downloaded ";
    private static final String[] tabs = {local,drive};
    static final int tabsize = 2;

    public ViewPagerForTabs(FragmentManager fm){
                super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                PlayerFragment playerFragment = new PlayerFragment();
                return playerFragment;
            case 1:
                DriveFragment driveFragment = new DriveFragment();
                return driveFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return tabsize;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
