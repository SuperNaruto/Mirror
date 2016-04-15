package com.example.vdllo.mirror.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.squareup.picasso.Picasso;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Bo on 16/4/8.
 */
public class UpListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private GoodsListBean allGoodsListData;
    private Context context;
    private int myPosition;
    final int TYPE_0 = 0;
    final int TYPE_1 = 1;
    final int TYPE_3 = 3;
    final int TYPE_4 = 4;



    public UpListViewAdapter(GoodsListBean allGoodsListData, Context context, int position) {
        this.allGoodsListData = allGoodsListData;
        this.context = context;
        this.myPosition = position;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return allGoodsListData.getData().getList().get(myPosition).getDesign_des().size() + 3;
    }

    @Override
    public Object getItem(int position) {
        return allGoodsListData.getData().getList().get(myPosition).getDesign_des().get(position);
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
                convertView = inflater.inflate(R.layout.up_listview_blank, parent, false);
                break;
            //半透明文字
            case TYPE_1:
                convertView = inflater.inflate(R.layout.details_head_item, parent, false);
                listViewHeadHolder = new ListViewHeadHolder(convertView);
                listViewHeadHolder.detailContext.setText(allGoodsListData.getData().getList().get(myPosition).getInfo_des());
                listViewHeadHolder.detailPrice.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_price());
                listViewHeadHolder.detailTitle.setText(allGoodsListData.getData().getList().get(myPosition).getBrand());
                listViewHeadHolder.detailBrand.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_name());
                listViewHeadHolder.shareIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareSDK.initSDK(context);
                        OnekeyShare oks = new OnekeyShare();
                        //关闭sso授权
                        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                        oks.setTitle(context.getString(R.string.app_name));
                        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                        oks.setTitleUrl("http://sharesdk.cn");
                        // text是分享文本，所有平台都需要这个字段
                        oks.setText("我是分享文本");
                        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                        // url仅在微信（包括好友和朋友圈）中使用
                        oks.setUrl("http://sharesdk.cn");
                        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                        oks.setComment("我是测试评论文本");
                        // site是分享此内容的网站名称，仅在QQ空间使用
                        oks.setSite(context.getString(R.string.app_name));
                        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
                        oks.show(context);
                    }
                });
                break;
            case TYPE_3:
                convertView = inflater.inflate(R.layout.details_line_item, parent, false);
                listViewTitle = new ListViewTitle(convertView);
                listViewTitle.title.setText(allGoodsListData.getData().getList().get(myPosition).getBrand());
                break;
            case TYPE_4:
                //第二部分图片
                convertView = inflater.inflate(R.layout.item_all_uplistview, parent, false);
                listViewDetailHolder = new ListViewDetailHolder(convertView);
                //listViewDetailHolder.background.setImageURI(Uri.parse(allGoodsListData.getData().getList().get(myPosition).getDesign_des().get(position - 3).getImg()));
                try {
                    String url = allGoodsListData.getData().getList().get(myPosition).getDesign_des().get(position - 3).getImg();
                    if (url != null) {
                        //Picasso加载图片
                        Picasso.with(context).load(url).into(listViewDetailHolder.background);
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
