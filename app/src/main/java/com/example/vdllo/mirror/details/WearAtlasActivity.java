package com.example.vdllo.mirror.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class WearAtlasActivity extends BaseAcitvity {
    private RecyclerView recyclerView;
    private WearAtlasAdapter wearAtlasAdapter;
    private GoodsListBean goodsListBean;
    private Handler handler;

    @Override
    protected int setContent() {
        return R.layout.activity_wearatlas;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_wear_atlas_recyclerView);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                goodsListBean = gson.fromJson(msg.obj.toString(), GoodsListBean.class);
                LinearLayoutManager manager = new LinearLayoutManager(WearAtlasActivity.this);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                wearAtlasAdapter = new WearAtlasAdapter(goodsListBean, position, WearAtlasActivity.this);
                recyclerView.setAdapter(wearAtlasAdapter);
                return false;
            }
        });

        NetHelper netHelper = new NetHelper(this);
        netHelper.getGoods(handler);
    }


}
