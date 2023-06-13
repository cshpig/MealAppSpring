package com.fdmgroup.mealappspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.repo.UserRepo;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepo userRepo;
	
	@Test
	void registerNewUser_passesUserIntoFindByUsernameMethodOfUserRepo_whenCalled() {
		String username = "bob";
		String password = "123";
		
		userService.registerNewUser(username, password);
		
		verify(userRepo).findByUsername(username);
	}
	
	@Test
	void registerNewUser_passUserIntoSaveMethodOfUserRepoAndReturnsTrue_whenUsernameIsUnique() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		
		// no need to stub null value for Optional because it returns Optional.empty() by default
		
		boolean registeredSuccess = userService.registerNewUser(username, password);
		
		verify(userRepo).save(any(User.class));
		assertEquals(true, registeredSuccess);
	}
	
	@Test
	void registerNewUser_returnFalse_whenUsernameIsNotUnique() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		when(userRepo.findByUsername(username)).thenReturn(Optional.ofNullable(user));
		
		boolean registeredSuccess = userService.registerNewUser(username, password);
		
		assertEquals(false, registeredSuccess);
	}
	
//	@Test
//	void validateUser_passesUserIntoFindByUsernameMethodOfUserRepo_whenCalled() {
//		String username = "bob";
//		String password = "123";
//		User user = new User(username, password);
//		
//		userService.validateUser(username, password);
//		
//		verify(userRepo).findByUsername(username);
//	}
//	
//	@Test
//	void validateUser__returnsTrue_whenUserExists() {
//		String username = "bob";
//		String password = "123";
//		User user = new User(username, password);
//		when(userRepo.findByUsername(username)).thenReturn(Optional.ofNullable(user));
//		
//		boolean isValid = userService.validateUser(username, password);
//		
//		assertEquals(true, isValid);
//	}
//	
//	@Test
//	void validateUser__returnsFalse_whenUserNotFound() {
//		String username = "bob";
//		String password = "123";
//		User user = new User(username, password);
//
//		// no need to stub null value for Optional because it returns Optional.empty() by default
//		
//		boolean isValid = userService.validateUser(username, password);
//		
//		assertEquals(false, isValid);
//	}
	
	@Test
	void retrieveUser_passesUserIntoFindByUsernameMethodOfUserRepo_whenCalled() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		
		userService.retrieveUser(username);
		
		verify(userRepo).findByUsername(username);
	}
	
	@Test
	void retrieveUser__returnsUser_whenUserExists() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		when(userRepo.findByUsername(username)).thenReturn(Optional.ofNullable(user));
		
		User userFound = userService.retrieveUser(username);
		
		assertEquals(user, userFound);
	}
	
	@Test
	void retrieveUser__returnsNull_whenUserNotFound() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		
		// no need to stub null value for Optional because it returns Optional.empty() by default
		
		User userFound = userService.retrieveUser(username);
		
		assertEquals(null, userFound);
	}
	


}
