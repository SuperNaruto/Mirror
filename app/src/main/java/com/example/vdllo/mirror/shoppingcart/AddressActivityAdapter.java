package com.example.vdllo.mirror.shoppingcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
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
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return addressBean.getData().getList().size() > 0 && addressBean != null ? addressBean.getData().getList().size() : 0;
    }


    @Override
    public Object getItem(int position) {
        return addressBean.getData().getList().size() > 0 && addressBean.getData().getList() != null ? addressBean.getData().getList().get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.nameTv = (TextView) convertView.findViewById(R.id.address_item_recipient_name);
            viewHolder.addressTv = (TextView) convertView.findViewById(R.id.address_item_address);
            viewHolder.telTv = (TextView) convertView.findViewById(R.id.address_item_tel);
            viewHolder.editIv = (ImageView) convertView.findViewById(R.id.address_edit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTv.setText(addressBean.getData().getList().get(position).getUsername());
        viewHolder.addressTv.setText(addressBean.getData().getList().get(position).getAddr_info());
        viewHolder.telTv.setText(addressBean.getData().getList().get(position).getCellphone());
        return convertView;
    }

    //缓存类
    class ViewHolder {
        private TextView nameTv, addressTv, telTv;
        private ImageView editIv;

    }

    public void setData(int position) {
        addressBean.getData().getList().remove(position);
        notifyDataSetChanged();
    }
}
