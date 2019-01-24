package com.course.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", description = "这是第一个版本的demo")
@RequestMapping("v1")
public class Demo {
    // 首先获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount", method = RequestMethod.GET)
    @ApiOperation(value = "可以获取到用户数", httpMethod = "GET")
    public int getUestCount() {
        return template.selectOne("getUserCount");
    }
}
