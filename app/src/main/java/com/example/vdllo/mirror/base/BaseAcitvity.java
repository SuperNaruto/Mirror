package com.example.vdllo.mirror.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Bo on 16/3/29.
 */
public abstract class BaseAcitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(setContent());
        initView();
        initData();
    }

    // 绑定布局
    protected abstract int setContent() ;

    // 绑定组件
    protected abstract void initView();

    // 加载数据
    protected abstract void initData();

    // 方便初始化组件
    protected <T extends View>T bindView (int id){
        return (T)findViewById(id);
    }
}
