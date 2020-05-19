package com.nitin.microservices2.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nitin.microservices2.data.UserEntity;
import com.nitin.microservices2.data.UserRepository;
import com.nitin.microservices2.exception.UserNotFoundException;
import com.nitin.microservices2.shared.UserDTO;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDetails) {

		// Generating the UUID
		userDetails.setUserId(UUID.randomUUID().toString());

		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		// userDetails.setEncryptedPassword("testing");
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		// Copy the transfer object into the DataEntoty
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		// JPA Repo provided method. Implementation is not required
		userRepository.save(userEntity);

		// Returning the Created object, leaving behind the password
		UserDTO returnValue = modelMapper.map(userEntity, UserDTO.class);

		return returnValue;
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email " + email + " Not Found"));
		
//		UserEntity userEntity = userRepository.findByEmail(email);
//		if (null == userEntity) {
//			throw new UserNotFoundException("Email " + email + " Not Found");
//		}
		
		return new ModelMapper().map(userEntity, UserDTO.class);
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User Id " + userId + " Not Found"));

//		UserEntity userEntity = userRepository.findByUserId(userId);
//		if (null == userEntity) {
//			throw new UserNotFoundException("User Id " + userId + " Not Found");
//		}

		UserDTO userDto = new ModelMapper().map(userEntity, UserDTO.class);

		return userDto;
	}

	// Comes from UserDetailsService class of Spring Framework which is extended in
	// the UsersService Interface
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		  UserEntity userEntity = userRepository. findByEmail(username) .orElseThrow(()
		  -> new UsernameNotFoundException(username));//Spring provided Exception
		  
		 
//		UserEntity userEntity = userRepository.findByEmail(username);
//
//		if (null == userEntity) {
//			new UsernameNotFoundException(username);// Spring provided Exception
//		}

		// User Class ids from Spring Security
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}
}