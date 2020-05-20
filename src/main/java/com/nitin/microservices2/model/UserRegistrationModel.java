package com.nitin.microservices2.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationModel {

	@NotNull(message="First Name cannot be null or empty")
	@Size(min=2,message="Name mush be alteast 2 characters long")
	private String firstName;
	private String lastName;
	
	@NotNull(message="email cannot be empty")
	@Email
	private String email;
	private String password;
}
