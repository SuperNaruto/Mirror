package com.example.vdllo.mirror.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vdllo.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/1.
 */
public class ShowPopMenu implements AdapterView.OnItemClickListener {

    private Context context;
    private android.widget.PopupWindow popupWindow;
    private ListView listView;
    private ShowMenuAdapter showMenuAdapter;
    private TextView textView,exitTv;
    private MainActivity mainActivity;

    // 构造方法传入上下文环境
    public ShowPopMenu(Context context) {
        this.context = context;
    }

    // 弹出PopupWindow的方法
    // 参数一：当前view
    // 参数二：menu标题的集合
    // 参数三：当前fragment的位置
    public void showPopupWindow(View v, ArrayList<String> titleData, int linePosition) {

        View view = LayoutInflater.from(context).inflate(R.layout.pop, null);
        textView = (TextView) view.findViewById(R.id.pop_return_textview);
        exitTv = (TextView) view.findViewById(R.id.pop_exit_textView);
        // 初始化组件
        initView(view);

        // 设置PopupWindow的数据
        showMenuAdapter = new ShowMenuAdapter(titleData, context, linePosition);
        listView.setAdapter(showMenuAdapter);
        listView.setOnItemClickListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到MainActivity，显示menu对应的Fragment
                Intent rIntent = new Intent(context, MainActivity.class);
                rIntent.putExtra("position", 0);
                context.startActivity(rIntent);

            }
        });

        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showDialog();
            }
        });

        // 设置PopupWindow的布局，显示的位置
        popupWindow = new android.widget.PopupWindow(context);
        popupWindow.setContentView(view);

        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        popupWindow = new android.widget.PopupWindow(view, dm.widthPixels,
                dm.heightPixels, true);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.update();
        popupWindow.setAnimationStyle(R.style.popWindow_anim);
        initData(view);
    }


    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.pop_listview);
    }

    private void initData(View view) {
        // 点击PopupWindow其他未设置监听的位置，PopupWindow消失
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        // 跳转到MainActivity，显示menu对应的Fragment
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.putExtra("position", position);
//        context.startActivity(intent);
        mainActivity = (MainActivity) context;
        mainActivity.getPositionFromPopWindow(position);
        popupWindow.dismiss();
    }
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("确定退出登录");//设置标题栏
        //积极响应
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "确定键", Toast.LENGTH_SHORT).show();
                Intent dIntent = new Intent(context,MainActivity.class);
                dIntent.putExtra("key",1);
                context.startActivity(dIntent);

            }
        });
        //消极响应
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "取消退出", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();//显示

    }
}


