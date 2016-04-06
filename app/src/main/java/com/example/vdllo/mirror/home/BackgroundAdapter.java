package com.example.vdllo.mirror.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class BackgroundAdapter extends FragmentPagerAdapter{

    private ArrayList<Fragment> datas;

    public BackgroundAdapter(FragmentManager fm, ArrayList<Fragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

}
