package com.code.send_message;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 测试发送不同delay time的消息，是按照什么顺序执行的。
 * 
 * HandlerLeak 参考文章http://hi.baidu.com/cenxcen/item/91ef79985d5c2532336eebc1
 * 
 * @author Administrator
 *
 */
public class SendMessageHandleOrderActivity extends Activity implements OnClickListener
{
    static int ID_BTN = 100; 
    
    private static final int MSG_HandlerLeak = 10;
    Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            // 下面是通过log，打出的message执行顺序。
            // 说明：looper对消息的取得、分发执行，是按照消息的已定时间来的，而不是按照发送顺序固定好的。
            // 2~~~~handle message[2]
            // 4~~~~handle message[4]
            // 3~~~~handle message[3]
            // 1~~~~handle message[1]
            switch (msg.what)
            {
            case 1:
                Log.d("", "1~~~~handle message[" + msg.what + "]");
                break;
            case 2:
                Log.d("", "2~~~~handle message[" + msg.what + "]");
                break;
            case 3:
                Log.d("", "3~~~~handle message[" + msg.what + "]");
                break;
            case 4:
                Log.d("", "4~~~~handle message[" + msg.what + "]");
                break;
            case 10:
                Log.d("", "leak~~~~handle message[" + msg.what + "]");
                break;

            default:
                break;
            }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
//        LayoutInflater inflater = this.getLayoutInflater();
//        getSystemService(LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(, root)
//        TextView tv = new TextView(this);
        Button tv = new Button(this);
        tv.setText("这是Send Message Handle Order Test!");
        tv.setId(ID_BTN);
        
        setContentView(tv);
        
        int nPid = android.os.Process.myPid();  
        Log.d("", "nPid is : " + nPid);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case 100:
            sendseriasMsgs();
            break;

        default:
            break;
        }
    }

    private void sendseriasMsgs()
    {
        if (null != mHandler)
        {
            // Handler leak 通过log一直测不出来。
            mHandler.sendEmptyMessageDelayed(MSG_HandlerLeak, 6000);
//            mHandler.sendEmptyMessageDelayed(1, 100);
            mHandler.sendEmptyMessage(2);
//            mHandler.sendEmptyMessageDelayed(3, 50);
//            mHandler.sendEmptyMessage(4);
        }
    }
    
    @Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){  
                AlertDialog.Builder alertbBuilder=new AlertDialog.Builder(this);  
                alertbBuilder.setTitle("真的要离开？").setMessage("你确定要离开？").setPositiveButton("确定", new DialogInterface.OnClickListener() {  
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                                //结束这个Activity   ？？难道每个activity的pid还都不一样？
                        int nPid = android.os.Process.myPid(); 
                        Log.d("", "android.os.Process.killProcess： " + nPid);
                        try
                        {
                            Thread.sleep(30);
                        }
                        catch (InterruptedException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        // 退出app，应该在最后一个Activity中做此处理，即MainActivity.
                        android.os.Process.killProcess(nPid);  
                            
                            // 无用
//                        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);   
//                        manager.restartPackage(getPackageName());
                        }  
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {  
      
                        @Override  
                        public void onClick(DialogInterface dialog, int which) {  
                                dialog.cancel();  
                        }  
                }).create();  
                alertbBuilder.show();  
        }  
        return true;  
    }  
}
