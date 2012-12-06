package com.code.demoproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/*
 * Model类 ，用来存储子模块demo（应用程序）信息  
 */
public class SubDemoInfo
{
    private String appLabel;    //应用程序标签   
    private Drawable appIcon ;  //应用程序图像   
    private Intent intent ;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity   
    private String pkgName ;    //应用程序所对应的包名   
      
    public SubDemoInfo(){}  
      
    public String getAppLabel() {  
        return appLabel;  
    }  
    public void setDemoAppLabel(String appName) {  
        this.appLabel = appName;  
    }  
    public Drawable getAppIcon() {  
        return appIcon;  
    }  
    public void setDemoAppIcon(Drawable appIcon) {  
        this.appIcon = appIcon;  
    }  
    public Intent getIntent() {  
        return intent;  
    }  
    public void setDemoIntent(Intent intent) {  
        this.intent = intent;  
    }  
    public String getPkgName(){  
        return pkgName ;  
    }  
    public void setDemoPkgName(String pkgName){  
        this.pkgName=pkgName ;  
    }  
}
