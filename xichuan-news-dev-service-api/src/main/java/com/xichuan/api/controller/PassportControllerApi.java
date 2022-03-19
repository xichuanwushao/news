package com.xichuan.api.controller;

import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@Api(value = "用户注册登录", tags = {"用户注册登录的controller"})
public interface PassportControllerApi {

    @ApiOperation(value = "获得短信验证码", notes = "获得短信验证码", httpMethod = "GET")
    @GetMapping("/getSMSCode")
    public GraceJSONResult getSMSCode();

}
