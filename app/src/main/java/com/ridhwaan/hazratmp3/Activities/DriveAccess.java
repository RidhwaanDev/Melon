package com.ridhwaan.hazratmp3.Activities;

import android.support.v4.app.Fragment;

import com.ridhwaan.hazratmp3.Fragment.DriveFragment;
import com.ridhwaan.hazratmp3.SingleFragmentActivity;

public class DriveAccess extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return DriveFragment.newInstance();
    }
}
