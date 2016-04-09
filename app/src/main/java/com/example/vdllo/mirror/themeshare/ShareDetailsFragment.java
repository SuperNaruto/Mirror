package com.example.vdllo.mirror.themeshare;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.StoryInfoBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.home.AllTypeAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/8.
 */
public class ShareDetailsFragment extends BaseFragment {
    private TextView smallTitleTv, titleTv, subTitleTv;
    private Handler handler;
    private StoryInfoBean datas;
    private int i;

    public ShareDetailsFragment(int i) {
        this.i = i;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_share_details;
    }

    @Override
    protected void initView() {
        smallTitleTv = bindView(R.id.share_details_smalltitle_tv);
        titleTv = bindView(R.id.share_details_title_tv);
        subTitleTv = bindView(R.id.share_details_subtitle_tv);

    }

    @Override
    protected void initData() {

        Intent intent = getActivity().getIntent();
        String s = intent.getStringExtra("position");
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //1.Gson解析
                Gson gson = new Gson();
                datas = gson.fromJson(msg.obj.toString(), StoryInfoBean.class);

                smallTitleTv.setText(datas.getData().getStory_data().getText_array().get(i).getSmallTitle());
                titleTv.setText(datas.getData().getStory_data().getText_array().get(i).getTitle());
                subTitleTv.setText(datas.getData().getStory_data().getText_array().get(i).getSubTitle());


                return false;
            }
        });
        OkHttpUtils.post().url(UrlBean.STORY_INFO).
                addParams("token", "").
                addParams("device_type", "2").
                addParams("story_id", s).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {

                //子线程无法刷新UI,利用handler发送Message到主线程
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


    }
}
