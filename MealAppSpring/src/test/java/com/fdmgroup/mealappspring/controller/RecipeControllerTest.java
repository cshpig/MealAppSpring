package com.fdmgroup.mealappspring.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdmgroup.mealappspring.model.Recipe;
import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.service.RecipeService;
import com.fdmgroup.mealappspring.service.UserService;


@WebMvcTest
public class RecipeControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean 
	RecipeService recipeService;

	
	@Test
	@WithMockUser
	void getAllRecipes_returnsDisplayRecipesView_whenCalledAndUserIsAuthenticated() throws Exception {
		List<Recipe> mockRecipes = new ArrayList<>();
		when(recipeService.retrieveRecipes()).thenReturn(mockRecipes);
		
		mvc.perform(MockMvcRequestBuilders.get("/recipes"))
		   .andExpect(MockMvcResultMatchers.view().name("display_recipes"))
		   .andExpect(MockMvcResultMatchers.model().attribute("recipes", mockRecipes));
		
		verify(recipeService).retrieveRecipes();
	}
	
	@Test
	void getAllRecipes_returnsDisplayRecipesView_whenCalledandUserIsUnauthenticated() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/recipes")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	@WithMockUser
	void searchRecipesByMealType_returnsDisplayRecipesView_whenCalled() throws Exception {
		String mealType = "dinner";
		Recipe recipe1 = new Recipe();
		Recipe recipe2 = new Recipe();
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(recipe1);
		recipes.add(recipe2);
		when(recipeService.retrieveRecipesByMealType(mealType)).thenReturn(recipes);
		
		mvc.perform(MockMvcRequestBuilders.post("/recipes/search/mealType").param("mealType", mealType).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.model().attribute("recipes", recipes))
		.andExpect(MockMvcResultMatchers.view().name("display_recipes"));
		
		verify(recipeService).retrieveRecipesByMealType(mealType);
	}

	@Test
	@WithMockUser
	void searchRecipesByCuisineType_returnsDisplayRecipesView_whenCalled() throws Exception {
		String cuisineType = "Indian";
		Recipe recipe1 = new Recipe();
		Recipe recipe2 = new Recipe();
		List<Recipe> recipes = new ArrayList<>();
		recipes.add(recipe1);
		recipes.add(recipe2);
		when(recipeService.retrieveRecipesByCuisineType(cuisineType)).thenReturn(recipes);
		
		mvc.perform(MockMvcRequestBuilders.post("/recipes/search/cuisineType").param("cuisineType", cuisineType).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.model().attribute("recipes", recipes))
		.andExpect(MockMvcResultMatchers.view().name("display_recipes"));
		
		verify(recipeService).retrieveRecipesByCuisineType(cuisineType);
	}
	
	@Test
	@WithMockUser
	void resetSearch_redirectsToRecipes_whenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/recipes/reset").with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/recipes"));
	}
	
	@Test
	@WithMockUser
	void goToCreateRecipePage_returnsCreateRecipeView_whenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/recipes/create").with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.view().name("create_recipe"));
	}
	
	@Test
	@WithMockUser
	void createRecipe_redirectsToMyRecipes_whenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/recipes/create").with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/my_recipes"));
	}
	
	@Test
	@WithMockUser
	void addIngredientsInCreateRecipe_redirectsToRecipesCreate_whenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/recipes/create/remove_ingredient").with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/recipes/create"));
	}
	
	@Test
	@WithMockUser
	void removeIngredientsInCreateRecipe_redirectsToRecipesCreate_whenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/recipes/create/remove_ingredient").with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/recipes/create"));
	}
	
	@Test
	@WithMockUser
	void getRecipe_returnsDisplayRecipeView_whenCalled() throws Exception {
		long recipeId = 1;
		Recipe recipe = new Recipe();
		when(recipeService.retrieveRecipe(recipeId)).thenReturn(recipe);
		when(recipeService.retrieveCreatorUsername(recipe)).thenReturn("user");
		
		mvc.perform(MockMvcRequestBuilders.get("/recipes/{id}", recipeId).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.view().name("display_recipe"))
		.andExpect(MockMvcResultMatchers.model().attribute("recipe", recipe))
		.andExpect(MockMvcResultMatchers.model().attribute("creatorUsername", "user"));
	}
	
	@Test
	@WithMockUser
	void goToEditRecipePage_returnsEditRecipeView_whenRecipeIsCreatedByUser() throws Exception {
		User user = new User("user", "password");
		long recipeId = 1;
		Recipe recipe = new Recipe();
		recipe.setCreatedBy(user);
		
		when(recipeService.retrieveRecipe(recipeId)).thenReturn(recipe);
		when(recipeService.retrieveCreatorUsername(recipe)).thenReturn("user");
		when(userService.retrieveActiveUser()).thenReturn("user");
		
		mvc.perform(MockMvcRequestBuilders.get("/recipes/{id}/edit", recipeId).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.view().name("edit_recipe"))
		.andExpect(MockMvcResultMatchers.model().attribute("recipe", recipe))
		.andExpect(MockMvcResultMatchers.model().attribute("creatorUsername", "user"));
	}

	@Test
	@WithMockUser
	void goToEditRecipePage_returnsDisplayRecipeView_whenRecipeIsNotCreatedByUser() throws Exception {
		User user = new User("user", "password");
		long recipeId = 1;
		Recipe recipe = new Recipe();
		recipe.setCreatedBy(user);
		
		when(recipeService.retrieveRecipe(recipeId)).thenReturn(recipe);
		when(recipeService.retrieveCreatorUsername(recipe)).thenReturn("user");
		when(userService.retrieveActiveUser()).thenReturn("someoneElse");
		
		mvc.perform(MockMvcRequestBuilders.get("/recipes/{id}/edit", recipeId).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.view().name("display_recipe"))
		.andExpect(MockMvcResultMatchers.model().attribute("recipe", recipe))
		.andExpect(MockMvcResultMatchers.model().attribute("creatorUsername", "user"));
	}
	
	@Test
	@WithMockUser
	void editRecipe_redirectToRecipesId_whenCalled() throws Exception {
		long recipeId = 1;

		mvc.perform(MockMvcRequestBuilders.post("/recipes/{id}/edit", recipeId).with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.redirectedUrl("/recipes/1"));
	}

	

	
}
