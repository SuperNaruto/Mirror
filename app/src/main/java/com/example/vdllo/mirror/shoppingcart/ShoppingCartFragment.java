package com.example.vdllo.mirror.shoppingcart;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.home.CatalogFragment;
import com.example.vdllo.mirror.home.MainActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ShoppingCartFragment extends BaseFragment {
    private LinearLayout linearLayout;
    private MainActivity activity;
    private TextView textView;


    @Override
    public int getLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected void initView() {
        linearLayout = bindView(R.id.shopping_cart_linearLayout);
        activity = (MainActivity) getContext();
        textView = bindView(R.id.shopping_cart_tv);
    }


    @Override
    protected void initData() {
        textView.setText("我的購物車");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showMenu();
            }
        });
    }
}
