package com.jida.common.util;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 雪花算法初始化器
 * 初始化snowflake的dataCenterId和workerId
 * <p>
 * 1.系统启动时生成默认dataCenterId和workerId，并尝试做为key存储到redis
 * 2.若是存储成功，设置redis过时时间为24h，把当前dataCenterId和workerId传入snowflake
 * 3.若是存储失败workerId自加1，并判断workerId不大于31，重复1步骤
 * 4.定义一个定时器，每隔24h刷新redis的过时时间为24h
 *
 * @CreatedBy: yangcan 2020/5/13 14:05
 * @Description:
 */
@Component
//@Configuration
public class SnowflakeInitiator {

    /**
     * snowflake的dataCenterId和workerId
     */
    public static SnowflakeVo snowflakeVo;
    public static String prefixRedisKey = "YC_SnowflakeRedisKey";
    private static String snowflakeRedisKey;
//    private static long LockExpire = 60 * 60 * 24;
    private static long LockExpire = 60;
    private static boolean stopTrying = false;
    @Autowired
    private StringRedisTemplate redisTemplate;

    public void init() throws InterruptedException {
        if (stopTrying) {
            System.out.println("snowflake强制结束生成key，key = " + JSON.toJSONString(snowflakeVo));
            return;
        }
        if (tryInit()) {
            System.out.println("snowflake结束生成key，key = " + JSON.toJSONString(snowflakeVo));
            return;
        }
        Thread.sleep(10);
        init();
    }

    public boolean tryInit() {
        snowflakeVo = nextKey(snowflakeVo);
        snowflakeRedisKey = prefixRedisKey + "_" + snowflakeVo.getDataCenterId() + "_" + snowflakeVo.getWorkerId();
        if (redisTemplate.hasKey(snowflakeRedisKey) == false) {
            if (redisTemplate.opsForValue().setIfAbsent(snowflakeRedisKey, "1", LockExpire, TimeUnit.SECONDS)) {
                System.out.println("成功抢占锁，Constants.snowflakeVo = " + JSON.toJSONString(snowflakeVo));
                return true;
            }
        }
        return false;
    }

    /**
     * 生成下一组不重复的dataCenterId和workerId
     *
     * @return
     */
    private SnowflakeVo nextKey(SnowflakeVo snowflakeVo) {
        if (snowflakeVo == null) {
            return new SnowflakeVo(1L, 1L);
        }

        if (snowflakeVo.getWorkerId() < 31) {
            // 若是workerId < 31
            snowflakeVo.setWorkerId(snowflakeVo.getWorkerId() + 1);
        } else {
            // 若是workerId >= 31
            if (snowflakeVo.getDataCenterId() < 31) {
                // 若是workerId >= 31 && dataCenterId < 31
                snowflakeVo.setDataCenterId(snowflakeVo.getDataCenterId() + 1);
                snowflakeVo.setWorkerId(1L);
            } else {
                // 若是workerId >= 31 && dataCenterId >= 31
                snowflakeVo.setDataCenterId(1L);
                snowflakeVo.setWorkerId(1L);
                stopTrying = true;
            }
        }
        return snowflakeVo;
    }

    /**
     * 从新设置过时时间，由定时任务调用
     */
    public void resetExpire() {
        redisTemplate.expire(snowflakeRedisKey, LockExpire, TimeUnit.SECONDS);
        System.out.println("YC 执行定时任务重置snowflakeRedisKey过时时间 resetExpire() redisKey = " + snowflakeRedisKey);
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        InetAddress address = InetAddress.getLocalHost();
//        String hostAddress = address.getHostAddress();
//        System.out.println(hostAddress);
//    }

    /**
     * 容器销毁时主动删除redis注册记录，此方法不适用于强制终止Spring容器的场景，只做为补充优化
     */
    public void destroy() {
        redisTemplate.delete(snowflakeRedisKey);
        System.out.println("YC destroy snowflakeRedisKey = " + snowflakeRedisKey);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SnowflakeVo {
        private Long dataCenterId;
        private Long workerId;
    }
}