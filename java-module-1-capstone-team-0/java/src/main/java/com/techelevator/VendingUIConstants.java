package com.techelevator;

public class VendingUIConstants {
	
	/*
	 * DEFINING ACCEPTED BILLS
	 */
	
	public static final String[] ACCEPTED_BILLS = {"1", "2", "5", "10"};
	
	/*
	 * DEFINING INITIAL STOCK VALUE
	 */
	
	public static final int INITIAL_STOCK = 5;
	
	/*
	 * DEFINING FOOD ENUMS
	 */
	
	public static enum FoodTypes{
		CHIP("Crunch Crunch"),
		CANDY("Munch Mqunch"),
		DRINK("Glug Glug"),
		GUM("Chew Chew");
		private String dispenseMessage;
		
		FoodTypes(String dispenseMessage){
			String suffix = ", Yum!";
			this.dispenseMessage = dispenseMessage + suffix;
		}
		
		public String getDispenseMessage() {
			return dispenseMessage;
		}
		
		public static FoodTypes convertString(String s) {
			for (FoodTypes foodType: FoodTypes.values()) {
				if (foodType.toString().toLowerCase().equals(s.toLowerCase())) {
					return foodType;
				}
			}
			return null;
		}
		
	}

}
