package com.fdmgroup.mealappspring.controller;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.mealappspring.model.User;
import com.fdmgroup.mealappspring.model.UserPrincipal;
import com.fdmgroup.mealappspring.service.UserService;

@Controller
public class HomeController implements ApplicationContextAware {
	
	static Logger logger = LogManager.getLogger(RecipeController.class.getName());

	private ApplicationContext context;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	@GetMapping("/")
	public String goToIndexPage() {
		logger.info("Redirecting to index page");
		return "index";
	}
	
	@GetMapping("/login")
	public String goToLoginPage() {
		logger.info("Redirecting to login page");
		return "login";
	}
	
	@GetMapping("/register")
	public String goToRegisterPage() {
		logger.info("Redirecting to register page");
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@RequestParam String username, @RequestParam String password) {
		
		boolean registerSuccess = userService.registerNewUser(username, password);
		
		if(registerSuccess) {
			logger.info("Registration successful");
			return "register_successful";
		} else {
			logger.error("Registration unsuccessful");
			return "register_unsuccessful";
		}
	}
	
//	@GetMapping("/feed")
//	public String goToFeed(Model model) {
//		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		model.addAttribute("username", userPrincipal.getUsername());
//		
//		logger.info("Redirecting to feed page");
//		return "feed";
//	}
	
//	@GetMapping("/feed")
//	public String goToFeed(Model model, UserPrincipal userPrincipal) {
//		userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		model.addAttribute("username", userPrincipal.getUsername());
//		
//		logger.info("Redirecting to feed page");
//		return "feed";
//	}
	
	@GetMapping("/feed")
	public String goToFeed(Model model, @AuthenticationPrincipal(expression="username") String username) {
		
		model.addAttribute("username", username);
		
		logger.info("Redirecting to feed page");
		return "feed";
	}
	
	@GetMapping("/my_recipes")
	public String goToMyRecipes(Model model, @AuthenticationPrincipal(expression="username") String username) {
//		String username = userService.retrieveActiveUser();
		User user = userService.retrieveUser(username);
		model.addAttribute("username", username);
		model.addAttribute("recipes", user.getRecipes());

		logger.info("Going to my_recipes page...");
		return "my_recipes";
	}

}
