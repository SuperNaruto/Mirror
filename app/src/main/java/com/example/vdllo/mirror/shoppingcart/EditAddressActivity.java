package com.example.vdllo.mirror.shoppingcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.UrlBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/14.
 */
public class EditAddressActivity extends BaseAcitvity implements View.OnClickListener {
    private ImageView returnIv;
    private EditText nameEt, telEt, addressEt;
    private Button editAddressBtn;
    private Handler handler;

    @Override
    protected int setContent() {
        return R.layout.activity_editaddress;
    }

    @Override
    protected void initView() {
        returnIv = bindView(R.id.activity_editAddress_return);
        returnIv.setOnClickListener(this);
        nameEt = bindView(R.id.edit_address_name_et);
        telEt = bindView(R.id.edit_address_phone_et);
        addressEt = bindView(R.id.edit_address_address_et);
        editAddressBtn = bindView(R.id.activity_editAddress_btn);
        editAddressBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        nameEt.setText(intent.getStringExtra("name"));
        addressEt.setText(intent.getStringExtra("info"));
        telEt.setText(intent.getStringExtra("tel"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_editAddress_return:
                finish();
                break;
            case R.id.activity_editAddress_btn:
                String name = nameEt.getText().toString();
                String tel = telEt.getText().toString();
                String address = addressEt.getText().toString();
                SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
                String token = sp.getString("token", "");
                if (!name.equals("") && !tel.equals("") && !address.equals("")) {
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            Toast.makeText(EditAddressActivity.this, "提交编辑成功", Toast.LENGTH_SHORT).show();
                            finish();
                            return false;
                        }
                    });
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("id");
                    OkHttpUtils.post().url(UrlBean.USER_EDIT_ADDRESS).addParams("token", token).addParams("addr_id", id).addParams("username", name)
                            .addParams("cellphone", tel).addParams("addr_info", address).build().execute(new Callback() {
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
                } else {
                    Toast.makeText(EditAddressActivity.this, "请填写信息", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
