package com.xichuan.user.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.controller.user.UserControllerApi;
import com.xichuan.model.pojo.AppUser;
import com.xichuan.model.pojo.bo.UpdateUserInfoBO;
import com.xichuan.model.pojo.vo.UserAccountInfoVO;
import com.xichuan.user.service.UserService;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author : wuxiao
 * @date : 9:51 2022/4/7
 */
@RestController
public class UserController extends BaseController implements UserControllerApi {
    final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult getAccountInfo(String userId) {

        // 0. 判断参数不能为空
        if (StringUtils.isBlank(userId)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.UN_LOGIN);
        }

        // 1. 根据userId查询用户的信息
        AppUser user = getUser(userId);

        // 2. 返回用户信息
        UserAccountInfoVO accountInfoVO = new UserAccountInfoVO();
        BeanUtils.copyProperties(user, accountInfoVO);

        return GraceJSONResult.ok(accountInfoVO);
    }


    private AppUser getUser(String userId) {
        AppUser  user = userService.getUser(userId);
        return user;
    }

    @Override
    public GraceJSONResult updateUserInfo(
            @Valid UpdateUserInfoBO updateUserInfoBO,
            BindingResult result) {

        // 0. 校验BO
        if (result.hasErrors()) {
            Map<String, String> map = getErrors(result);
            return GraceJSONResult.errorMap(map);
        }

        // 1. 执行更新操作
        userService.updateUserInfo(updateUserInfoBO);
        return GraceJSONResult.ok();
    }

}
