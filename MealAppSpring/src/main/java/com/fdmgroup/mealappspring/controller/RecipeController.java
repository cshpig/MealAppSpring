package com.fdmgroup.mealappspring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.fdmgroup.mealappspring.model.Ingredient;
import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.service.RecipeService;
import com.fdmgroup.mealappspring.service.UserService;

@Controller
@RequestMapping("/recipes")
public class RecipeController implements ApplicationContextAware {
	
	static Logger logger = LogManager.getLogger(RecipeController.class.getName());
	
	private ApplicationContext context;

	@Autowired
	UserService userService;
	
	@Autowired
	RecipeService recipeService;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	
	@GetMapping()
	public String getAllRecipes(Model model) {
		logger.info("Retrieving list of all recipes...");

		List<Recipe> recipes = recipeService.retrieveRecipes(); 
	    model.addAttribute("recipes", recipes); 
		return "display_recipes";
	}


	@PostMapping("search/mealType")
	public String searchRecipesByMealType(@RequestParam String mealType, Model model) {
		logger.info("Retrieving recipes that match search request...");
		
		List<Recipe> recipes = recipeService.retrieveRecipesByMealType(mealType);  
		model.addAttribute("recipes", recipes);
		return "display_recipes";
	}
	
	@PostMapping("search/cuisineType")
	public String searchRecipesByCuisineType(@RequestParam String cuisineType, Model model) {
		logger.info("Retrieving recipes that match search request...");
		
		List<Recipe> recipes = recipeService.retrieveRecipesByCuisineType(cuisineType);  
		model.addAttribute("recipes", recipes);
		return "display_recipes";
	}
	
	@PostMapping("reset")
	public String resetSearch(Model model) {
		logger.info("Redirecting to /recipes...");

		return "redirect:/recipes";
	}
	
	@GetMapping("create")
	public String goToCreateRecipePage(Recipe recipe, Ingredient ingredient) {
		
//	    model.addAttribute("recipe", context.getBean(Recipe.class)); 

	    logger.info("Going to create_recipe page...");
		return "create_recipe";
	}
	
	@PostMapping("create")
	public String createRecipe(Recipe recipe) {
		logger.info("Creating recipe...");
		
		String username = userService.retrieveActiveUser();
		User user = userService.retrieveUser(username);

		recipeService.saveRecipe(recipe, user);
		
		logger.info("Redirecting to /my_recipes ...");
		return "redirect:/my_recipes";
	}
	
	@PostMapping("create/add_ingredient")
	public String addIngredientInCreateRecipe(Recipe recipe, Model model) {
		logger.info("Adding new row to ingredient table...");
		
//	    if (recipe != null) {
//			if (recipe.getIngredients() != null) {
////				List<Ingredient> ingredients = new ArrayList<>();
//				ingredients.add(context.getBean(Ingredient.class));
//				recipe.setIngredients(ingredients);
//				logger.info(ingredients+" added to recipe");
//			} else {
////				List<Ingredient> ingredients = new ArrayList<>();
//				ingredients.add(context.getBean(Ingredient.class));
//				logger.info(ingredients+" added to recipe");
//			} 
//		}
		
		List<Ingredient> ingredients = new ArrayList<>();

	    if (recipe != null) {
	    	if (recipe.getIngredients() != null) {
	    		ingredients.add(context.getBean(Ingredient.class));
	    		recipe.setIngredients(ingredients);
	    		logger.info(ingredients+" added to recipe");
	    	} else {
	    		ingredients.add(context.getBean(Ingredient.class));
	    		logger.info(ingredients+" added to recipe");
	    	} 
	    }
	    

//		recipeService.ingredientNameToUpperCase(recipe);

//		recipeService.saveRecipe(recipe);
//		logger.info("Recipe saved: "+recipe);

		
		logger.info("Redirecting to /recipes/create ...");
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredients", ingredients);
		return "redirect:/recipes/create";

//		return "create_recipe";
	}
	
	@PostMapping("create/remove_ingredient")
	public String removeIngredientInCreateRecipe(Recipe recipe) {
		logger.info("Removing ingredient...");
		
//		for (Ingredient ingredient: ingredients) {
//			recipeService.addIngredient(recipe, ingredient);
//		}
		recipeService.saveRecipe(recipe);
		
		logger.info("Redirecting to /recipes/create ...");
		return "redirect:/recipes/create";
	}
	
	@GetMapping("{id}")
	public String getRecipe(@PathVariable("id") long recipeId, Model model) {
		Recipe recipe = recipeService.retrieveRecipe(recipeId);
		String creatorUsername = recipeService.retrieveCreatorUsername(recipe);
		model.addAttribute("recipe", recipe);
		model.addAttribute("creatorUsername", creatorUsername);
		
		logger.info("Going to display_recipe page for recipe id "+recipeId+"...");
		return "display_recipe";
	}
	
	
	@GetMapping("{id}/edit")
	public String goToEditRecipePage(@PathVariable("id") long recipeId, Model model) {
	    logger.info("Retrieving contents of recipe id "+recipeId+"...");

		Recipe recipe = recipeService.retrieveRecipe(recipeId); 
	    model.addAttribute("recipe", recipe); 
		model.addAttribute("creatorUsername", recipeService.retrieveCreatorUsername(recipe));
	    
	    if (recipe.getCreatedBy().getUsername().equals(userService.retrieveActiveUser())) {
		    logger.info("Going to edit_recipe page for recipe id "+recipeId+"...");
			return "edit_recipe";
		} else {
			logger.info("Access denied: "+userService.retrieveActiveUser()+" does not have write access to recipe id "+recipeId);
			return "display_recipe";
		}
	    
	}
	
	@PostMapping("{id}/edit")
	public String editRecipe(@PathVariable("id") long recipeId, Recipe recipe) {
		logger.info("Editing recipe...");
		
		String username = userService.retrieveActiveUser();
		User user = userService.retrieveUser(username);
		recipe.setRecipeId(recipeId);

		recipeService.saveRecipe(recipe, user);
		
		logger.info("Redirecting to /recipes/"+recipeId+" ...");
		return "redirect:/recipes/{id}";
	}
	


}
