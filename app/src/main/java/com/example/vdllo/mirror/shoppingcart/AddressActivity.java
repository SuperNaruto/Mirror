package com.example.vdllo.mirror.shoppingcart;

import android.util.Log;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.db.DaoSingleton;
import com.example.vdllo.mirror.db.MirrorEntity;
import com.example.vdllo.mirror.db.MirrorEntityDao;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/15.
 */
public class AddressActivity extends BaseAcitvity {
    private MirrorEntityDao mirrorEntityDao;
    private static String token;

    public static void setToken(String token) {
        AddressActivity.token = token;
    }

    @Override
    protected int setContent() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initData() {
        //cadd7128deb9c01cd31fed05dd7fca87


    }

}
