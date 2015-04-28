package com.recipspro;

import org.junit.Assert;
import org.junit.Test;

public class ReciproAppTest{
	@Test
	public void testNormalUseByComparator(){
		/*
		bread,10,slices,2/12/2015
		cheese,10,slices,25/12/2015
		butter,250,grams,25/12/2015
		peanut butter,250,grams,25/12/2015
		mixed salad,150,grams,25/8/2015
		
		[
			{
			"name": "grilled cheese on toast", 
				"ingredients":[
					{ "item":"bread", "amount":"2", "unit":"slices"},
					{ "item":"cheese", "amount":"2", "unit":"slices"}
				]
			}
			,
			{
				"name": "salad sandwich", 
				"ingredients": [
					{ "item":"bread", "amount":"2", "unit":"slices"},
					{ "item":"mixed salad", "amount":"100", "unit":"grams"}
				]
			}
		]
		*/
		String[] args = {"testfolder/fridge1.csv", "testfolder/recipes1.json"};
		Recipe recipe = ReciproApp.getRecepe(args);
		Assert.assertEquals("salad sandwich", recipe.getName());
	}
	@Test
	public void testUseByAndAmountComparator(){
		/*
		bread,10,slices,2/12/2015
		cheese,10,slices,25/12/2015
		butter,250,grams,25/12/2015
		peanut butter,250,grams,25/12/2015
		mixed salad,150,grams,25/12/2015
		
		
		[
			{
			"name": "grilled cheese on toast", 
				"ingredients":[
					{ "item":"bread", "amount":"6", "unit":"slices"},
					{ "item":"cheese", "amount":"2", "unit":"slices"}
				]
			}
			,
			{
				"name": "salad sandwich", 
				"ingredients": [
					{ "item":"bread", "amount":"5", "unit":"slices"},
					{ "item":"mixed salad", "amount":"100", "unit":"grams"}
				]
			}
		]
		*
		*/
		String[] args = {"testfolder/fridge1-with-bread-LeastUseBy.csv", "testfolder/recipes1-grilled-more-bread.json", "-amountAndUseBy"};
		Recipe recipe = ReciproApp.getRecepe(args);
		Assert.assertEquals("grilled cheese on toast", recipe.getName());
	}
}