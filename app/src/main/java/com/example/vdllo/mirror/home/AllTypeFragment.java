package com.example.vdllo.mirror.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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

    private LinearLayout linearLayout;
    private PopupWindow popupWindow;
    View view;

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recycleView);

        linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
        linearLayout.setOnClickListener(popClick);
    }

    // 点击弹出左侧菜单的显示方式
    View.OnClickListener popClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
			/*Toast toast = Toast.makeText(MainActivity.this, "这是一个代图片的Toast!", Toast.LENGTH_LONG);
			toast.show();*/

            getPopupWindow();
            // 这里是位置显示方式
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        }
    };

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {

        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

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

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件pop.xml的视图
        View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.pop, null,
                false);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, dm.widthPixels,
                dm.heightPixels, true);
//         popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//         popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置动画效果
//        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
//        // pop.xml视图里面的控件
//        initOpenMenuItem(popupWindow_view);
//        initOpenMenuOther(popupWindow_view);
//        initOpenPosition(popupWindow_view);

    }
}
