package com.example.vdllo.mirror.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment{

    private AllTypeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Integer> datas;

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
    }

    @Override
    protected void dataView() {
        // 1.获取图片的数据
        datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            datas.add(R.mipmap.home_pic);
        }
        // 2.设置布局管理器
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        // 3.设置适配器
        adapter = new AllTypeAdapter(datas);
        recyclerView.setAdapter(adapter);
    }
}
