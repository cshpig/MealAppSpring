package com.fdmgroup.mealappspring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.model.UserPrincipal;
import com.fdmgroup.mealappspring.repo.UserRepo;

@Service
public class UserService implements ApplicationContextAware {
	
	static Logger logger = LogManager.getLogger(UserService.class.getName());
	
	private ApplicationContext context;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder bcryptEncoder;
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	
	public boolean registerNewUser(String username, String password) {
		Optional<User> userOptional = userRepo.findByUsername(username);
		
		if(userOptional.isPresent()) {
			logger.info("Username "+username+" is taken");
			return false;
		} else {
			String pwHash = bcryptEncoder.encode(password);
			User user = context.getBean(User.class, username, pwHash);
			userRepo.save(user);
			logger.info("New user "+username+" created");
			return true;
		}
	}

//	public boolean validateUser(String username, String password) {
//		
//		Optional<User> userOptional = userRepo.findByUsername(username);
//		
//		if (userOptional.isPresent()) {
//			logger.info("User "+username+" found");
//			return userOptional.get().getPassword().equals(password);
//		}
//		
//		logger.info("User "+username+" not found");
//		return false;
//	}

	public User retrieveUser(String username) {
		logger.info("Retrieving user info...");
		
		Optional<User> userOptional = userRepo.findByUsername(username);
		
		if (userOptional.isPresent()) {
			logger.info("User "+username+" found");
			return userOptional.get();
		}
		
		logger.error("User "+username+" not found");
		return null;
	}
	
	public String retrieveActiveUser() {
		logger.info("Retrieving current active user's username...");

		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userPrincipal.getUsername();
		
	}


}
