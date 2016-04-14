package com.example.vdllo.mirror.home;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vdllo.mirror.Login.LoginActivity;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.themeshare.ThemeShareFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseAcitvity {

    //记录第一次点击的时间
    private long clickTime = 0;
    private TextView textView;
    private int num =0;
    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_linearlayout, new BackGroundFragment());
        textView = bindView(R.id.main_login_tv);
        ft.commit();
        bindView(R.id.main_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 属性动画
                // ObjectAnimator / ValueAnimator 动画的执行类
                // ofFloat构建并返回一个objectanimator动画之间的浮点值(小数值)。
                // 参数1：目标对象（Object target）
                // 参数2：属性名（String propertyName）
                // 参数3~N：小数值
                ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
                ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
            }
        });
    }

    @Override
    protected void initData() {
        if (num==0){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        Intent intent = getIntent();
        int num = intent.getIntExtra("key",0);
        if (num == 1){
            textView.setText("购物车");
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v4.app.FragmentTransaction myFt = getSupportFragmentManager().beginTransaction();
                    myFt.replace(R.id.main_linearlayout, new ShoppingCartFragment(4));
                    myFt.commit();
                }
            });

        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();

        }
    }

}

