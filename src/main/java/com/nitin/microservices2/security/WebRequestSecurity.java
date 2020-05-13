package com.nitin.microservices2.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nitin.microservices2.services.UsersService;

@Configuration
@EnableWebSecurity
public class WebRequestSecurity extends WebSecurityConfigurerAdapter{
	@Value("${security.enable-csrf}")
    private boolean csrfEnabled;
	@Autowired
	private Environment env;
	@Autowired
	private UsersService usersService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Temporarily disabling the Spring Security
		// super.configure(http);
		if (!csrfEnabled) {
			http.csrf().disable();
		}
		
		http.authorizeRequests()
			.antMatchers(env
					.getProperty("ant.matchers.path"))
			.permitAll()
			.and()
			.addFilter(getAuthenticationFilter());
		
		// Allow Only Gateway to interact
		// http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"));

		http.headers().frameOptions().disable();
	}
	private UserAuthenticationFilter getAuthenticationFilter()  throws Exception{
		UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(authenticationManager());
		//authenticationFilter.setAuthenticationManager(authenticationManager()); 
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
}
