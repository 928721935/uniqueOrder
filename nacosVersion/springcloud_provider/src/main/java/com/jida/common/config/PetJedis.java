//package com.jida.common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//
//import javax.annotation.Resource;
//@Configuration
//public class PetJedis {
//
//    private static final String JEDIS_NAME = "petJedis";
//
//    @Resource
//    private JedisPool jedisPool;
//
//
//    @Bean(name = JEDIS_NAME)
//    public Jedis getPetRedis() {
//        return jedisPool.getResource();
//    }
//}
