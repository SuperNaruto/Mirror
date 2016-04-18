package com.example.vdllo.mirror.shoppingcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseAcitvity;
import com.example.vdllo.mirror.bean.AddressBean;
import com.example.vdllo.mirror.bean.UrlBean;
import com.example.vdllo.mirror.toolclass.DensityUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/15.
 */
public class AddressActivity extends BaseAcitvity implements View.OnClickListener {
    private SwipeMenuListView myListView;
    private ImageView returnIv;
    private Button addAddressBtn;
    private AddressBean addressBean;
    private Handler listHandler, delHandler, mrHandler;
    private AddressActivityAdapter myAdapter;
    private int resultCode = 100;


    @Override
    protected int setContent() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        myListView = bindView(R.id.activity_address_listView);
        returnIv = bindView(R.id.activity_address_return);
        addAddressBtn = bindView(R.id.activity_address_btn);
        returnIv.setOnClickListener(this);
        addAddressBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        listHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                addressBean = gson.fromJson(msg.obj.toString(), AddressBean.class);
                myAdapter = new AddressActivityAdapter(AddressActivity.this, addressBean);
                myAdapter.notifyDataSetChanged();
                myListView.setAdapter(myAdapter);
                return false;
            }
        });
        //解析地址数据，传到适配器
        SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
        String token = sp.getString("token", "");
        OkHttpUtils.post().url(UrlBean.USER_ADDRESS_LIST).addParams("token", token)
                .addParams("device_type", "2").addParams("page", "").addParams("last_time", "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                listHandler.sendMessage(message);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });

        //SwipeMenuListView
        //1.create a SwipeMenuCreator to add items
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                //create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                //set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xE6,
                        0x28, 0x44)));
                //set item width
                deleteItem.setWidth(DensityUtils.dp2px(AddressActivity.this, 90));
                // set item title
                deleteItem.setTitle("删除");
                // set item title fontSize
                deleteItem.setTitleSize(13);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        myListView.setMenuCreator(creator);

        //2.deleteItem
        myListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        delHandler = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                myAdapter.setData(position);
                                myListView.setAdapter(myAdapter);
                                Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });

                        SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
                        String token = sp.getString("token", "");
                        OkHttpUtils.post().url(UrlBean.USER_DEL_ADDRESS).addParams("token", token)
                                .addParams("addr_id", addressBean.getData().getList().get(position).getAddr_id()).build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws Exception {
                                String body = response.body().string();
                                Message message = new Message();
                                message.obj = body;
                                delHandler.sendMessage(message);
                                return null;
                            }

                            @Override
                            public void onError(Call call, Exception e) {

                            }

                            @Override
                            public void onResponse(Object response) {

                            }
                        });

                        break;
                }
                return false;
            }
        });

        //listView行监听
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mrHandler = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        try {
                            JSONObject object = new JSONObject(msg.obj.toString());
                            String result = object.getString("result");
                            if (result.equals("1")) {
                                myAdapter.notifyDataSetChanged();
                                Toast.makeText(AddressActivity.this, "设置默认地址成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddressActivity.this, OrderDetailsActivity.class);
                                intent.putExtra("name", addressBean.getData().getList().get(position).getUsername());
                                intent.putExtra("tel", addressBean.getData().getList().get(position).getCellphone());
                                intent.putExtra("info", addressBean.getData().getList().get(position).getAddr_info());
                                setResult(resultCode, intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });

                SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
                String token = sp.getString("token", "");
                OkHttpUtils.post().url(UrlBean.USER_MR_ADDRESS).addParams("token", token).addParams("addr_id", addressBean.getData().getList().get(position).getAddr_id())
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        String body = response.body().string();
                        Message message = new Message();
                        message.obj = body;
                        mrHandler.sendMessage(message);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        Toast.makeText(AddressActivity.this, "设置失败", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_address_return:
                finish();
                break;
            case R.id.activity_address_btn:
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        listHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                addressBean = gson.fromJson(msg.obj.toString(), AddressBean.class);
                myAdapter = new AddressActivityAdapter(AddressActivity.this, addressBean);
                myAdapter.notifyDataSetChanged();
                myListView.setAdapter(myAdapter);
                return false;
            }
        });
        //解析地址数据，传到适配器
        SharedPreferences sp = getSharedPreferences("Mirror", MODE_PRIVATE);
        String token = sp.getString("token", "");
        OkHttpUtils.post().url(UrlBean.USER_ADDRESS_LIST).addParams("token", token)
                .addParams("device_type", "2").addParams("page", "").addParams("last_time", "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(final Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                listHandler.sendMessage(message);
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
