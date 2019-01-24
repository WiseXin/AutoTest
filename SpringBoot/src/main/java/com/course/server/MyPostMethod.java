package com.course.server;

import com.course.bean.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/", description = "这是我全部的post请求")
@RequestMapping("/v1")
public class MyPostMethod {
    //这个变量是用来存cookie信息
    private static Cookie cookie;

    //用户登录成功获取Cookies，然后再访问其他接口获取到列表
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录接口，成功后获取cookies信息", httpMethod = "POST")
    public String login(HttpServletResponse response, @RequestParam(value = "userName", required = true) String userName,
                        @RequestParam(value = "password", required = true) String password) {

        if (userName.equals("xin") && password.equals("123456")) {
            cookie = new Cookie("login", "true");
            response.addCookie(cookie);
            return "恭喜你登录成功";
        }
        return "用户名或者密码错误";
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", httpMethod = "POST")
    public String getUserList(HttpServletRequest request,
                              @RequestBody UserInfo u) {
        UserInfo user;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookie
        for (Cookie cookie : cookies) {
            if (cookie.getName() == "login" && cookie.getValue() == "true"
                    && u.getUserName() == "xin" && u.getPassword() == "123456") {
                user = new UserInfo();
                user.setName("lisi");
                user.setAge("18");
                user.setSex("man");
                return user.toString();
            }
        }

        return "参数不合法";
    }

}
