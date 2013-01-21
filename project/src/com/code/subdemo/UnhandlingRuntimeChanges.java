package com.code.subdemo;

import com.code.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*测试类：在Manifest中不处理Screen oritention变化事件。
        在代码Activity中，也不对声明周期做任何保存处理机制。*/
public class UnhandlingRuntimeChanges extends Activity implements OnClickListener
{
    int mTval = 0;
    TextView mView;
    Button mBtnIncrease;
    Button mBtnDecrease;
    
    static final String TAG_RUNTIME = "runtime";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG_RUNTIME, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_runtime_changes);
        
        mView = (TextView) findViewById(R.id.text_runtime);
        mBtnIncrease = (Button) findViewById(R.id.button_increase);
        mBtnDecrease = (Button) findViewById(R.id.button_decrease);
        
        mBtnIncrease.setOnClickListener(this);
        mBtnDecrease.setOnClickListener(this);
        
        // 进行初始化
        mTval = 3;
        String reuslt = getString(R.string.runtime_changes, mTval);
        mView.setText(reuslt);
    }
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.button_increase:
            mTval ++;
            break;
            
        case R.id.button_decrease:
            mTval --;
            break;

        default:
            break;
        }
        
        String reuslt = getString(R.string.runtime_changes, mTval);
        mView.setText(reuslt);
    }
    
    @Override
    protected void onDestroy()
    {
        Log.d(TAG_RUNTIME, "onDestroy");
        super.onDestroy();
    }
}
