package com.example.vdllo.mirror.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.shoppingcart.OrderDetailsActivity;
import com.example.vdllo.mirror.toolclass.LinkageListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import static android.widget.AbsListView.*;

/**
 * Created by Bo on 16/4/8.
 */
public class GoodsDetailsActivity extends BaseAcitvity implements View.OnClickListener {

    private LinkageListView listView;
    private static GoodsListBean data;
    private Handler handler;
    private static int pos;
    private SimpleDraweeView background;
    private Button backBtn, wearAtlasBtn, buyBtn;
    private boolean btnNotShow = true;
    private boolean locationNotFinshed = true;
    private int screenWidth;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private RelativeLayout showBtnLayout;
    private ImageView buyIv;

    public static void setData(GoodsListBean data, int pos) {
        GoodsDetailsActivity.data = data;
        GoodsDetailsActivity.pos = pos;
    }

    @Override
    protected int setContent() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.detail_listView);
        background = bindView(R.id.goodsDetail_background);
        showBtnLayout = bindView(R.id.details_relativeLayout);
        background = (SimpleDraweeView) findViewById(R.id.goodsDetail_background);
        wearAtlasBtn = (Button) findViewById(R.id.activity_details_wearAtlas_btn);
        buyIv = bindView(R.id.details_buy_iv);
        buyIv.setOnClickListener(this);
        wearAtlasBtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        listView.setAdapter(new UpListViewAdapter(), new DownListViewAdapter());
        listView.setLinkageSpeed(1.2f);
        background.setImageURI(Uri.parse(data.getData().getList().get(pos).getGoods_img()));
