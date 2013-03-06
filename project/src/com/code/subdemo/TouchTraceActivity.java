package com.code.subdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/*
 * 绘制touch轨迹trace。
 */
public class TouchTraceActivity extends Activity
{
    public static final String TAG = "TouchTrace";
    
    private View mView = null;
    private float mPosX = 0;
    private float mPosY = 0;
    private Path mPath = null;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            float x = event.getX();
            float y = event.getY();
            
            switch (event.getAction())
            {
            case MotionEvent.ACTION_DOWN:
                // 设置trace起点...
                mPath.moveTo(x, y);
                mView.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // 画trace...
                mPath.quadTo(mPosX, mPosY, x, y);
                mView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 保存trace...
//                saveTrace();
//                mTracePath.reset();
//                invalidate();
                break;
                
            default:
                break;
            }
            
            mPosX = x;
            mPosY = y;
            
            Log.d(TAG, String.format("mCurPosX = %f, mCurPosY = %f", mPosX, mPosY));
            return false;
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // 首先  请求设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // 全屏flag
        super.onCreate(savedInstanceState);
        
        // 方案一 OK
        setContentView(new TraceView(TouchTraceActivity.this));
        
        // 方案二  貌似无效
//        mView = new TraceView(TouchTraceActivity.this);
        /*mView = new ImageView(this);
        mView.setBackgroundResource(R.drawable.image01);
        mPath = new Path();
        mView.setFocusable(true);
        mView.setFocusableInTouchMode(true);
        mView.setOnTouchListener(mTouchListener);
        
        setContentView(mView);*/
        
    }
    
    
    
    /**
     * My trace view, extends android.view.View
     */
    public class TraceView extends View
    {
        private Context mContext;
        private Paint mPaint = null;
        private Paint mTextPaint = null;
        
        private Style mStyle = null;
        
        private Path mTracePath = null;
        private float mCurPosX = 0;
        private float mCurPosY = 0;
        private float mLastPosX = 0;
        private float mLastPosY = 0;
        
        private Dialog mSaveTraceDialog = null;
        
        public TraceView(Context context)
        {
            this(context, null);
            mContext = context;
        }
        
        public TraceView(Context context, AttributeSet attrs)
        {
            this(context, attrs, 0);
            mContext = context;
        }

        public TraceView(Context context, AttributeSet attrs, int defStyle)
        {
            super(context, attrs, defStyle);
            mContext = context;
            
            initialize(context);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
//            super.onDraw(canvas);
            // 整个View背景置为白色
            canvas.drawColor(Color.WHITE);
            
            // 先测试style的样式
//            styleTest(canvas);
            
            // 绘制文字Text.
//            drawText(canvas);
            
            // 绘制Touch轨迹
            drawTouchTrace(canvas);
        }
        
        private void drawTouchTrace(Canvas canvas)
        {
            mPaint.setColor(Color.BLACK);
            canvas.drawText("绘文字区域", 20, 30, mPaint);
            mPaint.setColor(Color.GRAY);
            mPaint.setStyle(Style.STROKE);
            mPaint.setStrokeWidth(2);
            canvas.drawRect(50, 50, 800, 600, mPaint);
            
            mTextPaint.setTextSize(20);
            mTextPaint.setColor(Color.RED);
            canvas.drawText("My trace current posX:" + mCurPosX, 60, 65, mTextPaint);
            canvas.drawText("My trace current posY:" + mCurPosY, 60 , 65+ 25, mTextPaint);
            
            
            mTextPaint.setTextSize(30);
            mTextPaint.setColor(Color.GREEN);
            canvas.drawPath(mTracePath, mTextPaint);
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            mLastPosX = event.getX();
            mLastPosY = event.getY();
            
            switch (event.getAction())
            {
            case MotionEvent.ACTION_DOWN:
                mSaveTraceDialog.dismiss();
                // 设置trace起点...
                mTracePath.moveTo(mLastPosX, mLastPosY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // 画trace...
                mTracePath.quadTo(mCurPosX, mCurPosY, mLastPosX, mLastPosY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 保存trace...
                saveTrace();
//                mTracePath.reset();
//                invalidate();
                break;
                
            default:
                break;
            }
            
            mCurPosX = mLastPosX;
            mCurPosY = mLastPosY;
            
            Log.d(TAG, String.format("mCurPosX = %f, mCurPosY = %f", mCurPosX, mCurPosY));
            return true;
        }

        private void saveTrace()
        {
            // show dialog
//            Dialog dialog = createDialog();
//            dialog.show();
            mSaveTraceDialog.show();
        }

        private Dialog createDialog()
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
            .setMessage("是否保存轨迹trace？")
            .setPositiveButton("保存", new DialogInterface.OnClickListener()
           {
               @Override
               public void onClick(DialogInterface dialog, int which)
               {
                   // do nothing
                   // 闭环
                   mTracePath.close();
               }
           })
           .setNegativeButton("取消", new DialogInterface.OnClickListener()
           {
               @Override
               public void onClick(DialogInterface dialog, int which)
               {
                   mTracePath.reset();
                   invalidate();
               }
           });
            
           return builder.create();
        }

        private void styleTest(Canvas canvas)
        {
            mStyle = Style.STROKE;   // 空心：绘制一个宽度为stroke-width的边框（中空视图区域）。
//          mStyle = Style.FILL;   // 实心：绘制一个不透明的颜色填充视图区域。
          mStyle = Style.FILL_AND_STROKE; // 绘制实心空心：没发现和FILL有啥区别。
          mPaint.setAntiAlias(true);
            mPaint.setStyle(mStyle);
            mPaint.setStrokeWidth(1);
            Log.d(TAG, "mPaint style = " + mPaint.getStyle());
            Log.d(TAG, "mPaint stroke width = " + mPaint.getStrokeWidth());
            mPaint.setColor(Color.BLUE);
            Rect styleRect = new Rect(100, 100, 500, 330);
            canvas.drawRect(styleRect, mPaint);
            mPaint.setColor(Color.GRAY);
            mPaint.setTextSize(80);
            canvas.drawText("mPaint style is " + mPaint.getStyle() + "...", styleRect.left + 3, styleRect.top + 25, mPaint);
        }
        
        private void initialize(Context context)
        {
            mStyle = Style.STROKE;
//            mStyle = Style.FILL;
//            mStyle = Style.FILL_AND_STROKE;
            mPaint = new Paint();
            mPaint.setColor(Color.WHITE);
            
            mTextPaint = new Paint();
            mTextPaint.setColor(Color.GREEN);
            mTextPaint.setAntiAlias(true);
            mTextPaint.setStyle(Paint.Style.STROKE);
            
            mTracePath = new Path();
            mTracePath.moveTo(0, 0);
            
            mSaveTraceDialog = createDialog();
            mSaveTraceDialog.setCanceledOnTouchOutside(true);
        }
    }
}
