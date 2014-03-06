package com.example.AndroidFramework.module.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by TALE on 3/6/14.
 */
public class NavigationViewPager extends ViewPager {

    private boolean swipingEnabled = true;

    public NavigationViewPager(Context context) {
        super(context);
    }

    public NavigationViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.swipingEnabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.swipingEnabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setSwipingEnabled(boolean enabled) {
        this.swipingEnabled = enabled;
        if (swipingEnabled) {
            setOffscreenPageLimit(3);
        } else {
            setOffscreenPageLimit(1);
        }
    }
}
