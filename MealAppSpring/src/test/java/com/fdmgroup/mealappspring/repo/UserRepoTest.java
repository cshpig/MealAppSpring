package com.fdmgroup.mealappspring.repo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.mealappspring.model.User;

@DataJpaTest
class UserRepoTest {
	
	@Autowired
	UserRepo userRepo;

	@Test
	void findByUsername_returnsCorrectUser() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		userRepo.save(user);
		
		Optional<User> userOptional = userRepo.findByUsername(username);
		User retrievedUser = userOptional.orElse(new User());
		
		assertEquals(user, retrievedUser);
	}

}
