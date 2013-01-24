package com.myle.resource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 测试View Animation。
 * 
 * @author 沈大伟
 * 
 * @date   2013-01-22
 */
public class AnimationResActivity extends Activity implements OnClickListener
{
    public static final String[] ANIMATION_NAMES = {"alpha", "scale", "rotate", "translate", "shiver_anim"};
    
    public static final int INDEX_ALPHA = 0;
    public static final int INDEX_SCALE = 1;
    public static final int INDEX_ROTATE = 2;
    public static final int INDEX_TRANSLATE = 3;
    public static final int INDEX_SHIVER_ANIM = 4;
    public static final int INDEX_MAX = ANIMATION_NAMES.length - 1;
    
    private TextView m_text01;
    private TextView m_text02;
    private TextView m_text03;
    private TextView m_text04;
    
    private Button m_btnTrigger;
    private Button m_btnTriggerActivity;
    private Button m_btnDraw;
    
    private CircleView m_circleView;
    
    private int m_nameIndex = 0;
    private boolean isSaved = true;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_animation);
        
        m_text01 = (TextView) findViewById(R.id.textView1);
        m_text02 = (TextView) findViewById(R.id.textView2);
        m_text03 = (TextView) findViewById(R.id.textView3);
        m_text04 = (TextView) findViewById(R.id.textView4);
        
        m_btnTrigger = (Button) findViewById(R.id.button_trigger);
        m_btnTriggerActivity = (Button) findViewById(R.id.button_activity_temp);
        m_btnDraw = (Button) findViewById(R.id.button_draw);
        m_circleView = (CircleView) findViewById(R.id.circleView1);
        
        String triggerText = getString(R.string.anim_trigger, ANIMATION_NAMES[m_nameIndex]);
        m_btnTrigger.setText(triggerText);
        m_btnTrigger.setOnClickListener(this);
        m_btnTriggerActivity.setOnClickListener(this);
        m_btnDraw.setOnClickListener(this);
        
        m_circleView.setBackgroundColor(Color.BLACK);
    }

    // View widget animation.
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.button_trigger:
        {
            // 动画效果切换
            Button trigger = (Button) v;
            Animation animation = createAnimation(m_nameIndex);
            int index = (m_nameIndex + 1) <= INDEX_MAX ? m_nameIndex + 1 : 0;
            String triggerText = getString(R.string.anim_trigger, ANIMATION_NAMES[index]);
            trigger.setText(triggerText);
            
            startAnimation2(m_nameIndex, animation);
            
            m_nameIndex ++;
            if (m_nameIndex > INDEX_MAX)
            {
                m_nameIndex = 0;
            }
        }
            break;
            
        case R.id.button_activity_temp:
            Intent intent = new Intent(AnimationResActivity.this, TempActivity.class);
            startActivity(intent);
            break;
            
        case R.id.button_draw:
            Button draw = (Button) v;
            isSaved = !isSaved;
            if (isSaved)
            {
                draw.setText("unsave_draw");
                m_circleView.setSaved(CircleView.drawType.SAVED);
            }
            else
            {
                draw.setText("save_draw");
                m_circleView.setSaved(CircleView.drawType.UNSAVED);
            }
            m_circleView.invalidate();
            break;

        default:
            break;
        }
    }
    
    /*动画的形式进入进出。*/
    // 该方法对activity的动画不起作用。
    @Override
    public void startActivityForResult(Intent intent, int requestCode)
    {
        super.startActivityForResult(intent, requestCode);
        
//        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
//        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
    
    // Activity animation
    /*动画的形式进入进出。*/
    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        
//        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            setResult(RESULT_OK);
            finish();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    
    // Activity animation
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
    
    private Animation createAnimation(int index)
    {
        Context context = AnimationResActivity.this;
        Animation animation = null;
        
        switch (index)
        {
        case INDEX_ALPHA:
            animation = AnimationUtils.loadAnimation(context, R.anim.alpha_in);
            break;
        case INDEX_SCALE:
            animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            break;
        case INDEX_ROTATE:
            animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
            break;
        case INDEX_TRANSLATE:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate);
            break;
        case INDEX_SHIVER_ANIM:
            animation = AnimationUtils.loadAnimation(context, R.anim.shiver_anim);
            break;

        default:
            animation = AnimationUtils.makeInAnimation(context, true);
            break;
        }
//        String name = ANIMATION_NAMES[index];
//        if (name.equals(""))
//        {
//            animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
//        }
//        else
//        {
//
//        }
        
        return animation;
    }
    
    private void startAnimation(Animation animation)
    {
        if (null != animation)
        {
            // 只在第一次start动画
            animation.start();
            // 在current time to start动画
//            animation.startNow();
            
            animation.cancel();  // cancel()will call listen's onAnimationEnd(), and must call reset() before start again.
        }
    }
    
    private void startAnimation2(int index, Animation animation)
    {
        if (null != animation)
        {
            View view = null;
            
            switch (index)
            {
            case INDEX_ALPHA:
                view = m_text01;
                break;
            case INDEX_SCALE:
                view = m_text02;
                break;
            case INDEX_ROTATE:
                view = m_text03;
                break;
            case INDEX_TRANSLATE:
                view = m_text04;
                break;
            case INDEX_SHIVER_ANIM:
                view = m_circleView;
                break;

            default:
                view = null;
                break;
            }
            
            view.startAnimation(animation);
        }
    }
}
