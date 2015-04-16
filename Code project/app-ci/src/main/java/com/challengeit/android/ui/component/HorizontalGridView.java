package com.challengeit.android.ui.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

public class HorizontalGridView extends GridView {

    public HorizontalGridView(Context context) {
        super(context);
    }

    public HorizontalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Flip vertical
        setScaleX(1.0f);
        setScaleY(-1.0f);

        // Rotate clockwise
        setPivotX(getHeight() / 2);
        setRotation(90);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Invert width and height
        getLayoutParams().width = getMeasuredHeight();
        getLayoutParams().height = getMeasuredWidth();
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        child.setScaleX(1.0f);
        child.setScaleY(-1.0f);
        child.setRotation(90);

        return super.drawChild(canvas, child, drawingTime);
    }
}