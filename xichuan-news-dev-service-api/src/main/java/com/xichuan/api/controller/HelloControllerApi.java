package com.xichuan.api.controller;


import com.xichuan.vommon.result.GraceJSONResult;
import org.springframework.web.bind.annotation.GetMapping;

public interface HelloControllerApi {

    @GetMapping("/hello")
    public Object hello();

    @GetMapping("/redis")
    public Object redis() ;
}
