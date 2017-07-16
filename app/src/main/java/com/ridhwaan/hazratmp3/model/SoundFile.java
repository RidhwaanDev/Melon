package com.ridhwaan.hazratmp3.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Ridhwaan on 3/15/2017.
 */

public class SoundFile implements Serializable {




    private String mName;
    private UUID mUUID;
    private long id;

    public SoundFile(String mName, long id) {
        this.mName = mName;
        this.id = id;
        this.mUUID = UUID.randomUUID();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public long getID() {
        return id;
    }



    public UUID getmUUID() {
        return mUUID;
    }

    @Override
    public String toString() {
        return "SoundFile{" +
                "id=" + id +
                ", mName='" + mName + '\'' +
                ", mUUID=" + mUUID +
                '}';
    }
}

