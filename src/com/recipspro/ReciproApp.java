package com.recipspro;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.recipspro.reader.CVSReader;
import com.recipspro.reader.JSONReader;

public class ReciproApp {
	List<Ingredient> fridgeContents;
	List<Recipe> recipes;
	File ingredientsFile = null;
	File recipesFile = null;
	Comparator<Recipe> recipeComparator;
	public ReciproApp(File ingredients, File recipes){
		this.ingredientsFile = ingredients;
		this.recipesFile = recipes;
	}
	public Recipe suggestRecipe(Comparator<Recipe> comparator) throws FileNotFoundException{
		CVSReader ingredientReader = new CVSReader(ingredientsFile);
		JSONReader recipeReader = new JSONReader(recipesFile);
		ingredientReader.parseFile();
		recipeReader.parseFile();
		fridgeContents = ingredientReader.getIngredients();
		recipes = recipeReader.getRecipes();
		
		List<Ingredient> usableFridgeItems = fridgeContents .stream()
				.filter(ing -> (ing.getAmount() > 0 && ing.getUse_by() != null
						&& ing.getUse_by().isAfter(LocalDate.now())))
				.collect(Collectors.toList());
		
		recipes = recipes.stream().map(item->item.getRecipeWithUseBy(usableFridgeItems)).collect(Collectors.toList());
		List<Recipe> doableList = recipes.stream().filter(recipe-> recipe.isRecipeViable(usableFridgeItems))
						.collect(Collectors.toList());
		//System.out.println("Shortlisted Doable Recipes: ");
		//doableList.forEach(System.out::println);
		
		Optional<Recipe> suggestedRecipe = doableList.stream().max(comparator);
		if(suggestedRecipe.isPresent())
			return suggestedRecipe.get();
		return null;
	}
	public static void main(String[] args) {
		if(args.length==1){
			if(args[0].equals("--help")){
				System.out.println("Usage: java com.recipspro.ReciproApp [fridge.csv recipe.json [options]] ");
				System.out.println("options: -amountAndUseBy : for using the Comparator for UseBY and max ratio Amount");
			}
			else{
				System.out.println("Invalid usage. Try usage: java com.recipspro.ReciproApp --help");
			}
			return;
		}
		Recipe recipe = getRecepe(args);
		if(recipe == null)
			System.out.println("Could not suggest a recipe" );
		else
			System.out.println("Suggested Recipe: " + recipe.getName());
	}
	static Recipe getRecepe(String[] args){
		File ingredients = null;
		File recipes = null;
		Comparator<Recipe> comparator = new RecipeIngredientUseByComparator();
		if(args.length==0){
			ingredients = new File("fridge.csv");
			recipes = new File("recipes.json");
			comparator = new RecipeIngredientUseByComparator();
		}else{
			if(args.length == 2 || args.length == 3){
				ingredients = new File(args[0]);
				recipes = new File(args[1]);
				if(args.length == 3){
					if("-amountAndUseBy".equals(args[2])){
						System.out.println("Using the Comparator for UseBY and max ratio Amount");
						comparator = new RecipeIngredientUseByAndAmountComparator();
					}
				}
			}
			else{
				System.out.println("Invalid usage. Try usage: java com.recipspro.ReciproApp --help");
			}
		}
		if(ingredients != null){
			System.out.println("User input: ingredients =" + ingredients.getAbsolutePath());
			System.out.println("User input: recipes =" + recipes.getAbsolutePath());
			try {
				ReciproApp app = new ReciproApp(ingredients, recipes);
				Recipe recipe = app.suggestRecipe(comparator);
				return recipe;
			} catch (FileNotFoundException e) {
				System.out.println("Exception occurred while reading input files.");
				e.printStackTrace();
			}
		}
		return null;
	}
}
