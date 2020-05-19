package com.nitin.microservices2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@Autowired
	Environment env;
	
	@GetMapping("/")
	public String healthCheck() {
		return "Users API runs fine on port : " + env.getProperty("server.port");
	}
	
	@GetMapping("/status-check")
	public String healthCkeck() {
		String msg = "Health is perfect\n";
		String port = "Port number is : " + env.getProperty("token.expiration.time");
		return msg+port;
	}
}
