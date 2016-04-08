package com.example.vdllo.mirror.shoppingcart;

import android.view.View;
import android.widget.LinearLayout;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.home.CatalogFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ShoppingCartFragment extends BaseFragment {
    private int i;
    private ArrayList<String> data;
    private LinearLayout linearLayout;

    public ShoppingCartFragment(int i) {
        this.i = i;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initView() {
        linearLayout = bindView(R.id.shopping_cart_linearLayout);
    }


    @Override
    protected void initData() {
        //menu
        data = new ArrayList<>();
        data.add("浏览所有分类");
        data.add("浏览太阳眼镜");
        data.add("浏览平光眼镜");
        data.add("专题分享");
        data.add("购物车");

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_linearlayout, new CatalogFragment(getActivity(), data, i));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
