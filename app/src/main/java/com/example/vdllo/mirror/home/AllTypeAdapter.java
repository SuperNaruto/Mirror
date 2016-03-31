package com.example.vdllo.mirror.home;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * …
 * Created by dllo on 16/3/30.
 */
public class AllTypeAdapter extends RecyclerView.Adapter<AllTypeAdapter.ViewHolder> {

    GoodsListBean datas;

    public AllTypeAdapter(GoodsListBean datas) {
        this.datas = datas;
    }

    public void addData(GoodsListBean datas) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item, null);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsListBean.DataEntity.ListEntity data = datas.getData().getList().get(position);
        holder.goodsPic.setImageURI(Uri.parse(data.getGoods_img()));
        holder.goodsNameTv.setText(data.getGoods_name());
        holder.goodsPriceTv.setText(data.getGoods_price());
        holder.brandTv.setText(data.getBrand());
        holder.productAreaTv.setText(data.getProduct_area());

    }

    @Override
    public int getItemCount() {
        return datas.getData().getList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView goodsPic;
        private TextView goodsNameTv, goodsPriceTv, productAreaTv, brandTv;

        public ViewHolder(View itemView) {
            super(itemView);
            goodsPic = (SimpleDraweeView) itemView.findViewById(R.id.all_type_iv);
            goodsNameTv = (TextView) itemView.findViewById(R.id.all_type_goods_name);
            goodsPriceTv = (TextView) itemView.findViewById(R.id.all_type_goods_price);
            productAreaTv = (TextView) itemView.findViewById(R.id.all_type_product_area);
            brandTv = (TextView) itemView.findViewById(R.id.all_type_brand);
        }
    }

}
