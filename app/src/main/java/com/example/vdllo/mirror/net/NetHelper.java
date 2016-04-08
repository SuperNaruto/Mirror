package com.example.vdllo.mirror.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.vdllo.mirror.bean.UrlBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/5.
 */
public class NetHelper {
    private Handler handler;

    //商品列表
    public void getGoods(final Handler handler) {
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.GOODS_LIST)
                .addParams("token", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "")
                .addParams("category_id", "")
                .addParams("version", "").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                //子线程无法刷新UI,利用handler发送Message到主线程
                String body = response.body().string();
                Message message = new Message();
                message.what = 1;
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
    }

    //专题分享
    public void getShareInfo(final Handler handler) {
        OkHttpUtils.post().url(UrlBean.STORY_LIST)
                .addParams("token", "")
                .addParams("uid", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "").build().execute(new Callback() {
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

    }

    //弹出菜单
    public void getMenu(final Handler handler) {
        OkHttpUtils.post().url(UrlBean.MENU_LIST).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.what = 2;
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


    }

}
