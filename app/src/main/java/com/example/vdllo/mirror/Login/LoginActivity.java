package com.example.vdllo.mirror.Login;

import android.content.Intent;
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
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.db.DaoSingleton;
import com.example.vdllo.mirror.db.MirrorEntity;
import com.example.vdllo.mirror.home.MainActivity;
import com.example.vdllo.mirror.shoppingcart.AddressActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/11.
 */
public class LoginActivity extends BaseAcitvity implements View.OnClickListener {
    private Button createBtn, loginBtn;
    private EditText numEt, pasEt;
    private ImageView sinaIv, qqIv;
    private ImageView imageView;
    private String num, password;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            try {
                JSONObject obj = new JSONObject(msg.obj.toString());
                String result = obj.getString("result");
                if (result.equals("")) {
                    Toast.makeText(LoginActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                } else if (result.equals("1")) {
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("key", 1);
                    startActivity(intent);
                    String token = obj.getJSONObject("data").getString("token");
                    AddressActivity.setToken(token);
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
        sinaIv = bindView(R.id.login_sina_iv);
        qqIv = bindView(R.id.login_qq_iv);
        imageView.setOnClickListener(this);
        createBtn.setOnClickListener(this);
        sinaIv.setOnClickListener(this);
        qqIv.setOnClickListener(this);


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
            case R.id.login_sina_iv:
                ShareSDK.initSDK(this);
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (platform.isAuthValid()) {
                    platform.removeAccount();
                }
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.i("android", platform.getDb().getUserName());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                platform.SSOSetting(false);
                platform.showUser(null);
                break;
            case R.id.login_qq_iv:
                ShareSDK.initSDK(this);
                Platform sPlatform = ShareSDK.getPlatform(QZone.NAME);
                if (sPlatform.isAuthValid()) {
                    sPlatform.removeAccount();
                }
                sPlatform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.i("android", platform.getDb().getUserName());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                sPlatform.SSOSetting(false);
                sPlatform.showUser(null);
                break;
        }
    }
}
