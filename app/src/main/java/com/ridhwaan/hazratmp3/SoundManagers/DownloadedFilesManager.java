package com.ridhwaan.hazratmp3.SoundManagers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.provider.MediaStore.Audio;

import com.ridhwaan.hazratmp3.model.SoundFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ridhwaan on 3/15/2017.
 */

public class DownloadedFilesManager {


    private ArrayList<SoundFile> mSoundList = new ArrayList<>();



    public DownloadedFilesManager(Context context){


        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;


        Cursor cursor = contentResolver.query(uri,null,null,null,null);

        if(cursor == null){
            Log.d("TAG", " cursor is null");
            return;
        } else if(!cursor.moveToNext()){
                Log.d("TAG", "CURSOR NOT MOVE NEXT");
        } else {
            int idColumn = cursor.getColumnIndex(Audio.Media._ID);
            int titleColumn = cursor.getColumnIndex(Audio.Media.TITLE);

      //      Log.d("TAG", "   "  + titleColumn + "    " + idColumn);


            do{

                long thisId = cursor.getLong(idColumn);
                String thisName = cursor.getString(titleColumn);

                SoundFile soundFile = new SoundFile(thisName,thisId);
                mSoundList.add(soundFile);

         //       Log.d("TAG" , "   "  +  thisId + "    " + thisName);

            } while(cursor.moveToNext());




        }


        }

    public List<SoundFile> getmSoundList() {
        return mSoundList;
    }
}



