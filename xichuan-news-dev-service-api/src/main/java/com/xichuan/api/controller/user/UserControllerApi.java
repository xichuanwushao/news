package com.xichuan.api.controller.user;

import com.xichuan.model.pojo.bo.UpdateUserInfoBO;
import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author : wuxiao
 * @date : 9:49 2022/4/7
 */

@Api(value = "用户信息相关Controller", tags = {"用户信息相关Controller"})
@RequestMapping("user")
public interface UserControllerApi {

    @ApiOperation(value = "获得用户账户信息", notes = "获得用户账户信息", httpMethod = "POST")
    @PostMapping("/getAccountInfo")
    public GraceJSONResult getAccountInfo(@RequestParam String userId);


    @ApiOperation(value = "修改/完善用户信息", notes = "修改/完善用户信息", httpMethod = "POST")
    @PostMapping("/updateUserInfo")
    public GraceJSONResult updateUserInfo(
            @RequestBody @Valid UpdateUserInfoBO updateUserInfoBO,
            BindingResult result);
}