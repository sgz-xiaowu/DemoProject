package com.code.demoproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.code.R;
import com.code.constant.IntentConstant;

public class MainActivity extends Activity implements OnItemClickListener
{
    public static final String TAG = "";
//    public static final String QUERY_ACTION = Intent.ACTION_MAIN;
    public static final String QUERY_ACTION = IntentConstant.ACTION;
    
    private ListView m_lisView;
    private ArrayList<SubDemoInfo> m_listSubDemoInfos;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 取得listview
        m_lisView = (ListView) findViewById(R.id.demo_list);
        m_listSubDemoInfos = new ArrayList<SubDemoInfo>();
        
        // 查询subdemo的activities info
        queryAppSubDemoInfo();
        
        // 设置adapter 填充数据
        DemoAdapter demoAdapter = new DemoAdapter();
        m_lisView.setAdapter(demoAdapter);
        m_lisView.setOnItemClickListener(this);
    }

    // 遍历查询符合条件的Activities
    public void queryAppSubDemoInfo()
    {
        // 定义匹配条件的Intent
        PackageManager pm = this.getPackageManager();
        Intent subIntent = new Intent(QUERY_ACTION, null);
//        subIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        subIntent.addCategory(IntentConstant.CATEGORY);

        // 执行查询
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(subIntent, PackageManager.MATCH_DEFAULT_ONLY/* 0 */);

        // 调用系统排序
        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        
        // for each 遍历fetch subdemo activities info
        if (null == resolveInfos || resolveInfos.size() == 0)
        {
            return;
        }
        
        if (null != m_listSubDemoInfos)
        {
            m_listSubDemoInfos.clear();
            
            for (ResolveInfo resolveInfo : resolveInfos)
            {
                String activityName = resolveInfo.activityInfo.name;    // 获取subdemo(启动应用程序)的activity name；
                String pkgName = resolveInfo.activityInfo.packageName;  // 获取subdemo(启动应用程序)的package name；
                String appLabel = resolveInfo.loadLabel(pm).toString(); // 获取subdemo(启动应用程序)的标签；
                
                Drawable appIcon = resolveInfo.loadIcon(pm);            // 获取subdemo(启动应用程序)的Icon；
                
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(pkgName, activityName));
                
                // for each 缓存信息到缓存数据结构中
                // 创建一个SubDemoInfo对象，缓存数据
                SubDemoInfo subInfo = new SubDemoInfo();
                subInfo.setDemoAppLabel(appLabel);
                subInfo.setDemoPkgName(pkgName);
                subInfo.setDemoAppIcon(appIcon);
                subInfo.setDemoIntent(launchIntent);
                
                m_listSubDemoInfos.add(subInfo);
                
                Log.i(TAG, appLabel + " activityName---" + activityName+ " pkgName---" + pkgName);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Intent subIntent = m_listSubDemoInfos.get(position).getIntent();
        startActivity(subIntent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class DemoAdapter extends BaseAdapter
    {
        LayoutInflater inflater = null;
        public DemoAdapter()
        {
            Context context = MainActivity.this;
            
            if (null == inflater)
            {
                inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            }
        }
        
        @Override
        public int getCount()
        {
            return null != m_listSubDemoInfos ? m_listSubDemoInfos.size() : 0;
        }

        @Override
        public Object getItem(int position)
        {
            return m_listSubDemoInfos.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 把缓存结构中的数据信息填充至View.
            View view = null;
            ViewHolder holder = null;
            if (convertView == null || convertView.getTag() == null)
            {  
                view = inflater.inflate(R.layout.subdemo_item_layout, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            }   
            else
            {  
                view = convertView;
                holder = (ViewHolder) convertView.getTag();
            }
            SubDemoInfo appInfo = (SubDemoInfo) m_listSubDemoInfos.get(position);
            holder.imageView.setImageDrawable(appInfo.getAppIcon());
            holder.labelView.setText("appLabel:  " + appInfo.getAppLabel());
            holder.packageView.setText("包名:         " + appInfo.getPkgName());
            return view;
        }
    }

    static class ViewHolder
    {
        ImageView imageView;
        TextView  labelView;
        TextView  packageView;

        public ViewHolder(View view)
        {
            this.imageView = (ImageView) view.findViewById(R.id.image_icon);  
            this.labelView = (TextView) view.findViewById(R.id.text_lable);  
            this.packageView = (TextView) view.findViewById(R.id.text_pkg_name);  
        }
    }

}
