package com.xichuan.user.controller;

import com.xichuan.api.controller.HelloControllerApi;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController implements HelloControllerApi {
    @Override
    public Object hello() {
        return "hello";
    }




}
