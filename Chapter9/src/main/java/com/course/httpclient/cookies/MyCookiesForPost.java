package com.course.httpclient.cookies;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    ResourceBundle bundle;
    String url;
    String result = "";
    CookieStore cookieStore;

    @BeforeTest
    public void beforeGetResources() {
        bundle = ResourceBundle.getBundle("application");
        url = bundle.getString("test.url");
    }

    @Test
    public void getCookies() throws IOException {
        //从配置文件中拼接测试的url
        String uri = bundle.getString("getCookies.uri");
        String testUrl = url + uri;

        HttpGet get = new HttpGet(testUrl);
        //创建存放cookies的地方
        cookieStore = new BasicCookieStore();
        //创建httpClient
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse httpResponse = client.execute(get);
        HttpEntity entity = httpResponse.getEntity();
        result = EntityUtils.toString(entity, "utf-8");
        System.out.println(result);
        List<Cookie> cookies = cookieStore.getCookies();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println(name + ";" + value);
        }
    }

    @Test(dependsOnMethods = {"getCookies"})
    public void testPostMethod() throws IOException {
        //拼接url
        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = url + uri;
        //post
        HttpPost post = new HttpPost(testUrl);
        //创建httpClent和设置cookies
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        //添加json参数
        JSONObject param = new JSONObject();
        param.put("name", "xin");
        param.put("age", "22");

        //设置请求头信息
        post.setHeader("content-type", "application/json");

        //将json参数转换为entity
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        //将参数信息添加到方法中
        post.setEntity(entity);
        //执行请求
        CloseableHttpResponse respone = client.execute(post);
        //接收响应结果
        String result = EntityUtils.toString(respone.getEntity());
        System.out.println(result);

        //将响应结果转换为JSON
        JSONObject resultJson = new JSONObject(result);
        String success = (String) resultJson.get("xin");
        String status = (String) resultJson.get("status");
        //判断结果是否符合预期
        Assert.assertEquals("success", success);
        Assert.assertEquals("1", status);
    }

}
