package com.ridhwaan.hazratmp3.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ridhwaan.hazratmp3.Activities.SoundController;
import com.ridhwaan.hazratmp3.Adapters.SoundFileGridAdapter;
import com.ridhwaan.hazratmp3.R;
import com.ridhwaan.hazratmp3.SoundManagers.DownloadedFilesManager;
import com.ridhwaan.hazratmp3.model.SoundFile;

import java.util.ArrayList;
import java.util.jar.Manifest;

/**
 * Created by Ridhwaan on 3/4/2017.
 */

public class PlayerFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {





    private DownloadedFilesManager mDownloadManager;
    private RecyclerView mRecyclerView;
    private SoundFileGridAdapter mSoundGridAdapter;

    private final String REQUEST_EXT_STO_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private final int REQUEST_EXT_STO_CODE = 1;

    private ArrayList<SoundFile> dataSet;

    public static PlayerFragment newInstance() {

        return new PlayerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check for permission

        if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){


            ActivityCompat.requestPermissions(getActivity(),new String[]{REQUEST_EXT_STO_PERMISSION},REQUEST_EXT_STO_CODE);
        }

        mDownloadManager = new DownloadedFilesManager(getActivity());

        dataSet = (ArrayList<SoundFile>) mDownloadManager.getmSoundList();


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player, container, false);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_player);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));


        mSoundGridAdapter = new SoundFileGridAdapter(getActivity(),dataSet);
        mSoundGridAdapter.setOnSoundFileClickListener(new SoundFileGridAdapter.OnSoundFileClickListener() {
            @Override
            public void onSoundFileClicked(SoundFile soundFile) {
                Intent i = SoundController.newInstance(getActivity(),soundFile);
                startActivity(i);
            }
        });
        mRecyclerView.setAdapter(mSoundGridAdapter);


        return v;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                switch(requestCode){

                    case REQUEST_EXT_STO_CODE: {
                        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                            return;
                        }
                    }
                }
    }
}
