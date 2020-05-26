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
import com.nitin.microservices2.model.SharesPortfolioResponseModel;
import com.nitin.microservices2.model.UserPortfolioResponseModel;
import com.nitin.microservices2.model.UserRegistrationModel;
import com.nitin.microservices2.model.UserResponseModel;
import com.nitin.microservices2.services.UsersService;
import com.nitin.microservices2.services.UsersServiceImpl;
import com.nitin.microservices2.shared.UserDTO;
import com.nitin.microservices2.shared.UserPortfolioDTO;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;
	@Autowired
	private UsersService userService;

	@PostMapping(value = "/add",
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	public ResponseEntity<CreatedUserResponseModel> createUser(@Valid @RequestBody UserRegistrationModel userDetails) {
		ModelMapper modelMapper = new ModelMapper(); 
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDTO userDto = modelMapper.map(userDetails, UserDTO.class);
		UserDTO createdUser = userService.createUser(userDto);
		
		//returning the response object, leaving behind the encrypted password
		CreatedUserResponseModel returnValue = modelMapper.map(createdUser, CreatedUserResponseModel.class);
		//Returning HTTP Status code as well
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);//Builder Pattern
	}
	
	
    @GetMapping(value="/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
       
        UserDTO userDto = userService.getUserByUserId(userId); 
        UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
        
        //Returning HTTP Response body
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
	
    @GetMapping(value="/email/{email}", produces = {MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserPortfolioResponseModel> getUserPortfolioByEmail(@PathVariable("email") String email) {
       
    	//Validate if email exist in the Users DB
        UserDTO userDto = userService.findByEmail(email); 
        
        if(userDto.getEmail() == null) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    	//call the Rest template

        UserPortfolioDTO userPortfolioDto = userService.getUserPortfolioByEmail(email); 
        UserPortfolioResponseModel returnValue = new ModelMapper().map(userPortfolioDto, UserPortfolioResponseModel.class);
        
        
        //Returning HTTP Response body
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
	
}
