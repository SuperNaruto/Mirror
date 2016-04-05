package com.example.vdllo.mirror.themeshare;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.StoryListBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.home.AllTypeAdapter;
import com.example.vdllo.mirror.home.ShowPopMenu;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/30.
 */
public class ThemeShareFragment extends BaseFragment {

    private LinearLayoutManager manager;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private ThemeShareAdapter adapter;
    private ShowPopMenu showPopMenu;
    private ArrayList<String> data;
    private Handler handler;
    private int i;
    private StoryListBean storyListBean;

    public ThemeShareFragment(int i) {
        this.i = i;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
        linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
        showPopMenu = new ShowPopMenu(getContext());
        data = new ArrayList<>();
        data.add("浏览所有分类");
        data.add("浏览平光眼镜");
        data.add("浏览太阳眼镜");
        data.add("专题分享");
        data.add("购物车");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu.showPopupWindow(v, data, i);
            }
        });
    }

    @Override
    protected void dataView() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //1.Gson解析
                Gson gson = new Gson();
                storyListBean = gson.fromJson(msg.obj.toString(), StoryListBean.class);
                // 2.设置布局管理器
                manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                // 3.设置适配器
                adapter = new ThemeShareAdapter(getContext(), storyListBean);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
        //专题分享
        getShareInfo();
    }

    public void getShareInfo() {
        OkHttpUtils.post().url(UrlBean.STORY_LIST).addParams("token", "")
                .addParams("uid", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "").build().execute(new Callback() {
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

    }
}

