package com.example.vdllo.mirror.home;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.MenuListBean;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by dllo on 16/3/31.
 */
public class CatalogAdapter extends BaseAdapter {
    private MenuListBean datas;
    private int line;
    private Context context;

    public CatalogAdapter(MenuListBean datas, Context context) {
        this.datas = datas;
        this.context = context;
    }
    // 传入当前页码数
    public void setLine(int position){
        line = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.getData().getList().size();
    }

    @Override
    public Object getItem(int position) {
        return datas.getData().getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.popup_window_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.show_menu_textview);
            holder.imageView = (ImageView) convertView.findViewById(R.id.show_menu_iv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(datas.getData().getList().get(position).getTitle());
        if (line == position) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public class ViewHolder {
        private TextView textView;
        private ImageView imageView;
    }


}
