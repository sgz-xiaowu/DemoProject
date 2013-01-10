package com.mobile.example.chapter.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.code.R;

import android.R.anim;
import android.R.color;
import android.R.integer;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ColorDisplayActivtiy extends Activity
{
    private Spinner mColorLayoutSpinner;
    private Spinner mColorTextSpinner;
    private RelativeLayout mTestLayout;
    private TextView mTestText;;
    
    private HashMap<String, Integer> mColorMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_color_display);

        mColorLayoutSpinner = (Spinner) findViewById(R.id.spinner1);
        mColorTextSpinner = (Spinner) findViewById(R.id.spinner2);
        
        mTestLayout = (RelativeLayout) findViewById(R.id.relativeLayout1);
        mTestText = (TextView) findViewById(R.id.textView4);
        
        mColorMaps = new HashMap<String, Integer>();

        AssetManager assetManager = this.getAssets();
        // assetManager.open(fileName, accessMode)
        Resources resources = this.getResources();
/*        int[] resArrayInts = resources.getIntArray(R.array.color);
        int[] resArrayInts1 = resources.getIntArray(R.array.month_num1);
        int[] resArrayInts2 = resources.getIntArray(R.array.month_num2);
        printlnArrays("color", resArrayInts);
        printlnArrays("month_num1", resArrayInts1);
        printlnArrays("month_num2", resArrayInts2);*/
        int[] resArrayIntsColor = resources.getIntArray(R.array.color_hex);
        printlnArrays("int_color", resArrayIntsColor);
        Log.d("count", "int_color size:" + resArrayIntsColor.length);

        String[] resArrayStrings_color = resources.getStringArray(R.array.color);
        String[] resArrayStrings_month_num = resources.getStringArray(R.array.month_num);
        Log.d("count", "string_color size:" + resArrayStrings_color.length);
//        printlnArrays("color", resArrayStrings_color);
//        printlnArrays("month_num", resArrayStrings_month_num);
        
        int nameSize = resArrayStrings_color.length;
        int valueSize = resArrayIntsColor.length;
        for (int i = 0; i < nameSize && i < valueSize; i++)
        {
            mColorMaps.put(resArrayStrings_color[i], resArrayIntsColor[i]);
        }
        
        // 如何遍历color.xml所有颜色值
//        XmlResourceParser xmlResourceParser = resources.getXml(R.color.aliceblue);
//        xmlResourceParser.
//        resources.getColor(color.background_dark)

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, /*(resource layout file,)*/
                android.R.layout.simple_spinner_dropdown_item/*simple_spinner_item*//*(textViewResourceId)*/, resArrayStrings_color);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, /*(resource layout file,)*/
                android.R.layout.simple_spinner_item/*(textViewResourceId)*/, resArrayStrings_color);
        /*
         * textViewResourceId 是spinner控件本身控件的布局
         * resource 即 DropDownViewResource 是点击spinner后，加载items项的下拉item布局
         */
        // 如果使用arrayAdapter，则spinner也将变为simple_spinner_dropdown_item后的单选样式
        // 如果使用arrayAdapter2，则spinner显示为simple_spinner_item的文本样式；点击后加载items的item是simple_spinner_dropdown_item单选布局。
        
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mColorLayoutSpinner.setAdapter(arrayAdapter2);
        //设置初始赋值index by 0.
        mColorLayoutSpinner.setSelection(3);
        mColorLayoutSpinner.setOnItemSelectedListener(new MySimpleItemSlectedListener());
        
        mColorTextSpinner.setAdapter(arrayAdapter2);
        //设置初始赋值index by 0.
        mColorTextSpinner.setSelection(0);
        mColorTextSpinner.setOnItemSelectedListener(new MySimpleItemSlectedListener());
    }

    private void printlnArrays(String resNameId, String[] arrays)
    {
        int size = arrays.length;
        for (int i = 0; i < size; i++)
        {
            Log.d(resNameId, arrays[i]);
        }
    }

    private void printlnArrays(String resNameId, int[] arrays)
    {
        int size = arrays.length;
        for (int i = 0; i < size; i++)
        {
            Log.d(resNameId, arrays[i] + "");
        }
    }
    
    class MySimpleItemSlectedListener implements OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            Log.d("dropdown view", "position=" + position + "|id=" + id);
            // 通过parent区分点击的哪一个spinner view。
            int parentId = parent.getId();
            Object item = parent.getAdapter().getItem(position);
            /*
             * Object getItem() 很重要， object仅为itemView绑定的数据对象
             * 在自定义adapter时，一定要注意，只返回相关数据Object，不能关联任何ItemView的信息
             * 
             * 这样，对于简单的ArrayAdapter，返回的ItemObject只能是String值，而非TextView。
             *
             */
            if (item instanceof String)
            {
                String name = (String) item;
                Log.d("", "item value = " + (String)item);
                int color = mColorMaps.get(name);
                if (parentId == R.id.spinner1)
                {
                    mTestLayout.setBackgroundColor(color);
                }
                else if (parentId == R.id.spinner2) 
                {
                    mTestText.setText(String.format(getString(R.string.colordisplay_content), name));
                    // 设置TextView背景颜色
//                    mTestText.setBackgroundColor(color);
                    // 设置TextView文本颜色
                    mTestText.setTextColor(color);
                }
                else
                {
                    // do nothing.
                }
            }
            else
            {
                Log.d("", "item is not String");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            
        }
    }
}
