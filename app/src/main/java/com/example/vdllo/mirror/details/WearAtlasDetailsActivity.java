package com.example.vdllo.mirror.details;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.base.BaseApplication;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.db.DaoSingleton;
import com.example.vdllo.mirror.home.AllTypeAdapter;
import com.example.vdllo.mirror.net.NetHelper;
import com.example.vdllo.mirror.toolclass.SmoothImageView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/4/13.
 */
public class WearAtlasDetailsActivity extends AppCompatActivity {
    private int locationX, locationY, width, height, pos, position;
    private SmoothImageView smoothImageView;
    private Handler handler;
    private GoodsListBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置背景为黑
        this.getWindow().setBackgroundDrawableResource(R.drawable.wear_atlas_detail_background);
        //接收传过来的值
        locationX = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_locationX), 0);
        locationY = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_locationY), 0);
        width = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_width), 0);
        height = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_height), 0);
        pos = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_pos), 0);
        position = getIntent().getIntExtra(getString(R.string.WearAtlasActivity_position), 0);
        smoothImageView = new SmoothImageView(this);
        smoothImageView.setOriginalInfo(width, height, locationX, locationY);
        smoothImageView.transformIn();
        //创建一个指定宽高参数 参数为-1,-1因为代表MATCH_PARENT
        smoothImageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        smoothImageView.setScaleType(SmoothImageView.ScaleType.FIT_CENTER);
        setContentView(smoothImageView);
        initData();
    }

    public void initData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                data = gson.fromJson(msg.obj.toString(), GoodsListBean.class);
                Picasso.with(WearAtlasDetailsActivity.this).load(data.getData().getList().get(pos).getWear_video().get(position).getData()).into(smoothImageView);
                return false;
            }
        });

        //商品列表
        NetHelper netHelper = new NetHelper(WearAtlasDetailsActivity.this);
        netHelper.getGoods(handler);
    }


    @Override
    public void onBackPressed() {
        smoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        smoothImageView.transformOut();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        smoothImageView.setOnTransformListener(new SmoothImageView.TransformListener() {
            @Override
            public void onTransformComplete(int mode) {
                if (mode == 2) {
                    finish();
                }
            }
        });
        smoothImageView.transformOut();
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            overridePendingTransition(0, 0);
        }
    }

}
