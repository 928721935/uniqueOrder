package com.jida.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private Integer port;
    @Value("${spring.redis.password:-1}")
    private String password;
    @Bean
    public JedisPool jedisPool() {
        // 1.设置连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置池中最大连接数
        config.setMaxTotal(50);
        // 设置空闲时池中保有的最大连接数
        config.setMaxIdle(10);
        config.setMaxWaitMillis(3000L);
        config.setTestOnBorrow(true);
        log.info(password);
        // 2.设置连接池对象
        if("-1".equals(password)){
            log.info("Redis不通过密码连接");
            return new JedisPool(config, host, port,0);
        } else {
            log.info("Redis通过密码连接" + password);
            return new JedisPool(config, host, port,0, password);
        }
    }


}
