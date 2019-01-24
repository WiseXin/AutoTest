package com.course.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {

        //存放结果
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        CloseableHttpClient build = HttpClientBuilder.create().build();
        CloseableHttpResponse execute = build.execute(get);
        String s = EntityUtils.toString(execute.getEntity(), "UTF-8");
//        HttpClient client = new DefaultHttpClient();
//        HttpResponse response=client.execute(get);
//        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(s);
    }
}
