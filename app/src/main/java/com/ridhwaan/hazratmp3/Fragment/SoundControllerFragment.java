package com.ridhwaan.hazratmp3.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ridhwaan.hazratmp3.Activities.DriveAccess;
import com.ridhwaan.hazratmp3.Activities.SoundController;
import com.ridhwaan.hazratmp3.R;
import com.ridhwaan.hazratmp3.SoundManagers.PlayerManager;
import com.ridhwaan.hazratmp3.model.SoundFile;

/**
 * Created by Ridhwaan on 3/19/2017.
 */

public class SoundControllerFragment extends Fragment {



    private Button mPlayButton;
    private Button mPauseButton;

    public static SoundControllerFragment newInstance(){
        return new SoundControllerFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.fragment_soundcontroller_layout,container,false);

        mPlayButton = (Button) v.findViewById(R.id.play_button);
        mPauseButton = (Button)v.findViewById(R.id.pause_button);




        Bundle args = getActivity().getIntent().getExtras();
        final SoundFile soundFile =(SoundFile) args.get(SoundController.ARG_SOUND_OBJ);


        final PlayerManager playerManager = PlayerManager.getInstance(getActivity());

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerManager.play(soundFile);
            }
        });

       mPauseButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               playerManager.pause();
               Intent i = new Intent(getActivity(),DriveAccess.class);
               startActivity(i);
           }
       });


        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
