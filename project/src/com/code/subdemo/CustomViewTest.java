package com.code.subdemo;

import com.code.R;
import com.custom.view.CircleView;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomViewTest extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    
        // 使用线性布局 LinearLayout
        LinearLayout layout = new LinearLayout(CustomViewTest.this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        
        TextView textView = new TextView(CustomViewTest.this);
        textView.setBackgroundColor(getResources().getColor(R.color.green));
        textView.setTextSize(30);
        textView.setText("This is a custom view test mode!");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.leftMargin = 100;
        params.topMargin = 100;
//        params.gravity = Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(params);
        layout.addView(textView);
        
        CircleView circleView = new CircleView(CustomViewTest.this);
        params.width = 160;
        params.height = 240;
        circleView.setLayoutParams(params);
        layout.addView(circleView);
        
    }
}
