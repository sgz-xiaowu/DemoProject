package com.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View
{
    public static final String TAG = "CircleView";
    public CircleView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public CircleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public CircleView(Context context)
    {
        super(context);
    }
    
    @Override
    public void draw(Canvas canvas)
    {
        Log.d(TAG, "draw");
        super.draw(canvas);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        Log.d(TAG, "draw");
        super.onDraw(canvas);
        
        // 绘制ARGB颜色
        canvas.drawARGB(100, 80, 136, 192);
    }

}
