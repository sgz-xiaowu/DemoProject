package com.code.subdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.code.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HttpPostTest extends Activity implements OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_http_post);
        
        Button post = (Button) findViewById(R.id.btn_http_post);
        post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_http_post:
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    Looper.prepare();
                    postDataToServer();
                    Looper.loop();
                }
            }, "Http-Get").start();
            break;

        default:
            break;
        }        
    }

    protected void postDataToServer()
    {
        String uriServer = "http://www.baidu.com";
        /*创建HttpPost连接*/
        HttpPost httpRequest = new HttpPost(uriServer);
        /*Post运行传递变量必须用NameValuePair[]数组存储*/
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("str", "I am posting value"));
        try
        {
            /*发出Http request*/
            httpRequest.setEntity(new UrlEncodedFormEntity(params));
            /*取出Http Response*/
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            int code = httpResponse.getStatusLine().getStatusCode();
            /*若状态码为200，则Ok*/
            if (code == 200)
            {
                /*取出应答字符串*/
                String result = EntityUtils.toString(httpResponse.getEntity()); 
                /*删除多余字符串*/
//                result = erase_replace();
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
            // TODO Auto-generated catch block
            reminder(e.getMessage());
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            reminder(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void reminder(String reminder)
    {
        Toast.makeText(this, reminder, Toast.LENGTH_LONG).show();
    }
}
