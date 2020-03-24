package com.techelevator.Hardware;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.VendingUIConstants;
import com.techelevator.VendingUIConstants.*;

public class Inventory {

	/*
	 * initializes a new inventory of items up to n amount, determined in constants class
	 * need a set to hold all possible food types as their objects to keep their info
	 * otherwise it is difficult to Map.put(Food) <-not sure on implementation
	 */
	private Map<String, Integer> stock = new LinkedHashMap<String, Integer>();
	private Set<Food> typesOfFood = new HashSet<Food>(); 
	
	public Inventory() {
		init();
	}
	
	private void init() {
		fillStock();
	}

	public boolean removeFood(String foodName, int quantity) {
		if (stock.get(foodName) >= quantity) {
			stock.put(foodName, stock.get(foodName) - quantity);
			return true;
		}
		return false;
	}
	
	public Food getFoodFromName(String name) {
		for (Food foodEntry: typesOfFood) {
			if (foodEntry.getName().equalsIgnoreCase(name)) {
				return foodEntry;
			}
		}
		return null;
	}
	
	public Food getFoodFromCode(String code) {
		for (Food foodEntry: typesOfFood) {
			if (foodEntry.getCode().equalsIgnoreCase(code)) {
				return foodEntry;
			}
		}
		return null;
	}
	
	public Integer getRemainingFoodAmnt(String foodName) {
		return stock.get(foodName);
	}

	public Map<String, Integer> getStock() {
		return stock;
	}          
	
	public String[] getFoodCodes() {
		String[] foodCodes = new String[stock.size()];
		int i = 0;
		for (Map.Entry<String, Integer> foodPackage: stock.entrySet()) {
			foodCodes[i] = getFoodFromName(foodPackage.getKey()).getCode();
			i++;
		}
		return foodCodes;
	}

	private void fillStock() {
		Scanner file;
		try {
			String path = "vendingmachine.csv";
			file = new Scanner(new File(path));
			while (file.hasNextLine()) {
				String[] vendingItem = file.nextLine().split("\\|");
				String potentialType = vendingItem[3];
				String name = vendingItem[1];
				BigDecimal price = new BigDecimal(Double.parseDouble(vendingItem[2]));
				String code = vendingItem[0];
				FoodTypes type = FoodTypes.convertString(potentialType);
				Food newFood = new Food(type, name, price, code);
				
				stock.put(name, VendingUIConstants.INITIAL_STOCK);
				typesOfFood.add(newFood);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String foodToString(Food food, int quantity) {
		String labelText = String.format("%-25s", food.getName());
		labelText += String.format("%-5s", "|");
		labelText += String.format("%-10s", " [" + food.getCode() + "]");
		labelText += String.format("%-5s", "|");
		labelText += String.format("%-10s", food.getPriceString());
		labelText += String.format("%-5s", "|");
		labelText += quantity > 0 ? quantity : "SOLD OUT";
		labelText += "\n";
		return labelText;
	}
	
	public String stockToString() {
		String titleString = "";
		titleString += String.format("%-25s", "FOOD NAME");
		titleString += String.format("%-5s", "|");
		titleString += String.format("%-10s", "[ID]");
		titleString += String.format("%-5s", "|");
		titleString += String.format("%-10s", "COST");
		titleString += String.format("%-5s", "|");
		titleString += "QUANTITY\n";
		titleString += "--------------------------------------------------------------\n";

		FoodTypes previousFood = null;
		for (Map.Entry<String, Integer> entry: stock.entrySet()) {
			Food food = getFoodFromName(entry.getKey());
			int quantity = entry.getValue();
			if (previousFood == null || previousFood != food.getType()) {
				titleString += "\n" + food.getType().toString() + ": ";
				previousFood = food.getType();
				titleString += "\n";
			}
			titleString += foodToString(food, quantity);
		}
		return titleString;
	}
}