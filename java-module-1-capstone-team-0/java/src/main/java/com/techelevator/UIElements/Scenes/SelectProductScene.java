package com.techelevator.UIElements.Scenes;

import uitextlib.ActionPackage;
import uitextlib.Scene;
import uitextlib.ActionPackage.*;
import uitextlib.Labels.TextLabel;
import uitextlib.Labels.TextResponseLabel;

import com.techelevator.Hardware.VendingMachine;

public class SelectProductScene extends Scene<VendingMachine> {

	private String[] concatArrays(String[] a, String[]b) {
	    String[] c = new String[a.length + b.length];
	    System.arraycopy(a, 0, c, 0, a.length);
	    System.arraycopy(b, 0, c, a.length, b.length);
	    return c;
	}
	
	@Override
	protected void init() {
		String titleString = "Please enter the code of the item you would like to purchase.\nEnter \"EXIT\" if you would like to go back.";
		String[] extraOptions = {"EXIT"};
		String[] validCodes = object.getInventory().getFoodCodes();
		
		String stockText = object.getInventory().stockToString();
		TextLabel stockLabel = new TextLabel(stockText);
		addNewElement(stockLabel);
		
		String moneyLabelText = "\nCurrent Money Provided: "+ object.getMoneyBox().toString() + "\n";
		TextLabel moneyLabel = new TextLabel(moneyLabelText);
		addNewElement(moneyLabel);
		
		TextResponseLabel<VendingMachine> purchaseChoices = new TextResponseLabel<VendingMachine>(titleString, concatArrays(extraOptions, validCodes));
		addNewElement(purchaseChoices);
	}
	
	@Override
	public ActionPackage<VendingMachine> processRequest(String request) {
		request = request.toUpperCase();
		if (request.equals("EXIT")) {
			return new ActionPackage<VendingMachine> (Actions.POP);
		}
		return new ActionPackage<VendingMachine> (Actions.ACTION, new ActionPackage<VendingMachine>(Actions.POP));
	}
}
