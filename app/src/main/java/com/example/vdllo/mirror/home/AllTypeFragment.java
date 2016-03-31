package com.example.vdllo.mirror.home;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment {

    private AllTypeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Integer> datas;
    private LinearLayout linearLayout;
    private ShowMenu showMenu;
    private ArrayList<String> data;
    private int i;

    public AllTypeFragment(int i) {
        this.i = i;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
        linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
        showMenu = new ShowMenu(getContext());
        data = new ArrayList<>();
        data.add("浏览所有分类");
        data.add("浏览平光眼镜");
        data.add("浏览太阳眼镜");
        data.add("专题分享");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu.showPopupWindow(v,data,i);
            }
        });
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

