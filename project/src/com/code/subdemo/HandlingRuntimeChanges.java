package com.code.subdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.code.R;

/*测试类：在Manifest中不处理Screen oritention变化事件。
 在代码Activity中，使用保存处理机制onSaveInstanceState。*/
public class HandlingRuntimeChanges extends Activity implements OnClickListener
{
    int                 mTval       = 0;
    TextView            mView;
    Button              mBtnIncrease;
    Button              mBtnDecrease;

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
        boolean saved = (null != savedInstanceState);
        Log.d(TAG_RUNTIME, "savedInstanceState is null == " + !saved);
        if (saved)
        {
            mTval = savedInstanceState.getInt(TAG_RUNTIME);
        }

        String reuslt = getString(R.string.runtime_changes, mTval);
        mView.setText(reuslt);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.button_increase:
            mTval++;
            break;

        case R.id.button_decrease:
            mTval--;
            break;

        default:
            break;
        }

        String reuslt = getString(R.string.runtime_changes, mTval);
        mView.setText(reuslt);
    }

    /**
     * This method only may be called.
     * It's only called before the activity is to be destroyed. Generally it is not called.
     * 
     * @{android-api activity}
     * In addition, the method onSaveInstanceState(Bundle) is called before
     * placing the activity in such a background state, allowing you to save
     * away any dynamic instance state in your activity into the given Bundle,
     * to be later received in onCreate(Bundle) if the activity needs to be
     * re-created.
     * 
     * See also translation { Android onSaveInstanceState中Bundle使用，保存应用状态--- http://blog.csdn.net/manp1212/article/details/7678512};
     */
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        int val = mTval;
        outState.putInt(TAG_RUNTIME, val);
    }

    @Override
    protected void onDestroy()
    {
        Log.d(TAG_RUNTIME, "onDestroy");
        super.onDestroy();
    }
}
