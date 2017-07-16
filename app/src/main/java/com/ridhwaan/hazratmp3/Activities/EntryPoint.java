package com.ridhwaan.hazratmp3.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ridhwaan.hazratmp3.R;
import com.ridhwaan.hazratmp3.ViewPagerForTabs;

/**
 * Created by Ridhwaan on 6/20/17.
 */

public class EntryPoint extends AppCompatActivity {

    private ViewPagerForTabs mViewPagerForTabs;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPagerForTabs = new ViewPagerForTabs(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mViewPagerForTabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
