package com.example.vdllo.mirror.home;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

/**
 * …
 * Created by dllo on 16/3/30.
 */
public class AllTypeAdapter extends RecyclerView.Adapter<AllTypeAdapter.ViewHolder> {

    GoodsListBean datas;
    Context context;
    int pos;

    public AllTypeAdapter(GoodsListBean datas, Context context, int pos) {
        this.pos = pos;
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (pos == 0) {
            GoodsListBean.DataEntity.ListEntity data = datas.getData().getList().get(pos);
            //Picasso加载图片
            Picasso.with(context).load(data.getGoods_img()).into(holder.goodsPic);
            holder.goodsNameTv.setText(data.getGoods_name());
            holder.goodsPriceTv.setText(data.getGoods_price());
            holder.brandTv.setText(data.getBrand());
            holder.productAreaTv.setText(data.getProduct_area());
        } else if (pos == 1) {
            GoodsListBean.DataEntity.ListEntity data = datas.getData().getList().get(pos);
            //Picasso加载图片
            Picasso.with(context).load(data.getGoods_img()).into(holder.goodsPic);
            holder.goodsNameTv.setText(data.getGoods_name());
            holder.goodsPriceTv.setText(data.getGoods_price());
            holder.brandTv.setText(data.getBrand());
            holder.productAreaTv.setText(data.getProduct_area());
        } else if (pos == 2) {
            GoodsListBean.DataEntity.ListEntity data = datas.getData().getList().get(pos);
            //Picasso加载图片
            Picasso.with(context).load(data.getGoods_img()).into(holder.goodsPic);
            holder.goodsNameTv.setText(data.getGoods_name());
            holder.goodsPriceTv.setText(data.getGoods_price());
            holder.brandTv.setText(data.getBrand());
            holder.productAreaTv.setText(data.getProduct_area());
        }


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView goodsPic;
        private TextView goodsNameTv, goodsPriceTv, productAreaTv, brandTv;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsPic = (ImageView) itemView.findViewById(R.id.all_type_iv);
            goodsNameTv = (TextView) itemView.findViewById(R.id.all_type_goods_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.all_type_goods_price);
            productAreaTv = (TextView) itemView.findViewById(R.id.all_type_product_area);
            brandTv = (TextView) itemView.findViewById(R.id.all_type_brand);
        }
    }

}
