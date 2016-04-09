package com.example.vdllo.mirror.themeshare;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.StoryListBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Picasso加载图片
        Picasso.with(context).load(storyListBean.getData().getList().get(position).getStory_img()).into(holder.goodsPic);
        holder.brandTv.setText(storyListBean.getData().getList().get(position).getStory_title());
        if (position == 0){
            return;
        }else {

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThemeShareActivity.class);
                    intent.putExtra("position",storyListBean.getData().getList().get(position).getStory_id());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return storyListBean.getData().getList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView goodsPic;
        private TextView brandTv;
        private RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsPic = (ImageView) itemView.findViewById(R.id.all_type_share_iv);
            brandTv = (TextView) itemView.findViewById(R.id.all_type_share_brand);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.theme_share_relativeLayout);
        }
    }


}
