package com.example.vdllo.mirror.shoppingcart;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
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
import com.example.vdllo.mirror.db.DaoSingleton;
import com.example.vdllo.mirror.db.MirrorEntity;
import com.example.vdllo.mirror.db.MirrorEntityDao;
import com.example.vdllo.mirror.toolclass.DensityUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/15.
 */
public class AddressActivity extends BaseAcitvity implements View.OnClickListener {
    private static String token;
    private SwipeMenuListView myListView;
    private ImageView returnIv;
    private Button addAddressBtn;
    private AddressBean addressBean;
    private Handler handler;
    private AddressActivityAdapter myAdapter;

    public static void setToken(String token) {
        AddressActivity.token = token;
    }

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
        //解析地址数据，传到适配器
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                addressBean = gson.fromJson(msg.obj.toString(), AddressBean.class);
                myAdapter = new AddressActivityAdapter(AddressActivity.this, addressBean);
                return false;
            }
        });
        OkHttpUtils.post().url(UrlBean.USER_ADDRESS_LIST).addParams("token", token)
                .addParams("device_type", "2").addParams("page", "").addParams("last_time", "")
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                Message message = new Message();
                String data = response.body().string();
                message.obj = data;
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
                deleteItem.setWidth(DensityUtils.dp2px(AddressActivity.this, 65));
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
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        OkHttpUtils.post().url(UrlBean.USER_DEL_ADDRESS).addParams("token", token)
                                .addParams("addr_id", addressBean.getData().getList().get(position).getAddr_id()).build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws Exception {
                                try {
                                    JSONObject object = new JSONObject(response.body().string());
                                    String result = object.getString("result");
                                    if (result.equals("1")) {
                                        Toast.makeText(AddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            @Override
                            public void onError(Call call, Exception e) {
                                Toast.makeText(AddressActivity.this, "删除失败", Toast.LENGTH_SHORT).show();

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
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OkHttpUtils.post().url(UrlBean.USER_MR_ADDRESS).addParams("token", token).addParams("addr_id", addressBean.getData().getList().get(position).getAddr_id())
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        try {
                            JSONObject object = new JSONObject(response.body().string());
                            String result = object.getString("result");
                            if (result.equals("1")) {
                                Toast.makeText(AddressActivity.this, "设置默认地址成功", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                Intent intent  = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
                break;

        }

    }
}
