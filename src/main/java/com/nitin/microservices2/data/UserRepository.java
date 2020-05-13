package com.nitin.microservices2.data;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
//	Optional<UserEntity> findByEmail(String email);
//	Optional<UserEntity> findByUserId(String userId);
	
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
}
