package com.example.vdllo.mirror.home;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.toolclass.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CustomViewPager viewPager;
    private MainAdapter adapter;
    private ArrayList<Fragment> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        viewPager = (CustomViewPager) findViewById(R.id.main_viewpager);

        datas = new ArrayList<>();

        datas.add(new AllTypeFragment());
        datas.add(new ShoppingCartFragment());

        adapter = new MainAdapter(getSupportFragmentManager(),datas);
        viewPager.setAdapter(adapter);

    }
}
