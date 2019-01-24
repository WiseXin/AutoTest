package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;
    //用来存储cookies
    private BasicCookieStore cookieStore;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        //从配置文件中拼接测试的url
        String testUrl = this.url + bundle.getString("getCookies.uri");

        //测试逻辑代码书写
        HttpGet get = new HttpGet(testUrl);
//        CloseableHttpClient client = HttpClientBuilder.create().build();
        cookieStore = new BasicCookieStore();
        //建立带cookie的httpClient
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
//        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        HttpResponse response = client.execute(get);//发送请求，发送成功后cookie将存在于cookieStore中
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);

        //获取cookies信息
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name:" + name + "\n cookie value:" + value);
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookie() throws IOException {
        String testurl = url + bundle.getString("test.get.with.cookies");
        HttpGet get = new HttpGet(testurl);
        //设置cookies信息
        CloseableHttpClient build = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        //发送get请求 返回响应码
        CloseableHttpResponse response = build.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }

    }

    public void testPostMethond() {

    }
}
