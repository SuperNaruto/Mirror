package com.example.vdllo.mirror.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import com.example.vdllo.mirror.bean.MenuListBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.net.NetHelper;
import com.example.vdllo.mirror.net.NetListener;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/5.
 */
public class CatalogFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private RelativeLayout relativeLayout;
    private ListView listView;
    private CatalogAdapter catalogAdapter;
    private TextView exitTv,shareTv;
    // 当前Fragment位置
    private MenuListBean menuListBean;
    private MainActivity mainActivity;
    private
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Gson gson = new Gson();
            menuListBean = gson.fromJson(msg.obj.toString(), MenuListBean.class);
            catalogAdapter = new CatalogAdapter(menuListBean, getContext());
            listView.setAdapter(catalogAdapter);
            return false;
        }
    });


    @Override
    public int getLayout() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initView() {
        mainActivity = (MainActivity)getContext();
        relativeLayout = bindView(R.id.catalog_relativelayout);
        listView = bindView(R.id.catalog_listview);
        exitTv = bindView(R.id.pop_exit_textView);
        shareTv = bindView(R.id.pop_share_textview);
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.jumpViewPager(4);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.disappearMenu();
            }
        });

        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.MENU_LIST)
                .build().execute(new Callback() {
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


    @Override
    protected void initData() {
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(0);
        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }
    // 设置适配器
    public void setupAdapter(int position) {
        catalogAdapter.setLine(position);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mainActivity.jumpViewPager(position);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.CatalogFragment_exit);
        //积极响应
        builder.setPositiveButton(R.string.CatalogFragment_makesure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getActivity().getSharedPreferences(getContext().getString(R.string.CatalogFragment_Mirror), getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(getContext().getString(R.string.CatalogFragment_ifLogin), false);
                editor.commit();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        //消极响应
        builder.setNegativeButton(R.string.CatalogFragment_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();//显示
    }

}
