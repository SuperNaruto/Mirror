package com.example.vdllo.mirror.themeshare;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.StoryListBean;
import com.squareup.picasso.Picasso;

/**
 * …
 * Created by dllo on 16/3/30.
 */
public class ThemeShareAdapter extends RecyclerView.Adapter<ThemeShareAdapter.ViewHolder> {
    StoryListBean storyListBean;
    Context context;

    public ThemeShareAdapter(Context context, StoryListBean data) {
        this.context = context;
        this.storyListBean = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.theme_share_item, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Picasso加载图片
        Picasso.with(context).load(storyListBean.getData().getList().get(position).getStory_img()).into(holder.goodsPic);
        holder.brandTv.setText(storyListBean.getData().getList().get(position).getStory_title());
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView goodsPic;
        private TextView brandTv;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsPic = (ImageView) itemView.findViewById(R.id.all_type_share_iv);
            brandTv = (TextView) itemView.findViewById(R.id.all_type_share_brand);
        }
    }


}
