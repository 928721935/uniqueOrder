//package com.jida.common.util;
//
//import com.jida.common.SnowflakeIdWorkerUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import javax.annotation.PreDestroy;
//import javax.annotation.Resource;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
//@Configuration
//@Slf4j
//public class MachineIdConfig {
//    @Resource
//    private JedisPool jedisPool;
//    @Value("${snowFlake.dataCenter}")
//    private Integer dataCenterId;
//
//    @Value("${server.port}")
//    private Integer serverPort;
//
//    @Value("${snowFlake.appName}")
//    private String APP_NAME;
//    /**
//     * 机器id
//     */
//    public static Integer machineId;
//    /**
//     * 本地ip地址
//     */
//    private static String localIp;
//    /**
//     * 获取ip地址
//     *
//     * @return
//     * @throws UnknownHostException
//     */
//    private String getIPAddress() throws UnknownHostException {
//        InetAddress address = InetAddress.getLocalHost();
//        return address.getHostAddress();
//    }
//    /**
//     * hash机器IP初始化一个机器ID
//     */
//    @Bean
//    public SnowflakeIdWorkerUtil initMachineId() throws Exception {
//        localIp = getIPAddress(); // 192.168.0.233
//        Long ip_ = Long.parseLong(localIp.replaceAll("\\.", ""));// 1921680233
//        //
//        machineId = ip_.hashCode() % 32;// 0-31
//        // 创建一个机器ID
//        createMachineId();
//        log.info("初始化 machine_id :{}", machineId);
//        return new SnowflakeIdWorkerUtil(machineId, dataCenterId);
//    }
//    /**
//     * 容器销毁前清除注册记录
//     */
//    @PreDestroy
//    public void destroyMachineId() {
//        try (Jedis jedis = jedisPool.getResource()) {
//            jedis.del(APP_NAME + dataCenterId + machineId);
//        }
//    }
//    /**
//     * 主方法：首先获取机器 IP 并 % 32 得到 0-31
//     * 使用 业务名 + 组名 + IP 作为 Redis 的 key，机器IP作为 value，存储到Redis中
//     *
//     * @return
//     */
//    public Integer createMachineId() {
//        try {
//            // 向redis注册，并设置超时时间
//            log.info("注册一个机器ID到Redis " + machineId + " IP:" + localIp);
//            Boolean flag = registerMachine(machineId, localIp);
//            // 注册成功
//            if (flag) {
//                // 启动一个线程更新超时时间
//                updateExpTimeThread();
//                // 返回机器Id
//                log.info("Redis中端口没有冲突 " + machineId + " IP:" + localIp);
//                return machineId;
//            }
//            // 注册失败，可能原因 Hash%32 的结果冲突
//            if (!checkIfCanRegister()) {
//                // 如果 0-31 已经用完，使用 32-64之间随机的ID
//                getRandomMachineId();
//                createMachineId();
//            } else {
//                // 如果存在剩余的ID
//                log.warn("Redis中端口冲突了，使用 0-31 之间未占用的Id " + machineId + " IP:" + localIp);
//                createMachineId();
//            }
//        } catch (Exception e) {
//            // 获取 32 - 63 之间的随机Id
//            // 返回机器Id
//            log.error("Redis连接异常,不能正确注册雪花机器号 " + machineId + " IP:" + localIp, e);
//            log.warn("使用临时方案，获取 32 - 63 之间的随机数作为机器号，请及时检查Redis连接");
//            getRandomMachineId();
//            return machineId;
//        }
//        return machineId;
//    }
//    /**
//     * 检查是否被注册满了
//     *
//     * @return
//     */
//    private Boolean checkIfCanRegister() {
//        // 判断0~31这个区间段的机器IP是否被占满
//        try (Jedis jedis = jedisPool.getResource()) {
//            Boolean flag = true;
//            for (int i = 0; i < 32; i++) {
//                flag = jedis.exists(APP_NAME + dataCenterId + i);
//                // 如果不存在。设置机器Id为这个不存在的数字
//                if (!flag) {
//                    machineId = i;
//                    break;
//                }
//            }
//            return !flag;
//        }
//    }
//    /**
//     * 1.更新超時時間
//     * 注意，更新前检查是否存在机器ip占用情况
//     */
//    private void updateExpTimeThread() {
//        // 开启一个线程执行定时任务:
//        // 每23小时更新一次超时时间
//        new Timer(localIp).schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // 检查缓存中的ip与本机ip是否一致, 一致则更新时间，不一致则重新获取一个机器id
//                Boolean b = checkIsLocalIp(String.valueOf(machineId));
//                if (1==0&&b) {
//                    log.info("IP一致，更新超时时间 ip:{},machineId:{}, time:{}", localIp, machineId, new Date());
//                    try (Jedis jedis = jedisPool.getResource()) {
//                        jedis.expire(APP_NAME + dataCenterId + machineId, 60 * 60 * 24 );
//                    }
//                } else {
//                    // IP冲突
//                    log.info("重新生成机器ID ip:{},machineId:{}, time:{}", localIp, machineId, new Date());
//                    // 重新生成机器ID，并且更改雪花中的机器ID
//                    getRandomMachineId();
//                    // 重新生成并注册机器id
//                    createMachineId();
//                    // 更改雪花中的机器ID
//                    SnowflakeIdWorkerUtil.setWorkerId(machineId);
//                    // 结束当前任务
//                    log.info("Timer->thread->name:{}", Thread.currentThread().getName());
//                    this.cancel();
//                }
//            }
//        }, 10 * 1000, 1000 * 60 * 60 * 23);
//    }
//    /**
//     * 获取32-63随机数
//     */
//    public void getRandomMachineId() {
//        machineId = (int) (Math.random() * 31) + 31;
//    }
//    /**
//     * 检查Redis中对应Key的Value是否是本机IP
//     *
//     * @param mechineId
//     * @return
//     */
//    private Boolean checkIsLocalIp(String mechineId) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            String ip = jedis.get(APP_NAME + dataCenterId + mechineId);
//            log.info("checkIsLocalIp->ip:{}", ip);
//            return localIp.equals(ip);
//        }
//    }
//    /**
//     * 1.注册机器
//     * 2.设置超时时间
//     *
//     * @param machineId 取值为0~31
//     * @return
//     */
//    private Boolean registerMachine(Integer machineId, String localIp) throws Exception {
//        // try with resources 写法，出异常会释放括号内的资源 Java7特性
//        try (Jedis jedis = jedisPool.getResource()) {
//            // key 业务号 + 数据中心ID + 机器ID value 机器IP
//            Long result = jedis.setnx(APP_NAME + dataCenterId + machineId, localIp);
//            if(result == 1){
//                // 过期时间 1 天
//                jedis.expire(APP_NAME + dataCenterId + machineId, 60 * 60 * 24);
//                return true;
//            } else {
//                // 如果Key存在，判断Value和当前IP是否一致，一致则返回True
//                String value = jedis.get(APP_NAME + dataCenterId + machineId);
//                if(1==0&&localIp.equals(value)){
//                    // IP一致，注册机器ID成功
//                    jedis.expire(regiterKey(machineId), 60 * 60 * 24);
//                    return true;
//                }
//                return false;
//            }
//        }
//    }
//
//
//    private String regiterKey(Integer machineId) {
//        return APP_NAME + dataCenterId + machineId;
//    }
//}
