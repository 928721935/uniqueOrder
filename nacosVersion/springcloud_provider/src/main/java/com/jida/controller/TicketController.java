package com.jida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jida.service.TicketService;

@RestController
public class TicketController {
	@Autowired
	TicketService ticketService;
	
	@GetMapping("/ticket")
	public String getTicket(){
		return ticketService.getTicket();
	}
}
