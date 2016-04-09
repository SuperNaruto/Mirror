package com.example.vdllo.mirror.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bo on 16/4/6.
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter {

    protected View createItemLayout(int resId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
    }

    public abstract class BaseHolder extends RecyclerView.ViewHolder {
        private View view;

        public BaseHolder(View itemView) {
            super(itemView);
            view = itemView;
            initView();

        }

        protected abstract void initView();


        public <T extends View> T bindItemView(int ResId) {
            T t = (T) view.findViewById(ResId);
            return t;
        }
    }
}
