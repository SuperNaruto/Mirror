package com.example.vdllo.mirror.details;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.squareup.picasso.Picasso;

/**
 * Created by dllo on 16/4/8.
 */
public class WearAtlasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private GoodsListBean goodsListBean;
    private int pos;
    private Context context;


    public WearAtlasAdapter(GoodsListBean goodsListBean, int pos, Context context) {
        this.goodsListBean = goodsListBean;
        this.pos = pos;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            return new PicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wear_atlas_item, parent, false));
        } else {
            return new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wear_atlas_head_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            HeadViewHolder headViewHolder = (HeadViewHolder) holder;
            headViewHolder.videoView.setVideoURI(Uri.parse(goodsListBean.getData().getList().get(pos).getWear_video().get(position).getData()));
            MediaController mediaController = new MediaController(context);
            headViewHolder.videoView.setMediaController(mediaController);
            headViewHolder.videoView.requestFocus();
        } else {
            PicViewHolder picViewHolder = (PicViewHolder) holder;
            Picasso.with(context).load(goodsListBean.getData().getList().get(pos).getWear_video().get(position + 1).getData()).into(picViewHolder.imageView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodsListBean.getData().getList().get(pos).getWear_video().size() - 1;
    }

    //头布局缓存类
    class HeadViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;


        public HeadViewHolder(View itemView) {
            super(itemView);
            videoView = (VideoView) itemView.findViewById(R.id.wear_atlas_video);

        }
    }

    //图片行布局缓存类
    class PicViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private ImageView headIv;

        public PicViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.wear_atlas_iv);
            headIv = (ImageView) itemView.findViewById(R.id.wear_atlas_headIv);
        }
    }

}
