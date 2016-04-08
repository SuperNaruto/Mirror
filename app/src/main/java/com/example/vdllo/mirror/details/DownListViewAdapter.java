package com.example.vdllo.mirror.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.GoodsListBean;

/**
 * Created by Bo on 16/4/8.
 */
public class DownListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int myPosition;
    final int TYPE_1 = 1;//blank
    final int TYPE_2 = 2;//文字
    final int TYPE_3 = 3;//站位
    private GoodsListBean allGoodsListData;

    public DownListViewAdapter(GoodsListBean allGoodsListData, Context context, int position) {
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
        } else if (position > 2 && position < allGoodsListData.getData().getList().get(myPosition).getDesign_des().size() + 2) {
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
        ListViewHeadHolder listViewHeadHolder = null;
        ListViewBlankHolder listViewBlankHolder = null;
        ListViewDeatilHolder listViewDeatilHolder = null;
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_1:
//空布局
                convertView = inflater.inflate(R.layout.down_listview_blank, parent, false);
                break;
            case TYPE_2:
                convertView = inflater.inflate(R.layout.down_listview, parent, false);
                listViewDeatilHolder = new ListViewDeatilHolder(convertView);
                //-2因为前连个item被占了
                try {
                    String s = allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getCountry();
                    if (s.equals("")) {
                        listViewDeatilHolder.goodsCountry.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getName());

                    } else {
                        listViewDeatilHolder.goodsCountry.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getCountry());

                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

                try {
                    listViewDeatilHolder.goodsContext.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getIntroContent());
                    listViewDeatilHolder.goodsLoaction.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getLocation());
                    listViewDeatilHolder.goodsEnLocation.setText(allGoodsListData.getData().getList().get(myPosition).getGoods_data().get(position - 3).getEnglish());
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_3:
                //第三个item 站位的
                convertView = inflater.inflate(R.layout.down_listview_blanktwo, parent, false);

        }


        return convertView;
    }
    //缓存类

    public class ListViewHeadHolder {
        private TextView detailBrand, detailTitle, detailContext, detailPrice;

        public ListViewHeadHolder(View view) {
//            detailBrand = (TextView) view.findViewById(R.id.detail_head_brand);
//            detailTitle = (TextView) view.findViewById(R.id.detail_head_title);
//            detailContext = (TextView) view.findViewById(R.id.detail_head_context);
//            detailPrice = (TextView) view.findViewById(R.id.detail_head_price);
        }

    }

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
