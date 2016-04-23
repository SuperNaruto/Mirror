package com.example.vdllo.mirror.home;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.base.BaseToast;
import com.example.vdllo.mirror.bean.MenuListBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.themeshare.ShareDetailsFragment;
import com.example.vdllo.mirror.themeshare.ThemeShareFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;
import com.example.vdllo.mirror.user.LoginActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Field;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseAcitvity implements ViewPager.OnPageChangeListener {

    //记录第一次点击的时间
    private long clickTime = 0;
    private TextView textView;

    private CustomViewPager viewPager;
    private ArrayList<Fragment> fragments; // viewpager里的fragment集合
    private BackgroundAdapter adapter;
    private boolean ifLogin = true;
    private CatalogFragment catalogFragment;
    private FrameLayout frameLayout;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Gson gson = new Gson();
            MenuListBean menuListBean = gson.fromJson(msg.obj.toString(), MenuListBean.class);
            fragments = new ArrayList<>();
            // 加载fragment
            for (int i = 0; i < menuListBean.getData().getList().size(); i++) {
                String type = menuListBean.getData().getList().get(i).getType();// 得到fragment对应的type
                String categoryId = menuListBean.getData().getList().get(i).getInfo_data();// fragment需要的id
                String title = menuListBean.getData().getList().get(i).getTitle();// 得到title
                // "3"是商品 "4"是购物车 "2"是专题分享 "6"是全部分类
                switch (type) {
                    case "3":
                        AllTypeFragment fragment = new AllTypeFragment();
                        fragments.add(fragment);
                        // 向商品的fragment里传入"category_id"的值
                        Bundle bundle = new Bundle();
                        bundle.putString("id", categoryId);
                        bundle.putString("title", title);
                        fragment.setArguments(bundle);
                        break;
                    case "4":
                        fragments.add(new ShoppingCartFragment());
                        break;
                    case "6":
                        fragments.add(new AllFragment());
                        break;

                }
            }
            fragments.add(new ThemeShareFragment());
            adapter = new BackgroundAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(adapter);
            return false;
        }
    });


    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.main_viewpager);
        viewPager.setOnPageChangeListener(this);
        catalogFragment = new CatalogFragment();
        frameLayout = bindView(R.id.mainactivity_top_fragment);
        //向占位布局里加入fragment
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mainactivity_top_fragment, catalogFragment).commit();
        textView = bindView(R.id.main_login_tv);
        bindView(R.id.main_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 属性动画
                // ObjectAnimator / ValueAnimator 动画的执行类
                // ofFloat构建并返回一个objectanimator动画之间的浮点值(小数值)。
                // 参数1：目标对象（Object target）
                // 参数2：属性名（String propertyName）
                // 参数3~N：小数值
                ObjectAnimator.ofFloat(v, getString(R.string.MainActivity_scaleX), 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
                ObjectAnimator.ofFloat(v, getString(R.string.CatalogFragment_scaleY), 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
            }
        });
    }

    @Override
    protected void initData() {
        SharedPreferences sp = getSharedPreferences(getString(R.string.MainActivity_Mirror), MODE_PRIVATE);
        ifLogin = sp.getBoolean(getString(R.string.MainActivity_ifLogin), false);
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.MENU_LIST)
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                //子线程无法刷新UI,利用handler发送Message到主线程
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                handler.sendMessage(message);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });

        Intent intent = getIntent();
        int s = intent.getIntExtra("position", 0);
        viewPager.setCurrentItem(s);
        if (ifLogin) {
            textView.setText(R.string.main_activity_shoppingCart_text);
        } else {
            textView.setText(R.string.main_activity_login_text);
        }

        SharedPreferences mySp = getSharedPreferences("Mirror", MODE_PRIVATE);
        ifLogin = mySp.getBoolean("ifLogin", false);
        if (ifLogin) {
            textView.setText(R.string.main_activity_shoppingCart_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(3);
                }
            });
        } else {
            textView.setText(R.string.main_activity_login_text);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            BaseToast.myToast(getString(R.string.MainActivity_clickQuit));
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
        }
    }

    // 展示菜单
    public void showMenu() {
        frameLayout.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.popwindow_anim);
        frameLayout.setAnimation(animation);
    }

    // 菜单消失
    public void disappearMenu() {
        frameLayout.setVisibility(View.GONE);
    }

    // 设置跳转
    public void jumpViewPager(int position) {
        disappearMenu();
        // 这个是设置viewPager切换过度时间的类
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        // 设置viewpager滑动动画的时间
        scroller.setScrollDuration(50);

        scroller.initViewPagerScroll(viewPager);
        // viewpager跳转到传入的页码数
        viewPager.setCurrentItem(position);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        catalogFragment.setupAdapter(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // 设置viewpager滑动速度的类
    public class ViewPagerScroller extends Scroller {
        private int mScrollDuration;             // 滑动速度

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

