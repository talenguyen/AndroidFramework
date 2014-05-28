package com.talenguyen.androidframework.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by TALE on 5/28/2014.
 */
public class SwipeToDeleteLayout extends SwipeLayout {

    private Button mDeleteView;

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
    protected void onFinishInflate() {
        super.onFinishInflate();
        createDeleteView();
    }

    protected void drawOffsetSpace(Canvas canvas, View child) {
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mInLayout = true;
        mDeleteView.layout(0, 0,
                mContentLeft, mDeleteView.getMeasuredHeight());
        mInLayout = false;
    }

    private void createDeleteView() {
        mDeleteView = new Button(getContext());
        mDeleteView.setBackgroundColor(0xFFFF8B8B);
        mDeleteView.setText("DELETE");
        final int padding = (int) (getResources().getDisplayMetrics().density * 50);
        mDeleteView.setPadding(padding, padding, padding, padding);
        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mDeleteView, lp);
    }
}
