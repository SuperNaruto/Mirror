package com.example.vdllo.mirror.toolclass;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by dllo on 16/3/30.
 */
public class CustomViewPager extends ViewPager {
    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //设置ViewPager的滑动动画
        setPageTransformer(true, new VerticalPageTransformer());
        //设置滚动模式
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            if (position < -1) {
                //设置透明度
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);
                //设置可滑动指示器的效果
                page.setTranslationX(page.getWidth() * -position);

                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);

            } else {
                page.setAlpha(0);
            }
        }
    }

    private MotionEvent swapXY(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float newX = (event.getY() / height) * width;
        float newY = (event.getX() / width) * height;

        event.setLocation(newX, newY);

        return event;
    }

    //是否拦截某个事件，返回false，不拦截事件，向子View进行分发
    // (默认返回的是false）。返回true，则会中断事件传递，
    // 并把事件交由当前View的onTouchEvent处理。
    // TODO: 16/3/30  查Api弄清楚
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        boolean intercepted = super.onInterceptHoverEvent(swapXY(event));
        swapXY(event);
        return intercepted;
    }

    //事件处理，返回结果表示是否消耗当前事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(swapXY(event));
    }
}

