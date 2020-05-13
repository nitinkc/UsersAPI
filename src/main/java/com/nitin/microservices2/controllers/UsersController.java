package com.nitin.microservices2.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitin.microservices2.model.CreatedUserResponseModel;
import com.nitin.microservices2.model.UserRegistrationModel;
import com.nitin.microservices2.model.UserResponseModel;
import com.nitin.microservices2.services.UsersService;
import com.nitin.microservices2.services.UsersServiceImpl;
import com.nitin.microservices2.shared.UserDTO;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;
	@Autowired
	private UsersService userService;

	@GetMapping("/status/check")
	public String healthCkeck() {
		String msg = "Health is perfect\n";
		String port = "Port number is : " + env.getProperty("local.server.port");
		return msg+port;
	}


	@PostMapping(value = "/add",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
				 produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<CreatedUserResponseModel> createUser(@Valid @RequestBody UserRegistrationModel userDetails) {
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDTO userDto = modelMapper.map(userDetails, UserDTO.class);
		UserDTO createdUser = userService.createUser(userDto);
		
		CreatedUserResponseModel returnValue = modelMapper.map(createdUser, CreatedUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
	
	
    @GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
       
        UserDTO userDto = userService.getUserByUserId(userId); 
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
        
        //Returning HTTP Response body
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
	
    //@PostMapping
	
}
