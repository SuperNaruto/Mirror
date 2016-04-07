package com.example.vdllo.mirror.home;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.GoodsListBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment {

    private LinearLayoutManager manager;
    private GoodsListBean goodsListBean;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private AllTypeAdapter adapter;
    private ArrayList<String> data;
    private Handler handler;
    private int i;
    private TextView titleTextView;

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
        linearLayout = bindView(R.id.all_type_linearlayout);
        titleTextView = bindView(R.id.all_type_titleTv);
    }


    @Override
    protected void initData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                //1.Gson解析
                Gson gson = new Gson();
                goodsListBean = gson.fromJson(msg.obj.toString(), GoodsListBean.class);
                // 2.设置布局管理器
                manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                // 3.设置适配器
                adapter = new AllTypeAdapter(goodsListBean, getContext(), i);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });

        //商品列表
        NetHelper netHelper = new NetHelper();
        netHelper.getGoods(handler);

        //menu
        data = new ArrayList<>();
        data.add("浏览所有分类");
        data.add("浏览太阳眼镜");
        data.add("浏览平光眼镜");
        data.add("专题分享");
        data.add("购物车");

        titleTextView.setText(data.get(i));

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.main_linearlayout, new CatalogFragment(getActivity(), data, i));
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }


}

