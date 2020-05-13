package com.nitin.microservices2.shared;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements Serializable {
	private static final long serialVersionUID = -6546756;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String encryptedPassword;	

}