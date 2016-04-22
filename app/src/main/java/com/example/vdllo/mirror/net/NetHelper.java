package com.example.vdllo.mirror.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.bean.UrlBean;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/5.
 */
public class NetHelper {
    private Handler handler;
    private Context context;
    private ImageLoaderConfiguration configuration;
    private final DisplayImageOptions options;
    private final OkHttpClient mOkHttpClient;
    private RequestQueue requestQueue; //创建请求队列
    private Map<String, String> mMap;

    /**
     * 定义一个ImageLoder对象,用来加载网络图片
     */
    private ImageLoader imageLoader;
    private String diskPath;

    /**
     * NetHelper 的构造方法
     * 引入NetHelper 必须传入当前上下文对象
     * 并在构造方法中,创建请求队列
     */

    public NetHelper (Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            diskPath = file.getAbsolutePath();
        } else {
            File file = context.getFilesDir();
            diskPath = file.getAbsolutePath();
        }
        File file = new File(diskPath + "/img");
        if (file.exists()) {
            diskPath = file.getAbsolutePath();
        }
        this.context = context;
        mOkHttpClient = new OkHttpClient();
        configuration = new ImageLoaderConfiguration.Builder(context).diskCache(new UnlimitedDiscCache(file)).diskCacheFileNameGenerator(new Md5FileNameGenerator()).build();
        ImageLoader.getInstance().init(configuration);
        imageLoader = ImageLoader.getInstance();

        //设置imageloader
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(Color.TRANSPARENT) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(Color.TRANSPARENT) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(Color.TRANSPARENT) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)  // default 设置图片在加载前是否重置、复位
                .delayBeforeLoading(1000)  // 下载前的延迟时间
                .cacheInMemory(true) // default  设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // default  设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.ARGB_4444) // default 设置图片的解码类型
                .displayer(new SimpleBitmapDisplayer()) // default  还可以设置圆角图片new RoundedBitmapDisplayer(20)
                .handler(new Handler()) // default
                .build();
    }
    //商品列表
    public void getGoods(final Handler handler) {
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.GOODS_LIST)
                .addParams("token", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "")
                .addParams("category_id", "")
                .addParams("version", "1.0.1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                //子线程无法刷新UI,利用handler发送Message到主线程
                String body = response.body().string();
                Message message = new Message();
                message.what = 1;
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


    //商品列表
    public void getSunGoods(final Handler handler) {
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.GOODS_LIST)
                .addParams("token", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "")
                .addParams("category_id", "268")
                .addParams("version", "1.0.1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                //子线程无法刷新UI,利用handler发送Message到主线程
                String body = response.body().string();
                Message message = new Message();
                message.what = 1;
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

    //商品列表
    public void getLineGoods(final Handler handler) {
        //okhttp网络解析
        OkHttpUtils.post().url(UrlBean.GOODS_LIST)
                .addParams("token", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "")
                .addParams("category_id", "269")
                .addParams("version", "1.0.1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                //子线程无法刷新UI,利用handler发送Message到主线程
                String body = response.body().string();
                Message message = new Message();
                message.what = 1;
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

    //一级专题分享
    public void getShareInfo(final Handler handler) {
        OkHttpUtils.post().url(UrlBean.STORY_LIST)
                .addParams("token", "")
                .addParams("uid", "")
                .addParams("device_type", "2")
                .addParams("page", "")
                .addParams("last_time", "").build().execute(new Callback() {
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


    /**
     * 给view 网络拉取背景的方法
     *
     * @param v   组件
     * @param url 网址
     */
    public void setBackGround(View v, String url) {
        Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url);
        v.setBackground(new BitmapDrawable(context.getResources(), bitmap));
    }

    /**
     * 给imageview 拉取图片并显示的方法
     * @param imageView 组件
     * @param url       网址
     */
    public void setImage(ImageView imageView, String url) {
        imageLoader.displayImage(url, imageView, options);
    }

    /**
     * 方法:getJsonData() 获取需要拉取的数据,使其拼接成可请求的 url
     *
     * @param requestType 请求类型
     * @param mMap        请求参数
     * @param netListener 网络请求成功和失败的监听对象
     */
    public void getJsonData(String requestType, NetListener netListener, HashMap<String, String> mMap) {
        String idUrl = requestType;
        postDataFromNet(idUrl, netListener, mMap);
    }

    /**
     * 方法:postDataFromNet() 根据Url,HashMap 类型的请求参数来进行请求
     *
     * @param url         拼接后的请求网址
     * @param mMap        POST请求所需的参数
     * @param netListener 网络请求成功和失败的监听对象
     */
    private void postDataFromNet(String url, final NetListener netListener, final HashMap<String, String> mMap) {
        final StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        netListener.getSuccess(response);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                netListener.getFailed("");
            }
        }) {
            /**
             *设置请求参数
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mMap;
            }
        };
        requestQueue.add(request);
    }


}
