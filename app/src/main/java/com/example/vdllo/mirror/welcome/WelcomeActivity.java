package com.example.vdllo.mirror.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.home.MainActivity;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by dllo on 16/4/1.
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageView welcomeIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeIv = (ImageView) findViewById(R.id.welcome_iv);
        //Handler主要用于异步消息的处理,通常用来处理相对耗时比较长的操作。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }

    public void getPic(){
        //okhttp解析


    }
}
