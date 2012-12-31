package com.code.subdemo;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.code.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HttpGetTest extends Activity implements OnClickListener
{
    private Handler m_handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_http_get);
        
        Button get = (Button) findViewById(R.id.btn_http_get);
        get.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_http_get:
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    Looper.prepare();
                    getDataFromServer();
                    Looper.loop();
                }
            }, "Http-Get").start();
            
            // network on main thread exception.
//            m_handler.post(new Runnable()
//            {
//                @Override
//                public void run()
//                {
////                    Looper.prepare();
//                    getDataFromServer();
////                    Looper.loop();
//                }
//            });
            break;

        default:
            break;
        }
    }

    private void getDataFromServer()
    {
//        String uriServer = "http://www.baidu.com";
        /*192.168.5.110 为本机PC ip地址*/
        String uriServer = "http://192.168.5.110:8080/HelloWorld/";
        /*在模拟器或真机访问时，不能使用localhost；只能在电脑上运行，通过浏览器调用localhost*/
//        String uriServer = "http://localhost:8080/HelloWorld/index.jsp";
        /*创建HttpGet连接*/
        HttpGet httpRequest = new HttpGet(uriServer);
        try
        {
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            int code = httpResponse.getStatusLine().getStatusCode();
            if (code == 200)
            {
                /*取出应答字符串*/
                String result = EntityUtils.toString(httpResponse.getEntity()); 
                /*删除多余字符串*/
//                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                reminder(result);
            }
            else
            {
                String errorReason = "Error Response: " + httpResponse.getStatusLine().toString();
//                Toast.makeText(this, errorReason, Toast.LENGTH_LONG).show();
                reminder(errorReason);
            }
        }
        catch (ClientProtocolException e)
        {
            reminder(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            reminder(e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    private void reminder(String reminder)
    {
        Toast.makeText(this, reminder, Toast.LENGTH_LONG).show();
    }
}
