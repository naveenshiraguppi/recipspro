package com.recipspro;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Recipe {
	private String name;
	private List<Ingredient> ingredients;
	public Recipe(String name, List<Ingredient> ingredients){
		this.name = name;
		this.ingredients = ingredients;
	}
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
	public LocalDate getNearestUseBy() {
		LocalDate retVal = null;
		if(ingredients != null){
			Optional<Ingredient> opt = ingredients.stream().max((a,b)-> {
				if(a.getUse_by() == null || b.getUse_by() == null){
					if(a.getUse_by() == null && b.getUse_by() == null)
						return 0;
					else if(b.getUse_by() == null)
							return 1;
					else return -1;
				}
				else 
					return b.getUse_by().compareTo(a.getUse_by());
			});
			if(opt.isPresent()){
				Ingredient itemWithNearestUseBy = opt.get();
				retVal = itemWithNearestUseBy.getUse_by();
			}
		}
		return retVal;
	}
	
	public Recipe getRecipeWithUseBy(List<Ingredient> usableFridgeItems) {
		List<Ingredient> ingredients = getIngredients().stream().map(item->{
			Ingredient ingredient = getMatchingItem(usableFridgeItems,item);
			if(ingredient != null){
				Ingredient newItem = new Ingredient(item.getItem(), item.getAmount(), item.getUnit(), ingredient.getUse_by(), ingredient.getAmount());
				return newItem;
			}else 
				return null;
		}).filter(item->item!=null).collect(Collectors.toList());
		Recipe newRecipe = new Recipe(getName(), ingredients);
		return newRecipe;
	}
	private Ingredient getMatchingItem(List<Ingredient> usableFridgeItems, Ingredient item) {
		List<Ingredient> items = usableFridgeItems.stream().filter(fridgeItem-> fridgeItem.equals(item) && fridgeItem.getAmount() >= item.getAmount()).collect(Collectors.toList());
		if(items.size() >= 1)
			return items.get(0);
		return null;
	}
	
	public boolean isRecipeViable(List<Ingredient> usableFridgeItems) {
		List<Ingredient> recipeItems = ingredients.stream().filter(item->isSufficientQuantity(usableFridgeItems, item)).collect(Collectors.toList());
		return recipeItems.size() == ingredients.size();
	}
	
	private boolean isSufficientQuantity(List<Ingredient> usableFridgeItems, Ingredient item) {
		long total = usableFridgeItems.stream().filter(fridgeItem-> fridgeItem.equals(item) && fridgeItem.getAmount() >= item.getAmount()).count();
		return total == 1;
	}
	public String toString(){
		return("Recipe: " + name + " Ingredients: " + ingredients);
	}
	public Ingredient getNearestUseByIngredient() {
		Ingredient retVal = null;
		if(ingredients != null){
			Optional<Ingredient> opt = ingredients.stream().max((a,b)-> {
				if(a.getUse_by() == null || b.getUse_by() == null){
					if(a.getUse_by() == null && b.getUse_by() == null)
						return 0;
					else if(b.getUse_by() == null)
							return 1;
					else return -1;
				}
				else 
					return b.getUse_by().compareTo(a.getUse_by());
			});
			if(opt.isPresent()){
				retVal = opt.get();
			}
		}
		return retVal;
	}
}
