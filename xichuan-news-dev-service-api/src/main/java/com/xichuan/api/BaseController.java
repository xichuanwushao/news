package com.xichuan.api;

import com.xichuan.vommon.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    @Autowired
    public RedisOperator redis;


    public static final String MOBILE_SMSCODE = "mobile:smscode";


}
