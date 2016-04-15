package com.example.vdllo.mirror.shoppingcart;

import android.view.View;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;

/**
 * Created by dllo on 16/4/14.
 */
public class OrderDetailsActivity extends BaseAcitvity {
    private ImageView returnIv;

    @Override
    protected int setContent() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        returnIv = bindView(R.id.order_details_return_iv);
    }

    @Override
    protected void initData() {
         returnIv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }
}
