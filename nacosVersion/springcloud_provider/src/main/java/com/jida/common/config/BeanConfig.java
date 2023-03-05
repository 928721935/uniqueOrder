package com.jida.common.config;

import com.jida.common.util.SnowflakeInitiator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

//@Configuration
@Component
public class BeanConfig {
    @Resource
    SnowflakeInitiator snowflakeInitiator;

    @PostConstruct
    public void init() throws InterruptedException {
        System.out.println("YC init something start");
        snowflakeInitiator.init();
        System.out.println("YC init something end");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("YC destroy something start");
        snowflakeInitiator.destroy();
        System.out.println("YC destroy something end");
    }
}
