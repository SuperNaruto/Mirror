package com.example.vdllo.mirror.themeshare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/8.
 */
public class ThemeShareActivityAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> datas;
    public ThemeShareActivityAdapter(FragmentManager fm,ArrayList<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }
}
