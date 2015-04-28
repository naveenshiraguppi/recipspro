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

public class CVSReaderTest {
	@Test
	public void testSimpleReader(){
		File file = new File("./test.cvs");
		System.out.println("File Path:" + file.getAbsolutePath());
		assertTrue(!file.isFile());
		try{
			CVSReader reader = new CVSReader(file);
			fail();
		}catch(IllegalArgumentException e){
			//expecting
		} catch (FileNotFoundException e) {
			fail();
		}
		try{
			CVSReader reader = new CVSReader(null);
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
			CVSReader reader = new CVSReader(file);
			fail();
		}catch(IllegalArgumentException e){
			//expecting
		} catch (FileNotFoundException e) {
			fail();
		}
		
		file = new File("testfolder/fridge1.csv");
		System.out.println("CSV File Path:" + file.getAbsolutePath());
		assertTrue(file.isFile());
		try{
			CVSReader reader = new CVSReader(file);
		}catch(IllegalArgumentException e){
			fail();
		} catch (FileNotFoundException e) {
			fail();
		}
	}
	@Test
	public void testContent(){
		String inputRecords = "bread,10,slices,25/12/2014\n" +
				"cheese,10,slices,25/12/2014\n" +
				"butter,250,grams,25/12/2014\n" +
				"peanut butter,250,grams,2/12/2014\n" +
				"mixed salad,150,grams,26/12/2013\n";
				BufferedReader br = new BufferedReader(new StringReader(inputRecords));
				{
					CVSReader reader = new CVSReader();
					reader.setReader(br);
					reader.parseFile();
					List<Ingredient> ingrediens = reader.getIngredients();
					assertEquals(5, ingrediens.size());
					assertEquals("bread", ingrediens.get(0).getItem());
					assertEquals("mixed salad", ingrediens.get(4).getItem());
					assertEquals(150, ingrediens.get(4).getAmount());
					assertEquals(Measures.valueOf("grams"), ingrediens.get(4).getUnit());
					assertEquals(26, ingrediens.get(4).getUse_by().getDayOfMonth());
					assertEquals(Month.DECEMBER, ingrediens.get(4).getUse_by().getMonth());
					assertEquals(2013, ingrediens.get(4).getUse_by().getYear());
				}
	}
	@Test
	public void testContentFromFile(){
		File file = new File("testfolder/fridge1.csv");
		CVSReader reader;
		try {
			reader = new CVSReader(file);
			reader.parseFile();
			List<Ingredient> ingrediens = reader.getIngredients();
			assertEquals(5, ingrediens.size());
			assertEquals("bread", ingrediens.get(0).getItem());
			assertEquals("mixed salad", ingrediens.get(4).getItem());
			assertEquals(150, ingrediens.get(4).getAmount());
			assertEquals(Measures.valueOf("grams"), ingrediens.get(4).getUnit());
			assertEquals(26, ingrediens.get(4).getUse_by().getDayOfMonth());
			assertEquals(Month.DECEMBER, ingrediens.get(4).getUse_by().getMonth());
			assertEquals(2013, ingrediens.get(4).getUse_by().getYear());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
