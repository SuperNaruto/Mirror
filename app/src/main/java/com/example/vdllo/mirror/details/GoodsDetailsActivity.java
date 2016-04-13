package com.example.vdllo.mirror.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.toolclass.LinkageListView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Bo on 16/4/8.
 */
public class GoodsDetailsActivity extends BaseAcitvity implements View.OnClickListener {

    private LinkageListView listView;
    private static GoodsListBean data;
    private Handler handler;
    private static int pos;
    private SimpleDraweeView background;
    private Button backBtn, wearAtlasBtn, buyBtn;
    private boolean btnNotShow = true;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private RelativeLayout showBtnLayout;

    public static void setData(GoodsListBean data, int pos) {
        GoodsDetailsActivity.data = data;
        GoodsDetailsActivity.pos = pos;
    }

    @Override
    protected int setContent() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        listView = (LinkageListView) findViewById(R.id.detail_listView);
        background = (SimpleDraweeView) findViewById(R.id.goodsDetail_background);
        wearAtlasBtn = (Button) findViewById(R.id.activity_details_wearAtlas_btn);
        wearAtlasBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        listView.setAdapter(new UpListViewAdapter(data, this, pos), new DownListViewAdapter(data, this, pos));
        listView.setLinkageSpeed(1.2f);
        background.setImageURI(Uri.parse(data.getData().getList().get(pos).getGoods_img()));
//        ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
//        animator.setDuration(1);
//        animator.start();
//        animation = ObjectAnimator.ofFloat(showBtnLayout, "translationX", 500);
//        animation.setDuration(1000);
//        animationBack = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
//        animationBack.setDuration(1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_details_wearAtlas_btn:
                WearAtlasActivity.setData(data, pos);
                Intent intent = new Intent(GoodsDetailsActivity.this, WearAtlasActivity.class);
                startActivity(intent);
                break;

        }
    }


}
