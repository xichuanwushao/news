package com.xichuan.user.controller;

import com.xichuan.vommon.result.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api
public class TestController {

    @GetMapping("/sayHello")
    @ApiOperation("sayHello测试方法")
    public JSONResult sayHello(){
        return JSONResult.ok("message hello world 5 ");
    }

    @PostMapping("/sayHello2")
    @ApiOperation("sayHello测试方法")
    public JSONResult sayHello2( String req){
        return JSONResult.ok("message Hello, ");
    }

    @GetMapping("/registerUser")
    @ApiOperation("测试方法")
    public JSONResult registerUser(){
        return JSONResult.ok("messagehello world 5 ");
    }
    @GetMapping("/searchUserPermissions")
    @ApiOperation("测试方法")
    public JSONResult searchUserPermissions(int id){
        return JSONResult.ok("message search UserPermissions");
    }


    @PostMapping("/addUser")
    @ApiOperation("添加用户测试用户权限")
    public JSONResult addUser(){
        return JSONResult.ok("用户添加成功");
    }


}
