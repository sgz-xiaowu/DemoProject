package com.myle.resource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ResouceMainActivity extends Activity
{
    private Button m_trigger;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        m_trigger = (Button) findViewById(R.id.button_activity_animation);
        m_trigger.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ResouceMainActivity.this, AnimationResActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    /*动画的形式进入进出。*/
    @Override
    public void startActivityForResult(Intent intent, int requestCode)
    {
        super.startActivityForResult(intent, requestCode);
        
//        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
    
    /*动画的形式进入进出。*/
    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        
//        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
}
