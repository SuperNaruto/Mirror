package com.example.vdllo.mirror.shoppingcart;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;

/**
 * Created by dllo on 16/4/14.
 */
public class OrderDetailsActivity extends BaseAcitvity {
    private ImageView returnIv,picIv;
    private TextView nameTv,contentTv,priceTv;
    private Button inputAddressBtn;

    @Override
    protected int setContent() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        returnIv = bindView(R.id.order_details_return_iv);
        picIv = bindView(R.id.order_details_pic_iv);
        nameTv = bindView(R.id.order_details_name_tv);
        contentTv = bindView(R.id.order_details_content_tv);
        priceTv = bindView(R.id.order_details_price_tv);
        inputAddressBtn = bindView(R.id.input_address_btn);
        inputAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
         returnIv.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

        getData();
    }

    private void getData(){
        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");
        nameTv.setText(name);
        priceTv.setText(price);
        contentTv.setText(content);
    }
}
