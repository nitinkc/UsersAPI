package com.nitin.microservices2.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitin.microservices2.model.LoginRequestModel;
import com.nitin.microservices2.services.UsersService;
import com.nitin.microservices2.shared.UserDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserAuthenticationFilter  extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private UsersService usersService;
	@Autowired
	private Environment env;
	
	public UserAuthenticationFilter(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			LoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(), LoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(), 
							creds.getPassword(), 
							new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
	throws IOException, ServletException{
		String userName = ((User) auth.getPrincipal()).getUsername();
		
		//Strange Exception
//		UserDTO userDetails = usersService.getUserDetailsByEmail(userName);
//
//		String token = Jwts.builder()
//				.setSubject(userDetails.getUserId())
//				.setSubject("Local Testing")
//				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration.time"))))
//				.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
//				.compact();
		String token = "Farji Token" + System.currentTimeMillis();
		res.addHeader("token", token);
//		res.addHeader("userId", userDetails.getUserId());
		res.addHeader("userId", userName);		
	}
}