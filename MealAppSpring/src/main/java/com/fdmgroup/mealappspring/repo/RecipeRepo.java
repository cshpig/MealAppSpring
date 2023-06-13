package com.fdmgroup.mealappspring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.model.User;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

	List<Recipe> findByRecipeName(String recipeName);
	List<Recipe> findByCuisineType(String cuisineType);
	List<Recipe> findByMealType(String mealType);
	List<Recipe> findByTotalTimeInMinLessThanEqual(int totalTime);
	List<Recipe> findByPrepTimeInMinLessThanEqual(int prepTime);
	List<Recipe> findByCookTimeInMinLessThanEqual(int cookTime);
	List<Recipe> findByCleanUpTimeInMinLessThanEqual(int cleanUpTime);
	List<Recipe> findByRecipeCreator(User user);
	
	@Query("Select r from Recipe r join fetch r.ingredients i where i.ingredientId like :ingredientId")
	List<Recipe> findByIngredient(@Param("ingredientId") long ingredientId);
	
//	void addIngredient(long recipeId, long ingredientId);
//	void removeIngredient(long recipeId, long ingredientId);

}
