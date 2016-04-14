package com.example.vdllo.mirror.home;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.MenuListBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.themeshare.ThemeShareFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/5.
 */
public class BackGroundFragment extends BaseFragment {
    private CustomViewPager viewPager;
    private BackgroundAdapter adapter;
    private ArrayList<Fragment> datas;
    private Handler handler;
    private MenuListBean menuListBean;
    private TextView textView;
    private int num;

    @Override
    public int getLayout() {
        return R.layout.fragment_background;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.main_viewpager);
        textView = bindView(R.id.background_login_tv);
    }

    @Override
    protected void initData() {
        datas = new ArrayList<>();
        datas.add(new AllTypeFragment(0));
        datas.add(new AllTypeFragment(1));
        datas.add(new AllTypeFragment(2));
        datas.add(new ThemeShareFragment(3));
        datas.add(new ShoppingCartFragment());
        adapter = new BackgroundAdapter(getActivity().getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);

        Intent intent = getActivity().getIntent();
        int s = intent.getIntExtra("position", 0);


        viewPager.setCurrentItem(s);
        Intent myIntent = getActivity().getIntent();
        num = myIntent.getIntExtra("key",0);
        if (num == 1){
            textView.setText("购物车");
        }
    }
}
