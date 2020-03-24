package com.techelevator.Hardware;

import java.math.BigDecimal;

import com.techelevator.GlobalObjects.Logger;

public class VendingMachine {
	
	Inventory inventory;
	MoneyBox moneyBox;
	
	public VendingMachine() {
		init();
	}
	
	public MoneyBox getMoneyBox() {
		return this.moneyBox;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public boolean feedMoney(String userInput) {
		/*
		 * definitely better things to catch than a generic Exception lol ???
		 */
		try {
			String prevBalance = moneyBox.toString();
			moneyBox.adjustBalance(new BigDecimal(Integer.parseInt(userInput)));
			String newBalance = moneyBox.toString();
			Logger.getInstance().writeToLog("FEED MONEY:", prevBalance, newBalance);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean getChange() {
		try {
			moneyBox.finishTransaction();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Food purchaseFood(String userInput) {
		Food food = null;
		try {
			System.out.println(userInput);
			food = inventory.getFoodFromCode(userInput);
			int foodStock = inventory.getRemainingFoodAmnt(food.getName());
			if (foodStock > 0) {
				if (food.getPrice().compareTo(moneyBox.getBalance()) == 1) {
					//if the price is greater than the balance of the moneybox, fail to buy and pop
					System.out.println("You don't have enough money to buy this item.");
				}else {
					//otherwise purchase the item
					/*
					 * setup variables to prep logs
					 */
					String previousBalance = moneyBox.toString();
					String newBalance;
					String headerName;
					
					/*
					 * purchasing logic
					 */
					moneyBox.adjustBalance(food.getPrice().multiply(new BigDecimal(-1)));
					inventory.removeFood(food.getName(), 1);
					String successString = "You bought "+food.getName()+" which costs "+food.getPriceString()+""
											+ ". You have a balance of "+ moneyBox.toString() + ".";
					System.out.println(successString);
					System.out.println(food.getType().getDispenseMessage());
					
					/*
					 * writing to the log and the sales cache
					 */
					newBalance = moneyBox.toString();
					headerName = food.getName() + " " + food.getCode();
					Logger.getInstance().writeToLog(headerName, previousBalance, newBalance);
					Logger.getInstance().updateSalesCache(food.getName());
				}
			}else {
				System.out.println("Sorry but that item is sold out!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return food;
	}
	
	private void init() {
		inventory = new Inventory();
		moneyBox = new MoneyBox();
	}
}
