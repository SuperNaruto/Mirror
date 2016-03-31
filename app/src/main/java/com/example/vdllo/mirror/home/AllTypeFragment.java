package com.example.vdllo.mirror.home;

import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment {

    private AllTypeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Integer> datas;
    private LinearLayout linearLayout;
    private ShowMenu showMenu;
    private ArrayList<String> data;
    private int i;
    private Handler handler;
    private GoodsListBean goodsListBean;

    public AllTypeFragment(int i) {
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
        showMenu = new ShowMenu(getContext());
        data = new ArrayList<>();
        data.add("浏览所有分类");
        data.add("浏览平光眼镜");
        data.add("浏览太阳眼镜");
        data.add("专题分享");
        data.add("购物车");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu.showPopupWindow(v, data, i);
            }
        });
    }

    @Override
    protected void dataView() {

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                goodsListBean = gson.fromJson(msg.obj.toString(), GoodsListBean.class);
                // 2.设置布局管理器
                manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                // 3.设置适配器
                adapter = new AllTypeAdapter(goodsListBean, getContext());
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        getInfo();
    }

    public void getInfo() {
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.GOODS_LIST).addParams("token", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "")
                .addParams("category_id", "")
                .addParams("version", "").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                handler.sendMessage(message);
                Log.d("aa", message.obj.toString());
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

