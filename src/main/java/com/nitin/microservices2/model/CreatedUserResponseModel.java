package com.nitin.microservices2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedUserResponseModel {
	private String userId;
    private String firstName;
    private String lastName;
    private String email;
}