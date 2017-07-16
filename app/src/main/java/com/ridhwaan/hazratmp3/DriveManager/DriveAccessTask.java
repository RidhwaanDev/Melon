package com.ridhwaan.hazratmp3.DriveManager;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ridhwaan on 6/15/17.
 */

public class DriveAccessTask extends AsyncTask<Void,Void, List<String>> {

    private Drive mDriveService = null;
    private Exception lastException = null;





    public DriveAccessTask(GoogleAccountCredential mGoogleCred){

        Log.d("TAG", "ACCESS TASK TEST");

        HttpTransport HttpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsFactory = JacksonFactory.getDefaultInstance();
        mDriveService = new Drive.Builder(HttpTransport,jsFactory,mGoogleCred)
        .setApplicationName("Music Player Plus").build();

    }


    @Override
    protected List<String> doInBackground(Void... params) {


        try{
            return getDataFromAPI();

        } catch (Exception e ){
            e.printStackTrace();
            lastException = e;
            cancel(true);
            return null;
        }
    }


    private List<String> getDataFromAPI() throws IOException{

        List<String> results = new ArrayList<>();
        FileList result = mDriveService.files().list().setPageSize(200).setFields("nextPageToken, files(id,name)").execute();

        List<File> files = result.getFiles();
        if(files != null){
            for(File file: files){
                results.add(file.getName() +"  " + file.getFileExtension() + "  " + file.getCreatedTime());

                if(getFileExt(file.getName()) == "mp3"){
                    Log.d("TAG", " MP3 file founde");
                }
            }
        }

        return results;

    }

    private String getFileExt(String filename){
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        for(String string: strings){
            Log.d("TAG" , string);
        }
    }

    @Override
    protected void onPreExecute() {

    }
}
