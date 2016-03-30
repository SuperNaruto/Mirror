package com.example.vdllo.mirror.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.example.vdllo.mirror.R;
import com.example.vdllo.mirror.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/3/30.
 */
public class AllTypeFragment extends BaseFragment {

    private AllTypeAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private List<Integer> datas;
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    @Override
    public int getLayout() {
        return R.layout.fragment_all_type;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.recycleView);
        linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
        linearLayout.setOnClickListener(popClick);
    }

    @Override
    protected void dataView() {
        // 1.获取图片的数据
        datas = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            datas.add(R.mipmap.home_pic);
        }
        // 2.设置布局管理器
        manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        // 3.设置适配器
        adapter = new AllTypeAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    // 点击弹出左侧菜单的显示方式
    View.OnClickListener popClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*Toast toast = Toast.makeText(MainActivity.this, "这是一个代图片的Toast!", Toast.LENGTH_LONG);
            toast.show();*/
            getPopupWindow();
            // 这里是位置显示方式
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
    };

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {

        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件pop.xml的视图
        View popupWindowView = getActivity().getLayoutInflater().inflate(R.layout.pop, null,
                false);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupWindow = new PopupWindow(popupWindowView, dm.widthPixels,
                dm.heightPixels, true);
//         popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//         popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置动画效果
        //PopupWindow的动画显示效果是通过setAnimationStyle(int id)方法设置的，
        // 其中id为一个style的id，所以我们要在styles.xml文件中设置一个动画样式
        popupWindow.setAnimationStyle(R.style.popWindow_anim);
        // 点击其他地方消失
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
//        // pop.xml视图里面的控件
//        initOpenMenuItem(popupWindowView);
//        initOpenMenuOther(popupWindowView);
//        initOpenPosition(popupWindowView);
    }
}

