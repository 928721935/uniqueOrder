package com.jida.controller;

import com.jida.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {
	@Autowired
	OrderService orderService;
	
	@GetMapping("/newOrder")
	public String newOrder(){
		return orderService.newOrder();
	}

	@GetMapping("/newOrderNo")
	public String newOrderNo(){
		return orderService.newOrderNo();
	}

	@GetMapping("/newOrderNoNew")
	public String newOrderNoNew(){
		return orderService.newOrderNoNew();
	}

	@GetMapping("/newOrderNoNewMysql")
	public String newOrderNoNewMysql(){
		return orderService.newOrderNoNewMysql();
	}
}