//        ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
//        animator.setDuration(1);
//        animator.start();
//        animation = ObjectAnimator.ofFloat(showBtnLayout, "translationX", 500);
//        animation.setDuration(1000);
//        animationBack = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
//        animationBack.setDuration(1000);
//        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -1000);
//        animator.setDuration(1);
//        animator.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_details_wearAtlas_btn:
                WearAtlasActivity.setData(data, pos);
                Intent intent = new Intent(GoodsDetailsActivity.this, WearAtlasActivity.class);
                startActivity(intent);
                break;
            case R.id.details_buy_iv:
                WearAtlasActivity.setData(data, pos);
                Intent bIntent = new Intent(GoodsDetailsActivity.this, OrderDetailsActivity.class);
                startActivity(bIntent);
                break;

        }
    }


    public class DownListViewAdapter extends BaseAdapter {

        final int TYPE_1 = 1;//blank
        final int TYPE_2 = 2;//文字
        final int TYPE_3 = 3;//站位

        @Override
        public int getCount() {
            return data.getData().getList().get(pos).getDesign_des().size() + 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 2) {
                return TYPE_3;
            } else if (position > 2 && position < data.getData().getList().get(pos).getGoods_data().size() + 3) {
                return TYPE_2;
            } else {
                return TYPE_1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewBlankHolder listViewBlankHolder = null;
            ListViewDeatilHolder listViewDeatilHolder = null;
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_1:
                    //空布局
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.down_listview_blank, parent, false);
                    break;
                case TYPE_2:
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.down_listview, parent, false);
                    listViewDeatilHolder = new ListViewDeatilHolder(convertView);
                    //-2因为前连个item被占了
                    try {
                        String s = data.getData().getList().get(pos).getGoods_data().get(position - 3).getCountry();
                        if (s.equals("")) {
                            listViewDeatilHolder.goodsCountry.setText(data.getData().getList().get(pos).getGoods_data().get(position - 3).getName());

                        } else {
                            listViewDeatilHolder.goodsCountry.setText(data.getData().getList().get(pos).getGoods_data().get(position - 3).getCountry());

                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    try {
                        listViewDeatilHolder.goodsContext.setText(data.getData().getList().get(pos).getGoods_data().get(position - 3).getIntroContent());
                        listViewDeatilHolder.goodsLoaction.setText(data.getData().getList().get(pos).getGoods_data().get(position - 3).getLocation());
                        listViewDeatilHolder.goodsEnLocation.setText(data.getData().getList().get(pos).getGoods_data().get(position - 3).getEnglish());
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    break;
                case TYPE_3:
                    //第三个item 站位的
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.down_listview_blanktwo, parent, false);
            }
            return convertView;
        }

        //缓存类
        public class ListViewBlankHolder {
        }

        public class ListViewDeatilHolder {

            private TextView goodsLoaction, goodsEnLocation, goodsCountry, goodsContext;

            public ListViewDeatilHolder(View view) {
                goodsLoaction = (TextView) view.findViewById(R.id.down_location);
                goodsEnLocation = (TextView) view.findViewById(R.id.down_en_location);
                goodsCountry = (TextView) view.findViewById(R.id.down_country);
                goodsContext = (TextView) view.findViewById(R.id.down_context);
            }
        }
    }

    public class UpListViewAdapter extends BaseAdapter {

        final int TYPE_0 = 0;
        final int TYPE_1 = 1;
        final int TYPE_4 = 2;
        final int TYPE_3 = 3;

        @Override
        public int getCount() {
            return data.getData().getList().get(pos).getDesign_des().size() + 3;
        }

        @Override
        public Object getItem(int position) {
            return data.getData().getList().get(pos).getDesign_des().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_1;
            } else if (position == 1) {
                return TYPE_0;
            } else if (position == 2) {
                return TYPE_3;
            } else {
                return TYPE_4;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListViewDetailHolder listViewDetailHolder = null;
            ListViewHeadHolder listViewHeadHolder = null;
            ListViewTitle listViewTitle = null;
            int type = getItemViewType(position);
            switch (type) {
                case TYPE_0:
                    //blank
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.up_listview_blank, parent, false);
                    break;
                //半透明文字
                case TYPE_1:
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.details_head_item, parent, false);
                    listViewHeadHolder = new ListViewHeadHolder(convertView);
                    listViewHeadHolder.detailContext.setText(data.getData().getList().get(pos).getInfo_des());
                    listViewHeadHolder.detailPrice.setText(data.getData().getList().get(pos).getGoods_price());
                    listViewHeadHolder.detailTitle.setText(data.getData().getList().get(pos).getBrand());
                    listViewHeadHolder.detailBrand.setText(data.getData().getList().get(pos).getGoods_name());
                    break;
                case TYPE_3:
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.details_line_item, parent, false);
                    listViewTitle = new ListViewTitle(convertView);
                    listViewTitle.title.setText(data.getData().getList().get(pos).getBrand());
                    break;
                case TYPE_4:
                    //第二部分图片
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.item_all_uplistview, parent, false);
                    listViewDetailHolder = new ListViewDetailHolder(convertView);
                    //listViewDetailHolder.background.setImageURI(Uri.parse(allGoodsListData.getData().getList().get(myPosition).getDesign_des().get(pos - 3).getImg()));
                    try {
                        String url = data.getData().getList().get(pos).getDesign_des().get(position - 3).getImg();
                        if (url != null) {
                            //Picasso加载图片
                            Picasso.with(GoodsDetailsActivity.this).load(url).into(listViewDetailHolder.background);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return convertView;
        }

        public class ListViewHeadHolder {
            private TextView detailBrand, detailTitle, detailContext, detailPrice;

            public ListViewHeadHolder(View view) {
                detailBrand = (TextView) view.findViewById(R.id.detail_head_brand);
                detailTitle = (TextView) view.findViewById(R.id.detail_head_title);
                detailContext = (TextView) view.findViewById(R.id.detail_head_context);
                detailPrice = (TextView) view.findViewById(R.id.detail_head_price);
            }
        }

        public class ListViewDetailHolder {
            private ImageView background;

            public ListViewDetailHolder(View view) {
                background = (ImageView) view.findViewById(R.id.goodsdetail_up_background);
            }
        }

        public class ListViewTitle {
            private TextView title;

            public ListViewTitle(View view) {
                title = (TextView) view.findViewById(R.id.details_line_tv);
            }
        }
    }
//
//    private void visibleLayout() {
//        if (btnNotShow && title.getY() <= 0) {
//            visibleLayout();
//            btnNotShow = false;
//        }
//        if (title.getY() > 0 && !btnNotShow) {
//            goneLayout();
//            btnNotShow = true;
//        }
//        if (locationNotFinshed) {
//            animation = ObjectAnimator.ofFloat(showBtnLayout, "translationX", (screenWidth - showBtnLayout.getWidth()) / 2);
//            animation.setDuration(500);
//            locationNotFinshed = false;
//        }
//        animation.start();
//    }
//
//    private void goneLayout() {
//        animationBack = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -1000);
//        animationBack.setDuration(500);
//        animationBack.start();
//        new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                return false;
//            }
//        }).sendEmptyMessageDelayed(50, 1000);
//
//    }


}
