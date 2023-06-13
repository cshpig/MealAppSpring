package com.fdmgroup.mealappspring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.mealappspring.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
	
//	void addFollowing(long followerUserId, long followingUserId);
//	void removeFollowing(long followerUserId, long followingUserId);
//	void addSavedCollection(long userId, long collectionId);
//	void removeSavedCollection(long userId, long collectionId);
}
