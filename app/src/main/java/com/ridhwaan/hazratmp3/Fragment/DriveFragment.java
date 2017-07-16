package com.ridhwaan.hazratmp3.Fragment;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.ridhwaan.hazratmp3.DriveManager.DriveAccessTask;
import com.ridhwaan.hazratmp3.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Ridhwaan on 6/15/17.
 */

public class DriveFragment extends Fragment {


    private static final String[] SCOPES = {DriveScopes.DRIVE_METADATA_READONLY};

    private GoogleAccountCredential mGoogleAccountCredential;

    private static final String KEY_ACCOUNT_NAME = "accountName";

    private TextView mDriveResultText;

    static final int REQUEST_AUTHORIZATION = 1000;
    static final int REQUEST_ACCOUNT_PICKER_CODE = 1001;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1002;

    private Button mDriveInitBtn,mGetDataBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isDeviceOnline()) {
            Log.d("TAG", " DEVICE IS ONLINE ");
        }
        mGoogleAccountCredential = GoogleAccountCredential.usingOAuth2(getActivity(), Arrays.asList(SCOPES)).setBackOff(new ExponentialBackOff());

    }
    //Test method to access drive API

    private void getResultsFromApi() {
        if (mGoogleAccountCredential.getSelectedAccountName() == null) {
            chooseAccount();
        }  else {
            new MakeRequestTask(mGoogleAccountCredential).execute();
        }
    }


    private boolean isDeviceOnline() {

        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connManager.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnected());
    }

    private void chooseAccount() {

        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.GET_ACCOUNTS)) {

            String accountname = getActivity().getPreferences(Context.MODE_PRIVATE).getString(KEY_ACCOUNT_NAME,null);

            if(accountname != null){
                mGoogleAccountCredential.setSelectedAccountName(accountname);
               //getResultsFromApi();
            }else {
                startActivityForResult(mGoogleAccountCredential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER_CODE);

                Log.d("TAG", " ERRROR GET ACCOUNT PERMISSION DOES NOT EXIST");
            }

        }else{

            Log.d("TAG", " REQUESTING PERMISSIONS");

            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("TAG", " RESULT ACCOUNT PICKER CODE");

        if (requestCode == REQUEST_ACCOUNT_PICKER_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {

                String accountname = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                if (accountname != null) {

                    SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editPreferences = prefs.edit();
                    editPreferences.putString(KEY_ACCOUNT_NAME, accountname);
                    editPreferences.apply();
                    mGoogleAccountCredential.setSelectedAccountName(accountname);
                   // getResultsFromApi();


                }

            }
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    public static Fragment newInstance() {

        return new DriveFragment();
    }

    private boolean isAccountNull(){

        return mGoogleAccountCredential.getSelectedAccountName() == null;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_drive_access,container,false);

            if(!isAccountNull()){
                // do data
            }

            mDriveInitBtn = (Button) v.findViewById(R.id.add_account_btn);

            mDriveInitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isAccountNull()) {
                        chooseAccount();
                        mDriveInitBtn.setText(mGoogleAccountCredential.getSelectedAccountName());
                        DriveAccessTask task = new DriveAccessTask(mGoogleAccountCredential);
                        task.execute();
                    }

                }
            });



        return v;
    }



    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.drive.Drive mService = null;
        private Exception mLastError = null;

        MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.drive.Drive.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Drive API Android Quickstart")
                    .build();
            Log.d("TAG", " ENTERED ASYNC TASK");
        }

        /**
         * Background task to call Drive API.
         *
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            Log.d("TAG", " DO IN BACKGROUND CALLED IN ASYNC TASK");

            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                e.printStackTrace();
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of up to 10 file names and IDs.
         *
         * @return List of Strings describing files, or an empty list if no files
         * found.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get a list of up to 10 files.

            List<String> fileInfo = new ArrayList<String>();
            FileList result = mService.files().list()
                    .setPageSize(5)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getFileExtension() == "mp3") {
                        fileInfo.add(file.getName());
                    } else{
                        fileInfo.add(file.getName() + "  " + " not mp3");
                    }
                }
            }
            else{

                Log.d("TAG", "  FILE NULL");

            }
            return fileInfo;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            Log.d("TAG", " On post execute called");

            StringBuilder sbuilder = new StringBuilder();

            for(String string: strings){
                sbuilder.append(strings);
            }

        }


        @Override
        protected void onCancelled() {
           if(mLastError instanceof UserRecoverableAuthIOException){
               if(mLastError != null ){

                   startActivityForResult(((UserRecoverableAuthIOException) mLastError).getIntent(), DriveFragment.REQUEST_AUTHORIZATION);

               }
           }
        }
    }
}

