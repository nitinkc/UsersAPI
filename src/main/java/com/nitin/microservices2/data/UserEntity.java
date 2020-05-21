package com.nitin.microservices2.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 9098253703581986923L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false, length=30)
	private String firstName;
	
	private String lastName;
	
	@Column(nullable=false, length=70, unique=true)
	private String email;
	
	@Column(nullable=false, unique=true)
	private String userId;
	
	@Column(nullable=false, unique=true)	
	private String encryptedPassword;
	
}