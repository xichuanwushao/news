package com.xichuan.user.controller;

import com.xichuan.api.controller.PassportControllerApi;
import com.xichuan.vommon.result.GraceJSONResult;
import com.xichuan.vommon.util.SMSUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PassportController implements PassportControllerApi {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Autowired
    private SMSUtils smsUtils;


    @Override
    public GraceJSONResult getSMSCode() {
        String randrom = "1234";
        smsUtils.sendSMS("18119312219",randrom);
        return GraceJSONResult.ok("测试成功");
    }
}
