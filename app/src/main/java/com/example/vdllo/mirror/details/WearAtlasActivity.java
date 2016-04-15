package com.example.vdllo.mirror.details;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.base.BaseApplication;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.toolclass.jcvideoplayer_lib.JCVideoPlayer;
import com.squareup.picasso.Picasso;

public class WearAtlasActivity extends BaseAcitvity {
    private ListView listView;
    private WearAtlasAdapter wearAtlasAdapter;
    private static GoodsListBean datas;
    private static int pos;
    private Handler handler;
    private JCVideoPlayer jcVideoPlayer;
    private View headView;

    @Override
    protected int setContent() {
        return R.layout.activity_wearatlas;
    }

    public static void setData(GoodsListBean datas, int pos) {
        WearAtlasActivity.datas = datas;
        WearAtlasActivity.pos = pos;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //解除所有视频
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.activity_wear_atlas_listView);
        headView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.wear_atlas_head_item, null);
        jcVideoPlayer = (JCVideoPlayer) headView.findViewById(R.id.wear_atlas_video);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < datas.getData().getList().get(pos).getWear_video().size(); i++) {
            if (datas.getData().getList().get(pos).getWear_video().get(i).getType().equals("8")) {
                jcVideoPlayer.setUp(datas.getData().getList().get(pos).getWear_video().get(i).getData(), null);
            } else if (datas.getData().getList().get(pos).getWear_video().get(i).getType().equals("9")) {
                Picasso.with(WearAtlasActivity.this).load(datas.getData().getList().get(pos).getWear_video().get(i).getData()).into(jcVideoPlayer.ivThumb);
            }
        }

        wearAtlasAdapter = new WearAtlasAdapter(datas, pos);
        listView.setAdapter(wearAtlasAdapter);
        listView.setDividerHeight(0);
        listView.addHeaderView(headView);

    }


    //wearAtlasAdapter
    public class WearAtlasAdapter extends BaseAdapter {
        private GoodsListBean bean;
        private int pos;

        public WearAtlasAdapter(GoodsListBean bean, int pos) {
            this.bean = bean;
            this.pos = pos;

        }

        @Override
        public int getCount() {
            return bean != null && bean.getData().getList().get(pos).getWear_video().size() > 0 ? bean.getData().getList().get(pos).getWear_video().size() - 2 : 0;
        }

        @Override
        public Object getItem(int position) {
            return bean.getData().getList().get(pos).getWear_video().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wear_atlas_item, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.wear_atlas_iv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Picasso.with(parent.getContext()).load(bean.getData().getList().get(pos).getWear_video().get(position + 2).getData()).into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WearAtlasActivity.this, WearAtlasDetailsActivity.class);
                    int[] location = new int[2];
                    //获取图片在屏幕上坐标和宽高
                    holder.imageView.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);
                    intent.putExtra("locationY", location[1]);
                    intent.putExtra("width", holder.imageView.getWidth());
                    intent.putExtra("height", holder.imageView.getHeight());
                    intent.putExtra("position", position + 2);
                    intent.putExtra("pos", pos);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
            if (datas.getData().getList().get(pos).getWear_video().get(position).getData() != null) {
                return convertView;
            }

            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
        }
    }


}
