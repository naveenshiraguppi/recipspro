package com.recipspro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RecipeTest {
	@Test
	public void testGetRecipeWithUseBy(){
		Recipe recipe = new Recipe("aaa", new ArrayList<Ingredient>());
		Ingredient ing = new Ingredient("bread", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		ing = new Ingredient("cheese", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		
		List<Ingredient> fridgeItems = new ArrayList<Ingredient>();
		fridgeItems.add(new Ingredient("cheese", 2, Measures.slices, LocalDate.now().plusDays(2)));
		fridgeItems.add(new Ingredient("bread", 2, Measures.slices, LocalDate.now().plusDays(3)));
		
		recipe = recipe.getRecipeWithUseBy(fridgeItems);
		assertEquals("aaa", recipe.getName());
		assertEquals(2, recipe.getIngredients().size());
		assertEquals("bread", recipe.getIngredients().get(0).getItem());
		assertEquals(LocalDate.now().plusDays(3), recipe.getIngredients().get(0).getUse_by());
		assertEquals("cheese", recipe.getIngredients().get(1).getItem());
		assertEquals(LocalDate.now().plusDays(2), recipe.getIngredients().get(1).getUse_by());
	}
	@Test
	public void testIsRecipeViable(){
		Recipe recipe = new Recipe("aaa", new ArrayList<Ingredient>());
		Ingredient ing = new Ingredient("bread", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		ing = new Ingredient("cheese", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		
		List<Ingredient> fridgeItems = new ArrayList<Ingredient>();
		fridgeItems.add(new Ingredient("cheese", 2, Measures.slices, LocalDate.now().plusDays(2)));
		fridgeItems.add(new Ingredient("bread", 2, Measures.slices, LocalDate.now().plusDays(3)));
		
		boolean retVal = recipe.isRecipeViable(fridgeItems);
		
		assertTrue(retVal);
	}
	@Test
	public void testIsRecipeViableFalse(){
		Recipe recipe = new Recipe("aaa", new ArrayList<Ingredient>());
		Ingredient ing = new Ingredient("bread", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		ing = new Ingredient("cheese", 2, Measures.slices,null);
		recipe.getIngredients().add(ing);
		
		List<Ingredient> fridgeItems = new ArrayList<Ingredient>();
		fridgeItems.add(new Ingredient("cheese", 1, Measures.slices, LocalDate.now().plusDays(2)));
		fridgeItems.add(new Ingredient("bread", 2, Measures.slices, LocalDate.now().plusDays(3)));
		
		boolean retVal = recipe.isRecipeViable(fridgeItems);
		
		assertFalse(retVal);
	}
}
