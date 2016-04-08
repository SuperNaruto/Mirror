package com.example.vdllo.mirror.details;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.home.BackGroundFragment;
import com.example.vdllo.mirror.net.NetHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by dllo on 16/4/6.
 */
public class DetailsActivity extends BaseAcitvity {

    private RecyclerView recyclerView;
    private static GoodsListBean.DataEntity.ListEntity data;
    private RelativeLayout showBtnLayout;
    private boolean btnNotShow = true;
    private ObjectAnimator animation;
    private ObjectAnimator animationBack;
    private NetHelper netHelper;

    public static void setData(GoodsListBean.DataEntity.ListEntity data) {
        DetailsActivity.data = data;
        Log.d("setData: ", data.getGoods_img());
    }

    @Override
    protected int setContent() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.details_recyclerView);
        showBtnLayout = bindView(R.id.details_relativeLayout);
        ObjectAnimator animator = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
        animator.setDuration(1);
        animator.start();
        animation = ObjectAnimator.ofFloat(showBtnLayout, "translationX", 500);
        animation.setDuration(1000);
        animationBack = ObjectAnimator.ofFloat(showBtnLayout, "translationX", -500);
        animationBack.setDuration(1000);
    }

    @Override
    protected void initData() {
        final NetHelper helper = new NetHelper(this);
        helper.setBackGround(bindView(R.id.activity_details_layout), data.getGoods_img());
        recyclerView.setAdapter(new DetailsAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
    }

    private void visibleLayout() {
        animation.start();
    }

    private void goneLayout() {
        animationBack.start();
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        }).sendEmptyMessageDelayed(50, 1000);
    }

    private class DetailsAdapter extends RecyclerViewAdapter {

        protected static final int HEAD_MODE = 1;
        protected static final int CONTENT_MODE = 2;
        protected static final int LAST_SECOND_MODE = 3;
        protected static final int LAST_MODE = 4;
        protected static final int HEAD_SECOND_MODE = 5;
        protected static final int EMPTY_MODE = 6;

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEAD_MODE;
            }
            if (position == 1) {
                return EMPTY_MODE;
            }
            if (position == 2) {
                return HEAD_SECOND_MODE;
            }
            else {
                return CONTENT_MODE;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == CONTENT_MODE) {
                return new BrowseGlassesHolder(createItemLayout(R.layout.details_type_one_item, parent));
            }
            if (viewType == HEAD_MODE) {
                return new HeadItemHolder(createItemLayout(R.layout.details_head_item, parent));
            }
            if (viewType == EMPTY_MODE) {
                return new EmptyHolder(createItemLayout(R.layout.details_empty_item, parent));
            }
            else {
                return new HeadSecondItemHolder(createItemLayout(R.layout.details_line_item, parent));
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == CONTENT_MODE) {
                ((BrowseGlassesHolder) holder).linearLayout.setVisibility(View.VISIBLE);
                ((BrowseGlassesHolder) holder).contentPosition = position;
                ((BrowseGlassesHolder) holder).contentTv.setText(data.getGoods_data().get(position - 3).getIntroContent());
                ((BrowseGlassesHolder) holder).titleTv.setText(data.getGoods_data().get(position - 3).getLocation());
                if (data.getGoods_data().get(position - 3).getLocation().equals("")) {
                    ((BrowseGlassesHolder) holder).addressTv.setText(data.getGoods_data().get(position - 3).getName());
                }
                netHelper.setImage(((BrowseGlassesHolder) holder).backImg, data.getDesign_des().get(position - 3).getImg());

                ((BrowseGlassesHolder) holder).lTitleTv.setText(data.getGoods_data().get(position - 3).getEnglish());

            } else {
//                ((BrowseGlassesHolder) holder).linearLayout.setVisibility(View.INVISIBLE);
                netHelper.setImage(((BrowseGlassesHolder) holder).backImg,data.getDesign_des().get(position - 3).getImg());

            }
            if (getItemViewType(position) == HEAD_MODE) {
                ((HeadItemHolder) holder).eTitleTv.setText(data.getGoods_name());
                ((HeadItemHolder) holder).contentTv.setText(data.getInfo_des());
                ((HeadItemHolder) holder).priceTv.setText(data.getGoods_price());
                ((HeadItemHolder) holder).typeTitleTv.setText(data.getModel());
            }
            if (getItemViewType(position) == HEAD_SECOND_MODE) {
                ((HeadSecondItemHolder) holder).titleTv.setText(data.getBrand());
            }
        }

        @Override
        public int getItemCount() {
            return data.getGoods_data().size() + 3;
        }

        class BrowseGlassesHolder extends BaseHolder {

            private RelativeLayout relativeLayout;
            private LinearLayout linearLayout;
            private int contentPosition;
            private TextView titleTv, lTitleTv, addressTv, contentTv;
            private ImageView backImg;

            public BrowseGlassesHolder(final View itemView) {
                super(itemView);
                titleTv = (TextView) itemView.findViewById(R.id.details_title);
                lTitleTv = (TextView) itemView.findViewById(R.id.details_little_title);
                addressTv = (TextView) itemView.findViewById(R.id.details_address);
                contentTv = (TextView) itemView.findViewById(R.id.details_content);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.details_type_one_linearLayout);
                relativeLayout = (RelativeLayout) itemView.findViewById(R.id.details_type_one_relativeLayout);
                backImg = (ImageView) itemView.findViewById(R.id.details_type_one_iv);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                    }

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        //实现滑动的核心代码
                        if (DetailsAdapter.this.getItemViewType(contentPosition) == CONTENT_MODE) {
                            relativeLayout.scrollTo((int) itemView.getX(), -((int) itemView.getY() / 10 - 50));
                        }
                    }
                });
            }
            @Override
            protected void initView() {

            }
        }

        class HeadItemHolder extends RecyclerView.ViewHolder {

            private TextView eTitleTv, typeTitleTv, contentTv, priceTv;

            public HeadItemHolder(final View itemView) {
                super(itemView);
                eTitleTv = (TextView) itemView.findViewById(R.id.details_head_item_title);
                typeTitleTv = (TextView) itemView.findViewById(R.id.details_head_item_little_title);
                contentTv = (TextView) itemView.findViewById(R.id.detail_head_context);
                priceTv = (TextView) itemView.findViewById(R.id.detail_head_price);
            }
        }

        class HeadSecondItemHolder extends RecyclerView.ViewHolder {
            private TextView titleTv;
            private ImageView line;
            public HeadSecondItemHolder(final View itemView) {
                super(itemView);
                line = (ImageView) itemView.findViewById(R.id.details_line_iv);
                //按钮动画的方法
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        Log.i("line",line.getY()+"");
                        if (btnNotShow && itemView.getY()+220 <= 0) {
                            visibleLayout();
                            btnNotShow = false;

                        }
                        if (itemView.getY()+210 > 0 && !btnNotShow) {
                            goneLayout();
                            btnNotShow = true;
                        }
                    }
                });
                titleTv = (TextView) itemView.findViewById(R.id.details_line_tv);
            }
        }

        class EmptyHolder extends RecyclerView.ViewHolder {
            public EmptyHolder(View itemView) {
                super(itemView);
            }
        }

        /**
         * 最后一行的缓存类
         */
        class LastItemHolder extends RecyclerView.ViewHolder {
            public LastItemHolder(View itemView) {
                super(itemView);
            }
        }


    }
}
