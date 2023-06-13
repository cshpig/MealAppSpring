package com.fdmgroup.mealappspring.controller;

import static org.mockito.BDDMockito.*;
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

@WebMvcTest(HomeController.class)
class HomeControllerTest {
	
	@Autowired
	private MockMvc mvc;	

	@MockBean
	UserService userService;
	
	@MockBean
	RecipeService recipeService;

	@Test
	@WithMockUser
	void goToIndexPage_ReturnsIndexView_WhenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("index"));
	}

	@Test
	@WithMockUser
	void goToRegisterPage_ReturnsRegisterView_WhenCalled() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/register")).andExpect(MockMvcResultMatchers.view().name("register"));
	}

	@Test
	@WithMockUser
	void registerUser_ReturnsLoginRegisterSuccessfulPage_WhenRegisterSuccess() throws Exception {
		when(userService.registerNewUser("user", "password")).thenReturn(true);
		
		mvc.perform(MockMvcRequestBuilders.post("/register")
				.param("username", "user").param("password", "password")
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
			.andExpect(MockMvcResultMatchers.view().name("register_successful"));
	}

	@Test
	@WithMockUser
	void registerUser_ReturnsRegisterUnsuccessfulPage_WhenRegisterUnsuccessful() throws Exception {
		String username = "bobby";
		String password = "123";

		when(userService.registerNewUser(username, password)).thenReturn(false);

		mvc.perform(MockMvcRequestBuilders.post("/register")
				.param("username", username).param("password", password)
				.with(SecurityMockMvcRequestPostProcessors.csrf()))
				.andExpect(MockMvcResultMatchers.view().name("register_unsuccessful"));
	}
	
	@Test
	@WithMockUser()
	void goToFeed_returnsFeedView_whenCalledAndUserIsLoggedIn() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/feed"))
		   .andExpect(MockMvcResultMatchers.view().name("feed"));
	}
	
	@Test
	@WithMockUser(username = "bob", password = "123")
	void goToMyRecipes_returnsMyRecipesView_whenUserIsLoggedIn() throws Exception {
		String username = "bob";
		String password = "123";
		User user = new User(username, password);
		
		Recipe recipe = new Recipe();
		recipe.setRecipeName("Butter Chicken");
		List<Recipe> recipesCreated = new ArrayList<>();
		recipesCreated.add(recipe);
		user.setRecipes(recipesCreated);
		
		when(userService.retrieveUser(username)).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.get("/my_recipes")).andExpect(MockMvcResultMatchers.view().name("my_recipes"));
	}


}
