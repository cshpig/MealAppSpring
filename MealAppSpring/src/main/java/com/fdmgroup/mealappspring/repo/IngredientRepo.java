package com.fdmgroup.mealappspring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.mealappspring.model.Ingredient;

@Repository
public interface IngredientRepo extends JpaRepository<Ingredient, Long>{

	Optional<Ingredient> findByIngredientName(String ingredientName);

}
