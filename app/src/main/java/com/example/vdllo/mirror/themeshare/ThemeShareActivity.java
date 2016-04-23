package com.example.vdllo.mirror.themeshare;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.StoryInfoBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.toolclass.CustomViewPager;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/8.
 */
public class ThemeShareActivity extends BaseAcitvity implements View.OnClickListener {
    private CustomViewPager customViewPager;
    private ThemeShareActivityAdapter themeShareActivityAdapter;
    private ArrayList<Fragment> datas;
    private ImageView cImageView,shareIv;
    private SimpleDraweeView simpleDraweeView;
    private Handler handler;
    private StoryInfoBean data;
    private ArrayList<String> imgs;

    @Override
    protected int setContent() {
        return R.layout.activity_theme_share;
    }

    @Override
    protected void initView() {
        customViewPager = bindView(R.id.theme_share_viewpager);
        cImageView = bindView(R.id.theme_share_close_iv);
        simpleDraweeView = bindView(R.id.theme_share_AIv);
        shareIv = bindView(R.id.theme_share_share_iv);
        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = data.getData().getStory_url();
                String u = data .getData().getStory_img();
                ShareSDK.initSDK(ThemeShareActivity.this);
                            OnekeyShare oks = new OnekeyShare();
                            //关闭sso授权
                            oks.disableSSOWhenAuthorize();
                            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                            oks.setImageUrl("");
                            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                            oks.setTitle(getString(R.string.app_name));
                            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                            oks.setTitleUrl(s);
                            // text是分享文本，所有平台都需要这个字段
                            oks.setText("CESHI");
                            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                            // url仅在微信（包括好友和朋友圈）中使用
                            oks.setUrl(null);
                            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                            oks.setComment("");
                            // site是分享此内容的网站名称，仅在QQ空间使用
                            oks.setSite(getString(R.string.app_name));
                            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                            oks.setSiteUrl(null);
                            // 启动分享GUI
                            oks.show(ThemeShareActivity.this);
            }
        });

    }

    @Override
    protected void initData() {
        //
        Intent intent = getIntent();
        String s = intent.getStringExtra("position");
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //1.Gson解析
                Gson gson = new Gson();
                data = gson.fromJson(msg.obj.toString(), StoryInfoBean.class);
                datas = new ArrayList<>();
                imgs = new ArrayList();
                for (int i = 0; i < data.getData().getStory_data().getText_array().size(); i++) {
                    datas.add(new ShareDetailsFragment());
                    themeShareActivityAdapter = new ThemeShareActivityAdapter(getSupportFragmentManager(), datas);
                    customViewPager.setAdapter(themeShareActivityAdapter);
                }
                for (int j = 0; j < data.getData().getStory_data().getImg_array().size(); j++) {
                    imgs.add(data.getData().getStory_data().getImg_array().get(j));

                }
                simpleDraweeView.setImageURI(Uri.parse(imgs.get(0)));
                return false;
            }
        });
        OkHttpUtils.post().url(UrlBean.STORY_INFO).
                addParams("token", "").
                addParams("device_type", "2").
                addParams("story_id", s).build().execute(new Callback() {
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


        //处理viewPager页面滑动的事件
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
        /*
         * 调用。其中三个参数的含义分别为：arg0 :当前页面，及你点击滑动的页面 arg1:当前页面偏移的百分比 arg2:当前页面偏移的像素位置
         * */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）。
            @Override
            public void onPageSelected(int position) {

            }
            //此方法是在状态改变的时候调用，其中arg0这个参数
            // 有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，
            // arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    simpleDraweeView.setImageURI(Uri.parse(imgs.get(customViewPager.getCurrentItem())));
                }
            }
        });

        //点击事件
        cImageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.theme_share_close_iv:
                finish();
                break;
        }
    }
}
