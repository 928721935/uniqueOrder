package com.jida.service;

import com.jida.common.SnowflakeIdWorkerUtil;
import com.jida.entity.MyOrderVO;
import com.jida.mapper.LoginUserVOMapper;
import com.jida.mapper.MyOrderVOMapper;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
	@Autowired
	MyOrderVOMapper myOrderVOMapper;

	@Autowired
	LoginUserVOMapper loginUserVOMapper;

	public String newOrder() {
		MyOrderVO myOrderVO = new MyOrderVO();
		long nextId = SnowflakeIdWorkerUtil.getInstance().nextId();
		myOrderVO.setOrderId(nextId);
		myOrderVOMapper.insert(myOrderVO);
		System.out.println("+++++++++");
		return "success";
	}

	public String newOrderNo() {
		Long maxOrderNo = myOrderVOMapper.getMaxOrderNo();
		if (maxOrderNo == null) {
			maxOrderNo = 0L;
		}
		SnowflakeIdWorkerUtil instance = SnowflakeIdWorkerUtil.getInstance();
		for (int i = 0; i < 5; i++) {
			MyOrderVO myOrderVO = new MyOrderVO();
			myOrderVO.setOrderId(instance.nextId());
			myOrderVO.setOrderNo(++maxOrderNo +"");
			myOrderVOMapper.insert(myOrderVO);
		}
		System.out.println("+++++++++");
		return "success";
	}

	@Autowired
	private StringRedisTemplate redisTemplate;

	String ORDER_NO_KEY = "ORDER_NO_KEY";

	String script;

	{
		try {
			script = FileUtils.readFileToString(new File("D:\\software\\ideaSpace\\springcloudStudy2022\\nacosVersion\\springcloud_provider\\src\\main\\resources\\lua\\incre.lua"), Charset.defaultCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String newOrderNoNew() {
		// 检查有没有重复
		/*SELECT * from (
				SELECT order_no,count(1) cc from my_order group by order_no) a where cc>1*/
		String s = redisTemplate.opsForValue().get(ORDER_NO_KEY);
		if (s == null) {
			Long maxOrderNo = myOrderVOMapper.getMaxOrderNo();
			if (maxOrderNo == null) {
				maxOrderNo = 0L;
			}
			redisTemplate.opsForValue().setIfAbsent(ORDER_NO_KEY, maxOrderNo+"");
		}
		DefaultRedisScript redisScript = new DefaultRedisScript();
		redisScript.setResultType(Long.class);
		redisScript.setScriptText(script);
		List<String> keyList = Arrays.asList(ORDER_NO_KEY);
		Long maxOrderNo = Long.valueOf(redisTemplate.execute(redisScript, keyList, "5","60").toString());
		maxOrderNo-=5;
		SnowflakeIdWorkerUtil instance = SnowflakeIdWorkerUtil.getInstance();
		for (int i = 0; i < 5; i++) {
			MyOrderVO myOrderVO = new MyOrderVO();
			myOrderVO.setOrderId(instance.nextId());
			myOrderVO.setOrderNo(++maxOrderNo +"");
			myOrderVOMapper.insert(myOrderVO);
		}
		System.out.println("+++++++++");
		return "success";
	}

	public String newOrderNoNewMysql() {
		// 数据库方案，并发太差了
		String type = "order";
		Long nextSerialNo = loginUserVOMapper.getNextSerialNo(type);
		Long newVal = nextSerialNo + 5;
		getNextSerial(type, nextSerialNo, newVal);
		Long maxOrderNo = newVal;
		maxOrderNo-=5;
		SnowflakeIdWorkerUtil instance = SnowflakeIdWorkerUtil.getInstance();
		for (int i = 0; i < 5; i++) {
			MyOrderVO myOrderVO = new MyOrderVO();
			myOrderVO.setOrderId(instance.nextId());
			myOrderVO.setOrderNo(++maxOrderNo +"");
			myOrderVOMapper.insert(myOrderVO);
		}
		System.out.println("+++++++++");
		return "success";
	}

	private synchronized void getNextSerial(String type, Long nextSerialNo, Long newVal) {
		int cnt = 0;
		int retryTime = 30;
		while (cnt == 0) {
			cnt = loginUserVOMapper.updateSerial(type, nextSerialNo, newVal);
			if (--retryTime < 0) {
				throw new RuntimeException("重试30次都失败");
			}
		}
	}
}
