package com.example.vdllo.mirror.home;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.MenuListBean;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.themeshare.ThemeShareFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;

import java.util.ArrayList;

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
    private boolean ifLogin = true;

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
        datas.add(new AllTypeFragment());
        datas.add(new AllTypeFragment());
        datas.add(new AllTypeFragment());
        datas.add(new ThemeShareFragment());
        datas.add(new ShoppingCartFragment());
        adapter = new BackgroundAdapter(getActivity().getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);

        Intent intent = getActivity().getIntent();
        int s = intent.getIntExtra("position", 0);
        viewPager.setCurrentItem(s);

        SharedPreferences sp = getActivity().getSharedPreferences("Mirror", Context.MODE_PRIVATE);
        ifLogin = sp.getBoolean("ifLogin", false);
        if (ifLogin) {
            textView.setText(R.string.main_activity_shoppingCart_text);
        } else {
            textView.setText(R.string.main_activity_login_text);
        }
    }
}
