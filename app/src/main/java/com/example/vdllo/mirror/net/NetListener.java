package com.example.vdllo.mirror.net;

/**
 * Created by dllo on 16/4/22.
 */
public interface NetListener {
    //请求成功
    void getSuccess(Object object);

    //请求失败
    void getFailed(int s);
}
