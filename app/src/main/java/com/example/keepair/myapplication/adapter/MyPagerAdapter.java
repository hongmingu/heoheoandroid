package com.example.keepair.myapplication.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Keepair on 2016-09-20.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    public Fragment[] arrFragments;
    public MyPagerAdapter(FragmentManager fm, Fragment[] arrFragmnets) {
        super(fm);
        this.arrFragments = arrFragmnets;
    }

    @Override
    public Fragment getItem(int position) {
        return arrFragments[position];
    }

    @Override
    public int getCount() {
        return arrFragments.length;
    }
}
