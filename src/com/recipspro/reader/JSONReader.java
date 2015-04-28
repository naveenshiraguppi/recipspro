package com.recipspro.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.recipspro.Ingredient;
import com.recipspro.Measures;

public class JSONReader {
	List<Recipe> recipes;
	BufferedReader br;
	public JSONReader(File inputFile) throws FileNotFoundException{
		if(inputFile == null || !inputFile.isFile() || !inputFile.getName().toLowerCase().endsWith(".json")){
			throw new IllegalArgumentException("Expecting non null, file with extension '.cvs'.");
		}
		this.br =  new BufferedReader(new FileReader(inputFile));
	}
	/**
	 * To be used by Unit Tests only.
	 */
	JSONReader(){
	}
	public void parseFile(){
		String recipeFileContent = br.lines().collect(Collectors.joining());
		Object jsonObject = JSONValue.parse(recipeFileContent);
		JSONArray array=(JSONArray)jsonObject;
		if(array != null){
			recipes = new ArrayList<Recipe>((int)(array.size()* 1.4));
			for(Object obj: array){
				Recipe recipe = new Recipe();
				JSONObject jsonObj = (JSONObject)obj;
				String name = (String)jsonObj.get("name");
				JSONArray ingredientsObj = (JSONArray)jsonObj.get("ingredients");
				List<Ingredient> ingredients = new ArrayList<Ingredient>((int)(ingredientsObj.size() * 1.4));
				for(Object ingObj: ingredientsObj){
					JSONObject jsonIngObj = (JSONObject)ingObj;
					String itemName = (String)jsonIngObj.get("item");
					String itemAmount = (String)jsonIngObj.get("amount");
					String unit = (String)jsonIngObj.get("unit");
					Ingredient ingredient = new Ingredient(itemName, Integer.parseInt(itemAmount), Measures.valueOf(unit), null);
					ingredients.add(ingredient);
				}
				recipe.setName(name);
				recipe.setIngredients(ingredients);
				recipes.add(recipe);
			}
		}
	}
	public List<Recipe> getRecipes(){
		return recipes;
	}
	public void setReader(BufferedReader br2) {
		this.br = br2;
	}
}
