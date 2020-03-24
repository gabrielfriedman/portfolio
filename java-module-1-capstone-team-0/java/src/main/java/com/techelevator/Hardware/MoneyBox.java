package com.techelevator.Hardware;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class MoneyBox {
	
	private enum MoneyValues{
		QUARTER(BigDecimal.valueOf(0.25)),
		DIME(BigDecimal.valueOf(0.10)),
		NICKEL(BigDecimal.valueOf(0.05)),
		PENNY(BigDecimal.valueOf(0.01));
		
		private BigDecimal value;
		
		MoneyValues(BigDecimal value){
			this.value = value;
		}
		
		public BigDecimal getValue() {
			return value;
		}
	}
	
	public MoneyBox() {};
	
	public MoneyBox(BigDecimal balance) {
		this.balance = balance;
	}
	
	private BigDecimal balance = new BigDecimal(0);
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void adjustBalance(BigDecimal adjustment) {
		balance = balance.add(adjustment);
	}
	
	
	public Map<String, Integer> finishTransaction() {
		BigDecimal tempBalance = balance;
		Map<String, Integer> coins = new LinkedHashMap<String, Integer>();
		
		/*
		 * gets num of coins (rounded using cast to int)
		 * gets value of those coins (n * coinValue)
		 * subtracts that from our balance
		 */

		for (MoneyValues moneyType: MoneyValues.values()) {
			BigDecimal amntCoins = new BigDecimal(tempBalance.divide(moneyType.getValue()).intValue());
			if (amntCoins.intValue() < 1) { continue; } //skips if the coin value doesn't go into enough times (0.25 tries to go into 0.20 -> <1)
			BigDecimal coinDollarAmnt = amntCoins.multiply(moneyType.getValue());
			tempBalance = tempBalance.subtract(coinDollarAmnt);
			
			coins.put(moneyType.toString().toLowerCase(), amntCoins.intValue());
		}
		
		if  (coins.size() == 0) { return null; }
		String printString = "Change returned: ";
		for (Map.Entry<String, Integer> coin: coins.entrySet()) {
			printString += "\n" + coin.getValue() + " " + coin.getKey() + (coin.getValue() > 1 ? "s" : "");
		}
		System.out.println(printString);
		balance = BigDecimal.valueOf(0);
		return coins;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "$"+df.format(getBalance().doubleValue());
	}
}
