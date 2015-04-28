package com.recipspro.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.Month;
import java.util.List;

import org.junit.Test;

import com.recipspro.Ingredient;
import com.recipspro.Measures;

public class JSONReaderTest {
	@Test
	public void testSimpleReader(){
		File file = new File("./test.cvs");
		System.out.println("File Path:" + file.getAbsolutePath());
		assertTrue(!file.isFile());
		try{
			JSONReader reader = new JSONReader(file);
			fail();
		}catch(IllegalArgumentException e){
			//expecting
		} catch (FileNotFoundException e) {
			fail();
		}
		try{
			JSONReader reader = new JSONReader(null);
			fail();
		}catch(IllegalArgumentException e){
			//expecting
		} catch (FileNotFoundException e) {
			fail();
		}
		file = new File("testfolder/fridge1.csv");
		System.out.println("JSON File Path:" + file.getAbsolutePath());
		assertTrue(file.isFile());
		try{
			JSONReader reader = new JSONReader(file);
			fail();
		}catch(IllegalArgumentException e){
			//expecting
		} catch (FileNotFoundException e) {
			fail();
		}
		
		file = new File("testfolder/recipes1.json");
		System.out.println("JSON File Path:" + file.getAbsolutePath());
		assertTrue(file.isFile());
		try{
			JSONReader reader = new JSONReader(file);
		}catch(IllegalArgumentException e){
			fail();
		} catch (FileNotFoundException e) {
			fail();
		}
	}
	@Test
	public void testContent(){
		String inputRecords = "["
				+ "{	\"name\": \"grilled cheese on toast\","
				+ " 	\"ingredients\":[{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},	"
				+ "						{ \"item\":\"cheese\", \"amount\":\"2\", \"unit\":\"slices\""
				+ "						}"
				+ "						]}"
				+ ","
				+ "{	\"name\": \"salad sandwich\", "
				+ "			\"ingredients\": [{ \"item\":\"bread\", \"amount\":\"2\", \"unit\":\"slices\"},"
				+ "							{ \"item\":\"mixed salad\", \"amount\":\"100\", \"unit\":\"grams\""
				+ "							}"
				+ "							]"
				+ "}"
				+ "]";
				BufferedReader br = new BufferedReader(new StringReader(inputRecords));
				JSONReader reader = new JSONReader();
				reader.setReader(br);
				reader.parseFile();
				List<Recipe> recipes = reader.getRecipes();
				assertEquals(2, recipes.size());
				Recipe recipe = recipes.get(0);
				assertEquals("grilled cheese on toast",recipe.getName());
				
				assertEquals("bread",recipe.getIngredients().get(0).getItem());
				assertEquals(2,recipe.getIngredients().get(0).getAmount());
				assertEquals(Measures.slices,recipe.getIngredients().get(0).getUnit());
				assertEquals("cheese",recipe.getIngredients().get(1).getItem());
				assertEquals(2,recipe.getIngredients().get(1).getAmount());
				assertEquals(Measures.slices,recipe.getIngredients().get(1).getUnit());
				recipe = recipes.get(1);
				assertEquals("bread",recipe.getIngredients().get(0).getItem());
				assertEquals(2,recipe.getIngredients().get(0).getAmount());
				assertEquals(Measures.slices,recipe.getIngredients().get(0).getUnit());
				assertEquals("mixed salad",recipe.getIngredients().get(1).getItem());
				assertEquals(100,recipe.getIngredients().get(1).getAmount());
				assertEquals(Measures.grams,recipe.getIngredients().get(1).getUnit());
				
	}
	
	@Test
	public void testContentFromFile(){
		File file = new File("testfolder/recipes1.json");
		JSONReader reader;
		try {
			reader = new JSONReader(file);
			reader.parseFile();
			List<Recipe> recipes = reader.getRecipes();
			assertEquals(2, recipes.size());
			Recipe recipe = recipes.get(0);
			assertEquals("grilled cheese on toast",recipe.getName());
			
			assertEquals("bread",recipe.getIngredients().get(0).getItem());
			assertEquals(2,recipe.getIngredients().get(0).getAmount());
			assertEquals(Measures.slices,recipe.getIngredients().get(0).getUnit());
			assertEquals("cheese",recipe.getIngredients().get(1).getItem());
			assertEquals(2,recipe.getIngredients().get(1).getAmount());
			assertEquals(Measures.slices,recipe.getIngredients().get(1).getUnit());
			recipe = recipes.get(1);
			assertEquals("bread",recipe.getIngredients().get(0).getItem());
			assertEquals(2,recipe.getIngredients().get(0).getAmount());
			assertEquals(Measures.slices,recipe.getIngredients().get(0).getUnit());
			assertEquals("mixed salad",recipe.getIngredients().get(1).getItem());
			assertEquals(100,recipe.getIngredients().get(1).getAmount());
			assertEquals(Measures.grams,recipe.getIngredients().get(1).getUnit());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
