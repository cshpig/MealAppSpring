package com.fdmgroup.mealappspring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.JoinColumn;

@Component
@Scope("prototype")
@Entity
@Table(name = "user_table")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	@Column(unique=true)
	private String username;
	private String password;
	
	@ManyToMany
	@JoinTable(name="Following", joinColumns=
	@JoinColumn(name="FK_FOLLOWER_USER_ID"), inverseJoinColumns=
	@JoinColumn(name="FK_FOLLOWING_USER_ID"))
	private List<User> following = new ArrayList<>();
	
	@OneToMany(mappedBy = "recipeCreator")
	private List<Recipe> createdRecipes = new ArrayList<>();
	@OneToMany(mappedBy = "collectionCreator")
	private List<Collection> createdCollections = new ArrayList<>(); // do we need setters for listr
	@ManyToMany
	@JoinTable(name="Saved_Collection", joinColumns=
	@JoinColumn(name="FK_USER_ID"), inverseJoinColumns=
	@JoinColumn(name="FK_COLLECTION_ID"))
	private List<Collection> savedCollections = new ArrayList<>();

	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<Recipe> getRecipes() {
		return createdRecipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.createdRecipes = recipes;
	}

	public List<Collection> getCreatedCollections() {
		return createdCollections;
	}

	public void setCreatedCollections(List<Collection> createdCollections) {
		this.createdCollections = createdCollections;
	}

	public List<Collection> getSavedCollections() {
		return savedCollections;
	}

	public void setSavedCollections(List<Collection> savedCollections) {
		this.savedCollections = savedCollections;
	}
	
	public List<User> getFollowing() {
		return following;
	}

	public void setFollowing(List<User> following) {
		this.following = following;
	}
	
	public void addFollowing(User user) {
		following.add(user);
	}
	
	public void removeFollowing(User user) {
		following.remove(user);
	}
	
	public void saveCollection(Collection collection) {
		savedCollections.add(collection);
	}
	
	public void removeCollection(Collection collection) {
		savedCollections.remove(collection);
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + "]";
	}
	
	

}
