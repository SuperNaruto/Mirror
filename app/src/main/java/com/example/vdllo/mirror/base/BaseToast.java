package com.example.vdllo.mirror.base;

import android.widget.Toast;

/**
 * Created by Bo on 16/3/29.
 */
public class BaseToast {

    private static boolean isShow = true;

    public static void myToast(String data) {
        if (isShow) {
            Toast.makeText(BaseApplication.getContext(), data, Toast.LENGTH_SHORT).show();
        }
    }

    public static void myToast(int data) {
        if (isShow) {
            Toast.makeText(BaseApplication.getContext(), String.valueOf(data), Toast.LENGTH_SHORT).show();
        }
    }
}
