package com.code.subdemo;

import com.code.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * 两个ImageView分别以color、drawable的方式设置resource，证明两个方式的区别
 */
public class Color_Drawable_Diff extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_color_drawable_diff);
    }
}
