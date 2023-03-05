package com.jida.controller;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/miaoSha")
@RestController
public class DemoController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String GOODS_STOCK_KEY = "goods:001";  //  秒杀商品库存
    private static final String GOODS_USER_KEY = "users:001";   //  抢购成功的用户列表

    /**
     * 在不加锁的情况下，会发生超卖
     */
    @GetMapping("/seckill")
    public String seckill() {
        int userId = (int) (Math.random() * 1000);

        ValueOperations valueOps = stringRedisTemplate.opsForValue();
        ListOperations listOps = stringRedisTemplate.opsForList();

        int stock = Integer.parseInt(valueOps.get(GOODS_STOCK_KEY).toString());

        if (stock > 0) {
            valueOps.decrement(GOODS_STOCK_KEY);
            listOps.leftPush(GOODS_USER_KEY, String.valueOf(userId));
            return "抢购成功";
        } else {
            return "商品已售罄";
        }
    }

    /**
     * 将多个命令打包成一个原子操作，利用redis单线程执行命令的特性，在不加锁的情况下避免了资源竞争
     */
    @GetMapping("/seckill_lua")
    public String seckill_lua() {
        int userId = (int) (Math.random() * 1000);

        String script = "if tonumber(redis.call('get', KEYS[1])) > 0 then " +
                "redis.call('decr', KEYS[1]); " +
                "redis.call('lpush', KEYS[2], ARGV[1]); " +
                "return 1; " +
                "else " +
                "return 0; " +
                "end; ";

        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setResultType(Long.class);
        redisScript.setScriptText(script);

        List<String> keyList = Arrays.asList(GOODS_STOCK_KEY, GOODS_USER_KEY);

        Long result = Long.valueOf(stringRedisTemplate.execute(redisScript, keyList, String.valueOf(userId)).toString());

        if (result == 1) {
            return "抢购成功";
        } else {
            return "商品已售罄";
        }
    }
}