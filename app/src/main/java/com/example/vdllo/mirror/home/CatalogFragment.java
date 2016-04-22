package com.example.vdllo.mirror.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/5.
 */
public class CatalogFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private RelativeLayout relativeLayout;
    private Context context;
    private ListView listView;
    private ArrayList titleData;
    private CatalogAdapter catalogAdapter;
    private TextView exitTv;
    // 当前Fragment位置
    private int linePosition;

    //构造方法传入上下文
    public CatalogFragment(Context context, ArrayList titleData, int linePosition) {
        this.context = context;
        this.titleData = titleData;
        this.linePosition = linePosition;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_catalog;
    }

    @Override
    protected void initView() {
        relativeLayout = bindView(R.id.catalog_relativelayout);
        listView = bindView(R.id.catalog_listview);
        exitTv = bindView(R.id.pop_exit_textView);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected void initData() {
        catalogAdapter = new CatalogAdapter(titleData, context, linePosition);
        listView.setAdapter(catalogAdapter);
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(0);
        exitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 跳转到MainActivity，显示menu对应的Fragment
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("position", position);
        getActivity().startActivity(intent);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("确定退出登錄");
        //积极响应
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getActivity().getSharedPreferences("Mirror", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("ifLogin", false);
                editor.commit();
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });
        //消极响应
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "已取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();//显示
    }

}
