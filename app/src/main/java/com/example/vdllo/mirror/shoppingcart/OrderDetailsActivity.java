package com.example.vdllo.mirror.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.AlipayBean;
import com.example.vdllo.mirror.bean.OrderDetailsBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import alipay.PayResult;
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
    private Handler handler, orderHandler;
    private OrderDetailsBean orderDetailsBean;
    private Button buyBtn;
    private PopupWindow myPopupWindow;
    private LinearLayout aliPay;
    private AlipayBean alipayBean;
    private String str;
    //支付宝调用
    private static final int SDK_PAY_FLAG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResultActvity = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResultActvity.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResultActvity.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderDetailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderDetailsActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

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
                initPay();
                showPopWindow(v);
            }
        });
    }

    public void showPopWindow(View view) {
        myPopupWindow = new PopupWindow(this);
        View v = LayoutInflater.from(this).inflate(R.layout.popwindow_payment, null);
        myPopupWindow.setContentView(v);
        myPopupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        myPopupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        myPopupWindow.setBackgroundDrawable(null);
        myPopupWindow.setOutsideTouchable(true);
        myPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        myPopupWindow.setAnimationStyle(R.style.PaymentAnim);
        myPopupWindow.update();
        initPopWindow(v);

    }

    private void initPay() {
        orderHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    Gson gson = new Gson();
                    alipayBean = gson.fromJson(msg.obj.toString(), AlipayBean.class);
                    str = alipayBean.getData().getStr();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }

                return false;
            }
        });

        Intent intent = getIntent();
        SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
        String token = sp.getString("token", "");
        OkHttpUtils.post().url(UrlBean.PAY_ALI).addParams("token", token).addParams("order_no", intent.getStringExtra("order_id"))
                .addParams("addr_id", intent.getStringExtra("addr_id")).addParams("goodsname", intent.getStringExtra("name")).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                orderHandler.sendMessage(message);
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

    private void initPopWindow(final View view) {
        aliPay = (LinearLayout) view.findViewById(R.id.aliPay);
        aliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //解析出str
                final String payInfo = str;
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderDetailsActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(payInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        myPopupWindow.dismiss();
//                    }
//                });
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (myPopupWindow != null && myPopupWindow.isShowing()) {
            myPopupWindow.dismiss();
            myPopupWindow = null;
        }
        return super.onTouchEvent(event);
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
                .addParams("price", price).addParams("device_type", "2").addParams("goods_num", "1").build().execute(new Callback() {
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
