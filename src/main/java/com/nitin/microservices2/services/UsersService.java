package com.nitin.microservices2.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nitin.microservices2.shared.UserDTO;
import com.nitin.microservices2.shared.UserPortfolioDTO;

@Service
public interface UsersService extends UserDetailsService {//UserDetailsService comes from Spring framework

	UserDTO createUser(UserDTO userDetails);
	UserDTO getUserDetailsByEmail(String email);
	UserDTO getUserByUserId(String userId);
	UserDTO findByEmail(String email);
	UserPortfolioDTO getUserPortfolioByEmail(String email);
}
