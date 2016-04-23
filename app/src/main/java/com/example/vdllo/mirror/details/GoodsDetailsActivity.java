package com.example.vdllo.mirror.details;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.base.BaseToast;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.OrderDetailsBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.example.vdllo.mirror.shoppingcart.OrderDetailsActivity;
import com.example.vdllo.mirror.toolclass.LinkageListView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

import static android.widget.AbsListView.*;

/**
 * Created by Bo on 16/4/8.
 */
public class GoodsDetailsActivity extends BaseAcitvity implements View.OnClickListener {
    private float radio;
    private float height;
    private LinkageListView listView;
    private  static GoodsListBean data;
    private Handler handler;
    private static int pos;
    private SimpleDraweeView background;
    private Button wearAtlasBtn;
    private boolean btnNotShow = true;
    private RelativeLayout showBtnLayout;
    private ImageView buyIv, returnIv;
    private OrderDetailsBean orderDetailsBean;
    private boolean ifLogin = true;

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
        EventBus.getDefault().register(this);
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
        ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, getString(R.string.GoodsDetails_translationX), -1500);
        animator.setDuration(1);
        animator.start();
    }

    @Override
    protected void initData() {
        listView.setAdapter(new UpListViewAdapter(), new DownListViewAdapter());
        listView.setLinkageSpeed(1.2f);
        background.setImageURI(Uri.parse(data.getData().getList().get(pos).getGoods_img()));
        Log.d("+++++","____"+data);
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
                SharedPreferences sp = getSharedPreferences(getString(R.string.GoodsDetails_Mirror), MODE_PRIVATE);
                String token = sp.getString(getString(R.string.Mirror_token), "");
                ifLogin = sp.getBoolean(getString(R.string.Login_ifLogin), false);
                if (ifLogin) {
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            Gson gson = new Gson();
                            orderDetailsBean = gson.fromJson(msg.obj.toString(), OrderDetailsBean.class);
                            //解析数据，通过Intent传入订单详情界面
                            OrderDetailsBean.DataEntity.GoodsEntity goodsEntity = orderDetailsBean.getData().getGoods();
                            OrderDetailsBean.DataEntity.AddressEntity addressEntity = orderDetailsBean.getData().getAddress();
                            OrderDetailsBean.DataEntity dataEntity = orderDetailsBean.getData();
                            Intent bIntent = new Intent(GoodsDetailsActivity.this, OrderDetailsActivity.class);
                            bIntent.putExtra(getString(R.string.GoodsDetails_name), goodsEntity.getGoods_name());
                            bIntent.putExtra(getString(R.string.GoodsDetails_pic), goodsEntity.getPic());
                            bIntent.putExtra(getString(R.string.GoodsDetails_content), goodsEntity.getDes());
                            bIntent.putExtra(getString(R.string.GoodsDetails_price), goodsEntity.getPrice());
                            bIntent.putExtra(getString(R.string.GoodsDetails_order_id), dataEntity.getOrder_id());
                            bIntent.putExtra(getString(R.string.GoodsDetails_addr_id), addressEntity.getAddr_id());
                            bIntent.putExtra(getString(R.string.GoodsDetails_id), data.getData().getList().get(pos).getGoods_id());
                            startActivity(bIntent);
                            return false;
                        }
                    });
                    //点击购买下订单
                    OkHttpUtils.post().url(UrlBean.ORDER_SUB).addParams(getString(R.string.Mirror_token), token)
                            .addParams(getString(R.string.GoodsDetails_goods_id), data.getData().getList().get(pos).getGoods_id())
                            .addParams(getString(R.string.GoodsDetails_goods_num), "1").addParams(getString(R.string.GoodsList_price), data.getData().getList().get(pos).getGoods_price())
                            .addParams(getString(R.string.GoodsDetails_discount_id), "").addParams(getString(R.string.GoodsDetails_device_type), "2")
                            .build().execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response) throws Exception {
                            String body = response.body().string();
                            Message message = new Message();
                            message.obj = body;
                            handler.sendMessage(message);
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e) {

                        }

                        @Override
                        public void onResponse(Object response) {

                        }
                    });
                } else {
                    BaseToast.myToast(getString(R.string.GoodsDetails_please_login));
                }


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
                    GoodsListBean.DataEntity.ListEntity.GoodsDataEntity goodsDataEntity = data.getData().getList().get(pos).getGoods_data().get(position - 3);
                    //-2因为前连个item被占了
                    try {
                        String country = goodsDataEntity.getCountry();
                        if (country.equals("")) {
                            listViewDeatilHolder.goodsCountry.setText(goodsDataEntity.getName());

                        } else {
                            listViewDeatilHolder.goodsCountry.setText(goodsDataEntity.getCountry());

                        }
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    try {
                        listViewDeatilHolder.goodsContext.setText(goodsDataEntity.getIntroContent());
                        listViewDeatilHolder.goodsLoaction.setText(goodsDataEntity.getLocation());
                        listViewDeatilHolder.goodsEnLocation.setText(goodsDataEntity.getEnglish());
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
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.up_listview_blank, parent, false);
                    break;
                //半透明文字
                case TYPE_1:
                    convertView = LayoutInflater.from(GoodsDetailsActivity.this).inflate(R.layout.details_head_item, parent, false);
                    listViewHeadHolder = new ListViewHeadHolder(convertView);
                    final GoodsListBean.DataEntity.ListEntity listEntity = data.getData().getList().get(pos);
                    listViewHeadHolder.detailContext.setText(listEntity.getInfo_des());
                    listViewHeadHolder.detailPrice.setText(listEntity.getGoods_price());
                    listViewHeadHolder.detailTitle.setText(listEntity.getBrand());
                    listViewHeadHolder.detailBrand.setText(listEntity.getGoods_name());
                    listViewHeadHolder.shareIv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String share = data.getData().getList().get(pos).getGoods_share();
                            ShareSDK.initSDK(GoodsDetailsActivity.this);
                            OnekeyShare oks = new OnekeyShare();
                            //关闭sso授权
                            oks.disableSSOWhenAuthorize();
                            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//                            oks.setImageUrl("");
                            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                            oks.setTitle(getString(R.string.app_name));
                            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                            oks.setTitleUrl(share);
                            // text是分享文本，所有平台都需要这个字段
                            oks.setText(data.getData().getList().get(pos).getBrand());
                            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                            // url仅在微信（包括好友和朋友圈）中使用
                            oks.setUrl(null);
                            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                            oks.setComment("");
                            // site是分享此内容的网站名称，仅在QQ空间使用
                            oks.setSite(getString(R.string.app_name));
                            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                            oks.setSiteUrl(null);
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
//                            netHelper.setImage(listViewDetailHolder.background, url);
                            Picasso.with(parent.getContext()).cancelRequest(listViewDetailHolder.background);
                            Picasso.with(GoodsDetailsActivity.this).load(url).resize(600, 600).into(listViewDetailHolder.background);
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
            private LinearLayout linearLayout;
            private boolean isFirst = true;
            private float height;

            public ListViewHeadHolder(final View view) {
                detailBrand = (TextView) view.findViewById(R.id.detail_head_brand);
                detailTitle = (TextView) view.findViewById(R.id.detail_head_title);
                detailContext = (TextView) view.findViewById(R.id.detail_head_context);
                detailPrice = (TextView) view.findViewById(R.id.detail_head_price);
                shareIv = (ImageView) view.findViewById(R.id.details_head_share_iv);
                linearLayout = (LinearLayout) view.findViewById(R.id.detail_head_linearLayout);
                listView.getBottomListView().setOnScrollChangeListener(new OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if (isFirst) {
                            height = view.getHeight();
                            if (height != 0) {
                                isFirst = false;
                            }
                        }
                        //改变透明度
                        linearLayout.setAlpha((float) ((1 / height) * view.getBottom()));
                    }
                });
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

            public ListViewTitle(final View view) {
                title = (TextView) view.findViewById(R.id.details_line_tv);
            }
        }
    }

    public void onEvent(Integer itemPosition) {
        if (itemPosition >= 1 && btnNotShow) {
            showBtnLayout.setVisibility(View.VISIBLE);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -800f, 0f);
            animator1.setDuration(500);
            animator1.start();
            btnNotShow = false;
        }
        if (itemPosition < 1 && !btnNotShow) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, "translationX", 0f, -800f);
            animator.setDuration(500);
            animator.start();
            new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    showBtnLayout.setVisibility(View.GONE);
                    return false;
                }
            }).sendEmptyMessageDelayed(99, 500);
            btnNotShow = true;
            //return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
