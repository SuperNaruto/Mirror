package com.example.vdllo.mirror.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bo on 16/3/29.
 */
public abstract class BaseFragment extends Fragment {

    // 绑定布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), null);
    }

    // 绑定组件
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    // 加载数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    // 方便初始化组件
    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }
}
