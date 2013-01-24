package com.myle.resource;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class TempActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.layout_temp);
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
    
    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.activtiy_in, R.anim.activtiy_out);
    }
}
