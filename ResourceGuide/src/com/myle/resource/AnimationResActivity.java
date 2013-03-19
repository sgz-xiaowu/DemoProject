package com.myle.resource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
    public static final String[] INTERPOLATOR_NAMES = {"AccelerateDecelerateInterpolator",
                                                       "AccelerateInterpolator",
                                                       "AnticipateInterpolator", 
                                                       "AnticipateOvershootInterpolator",
                                                       "BounceInterpolator",
                                                       "CycleInterpolator",
                                                       "DecelerateInterpolator",
                                                       "LinearInterpolator",
                                                       "OvershootInterpolator"};
    
    // view animation
    public static final int INDEX_ALPHA = 0;
    public static final int INDEX_SCALE = 1;
    public static final int INDEX_ROTATE = 2;
    public static final int INDEX_TRANSLATE = 3;
    public static final int INDEX_SHIVER_ANIM = 4;
    public static final int INDEX_MAX_ANIMATION = ANIMATION_NAMES.length - 1;
    
    // interpolator     
                    // Overshoot超越，超出  Anticipate提前，早期                                      //动画效果
    public static final int INDEX_AccelerateDecelerateInterpolator = 10;    //先加速，后减速
    public static final int INDEX_AccelerateInterpolator = 11;              //加速
    public static final int INDEX_AnticipateInterpolator = 12;              // 动画开始，超出
    public static final int INDEX_AnticipateOvershootInterpolator = 13;     // 动画开始&结束，超出
    public static final int INDEX_BounceInterpolator = 14;                  //弹球
    public static final int INDEX_CycleInterpolator = 15;                   //循环，并开始超出
    public static final int INDEX_DecelerateInterpolator = 16;              //减速
    public static final int INDEX_LinearInterpolator = 17;                  //线性，即匀速
    public static final int INDEX_OvershootInterpolator = 18;               // 动画结束，超出
    public static final int INDEX_MAX_INTERPOLATOR = INTERPOLATOR_NAMES.length - 1;
    
    private TextView m_text01;
    private TextView m_text02;
    private TextView m_text03;
    private TextView m_text04;
    
    private Button m_btnTrigger;
    private Button m_btnTriggerActivity;
    private Button m_btnDraw;
    private Button m_btnInterpolator;
    private Button m_btnFrame;
    private Button m_btnAnimTest;
    
    private CircleView m_circleView;
    private ImageView m_imageFrameView;
    
    private int m_nameAnimationIndex = 0;
    private int m_nameInterpotatorIndex = 0;
    private boolean isSaved = true;
    private boolean isFramePlaying = false;
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
        m_btnInterpolator = (Button) findViewById(R.id.button_interpolator);
        m_btnFrame = (Button) findViewById(R.id.button_frame);
        
        m_btnAnimTest = (Button) findViewById(R.id.button_test);
        
        m_circleView = (CircleView) findViewById(R.id.circleView1);
        m_imageFrameView = (ImageView) findViewById(R.id.image_frame);
        
        String triggerText = getString(R.string.anim_trigger, ""/*ANIMATION_NAMES[0]*/);
        m_btnTrigger.setText(triggerText);
        String interpolatorText = getString(R.string.anim_interpolator, ""/*INTERPOLATOR_NAMES[0]*/);
        m_btnInterpolator.setText(interpolatorText);
        m_btnTrigger.setOnClickListener(this);
        m_btnTriggerActivity.setOnClickListener(this);
        m_btnDraw.setOnClickListener(this);
        m_btnInterpolator.setOnClickListener(this);
        m_btnFrame.setOnClickListener(this);
        m_btnFrame.setText(R.string.trigger_frame_puase);
        m_btnAnimTest.setOnClickListener(this);
        
        m_circleView.setBackgroundColor(Color.BLACK);
        m_imageFrameView.setBackgroundResource(R.drawable.hi);
        
        // Frame动画启动，不能在onCreate中调用，因为此时，animationDrawable还没有与ImageView完全绑定。
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        // onResume也不能启动Frame动画。
        /*AnimationDrawable animalAnimation = (AnimationDrawable) m_imageFrameView.getBackground();
        if (null != animalAnimation)
        {
                animalAnimation.start();
        }*/
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
            Animation animation = createAnimation(m_nameAnimationIndex);
//            int index = (m_nameIndex + 1) <= INDEX_MAX_ANIMATION ? m_nameIndex + 1 : 0;
            String triggerText = getString(R.string.anim_trigger, ANIMATION_NAMES[m_nameAnimationIndex]);
            trigger.setText(triggerText);
            
            startAnimation2(m_nameAnimationIndex, animation);
            
            m_nameAnimationIndex ++;
            if (m_nameAnimationIndex > INDEX_MAX_ANIMATION)
            {
                m_nameAnimationIndex = 0;
            }
        }
            break;
            
        case R.id.button_interpolator:
        {
            int oriIndex = m_nameInterpotatorIndex + 10;
            // 动画效果切换
            Button interpolator = (Button) v;
            Animation animation = createAnimation(oriIndex);
//            int nowIndex = (m_nameIndex + 1) <= INDEX_MAX_INTERPOLATOR ? m_nameIndex + 1 : 0;
            String triggerText = getString(R.string.anim_interpolator, INTERPOLATOR_NAMES[m_nameInterpotatorIndex]);
            interpolator.setText(triggerText);
            
            startAnimation2(oriIndex, animation);
            
            m_nameInterpotatorIndex ++;
            if (m_nameInterpotatorIndex > INDEX_MAX_INTERPOLATOR)
            {
                m_nameInterpotatorIndex = 0;
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
            
        case R.id.button_frame:
            Button frame = (Button) v;
            AnimationDrawable animalAnimation = (AnimationDrawable) m_imageFrameView.getBackground();
            if (null != animalAnimation)
            {
                if (isFramePlaying)
                {
                    frame.setText(R.string.trigger_frame_puase);
                    animalAnimation.stop();
                }
                else
                {
                    frame.setText(R.string.trigger_frame_play);
                    animalAnimation.start();
                }
                isFramePlaying = !isFramePlaying;
            }
            break;
            
        case R.id.button_test:
            Intent intent2 = new Intent(AnimationResActivity.this, TestAnimActivity.class);
            startActivity(intent2);
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
        // animation
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
        
        // Inter_polator
        case INDEX_AccelerateDecelerateInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_accelerate_decelerate);
            break;
        case INDEX_AccelerateInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_accelerate);
            break;
        case INDEX_AnticipateInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_anticipate);
            break;
        case INDEX_AnticipateOvershootInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_anticipate_overshoot_);
            break;
        case INDEX_BounceInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_bounce);
            break;
        case INDEX_CycleInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_cycle);
            break;
        case INDEX_DecelerateInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_decelerate);
            break;
        case INDEX_LinearInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_linear);
            break;
        case INDEX_OvershootInterpolator:
            animation = AnimationUtils.loadAnimation(context, R.anim.translate_overshoot);
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

            // Inter_polator
            case INDEX_AccelerateDecelerateInterpolator:
            case INDEX_AccelerateInterpolator:
            case INDEX_AnticipateInterpolator:
            case INDEX_AnticipateOvershootInterpolator:
            case INDEX_BounceInterpolator:
            case INDEX_CycleInterpolator:
            case INDEX_DecelerateInterpolator:
            case INDEX_LinearInterpolator:
            case INDEX_OvershootInterpolator:
                view = m_text04;
                break;
            default:
                view = null;
                break;
            }
            
            view.startAnimation(animation);
        }
    }
}
