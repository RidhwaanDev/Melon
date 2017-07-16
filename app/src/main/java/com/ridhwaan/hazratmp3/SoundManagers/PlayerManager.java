package com.ridhwaan.hazratmp3.SoundManagers;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.*;
import android.media.SoundPool;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.ridhwaan.hazratmp3.model.SoundFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ridhwaan on 3/15/2017.
 */

public class PlayerManager {

    private static PlayerManager ourManager;
    private MediaPlayer mMediaPlayer;
    Context mContext;

    public static PlayerManager getInstance(Context context) {

        if(ourManager == null){
            ourManager = new PlayerManager(context);
        }
        return ourManager;

    }

    private PlayerManager (Context c){
           this.mContext = c;
        mMediaPlayer  = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }


    public void play(SoundFile file){
            Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,file.getID());

                try{
                    mMediaPlayer.setDataSource(mContext,contentUri);
                    mMediaPlayer.prepare();
                } catch (IOException e){
                    e.printStackTrace();
                }

            mMediaPlayer.start();

          }

    public void pause(){

        mMediaPlayer.pause();


    }






}