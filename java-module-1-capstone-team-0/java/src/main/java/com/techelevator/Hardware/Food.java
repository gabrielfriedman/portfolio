package com.techelevator.Hardware;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.techelevator.VendingUIConstants.FoodTypes;

public class Food{
	private final String name;
	private final BigDecimal price;
	private final FoodTypes type;
	private final String code;
	
	public Food(FoodTypes type, String name, BigDecimal price, String code){
		this.type = type;
		this.name = name;
		this.price = price;
		this.code = code;
	}
	
	public FoodTypes getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getPriceString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return "$"+df.format(price.doubleValue());
	}
}
