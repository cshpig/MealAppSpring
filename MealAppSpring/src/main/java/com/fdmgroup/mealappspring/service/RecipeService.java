package com.fdmgroup.mealappspring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.fdmgroup.mealappspring.model.Ingredient;
import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.repo.RecipeRepo;

@Service
public class RecipeService {
	
	static Logger logger = LogManager.getLogger(RecipeService.class.getName());
		
	@Autowired
	RecipeRepo recipeRepo;
	
	public void saveRecipe(Recipe recipe, User user) {
		recipe.setTotalTimeInMin();
		recipe.setCreatedBy(user);
		recipeRepo.save(recipe);
		logger.info(recipe.getRecipeName()+" saved");
	}

	public void saveRecipe(Recipe recipe) {
		recipeRepo.save(recipe);
		logger.info(recipe.getRecipeName()+" saved");
	}

	public Recipe retrieveRecipe(long recipeId) {
		Optional<Recipe> recipeOptional = recipeRepo.findById(recipeId);
		
		if (recipeOptional.isPresent()) {
			logger.info("Recipe "+recipeId+" found");
			return recipeOptional.get();
		}
		
		logger.error("Recipe "+recipeId+" not found");
		return null;
	}

	public String retrieveCreatorUsername(Recipe recipe) {
		return recipe.getCreatedBy().getUsername();
	}

	public long retrieveCreatorId(Recipe recipe) {
		return recipe.getCreatedBy().getUserId();
	}
	
	public void ingredientNameToUpperCase(Recipe recipe) {
		for (Ingredient ingredient : recipe.getIngredients()) {
			ingredient.setIngredientName(ingredient.getIngredientName().toUpperCase());
		}
	}

	public List<Recipe> retrieveRecipes() {
		return recipeRepo.findAll();
	}

	public List<Recipe> retrieveRecipesByMealType(String mealType) {
		return recipeRepo.findByMealType(mealType);
	}

	public List<Recipe> retrieveRecipesByCuisineType(String cuisineType) {
		return recipeRepo.findByCuisineType(cuisineType);
	}




}
