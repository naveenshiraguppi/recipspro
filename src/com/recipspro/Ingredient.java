package com.recipspro;

import java.time.LocalDate;

public class Ingredient {
	private String item ;
	private int amount;
	private Measures unit;
	private LocalDate use_by;
	public Ingredient(String item, int amount, Measures unit, LocalDate date) {
		this.item = item;
		this.amount = amount;
		this.unit =unit;
		this.use_by = date;
	}
	public String toString(){
		return "item: " + item + ", amount: " + amount + ", unit: " + unit + " : use_by:" + use_by;
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
	
}
