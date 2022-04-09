package com.xichuan.api.controller.admin;

import com.xichuan.model.pojo.bo.AdminLoginBO;
import com.xichuan.vommon.result.GraceJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author : wuxiao
 * @date : 21:59 2022/4/9
 */
@Api(value = "管理员admin维护", tags = {"管理员admin维护的controller"})
@RequestMapping("adminMng")
public interface AdminMngControllerApi {


    @ApiOperation(value = "hello方法的接口", notes = "hello方法的接口", httpMethod = "POST")
    @PostMapping("/adminLogin")
    public GraceJSONResult adminLogin(@RequestBody @Valid AdminLoginBO adminLoginBO,
                                      BindingResult result,
                                      HttpServletRequest request,
                                      HttpServletResponse response);
}
