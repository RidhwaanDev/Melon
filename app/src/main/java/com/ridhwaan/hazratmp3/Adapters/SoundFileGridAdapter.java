package com.ridhwaan.hazratmp3.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ridhwaan.hazratmp3.Activities.SoundController;
import com.ridhwaan.hazratmp3.Fragment.PlayerFragment;
import com.ridhwaan.hazratmp3.R;
import com.ridhwaan.hazratmp3.SoundManagers.PlayerManager;
import com.ridhwaan.hazratmp3.model.SoundFile;

import java.util.ArrayList;

/**
 * Created by Ridhwaan on 3/19/2017.
 */

public class SoundFileGridAdapter extends RecyclerView.Adapter<SoundHolder> {

    private Context mContext;
    private ArrayList<SoundFile> dataSet;

    public static OnSoundFileClickListener mClickListener;


    public interface OnSoundFileClickListener{
        void onSoundFileClicked(SoundFile soundFile);
    }

    public void setOnSoundFileClickListener(OnSoundFileClickListener listener){

        mClickListener = listener;
    }


    public SoundFileGridAdapter(Context context, ArrayList<SoundFile> dataset){

        this.mContext = context;

        this.dataSet = dataset;
    }

    @Override
    public SoundHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.list_item_sound,parent,false);
        return new SoundHolder(v,mContext);



    }

    @Override
    public void onBindViewHolder(SoundHolder holder, int position) {
        SoundFile localSoundFile = dataSet.get(position);
        holder.bindHolder(localSoundFile,position);

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }



}

   class SoundHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private SoundFile soundFile;
    private int position;
    private TextView mTextView;
    private Context c = null;

    public SoundHolder(View itemView, Context c ) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.title_sound_file);
        itemView.setOnClickListener(this);
        this.c = c;

    }

    @Override
    public void onClick(View view) {
        //// TODO: 3/19/2017 update player manager
        Log.d("TAG", "ITEM CLICK");
        SoundFileGridAdapter.mClickListener.onSoundFileClicked(soundFile);

    }


    public void bindHolder(SoundFile soundFile, int position){
        mTextView.setText(soundFile.getmName());
        this.soundFile = soundFile;
        this.position = position;
    }


}