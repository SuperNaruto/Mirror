package com.example.vdllo.mirror.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.CreateBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.home.MainActivity;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/11.
 */
public class LoginActivity extends BaseAcitvity implements View.OnClickListener {
    private Button createBtn, loginBtn;
    private EditText numEt, pasEt;
    private ImageView imageView;
    private String num, password;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            try {
                JSONObject obj = new JSONObject(msg.obj.toString());
                String result = obj.getString("result");
                //发送给addressActivity
                EventBus.getDefault().post(obj);
                if (result.equals("")) {
                    Toast.makeText(LoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("key", 1);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return false;
        }
    });

    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        createBtn = bindView(R.id.login_create_btn);
        loginBtn = bindView(R.id.login_login_btn);
        numEt = bindView(R.id.login_phonenumber_et);
        pasEt = bindView(R.id.login_password_et);
        imageView = bindView(R.id.login_return_iv);
        imageView.setOnClickListener(this);
        createBtn.setOnClickListener(this);

        numEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pasEt.setEnabled(true);
                num = numEt.getText().toString();
                if (numEt.getText().length() == 0) {
                    loginBtn.setBackgroundResource(R.mipmap.login_unusable_button);
                } else if (pasEt.getText().length() == 0) {
                    loginBtn.setBackgroundResource(R.mipmap.login_unusable_button);
                }
                pasEt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        loginBtn.setBackgroundResource(R.mipmap.login_usable_button);
                        password = pasEt.getText().toString();
                        if ((pasEt.getText().length() == 0) && (numEt.getText().length() == 0)) {
                            loginBtn.setBackgroundResource(R.mipmap.login_unusable_button);
                        } else if (pasEt.getText().length() == 0) {
                            loginBtn.setBackgroundResource(R.mipmap.login_unusable_button);
                        }

                        loginBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OkHttpUtils.post().url(UrlBean.USER_LOGIN).
                                        addParams("phone number", num).addParams("password", password).build().execute(new Callback() {
                                    @Override
                                    public Object parseNetworkResponse(Response response) throws Exception {
                                        //子线程无法刷新UI,利用handler发送Message到主线程
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
                        });
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_return_iv:
                finish();
                break;
            case R.id.login_create_btn:
                Intent myIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(myIntent);
                break;
        }
    }
}
