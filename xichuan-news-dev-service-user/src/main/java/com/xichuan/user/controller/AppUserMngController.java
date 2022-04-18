package com.xichuan.user.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.controller.user.AppUserMngControllerApi;
import com.xichuan.user.service.AppUserMngService;
import com.xichuan.user.service.UserService;
import com.xichuan.vommon.enums.UserStatus;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.result.ResponseStatusEnum;
import com.xichuan.vommon.util.JsonUtils;
import com.xichuan.vommon.util.PagedGridResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author : wuxiao
 * @date : 20:35 2022/4/18
 */
@RestController
public class AppUserMngController extends BaseController implements AppUserMngControllerApi {
    final static Logger logger = LoggerFactory.getLogger(AppUserMngController.class);

    @Autowired
    private AppUserMngService appUserMngService;

    @Autowired
    private UserService userService;

    @Override
    public GraceJSONResult queryAll(String nickname,
                                    Integer status,
                                    Date startDate,
                                    Date endDate,
                                    Integer page,
                                    Integer pageSize) {

        System.out.println(startDate);
        System.out.println(endDate);

        if (page == null) {
            page = COMMON_START_PAGE;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }



        PagedGridResult result = appUserMngService.queryAllUserList(nickname,
                status,
                startDate,
                endDate,
                page,
                pageSize);

        return GraceJSONResult.ok(result);
    }
    @Override
    public GraceJSONResult userDetail(String userId) {
        return GraceJSONResult.ok(userService.getUser(userId));
    }

    @Override
    public GraceJSONResult freezeUserOrNot(String userId, Integer doStatus) {
        if (!UserStatus.isUserStatusValid(doStatus)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.USER_STATUS_ERROR);
        }
        appUserMngService.freezeUserOrNot(userId, doStatus);

        // 刷新用户状态：
        // 1. 删除用户会话，从而保障用户需要重新登录以后再来刷新他的会话状态
        // 2. 查询最新用户的信息，重新放入redis中，做一次更新
        redis.del(REDIS_USER_INFO + ":" + userId);

        return GraceJSONResult.ok();
    }


}
