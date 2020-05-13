package com.nitin.microservices2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestModel {

	private String email;
	private String password;
}
