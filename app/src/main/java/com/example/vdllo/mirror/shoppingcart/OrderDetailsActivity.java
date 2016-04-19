package com.example.vdllo.mirror.shoppingcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.OrderDetailsBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/14.
 */
public class OrderDetailsActivity extends BaseAcitvity {
    private ImageView returnIv, picIv;
    private TextView nameTv, contentTv, priceTv, recipientTv, infoTv, telTv;
    private Button inputAddressBtn;
    private int requestCode = 200;
    private Handler handler;
    private OrderDetailsBean orderDetailsBean;
    private Button buyBtn;
    private PopupWindow myPopupWindow;

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
        recipientTv = bindView(R.id.order_details_nameTv);
        infoTv = bindView(R.id.order_details_infoTv);
        telTv = bindView(R.id.order_details_telTv);
        inputAddressBtn = bindView(R.id.input_address_btn);
        inputAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, AddressActivity.class);
                startActivityForResult(intent, requestCode);
            }
        });
        buyBtn = bindView(R.id.order_details_buyBtn);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 100) {
            recipientTv.setText("收件人：" + data.getStringExtra("name"));
            infoTv.setText("地址：" + data.getStringExtra("info"));
            telTv.setText("联系电话：" + data.getStringExtra("tel"));
            inputAddressBtn.setText("更改地址");
        }
    }

    @Override
    protected void initData() {
        finishIv();
        getData();
    }


    private void getData() {
        //购买商品详细
        Intent intent = getIntent();
        String price = intent.getStringExtra("price");
        String name = intent.getStringExtra("name");
        String content = intent.getStringExtra("content");
        String pic = intent.getStringExtra("pic");
        nameTv.setText(name);
        priceTv.setText(price);
        contentTv.setText(content);
        Picasso.with(OrderDetailsActivity.this).load(pic).into(picIv);

        //地址详细
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    orderDetailsBean = new Gson().fromJson(msg.obj.toString(), OrderDetailsBean.class);
                    if (orderDetailsBean != null) {
                        recipientTv.setText("收件人:" + orderDetailsBean.getData().getAddress().getUsername());
                        telTv.setText("联系电话：" + orderDetailsBean.getData().getAddress().getCellphone());
                        infoTv.setText("地址:" + orderDetailsBean.getData().getAddress().getAddr_info());
                        inputAddressBtn.setText("更改地址");
                    } else {
                        inputAddressBtn.setText("添加地址");
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        String id = intent.getStringExtra("id");
        SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
        String token = sp.getString("token", "");
        OkHttpUtils.post().url(UrlBean.ORDER_SUB).addParams("token", token).addParams("goods_id", id)
                .addParams("price", price)
                .addParams("device_type", "2").addParams("goods_num", "1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                handler.sendMessage(message);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });

    }

    private void finishIv() {
        returnIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
