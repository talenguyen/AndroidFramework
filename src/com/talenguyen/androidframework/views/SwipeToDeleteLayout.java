package com.talenguyen.androidframework.views;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by TALE on 5/28/2014.
 */
public class SwipeToDeleteLayout extends SwipeLayout {

    public SwipeToDeleteLayout(Context context) {
        this(context, null, 0);
    }

    public SwipeToDeleteLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeToDeleteLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setMaxHorizontalDistance((int) (200 * context.getResources().getDisplayMetrics().density));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setEdgeSize(w);
    }
}
