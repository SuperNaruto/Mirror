package com.example.vdllo.mirror.shoppingcart;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.vdllo.mirror.bean.AddressBean;

/**
 * Created by dllo on 16/4/15.
 */
public class AddressActivityAdapter extends BaseAdapter {
    private Context context;
    private AddressBean addressBean;

    public AddressActivityAdapter(Context context, AddressBean addressBean) {
        this.context = context;
        this.addressBean = addressBean;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
