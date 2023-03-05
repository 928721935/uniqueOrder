package com.jida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@EnableDiscoveryClient
@RestController
@RequestMapping("/order")
public class BugController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("/bug")
	public String bug(String name){
		//RateLimiter
		String forObject = restTemplate.getForObject("http://springcloud-provider/ticket", String.class);
		return name+"购买了"+forObject;
	}

	@GetMapping("/getOrder")
	public String getOrder(){
		//RateLimiter
		String forObject = restTemplate.getForObject("http://springcloud-provider/order/newOrder", String.class);
		return "success";
	}

	@GetMapping("/getOrderNo")
	public String getOrderNo(){
		//RateLimiter
		String forObject = restTemplate.getForObject("http://springcloud-provider/order/newOrderNo", String.class);
		return "success";
	}
}
