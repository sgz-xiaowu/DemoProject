package com.code.subdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.code.R;
import com.custom.view.CircleView;

/**
 * Case 1: RelativeLayout.addView() view01/view02/view03/view04时，若使用同一个LayoutParams，则将只显示最后Last View。
 *         ViewGroup.addView()不能使用相同的params。
 */
public class CustomViewTest2 extends Activity
{
    public static final int ID_TEXTVIEW01 = 100;
    public static final int ID_CIRCLEVIEW = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
    
        // 使用相对布局 RelativeLayout
        RelativeLayout layout = new RelativeLayout(CustomViewTest2.this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(layout);
        
        TextView textView01 = new TextView(CustomViewTest2.this);
        textView01.setId(ID_TEXTVIEW01);
        textView01.setBackgroundColor(getResources().getColor(R.color.green));
        textView01.setTextSize(30);
        textView01.setText("This is a custom view test mode!");
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-2, -2);
        params.width = 200;
        params.leftMargin = 60;
        params.topMargin = 40;
//        textView01.setLayoutParams(params);
        layout.addView(textView01, 0, params);
        
        CircleView circleView = new CircleView(CustomViewTest2.this);
        RelativeLayout.LayoutParams cirparams = new RelativeLayout.LayoutParams(160, 240);
//        cirparams.width = 160;
//        cirparams.height = 240;
        /*addRule(verb, anchor)中anchor若为-1则失效。
         * anchor: The id of another view to use as an anchor, or a boolean value.
         *         -For boolean (represented as RelativeLayout.TRUE) for true or 0 for false). 
         *         -For verbs that don't refer to another sibling (for example, ALIGN_WITH_PARENT_BOTTOM) just use -1.
         */
        Log.d(CircleView.TAG, "textview01'id = " + textView01.getId());
        cirparams.addRule(RelativeLayout.RIGHT_OF, ID_TEXTVIEW01); // 在textview01右侧
//        params.addRule(RelativeLayout.ALIGN_BASELINE, ID_TEXTVIEW01); // baseline对齐，就是中心线
        cirparams.leftMargin = 30;
//        circleView.setLayoutParams(params);
        circleView.setBackgroundColor(getResources().getColor(R.color.red));
        layout.addView(circleView, 1, cirparams);
        
    }
}
