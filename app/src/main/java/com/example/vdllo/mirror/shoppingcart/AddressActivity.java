package com.example.vdllo.mirror.shoppingcart;

import android.util.Log;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.CreateBean;

import org.json.JSONException;
import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/4/15.
 */
public class AddressActivity extends BaseAcitvity {

    @Override
    protected int setContent() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);

    }

    //接收传过来的，使用token
    public void onEvent(JSONObject jsonObject) throws JSONException {
        String token = (String) jsonObject.get("token");
        Log.d("aaa",token);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
