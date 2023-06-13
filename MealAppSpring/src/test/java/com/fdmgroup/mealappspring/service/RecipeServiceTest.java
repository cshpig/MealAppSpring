package com.fdmgroup.mealappspring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.repo.RecipeRepo;

@SpringBootTest
public class RecipeServiceTest {
	
	@Autowired
	private RecipeService recipeService;
	
	@MockBean
	private RecipeRepo recipeRepo;
	
	@MockBean
	private Recipe mockRecipe;
	
	@Test
	void saveRecipe_passesRecipeIntoSaveMethodOfRecipeRepo_whenCalled() {
		Recipe recipe = new Recipe();
		
		recipeService.saveRecipe(recipe);
		
		verify(recipeRepo, times(1)).save(recipe);
	}
	
	@Test
	void saveRecipe_passesRecipeAndUserIntoSaveMethodOfRecipeRepo_whenCalled() {
		User user = new User();
		
		recipeService.saveRecipe(mockRecipe, user);
		
		verify(mockRecipe).setCreatedBy(user);
		verify(recipeRepo).save(mockRecipe);
	}
	
	@Test
	void retrieveRecipe_passesRecipeIdIntoFindByIdMethodOfRecipeRepo_whenCalled() {
		Recipe recipe = new Recipe();
		long recipeId = 1;
		
		recipeService.retrieveRecipe(recipeId);
		
		verify(recipeRepo).findById(recipeId);
	}

	@Test
	void retrieveRecipe_returnsRecipe_whenRecipeExists() {
		Recipe recipe = new Recipe();
		long recipeId = 1;
		when(recipeRepo.findById(recipeId)).thenReturn(Optional.ofNullable(recipe));
		
		Recipe recipeRetrieved = recipeService.retrieveRecipe(recipeId);
		
		assertEquals(recipe, recipeRetrieved);
	}
	
	@Test
	void retrieveRecipe_returnsNull_whenRecipeDoesNotExist() {
		Recipe recipe = new Recipe();
		long recipeId = 1;
		
		Recipe recipeRetrieved = recipeService.retrieveRecipe(recipeId);
		
		assertNull(recipeRetrieved);
	}
	
	@Test
	void retrieveRecipes_returnsListOfRecipesOfSizeZeroReturnedByRecipeRepo_whenNoRecipeInDB() {
		List<Recipe> recipes = new ArrayList<>();
		when(recipeRepo.findAll()).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipes();
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(0, recipesRetrieved.size());
	}

	@Test
	void retrieveRecipes_returnsListOfRecipesOfSizeOneReturnedByRecipeRepo_whenOneRecipeInDB() {
		Recipe recipe = new Recipe();
		List<Recipe> recipes = List.of(recipe);
		when(recipeRepo.findAll()).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipes();
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(1, recipesRetrieved.size());
	}
	
	@Test
	void retrieveRecipesByMealType_returnsListOfRecipesOfSizeZeroReturnedByRecipeRepo_whenNoRecipeFound() {
		List<Recipe> recipes = new ArrayList<>();
		String mealType = "dinner";
		when(recipeRepo.findByMealType(mealType)).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipesByMealType(mealType);
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(0, recipesRetrieved.size());
	}

	@Test
	void retrieveRecipesByMealType_returnsListOfRecipesOfSizeOneReturnedByRecipeRepo_whenOneRecipeFound() {
		Recipe recipe = new Recipe();
		List<Recipe> recipes = List.of(recipe);
		String mealType = "dinner";
		when(recipeRepo.findByMealType(mealType)).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipesByMealType(mealType);
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(1, recipesRetrieved.size());
	}

	@Test
	void retrieveRecipesByCuisineType_returnsListOfRecipesOfSizeZeroReturnedByRecipeRepo_whenNoRecipeFound() {
		List<Recipe> recipes = new ArrayList<>();
		String cuisineType = "western";
		when(recipeRepo.findByCuisineType(cuisineType)).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipesByCuisineType(cuisineType);
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(0, recipesRetrieved.size());
	}
	
	@Test
	void retrieveRecipesByCuisineType_returnsListOfRecipesOfSizeOneReturnedByRecipeRepo_whenOneRecipeFound() {
		Recipe recipe = new Recipe();
		List<Recipe> recipes = List.of(recipe);
		String cuisineType = "western";
		when(recipeRepo.findByCuisineType(cuisineType)).thenReturn(recipes);
		
		List<Recipe> recipesRetrieved = recipeService.retrieveRecipesByCuisineType(cuisineType);
		
		assertEquals(recipes, recipesRetrieved);
		assertEquals(1, recipesRetrieved.size());
	}
	

}
