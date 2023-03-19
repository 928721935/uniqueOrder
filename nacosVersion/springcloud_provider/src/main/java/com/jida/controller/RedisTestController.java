package com.jida.controller;

import com.jida.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/redis")
@RestController
public class RedisTestController {

    @Resource
    private RedisService redisService;

    @GetMapping("/setKey")
    public String setKey() {
        return redisService.setKey();
    }

    @GetMapping("/getHashField")
    public String getHashField() {
        return redisService.getHashField();
    }

    @GetMapping("/fillCurrUserName")
    public String fillCurrUserName() {
        return redisService.fillCurrUserName();
    }
}