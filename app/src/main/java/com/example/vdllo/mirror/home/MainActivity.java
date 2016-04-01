package com.example.vdllo.mirror.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends BaseAcitvity {

    private CustomViewPager viewPager;
    private MainAdapter adapter;
    private ArrayList<Fragment> datas;
    private static final String TAG = MainActivity.class.getSimpleName();

    private long clickTime = 0; //记录第一次点击的时间

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
        datas.add(new AllTypeFragment(0));
        datas.add(new AllTypeFragment(1));
        datas.add(new AllTypeFragment(2));
        datas.add(new AllTypeFragment(3));
        datas.add(new ShoppingCartFragment());
        adapter = new MainAdapter(getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);

        Intent intent = getIntent();
        int s = intent.getIntExtra("position", 0);
        viewPager.setCurrentItem(s);


        Intent rIntent = getIntent();
        int m = rIntent.getIntExtra("position", 0);
        viewPager.setCurrentItem(m);
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
            Log.e(TAG, "exit application");
            this.finish();

        }
    }

    public void getPositionFromPopWindow(int position) {
        //这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(200);//这个是设置切换过渡时间为0毫秒
        scroller.initViewPagerScroll(viewPager);
        viewPager.setCurrentItem(position);
    }

    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration = 2000;             // 滑动速度

        /**
         * 设置速度速度
         *
         * @param duration
         */
        public void setScrollDuration(int duration) {
            this.mScrollDuration = duration;
        }

        public ViewPagerScroller(Context context) {
            super(context);
        }


        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mScrollDuration);
        }


        public void initViewPagerScroll(ViewPager viewPager) {
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                mScroller.set(viewPager, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
