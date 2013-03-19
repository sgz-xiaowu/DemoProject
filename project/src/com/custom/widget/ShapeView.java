package com.custom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

public class ShapeView extends View
{
    int X, Y;
    private Paint mBoarderPaint;
    private float mBoarderWidth = 3;
    private int mBoarderColor = Color.RED;
    
    public ShapeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    public ShapeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public ShapeView(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }
    
    
    private void init()
    {
        // TODO Auto-generated method stub
        mBoarderPaint = new Paint();
        mBoarderPaint.setStyle(Style.STROKE);
        mBoarderPaint.setAntiAlias(true);
        mBoarderPaint.setStrokeWidth(mBoarderWidth);
        mBoarderPaint.setColor(mBoarderColor);
        
        X = 100; Y = 80;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        drawShape(canvas);
    }

    String[] alias = {"A", "B", "C", "D", "E"};
    private void drawShape(Canvas canvas)
    {
        Shape shape = new Shape()
        {
            @Override
            public void draw(Canvas canvas, Paint paint)
            {
                int l = getLeft() + getPaddingLeft();
                int t = getTop() + getPaddingTop();
                int r = getRight() - getPaddingRight();
                int b = getBottom() - getPaddingBottom();
                
                
                int[][] posii = initPoints(l, t, r, b);
                
                Path sPath = new Path();
                sPath.reset();
                int count = posii.length;
                
                Paint cirlePaint = new Paint();
                cirlePaint.setAntiAlias(true);
                cirlePaint.setColor(Color.BLACK);
                cirlePaint.setStyle(Style.FILL);
                cirlePaint.setStrokeWidth(1);
                cirlePaint.setTextSize(16);
                String viewRegion = String.format("view region[l:%d, t:%d, r:%d, b:%d]", l, t, r, b);
                canvas.drawText(viewRegion, l + 5, t + b/2, paint);
                for (int i = 0; i < count; i++)
                {
                    int tx = posii[i][0];
                    int ty = posii[i][1];
                    if (i == 0)
                    {
                        sPath.moveTo(tx, ty);
                    }
                    else 
                    {
                        sPath.lineTo(tx, ty);
                    }
                    
                    canvas.drawCircle(tx, ty, 5, /*cirlePaint*/paint);
                    String axis = String.format("%s:(%d, %d)", alias[i], tx, ty);
                    canvas.drawText(axis, tx, ty, /*cirlePaint*/paint);
                }
                sPath.close();
                canvas.drawPath(sPath, mBoarderPaint);
            }
        };
        Drawable drawable = new ShapeDrawable(shape);
        drawable.draw(canvas);
    
    }
    
    private int[][] initPoints(int l, int t, int r, int b)
    {
        int[][] posii = new int[5][2];
        
        posii[0][0] = X; posii[0][1] = Y;
        posii[1][0] = r; posii[1][1] = t;
        posii[2][0] = r; posii[2][1] = b;
        posii[3][0] = l; posii[3][1] = b;
        posii[4][0] = l; posii[4][1] = t;
        
        return posii;
    }
}
