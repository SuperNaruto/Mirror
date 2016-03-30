package com.example.vdllo.mirror.home;

import android.support.v4.app.Fragment;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.toolclass.CustomViewPager;
import java.util.ArrayList;

public class MainActivity extends BaseAcitvity {

    private CustomViewPager viewPager;
    private MainAdapter adapter;
    private ArrayList<Fragment> datas;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.main_viewpager);
    }

    @Override
    protected void initData() {
        datas = new ArrayList<>();
        datas.add(new AllTypeFragment());
        datas.add(new ShoppingCartFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);
    }
}
