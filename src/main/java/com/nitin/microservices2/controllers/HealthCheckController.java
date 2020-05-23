package com.nitin.microservices2.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

	@Autowired
	Environment env;
	
	@GetMapping("/users")
	public String healthCheck() {
		return "Users API runs fine on port : " + env.getProperty("server.port") + "\n" + "with time out:" + env.getProperty("token.expiration.time");
	}
	
	@GetMapping("/users/status-check")
	public String healthCkeck() {
		String msg = "Health is perfect @" + new Date(System.currentTimeMillis()) + "\n";
		String port = "Test Config PROP is :: " + env.getProperty("test.prop");
		return msg+port;
	}
	
	@GetMapping("/zuul-check")
	public String ZuulGatewayCheck() {
		return env.getProperty("token.secret");
	}
}
