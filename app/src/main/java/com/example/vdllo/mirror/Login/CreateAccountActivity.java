package com.example.vdllo.mirror.Login;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.CreateBean;
import com.example.vdllo.mirror.bean.StoryInfoBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.themeshare.ShareDetailsFragment;
import com.example.vdllo.mirror.themeshare.ThemeShareActivityAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/11.
 */
public class CreateAccountActivity extends BaseAcitvity {
    private EditText phoneEt, numEt, passwordEt;
    private Button sendBtn, createBtn;

    private TimerTask timerTask;
    private Timer timer;
    private int count = 60;

    private String num,number;
    private CreateBean data;


    Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //1.Gson解析
            Gson gson = new Gson();
            data = gson.fromJson(msg.obj.toString(), CreateBean.class);
            String toast;
            toast = data.getMsg();
            Log.d("我要的东西呢","就是错误信息"+toast);
            Toast.makeText(CreateAccountActivity.this, toast, Toast.LENGTH_SHORT).show();
            return false;
        }
    });

    @Override
    protected int setContent() {
        return R.layout.activity_create_account;
    }

    @Override
    protected void initView() {
        phoneEt = bindView(R.id.create_account_phone_et);
        numEt = bindView(R.id.create_account_num_et);
        passwordEt = bindView(R.id.create_account_password_et);

        sendBtn = bindView(R.id.create_account_sendout_btn);
        createBtn = bindView(R.id.create_account_create_btn);
    }

    @Override
    protected void initData() {
        phoneEt.addTextChangedListener(new TextWatcher() {

                                           @Override
                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                           }

                                           @Override
                                           public void onTextChanged(CharSequence s, int start, int before, final int count) {
                                               if (phoneEt.getText().length() == 0) {
                                                   sendBtn.setOnClickListener(new View.OnClickListener() {
                                                                                  @Override
                                                                                  public void onClick(View v) {
                                                                                      Toast.makeText(CreateAccountActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                                                                                  }


                                                                              }

                                                   );
                                               }

                                               sendBtn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       startCount();
                                                       number = phoneEt.getText().toString();

                                                       OkHttpUtils.post().url(UrlBean.USER_SEND_CODE).
                                                               addParams("phone number", number).build().execute(new Callback() {
                                                           @Override
                                                           public Object parseNetworkResponse(Response response) throws Exception {
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
                                               numEt.addTextChangedListener(new TextWatcher() {
                                                   @Override
                                                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                   }

                                                   @Override
                                                   public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                       num = numEt.getText().toString();

                                                       passwordEt.addTextChangedListener(new TextWatcher() {
                                                           @Override
                                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                           }

                                                           @Override
                                                           public void onTextChanged(CharSequence s, final int start, int before, int count) {
                                                               final String password = passwordEt.getText().toString();

                                                               createBtn.setOnClickListener(new View.OnClickListener() {
                                                                   @Override
                                                                   public void onClick(View v) {
                                                                       OkHttpUtils.post().url(UrlBean.USER_REG).
                                                                               addParams("phone number", number).addParams("number", num).addParams("password", password).build().execute(new Callback() {
                                                                           @Override
                                                                           public Object parseNetworkResponse(Response response) throws Exception {
                                                                               //子线程无法刷新UI,利用handler发送Message到主线程
                                                                               String body = response.body().string();
                                                                               Message message = new Message();
                                                                               message.obj = body;
                                                                               myHandler.sendMessage(message);
                                                                               Intent intent = new Intent(CreateAccountActivity.this,LoginActivity.class);
                                                                               startActivity(intent);
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
                                           public void afterTextChanged(Editable s) {
                                           }
                                       }

        );

    }

    public void startCount() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);

            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    sendBtn.setText("" + count);
                    if (count > 0) sendBtn.setText(count + "秒");
                    else
                    sendBtn.setText("重新获取");
            }
        }
    };

}
