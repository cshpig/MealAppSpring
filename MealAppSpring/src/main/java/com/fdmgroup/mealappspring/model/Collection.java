package com.fdmgroup.mealappspring.model;

import java.sql.Date;
import java.util.List;

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
public class Collection {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long collectionId;
	private String collectionName;
	private String collectionDescription;
	private Date dateCreated;
	
	@ManyToOne
	private User collectionCreator;
	
	@ManyToMany
	@JoinTable(name="RECIPE_COLLECTION", joinColumns =
	@JoinColumn(name="FK_COLLECTION_ID"), inverseJoinColumns =
	@JoinColumn(name="FK_RECIPE_ID"))
	private List<Recipe> savedRecipes;

	public Collection() {
		super();
	}

	public Collection(String collectionName, String collectionDescription, Date dateCreated, User createdBy) {
		super();
		this.collectionName = collectionName;
		this.collectionDescription = collectionDescription;
		this.dateCreated = dateCreated;
		this.collectionCreator = createdBy;
	}

	public long getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getCollectionDescription() {
		return collectionDescription;
	}

	public void setCollectionDescription(String collectionDescription) {
		this.collectionDescription = collectionDescription;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getCreatedBy() {
		return collectionCreator;
	}

	public void setCreatedBy(User createdBy) {
		this.collectionCreator = createdBy;
	}

	public List<Recipe> getSavedRecipes() {
		return savedRecipes;
	}

	public void setSavedRecipes(List<Recipe> savedRecipes) {
		this.savedRecipes = savedRecipes;
	}
	
	public void addRecipe(Recipe recipe) {
		savedRecipes.add(recipe);
	}

	public void removeRecipe(Recipe recipe) {
		savedRecipes.remove(recipe);
	}


}
