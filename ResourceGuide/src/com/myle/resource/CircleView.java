package com.myle.resource;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CircleView extends View
{
    public enum drawType
    {
        SAVED,
        
        UNSAVED
    }
    
    Paint mViewPaint;
    Paint mTextPaint;
    Path mPath;
    
    // enum 类型就是它的定义。
    drawType m_type = drawType.SAVED;
    
    public CircleView(Context context)
    {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public CircleView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        
        init(context);
    }

    public void setSaved(drawType save)
    {
        this.m_type = save;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        
        // draw circle 
        /*绘制三个circle*/
//        canvas.drawCircle(20, 100, 10, mViewPaint);
//        canvas.drawCircle(50, 100, 10, mViewPaint);
//        canvas.drawCircle(80, 100, 10, mViewPaint);

        // draw drawable
        Drawable drawable = createDrawable();
//        canvas.save();
//        for (int i = 0; i < 5; i++)
//        {
//            canvas.translate(40, 30);
//            drawable.draw(canvas);
//        }
//        canvas.restore();
//        drawable.draw(canvas);
        
        // Save & Restore
        int px = getMeasuredWidth();
        int py = getMeasuredHeight();
        // draw background
        canvas.drawRect(10, 10, px-10, py-10, mViewPaint);
        // draw right arrow
        /*canvas.drawLine(4*px/5, 20, px-20, py/2, mTextPaint);
        canvas.drawLine(0+20, py/2, px-20, py/2, mTextPaint);
        canvas.drawLine(4*px/5, py-20, px-20, py/2, mTextPaint);*/
        
        // draw up arrow
        switch (m_type)
        {
        case SAVED:
            canvas.save();
            canvas.rotate(90, px/2, py/2);
            canvas.drawLine(1*px/3, py/2, px/2, 0+20, mTextPaint);
            canvas.drawLine(px/2, py-20, px/2, 0+20, mTextPaint);
            canvas.drawLine(2*px/3, py/2, px/2, 0+20, mTextPaint);
            canvas.restore();
            canvas.drawCircle(2*px/3, py-20, 10, mTextPaint);
            mTextPaint.setTextSize(20);
            canvas.drawText("Saved", px-100, py-20, mTextPaint);
            break;
            
        case UNSAVED:
            canvas.rotate(90, px/2, py/2);
            canvas.drawLine(1*px/3, py/2, px/2, 0+20, mTextPaint);
            canvas.drawLine(px/2, py-20, px/2, 0+20, mTextPaint);
            canvas.drawLine(2*px/3, py/2, px/2, 0+20, mTextPaint);
            canvas.drawCircle(2*px/3, py-20, 10, mTextPaint);
            mTextPaint.setTextSize(20);
            canvas.drawText("UnSaved", px/2, py-50, mTextPaint);
            break;
        }
        
    }
    
    private Drawable createDrawable()
    {
        OvalShape oval = new OvalShape();
        oval.resize(25, 25);
        ShapeDrawable shape = new ShapeDrawable(oval);
        Paint paint = shape.getPaint();
        paint.setColor(Color.WHITE);
        return shape;
    }
    
    private void init(Context context)
    {
        mViewPaint = new Paint();
        mTextPaint = new Paint();
        
        mViewPaint.setColor(Color.GREEN);
        mTextPaint.setColor(Color.RED);
    }

}
