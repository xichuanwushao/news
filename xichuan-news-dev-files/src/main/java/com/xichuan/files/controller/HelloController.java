package com.xichuan.files.controller;

import com.xichuan.api.controller.HelloControllerApi;
import com.xichuan.vommon.result.GraceJSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements HelloControllerApi {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    public Object hello() {
        return GraceJSONResult.ok("Hello World!");
    }

    @Override
    public Object redis() {
        return null;
    }


}
