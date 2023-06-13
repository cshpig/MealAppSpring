package com.fdmgroup.mealappspring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.model.UserPrincipal;
import com.fdmgroup.mealappspring.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepo.findByUsername(username);
		User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User "+username+" not found"));
		return new UserPrincipal(user);
	}

}
