package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/", description = "这是我全部的get方法")
public class MyGetMethod {

    //接口返回cookie信息
    @RequestMapping(value = "/getCookies", method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到cookies", httpMethod = "GET")
    public String getCookies(HttpServletResponse respone) {
        Cookie cookie = new Cookie("login", "true");
        respone.addCookie(cookie);
        return "获得cookies信息成功";
    }

    /**
     * 要求客户端携带cookies访问
     * 这是一个携带cookies信息才能访问的get请求
     */
    @RequestMapping(value = "/get/with/cookies", method = RequestMethod.GET)
    @ApiOperation(value = "这是一个携带cookies信息才能访问的get请求", httpMethod = "GET")
    public String getWithCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            return "必须携带cookies信息访问";
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")) {
                return "恭喜你访问成功";
            }
        }

        return "你必须携带正确的cookies信息";
    }

    /**
     * 一个携带参数才能访问的get请求
     * 第一种实现方式 key=value&key=value
     * 模拟获取商品列表
     */
    @RequestMapping(value = "/get/with/param", method = RequestMethod.GET)
    @ApiOperation(value = "这是一个携带参数才能访问的get请求", httpMethod = "GET")
    public Map getList(@RequestParam Integer start, @RequestParam Integer end) {
        Map<String, Integer> list = new HashMap<>();
        list.put("水果", 23);
        list.put("皮鞋", 323);
        list.put("表", 3233);
        return list;
    }

    /**
     * 第二种需要携带参数访问的get请求
     * url： ip:port/get/with/param/10/20
     */
    @RequestMapping(value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "这是第二个携带参数才能访问的get请求", httpMethod = "GET")
    public Map myGetList(@PathVariable Integer start, @PathVariable Integer end) {
        Map<String, Integer> list = new HashMap<>();
        list.put("水果", 33);
        list.put("皮鞋", 123);
        list.put("表", 22);
        return list;
    }

}
