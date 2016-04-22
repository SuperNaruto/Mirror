package com.example.vdllo.mirror.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment {

    private LinearLayoutManager manager;
    private GoodsListBean goodsListBean;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private AllTypeAdapter adapter;
    private String title; // 标题上的文字
    private Handler handler;
    private int i;
    private TextView titleTextView;
    private MainActivity mainActivity;
    private String categoryId; // 每个fragment对应的id

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
        linearLayout = bindView(R.id.all_type_linearlayout);
        titleTextView = bindView(R.id.all_type_titleTv);
        mainActivity = (MainActivity)getContext();

        //为标签设置名字
        // 接收fragment的id 放进请求参数里
        Bundle bundle = getArguments();
        title = bundle.getString("title");
        titleTextView.setText(title);
        categoryId = bundle.getString("id");
        Log.d("android","++++" + categoryId);

        //设置popupWindow监听
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.showMenu();
    }
});

    }


    @Override
    protected void initData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //1.Gson解析
                Gson gson = new Gson();
                goodsListBean = gson.fromJson(msg.obj.toString(), GoodsListBean.class);
                // 2.设置布局管理器
                manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                // 3.设置适配器
                adapter = new AllTypeAdapter(goodsListBean, getContext(), i);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        //商品列表
        NetHelper netHelper = new NetHelper(getActivity());
        if (categoryId.equals("268")){
            netHelper.getSunGoods(handler);
        }else if (categoryId.equals("269")){
            netHelper.getLineGoods(handler);
        }


    }

}

