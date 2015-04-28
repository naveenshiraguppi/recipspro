package com.recipspro.reader;

import java.util.List;

import com.recipspro.Ingredient;

public class Recipe {
	private String name;
	private List<Ingredient> ingredients;
	public Recipe(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
}
