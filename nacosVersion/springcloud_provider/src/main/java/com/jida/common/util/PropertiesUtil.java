package com.jida.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertiesUtil {

    private static Environment env;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void getApiToken() {
        env = this.environment;
    }

    public static String getProperty(String key) {
        return env.getProperty(key);
    }
}