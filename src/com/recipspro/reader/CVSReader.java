package com.recipspro.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.recipspro.Ingredient;
import com.recipspro.Measures;

public class CVSReader {
	List<Ingredient> ingredients = new ArrayList<Ingredient>();
	BufferedReader br;
	public CVSReader(File inputFile) throws FileNotFoundException{
		if(inputFile == null || !inputFile.isFile() || !inputFile.getName().toLowerCase().endsWith(".csv")){
			throw new IllegalArgumentException("Expecting non null, file with extension '.cvs'.");
		}
		this.br =  new BufferedReader(new FileReader(inputFile));
	}
	/**
	 * To be used by Unit Tests only.
	 */
	CVSReader(){
	}
	public void parseFile(){
		ingredients = br.lines()
					.filter((s)-> {
						boolean retVal = s.trim().length() > 0;
						return retVal;
					})
					.map((s)->{
						String [] arr = s.split(",");
						return arr;
					})
					.map((s)->{
			Ingredient ing = new Ingredient(s[0],Integer.parseInt(s[1]),Measures.valueOf(s[2]), parseDate(s[3]));
			return ing;
		}).collect(Collectors.toList());
	}
	private LocalDate parseDate(String dateStr){
		String[] parts = dateStr.split("/");
		if(parts == null || parts.length != 3){
			throw new IllegalArgumentException("Invalid Date format. Expecting dd/MM/yyyy.");
		}
		LocalDate date = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
		return date;
	}
	void setReader(BufferedReader br){
		this.br = br;
	}
	public List<Ingredient> getIngredients(){
		return ingredients;
	}
}
