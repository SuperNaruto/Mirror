package com.example.vdllo.mirror.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseApplication;
import com.example.vdllo.mirror.bean.StartImgBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.home.MainActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/1.
 */
public class WelcomeActivity extends AppCompatActivity {
    private SimpleDraweeView welcomeIv;
    private Handler myHandler;
    private StartImgBean startImgBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeIv = (SimpleDraweeView) findViewById(R.id.welcome_iv);
        //handler接收消息,Gson解析图片,并刷新UI
        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                startImgBean = gson.fromJson(msg.obj.toString(), StartImgBean.class);
//                Picasso.with(BaseApplication.getContext()).load(startImgBean.getImg()).into(welcomeIv);
                welcomeIv.setImageURI(Uri.parse(startImgBean.getImg()));
                return false;
            }
        });
        //获取网络图片
        getPic();

        //Handler主要用于异步消息的处理,通常用来处理相对耗时比较长的操作。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 3000);
    }

    public void getPic() {
        //okhttp解析
        OkHttpUtils.post().url(UrlBean.INDEX_STARTED_IMG).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                myHandler.sendMessage(message);
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
}
