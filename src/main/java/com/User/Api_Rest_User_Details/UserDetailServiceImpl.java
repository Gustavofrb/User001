package com.User.Api_Rest_User_Details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.User.Api_Rest_User.UserRepository;

@Component
public class UserDetailServiceImpl implements UserDetailsService{

	private final UserRepository repository;
	
	
	public UserDetailServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		
		return null;
	}

}
