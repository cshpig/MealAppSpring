package com.fdmgroup.mealappspring.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ingredientId;
	private String ingredientName;

	public Ingredient() {
		super();
	}

	public Ingredient(String ingredientName) {
		super();
		this.ingredientName = ingredientName;
	}

	public long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	
	@Override
	public String toString() {
		return "Ingredient [ingredientId=" + ingredientId + ", ingredientName=" + ingredientName + "]";
	}


}
