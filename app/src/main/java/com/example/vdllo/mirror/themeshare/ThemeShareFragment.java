package com.example.vdllo.mirror.themeshare;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.StoryListBean;
import com.example.vdllo.mirror.home.CatalogFragment;
import com.example.vdllo.mirror.home.MainActivity;
import com.example.vdllo.mirror.net.NetHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ThemeShareFragment extends BaseFragment {

    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ThemeShareAdapter adapter;
    private Handler handler;
    private int i;
    private StoryListBean storyListBean;
    private TextView titleTextView;
    private MainActivity mainActivity;


    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
        linearLayout = bindView(R.id.all_type_linearlayout);
        titleTextView = bindView(R.id.all_type_titleTv);
        mainActivity = (MainActivity) getContext();
        titleTextView.setText("專題分享");

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
                storyListBean = gson.fromJson(msg.obj.toString(), StoryListBean.class);
                // 2.设置布局管理器
                manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                // 3.设置适配器
                adapter = new ThemeShareAdapter(getContext(), storyListBean);
                recyclerView.setAdapter(adapter);

                return false;
            }
        });
        //专题分享
        NetHelper netHelper = new NetHelper(getActivity());
        netHelper.getShareInfo(handler);
    }


}

