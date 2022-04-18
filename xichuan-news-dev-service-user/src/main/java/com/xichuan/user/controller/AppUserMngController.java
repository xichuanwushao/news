package com.xichuan.user.controller;

import com.xichuan.api.BaseController;
import com.xichuan.api.controller.user.AppUserMngControllerApi;
import com.xichuan.user.service.UserService;
import com.xichuan.vommon.result.GraceJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author : wuxiao
 * @date : 20:35 2022/4/18
 */
@RestController
public class AppUserMngController extends BaseController implements AppUserMngControllerApi {
    final static Logger logger = LoggerFactory.getLogger(AppUserMngController.class);


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


        return GraceJSONResult.ok();
    }

}
