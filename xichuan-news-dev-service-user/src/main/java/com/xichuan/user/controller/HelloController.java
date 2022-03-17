package com.xichuan.user.controller;

import com.xichuan.api.controller.HelloControllerApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController implements HelloControllerApi {

    final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Override
    public Object hello() {
        logger.debug("debug: hello~");
        logger.info("info: hello~");
        logger.warn("warn: hello~");
        logger.error("error: hello~");
        return "hello";
    }




}
