package com.imooc.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController  {
    @GetMapping("/hello")
    public Object hello() {
        return "hello";
    }


}
