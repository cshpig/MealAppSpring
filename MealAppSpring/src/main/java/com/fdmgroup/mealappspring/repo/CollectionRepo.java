package com.fdmgroup.mealappspring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.mealappspring.model.Collection;
import com.fdmgroup.mealappspring.model.User;

@Repository
public interface CollectionRepo extends JpaRepository<Collection, Long> {

	List<Collection> findByCollectionName(String collectionName);
	List<Collection> findByCollectionCreator(User userId);
	
	// check if these SAVE/update methods exists
//	void updateCollectionName(Collection collection);
//	void updateCollectionDescription(Collection collection);
	
	// methods to modify List<Recipe>
//	void addRecipe(long collectionId, long recipeId);
//	void removeRecipe(long collectionId, long recipeId);
	
	
}
