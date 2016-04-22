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
        intent.putExtra(context.getString(R.string.CatalogFragment_position), position);
        getActivity().startActivity(intent);
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.CatalogFragment_exit);
        //积极响应
        builder.setPositiveButton(R.string.CatalogFragment_makesure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getActivity().getSharedPreferences(context.getString(R.string.CatalogFragment_Mirror), getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean(context.getString(R.string.CatalogFragment_ifLogin), false);
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
