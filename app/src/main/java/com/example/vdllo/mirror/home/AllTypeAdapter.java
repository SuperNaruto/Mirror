package com.example.vdllo.mirror.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vdllo.mirror.R;

import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeAdapter extends RecyclerView.Adapter<AllTypeAdapter.ViewHolder>{
    List<Integer> datas;

    public AllTypeAdapter(List<Integer> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item, null);
        ViewHolder vh = new ViewHolder(view);
        vh.iv = (ImageView) view.findViewById(R.id.all_type_iv);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.iv.setImageResource(datas.get(position));


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
