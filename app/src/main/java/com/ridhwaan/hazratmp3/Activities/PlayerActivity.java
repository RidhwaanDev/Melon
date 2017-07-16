package com.ridhwaan.hazratmp3.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ridhwaan.hazratmp3.Fragment.PlayerFragment;
import com.ridhwaan.hazratmp3.R;
import com.ridhwaan.hazratmp3.SingleFragmentActivity;

public class PlayerActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }



}
