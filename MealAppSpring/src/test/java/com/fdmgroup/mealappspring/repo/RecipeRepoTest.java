package com.fdmgroup.mealappspring.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fdmgroup.mealappspring.model.Ingredient;
import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.model.User;

@DataJpaTest
class RecipeRepoTest {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RecipeRepo recipeRepo;
	
	@Autowired
	IngredientRepo ingredientRepo;
	
	Recipe mockRecipe;
	
	@BeforeEach
	void setup() {		
		String recipeName = "Butter Chicken";
		String cuisineType = "Indian";
		String mealType = "Dinner";
		int prepTime = 30;
		int cookTime = 30;
		int cleanUpTime = 30;
		
		mockRecipe = new Recipe();
		mockRecipe.setRecipeName(recipeName);
		mockRecipe.setCuisineType(cuisineType);
		mockRecipe.setMealType(mealType);
		mockRecipe.setPrepTimeInMin(prepTime);
		mockRecipe.setCleanUpTimeInMin(cleanUpTime);
		mockRecipe.setCookTimeInMin(cookTime);
		mockRecipe.setTotalTimeInMin();
	}
	
	@Test
	void findByRecipeName_returnsListOfSizeZero_whenNoRecipeFound() {
		List<Recipe> recipesFound = recipeRepo.findByRecipeName("Butter Chicken");
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByRecipeName_returnsListOfSizeOne_whenOneRecipeFound() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByRecipeName("Butter Chicken");
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByCuisineType_returnsListOfSizeZero_whenNoRecipeFound() {
		List<Recipe> recipesFound = recipeRepo.findByCuisineType("Indian");
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByCuisineType_returnsListOfSizeOne_whenOneRecipeFound() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCuisineType("Indian");
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByMealType_returnsListOfSizeZero_whenNoRecipeFound() {
		List<Recipe> recipesFound = recipeRepo.findByMealType("Dinner");
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByMealType_returnsListOfSizeOne_whenOneRecipeFound() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByMealType("Dinner");
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}

	@Test
	void findByPrepTimeInMinLessThanEqual_returnsListOfSizeZero_whenNoRecipePrepTimeLessThan10() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByPrepTimeInMinLessThanEqual(10);
		
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByPrepTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipePrepTimeLessThan40() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByPrepTimeInMinLessThanEqual(40);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}

	@Test
	void findByPrepTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipePrepTimeEqual30() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByPrepTimeInMinLessThanEqual(30);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}

	@Test
	void findByCookTimeInMinLessThanEqual_returnsListOfSizeZero_whenNoRecipePrepTimeLessThan10() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCookTimeInMinLessThanEqual(10);
		
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByCookTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeCookTimeLessThan40() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCookTimeInMinLessThanEqual(40);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByCookTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeCookTimeEqual30() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCookTimeInMinLessThanEqual(30);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByCleanUpTimeInMinLessThanEqual_returnsListOfSizeZero_whenNoRecipeCleanUpTimeLessThan10() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCleanUpTimeInMinLessThanEqual(10);
		
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByCleanUpTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeCleanUpTimeLessThan40() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCleanUpTimeInMinLessThanEqual(40);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByCleanUpTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeCleanUpTimeEqual30() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByCleanUpTimeInMinLessThanEqual(30);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByTotalTimeInMinLessThanEqual_returnsListOfSizeZero_whenNoRecipeTotalTimeLessThan10() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByTotalTimeInMinLessThanEqual(10);
		
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByTotalTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeTotalTimeLessThan100() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByTotalTimeInMinLessThanEqual(100);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByTotalTimeInMinLessThanEqual_returnsListOfSizeOne_whenRecipeTotalTimeEqual90() {
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByTotalTimeInMinLessThanEqual(90);
		
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByRecipeCreator_returnsListOfSizeZero_whenNoRecipeFound() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		userRepo.save(user);
		
		List<Recipe> recipesFound = recipeRepo.findByRecipeCreator(user);
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByRecipeCreator_returnsListOfSizeOne_whenOneRecipeFound() {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		userRepo.save(user);
		
		mockRecipe.setCreatedBy(user);
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByRecipeCreator(user);
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}
	
	@Test
	void findByIngredient_returnsListOfSizeZero_whenNoRecipeFound() {
		List<Recipe> recipesFound = recipeRepo.findByIngredient(anyLong());
		assertEquals(0, recipesFound.size());
	}
	
	@Test
	void findByIngredient_returnsListOfSizeOne_whenOneRecipeFound() {
		Ingredient ingredient = new Ingredient();
		ingredientRepo.save(ingredient);
		mockRecipe.setIngredients(List.of(ingredient));
		recipeRepo.save(mockRecipe);
		
		List<Recipe> recipesFound = recipeRepo.findByIngredient(ingredient.getIngredientId());
		assertEquals(1, recipesFound.size());
		assertThat(recipesFound, contains(mockRecipe));
	}


}
