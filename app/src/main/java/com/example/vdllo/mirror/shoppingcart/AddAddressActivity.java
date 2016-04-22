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
import com.example.vdllo.mirror.base.BaseToast;
import com.example.vdllo.mirror.bean.UrlBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/14.
 */
public class AddAddressActivity extends BaseAcitvity implements View.OnClickListener {
    private ImageView returnIv;
    private EditText nameEt, telEt, addressEt;
    private Button addAddressBtn;
    private Handler handler;

    @Override
    protected int setContent() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void initView() {
        returnIv = bindView(R.id.activity_addAddress_return);
        returnIv.setOnClickListener(this);
        nameEt = bindView(R.id.add_address_name_et);
        telEt = bindView(R.id.add_address_phone_et);
        addressEt = bindView(R.id.add_address_address_et);
        addAddressBtn = bindView(R.id.activity_addAddress_btn);
        addAddressBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addAddress_return:
                finish();
                break;
            case R.id.activity_addAddress_btn:
                String name = nameEt.getText().toString();
                String tel = telEt.getText().toString();
                String address = addressEt.getText().toString();
                SharedPreferences sp = getSharedPreferences(getString(R.string.AddAddressActivity_Mirror), MODE_PRIVATE);
                String token = sp.getString(getString(R.string.AddAddressActivity_token), "");
                if (!name.equals("") && !tel.equals("") && !address.equals("")) {
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            BaseToast.myToast(getString(R.string.AddAddressActivity_success));
                            finish();
                            return false;
                        }
                    });
                    OkHttpUtils.post().url(UrlBean.USER_ADD_ADDRESS).addParams(getString(R.string.AddAddressActivity_token), token)
                            .addParams(getString(R.string.AddAddressActivity_username), name)
                            .addParams(getString(R.string.AddAddressActivity_cellphone), tel)
                            .addParams(getString(R.string.AddAddressActivity_addr_info), address).build().execute(new Callback() {
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
                    BaseToast.myToast(getString(R.string.AddAddressActivity_write));
                }
                break;
        }
    }
}
