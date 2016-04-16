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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    private ImageView buyIv, returnIv;

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
        returnIv = bindView(R.id.activity_details_return);
        returnIv.setOnClickListener(this);
        buyIv.setOnClickListener(this);
        wearAtlasBtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        listView.setAdapter(new UpListViewAdapter(), new DownListViewAdapter());
        listView.setLinkageSpeed(1.2f);
        background.setImageURI(Uri.parse(data.getData().getList().get(pos).getGoods_img()));
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
                Intent bIntent = new Intent(GoodsDetailsActivity.this, OrderDetailsActivity.class);
                bIntent.putExtra("name",data.getData().getList().get(pos).getGoods_name());
                bIntent.putExtra("pic",data.getData().getList().get(pos).getGoods_pic());
                bIntent.putExtra("content",data.getData().getList().get(pos).getBrand());
                bIntent.putExtra("price",data.getData().getList().get(pos).getGoods_price());
                startActivity(bIntent);
                break;
            case R.id.activity_details_return:
                finish();
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
                    listViewHeadHolder.shareIv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String s = data.getData().getList().get(pos).getGoods_share();
                            Log.d("我来看看","哈哈哈哈" + s);
                            ShareSDK.initSDK(GoodsDetailsActivity.this);
                            OnekeyShare oks = new OnekeyShare();
                            //关闭sso授权
                            oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                            oks.setTitle(getString(R.string.app_name));
                            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                            oks.setTitleUrl("哈哈哈");
                            // text是分享文本，所有平台都需要这个字段
                            oks.setText(s);
                            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                            // url仅在微信（包括好友和朋友圈）中使用
                            oks.setUrl("哈哈哈");
                            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                            oks.setComment("我是测试评论文本");
                            // site是分享此内容的网站名称，仅在QQ空间使用
                            oks.setSite(getString(R.string.app_name));
                            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                            oks.setSiteUrl("哈哈哈");

// 启动分享GUI
                            oks.show(GoodsDetailsActivity.this);
                        }
                    });
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
                            Picasso.with(parent.getContext()).cancelRequest(listViewDetailHolder.background);
                            Picasso.with(GoodsDetailsActivity.this).load(url).resize(600,600).into(listViewDetailHolder.background);
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
            private ImageView shareIv;

            public ListViewHeadHolder(View view) {
                detailBrand = (TextView) view.findViewById(R.id.detail_head_brand);
                detailTitle = (TextView) view.findViewById(R.id.detail_head_title);
                detailContext = (TextView) view.findViewById(R.id.detail_head_context);
                detailPrice = (TextView) view.findViewById(R.id.detail_head_price);
                shareIv = (ImageView) view.findViewById(R.id.details_head_share_iv);
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
