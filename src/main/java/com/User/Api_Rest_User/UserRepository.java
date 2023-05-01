package com.User.Api_Rest_User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{

	
	   public Optional<User> findByLogin(String login);
}