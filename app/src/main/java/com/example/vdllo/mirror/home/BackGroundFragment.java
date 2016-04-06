package com.example.vdllo.mirror.home;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.shoppingcart.ShoppingCartFragment;
import com.example.vdllo.mirror.toolclass.CustomViewPager;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by dllo on 16/4/5.
 */
public class BackGroundFragment extends BaseFragment{
    private CustomViewPager viewPager;
    private BackgroundAdapter adapter;
    private ArrayList<Fragment> datas;

    @Override
    public int getLayout() {
        return R.layout.fragment_background;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.main_viewpager);
    }

    @Override
    protected void dataView() {
        datas = new ArrayList<>();
        datas.add(new AllTypeFragment(0));
        datas.add(new AllTypeFragment(1));
        datas.add(new AllTypeFragment(2));
        datas.add(new AllTypeFragment(3));
        datas.add(new ShoppingCartFragment());
        adapter = new BackgroundAdapter(getActivity().getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);

        Intent intent = getActivity().getIntent();
        int s = intent.getIntExtra("position",0);
        viewPager.setCurrentItem(s);

    }


}
