package com.fdmgroup.mealappspring.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long recipeId;
	private String recipeName;
	private String recipeContent;
	private Date dateAdded;
	private String cuisineType;
	private String mealType;
	private int totalTimeInMin;
	private int prepTimeInMin;
	private int cookTimeInMin;
	private int cleanUpTimeInMin;

	@ManyToOne
//	@JoinColumn(name = "FK_USER_ID")
	private User recipeCreator;

//	@ManyToMany(cascade = CascadeType.PERSIST)
	@ManyToMany
	@JoinTable(name="RECIPE_INGREDIENT", joinColumns=
	@JoinColumn(name="FK_RECIPE_ID"), inverseJoinColumns=
	@JoinColumn(name="FK_INGREDIENT_ID"))
	private List<Ingredient> ingredients = new ArrayList<>();

	public Recipe() {
		super();
	}

	public Recipe(String recipeName, String recipeContent, Date dateAdded, String cuisineType, String mealType,
			int prepTimeInMin, int cookTimeInMin, int cleanUpTimeInMin) {
		super();
		this.recipeName = recipeName;
		this.recipeContent = recipeContent;
		this.dateAdded = dateAdded;
		this.cuisineType = cuisineType;
		this.mealType = mealType;
		this.prepTimeInMin = prepTimeInMin;
		this.cookTimeInMin = cookTimeInMin;
		this.cleanUpTimeInMin = cleanUpTimeInMin;
		this.totalTimeInMin = prepTimeInMin+cookTimeInMin+cleanUpTimeInMin;
	}

	public long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(long recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getRecipeContent() {
		return recipeContent;
	}

	public void setRecipeContent(String recipeContent) {
		this.recipeContent = recipeContent;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getCuisineType() {
		return cuisineType;
	}

	public void setCuisineType(String cuisineType) {
		this.cuisineType = cuisineType;
	}

	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public int getTotalTimeInMin() {
		return totalTimeInMin;
	}

	public void setTotalTimeInMin() {
//		this.totalTimeInMin = totalTimeInMin;
		this.totalTimeInMin = prepTimeInMin+cookTimeInMin+cleanUpTimeInMin;
	}

	public int getPrepTimeInMin() {
		return prepTimeInMin;
	}

	public void setPrepTimeInMin(int prepTimeInMin) {
		this.prepTimeInMin = prepTimeInMin;
	}
	
	public int getCookTimeInMin() {
		return cookTimeInMin;
	}
	
	public void setCookTimeInMin(int cookTimeInMin) {
		this.cookTimeInMin = cookTimeInMin;
	}

	public int getCleanUpTimeInMin() {
		return cleanUpTimeInMin;
	}

	public void setCleanUpTimeInMin(int cleanUpTimeInMin) {
		this.cleanUpTimeInMin = cleanUpTimeInMin;
	}

	public User getCreatedBy() {
		return recipeCreator;
	}

	public void setCreatedBy(User createdBy) {
		this.recipeCreator = createdBy;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	
	public void removeIngredient(Ingredient ingredient) {
		ingredients.remove(ingredient);
	}

	@Override
	public String toString() {
		return "Recipe [recipeId=" + recipeId + ", recipeName=" + recipeName + ", recipeContent=" + recipeContent
				+ ", dateAdded=" + dateAdded + ", cuisineType=" + cuisineType + ", mealType=" + mealType
				+ ", totalTimeInMin=" + totalTimeInMin + ", prepTimeInMin=" + prepTimeInMin + ", cookTimeInMin="
				+ cookTimeInMin + ", cleanUpTimeInMin=" + cleanUpTimeInMin + ", recipeCreatedBy=" + recipeCreator
				+ ", ingredients=" + ingredients + "]";
	}
	

}
