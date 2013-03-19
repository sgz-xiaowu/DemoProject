package com.myle.resource;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TestAnimActivity extends Activity implements OnTouchListener, OnClickListener
{
    private GestureDetector mGestureListener = new GestureDetector(new GestureDetector.OnGestureListener()
    {
        
        @Override
        public boolean onSingleTapUp(MotionEvent e)
        {
            // TODO Auto-generated method stub
            return false;
        }
        
        @Override
        public void onShowPress(MotionEvent e)
        {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            // TODO Auto-generated method stub
            if (Math.abs(e1.getX() - e2.getX()) > 10)
            {
                startToLeftAnimation();
                return true;
            }
            else if (Math.abs(e2.getX() - e1.getX()) > 10) {
                startToRightAnimation();
                return true;
            }
            return false;
        }
        
        @Override
        public void onLongPress(MotionEvent e)
        {
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            // TODO Auto-generated method stub
            return false;
        }
        
        @Override
        public boolean onDown(MotionEvent e)
        {
            // TODO Auto-generated method stub
            return false;
        }
    });
    
    private ImageView imageView01;
    private ImageView imageView02;
    
    private Animation animationIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.layout_anim_test);
        
        RelativeLayout container = (RelativeLayout) findViewById(R.id.layout_container);
        container.setOnTouchListener(this);
        container.setOnClickListener(this);
        
        imageView01 = (ImageView) findViewById(R.id.imageView1);
        imageView02 = (ImageView) findViewById(R.id.imageView2);
        
//        imageView01.setOnTouchListener(this);
//        imageView02.setOnTouchListener(this);
        
    }

    protected void startToRightAnimation()
    {
        Log.d("", "startToRightAnimation");
        
        Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.page_in);
        Animation animationR = AnimationUtils.loadAnimation(this, R.anim.page_out_right);
        imageView01.startAnimation(animationR);
        imageView02.startAnimation(animationIn);
    }

    protected void startToLeftAnimation()
    {
        Log.d("", "startToLeftAnimation");
        Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.page_in);
        Animation animationL = AnimationUtils.loadAnimation(this, R.anim.page_out_left);
        imageView01.startAnimation(animationL);
        imageView02.startAnimation(animationIn);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView02.getLayoutParams();
        params.height = (int) (params.height*0.5);
        params.width = (int) (params.width*0.5);
        imageView02.setLayoutParams(params);
        imageView01.setVisibility(View.INVISIBLE);
        imageView02.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    
    {
        if (mGestureListener.onTouchEvent(event))
        {
            Log.d("", "gesture effect!");
            
            return true;
        }
        
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v)
    {
        startToLeftAnimation();
    }
}
