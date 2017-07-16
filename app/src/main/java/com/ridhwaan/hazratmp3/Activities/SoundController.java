package com.ridhwaan.hazratmp3.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ridhwaan.hazratmp3.Fragment.SoundControllerFragment;
import com.ridhwaan.hazratmp3.SingleFragmentActivity;
import com.ridhwaan.hazratmp3.model.SoundFile;

/**
 * Created by Ridhwaan on 3/19/2017.
 */

public class SoundController extends SingleFragmentActivity {

    public static final String ARG_SOUND_OBJ = SoundController.class.getCanonicalName();

    @Override
    public Fragment createFragment() {
        return  SoundControllerFragment.newInstance();
    }

    public static Intent newInstance (Context packageContext , SoundFile soundfile){

        Intent i = new Intent(packageContext,SoundController.class);
        i.putExtra(ARG_SOUND_OBJ,soundfile);
        return i;

    }

}
