package com.recipspro;

import java.time.LocalDate;

public class Ingredient {
	private String item ;
	private int amount;
	private Measures unit;
	private LocalDate use_by;
	private int totalAmount;
	public Ingredient(String item, int amount, Measures unit, LocalDate date) {
		this.item = item;
		this.amount = amount;
		this.unit =unit;
		this.use_by = date;
	}
	public Ingredient(String item, int amount, Measures unit, LocalDate date, int totalAmount) {
		this.item = item;
		this.amount = amount;
		this.unit =unit;
		this.use_by = date;
		this.totalAmount = totalAmount;
	}
	public String toString(){
		return "(item: " + item + ", amount: " + amount + ", unit: " + unit + " : use_by:" + use_by+")";
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Measures getUnit() {
		return unit;
	}
	public void setUnit(Measures unit) {
		this.unit = unit;
	}
	public LocalDate getUse_by() {
		return use_by;
	}
	public void setUse_by(LocalDate use_by) {
		this.use_by = use_by;
	}
	@Override
	public boolean equals(Object obj){
		if (obj == this) { return true; }
		if((obj == null) || (obj.getClass() != this.getClass())) { return false; }
		Ingredient ingredient = (Ingredient) obj; 
		return (item == ingredient.item || item.equals(ingredient.item));
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
