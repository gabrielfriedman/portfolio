package com.techelevator.UIElements.Scenes;

import com.techelevator.VendingUIConstants;

import uitextlib.ActionPackage;
import uitextlib.Scene;
import uitextlib.ActionPackage.*;
import uitextlib.Labels.TextResponseLabel;

import com.techelevator.Hardware.VendingMachine;

public class FeedMoneyScene extends Scene<VendingMachine> {

	@Override
	protected void init() {
		String titleString = "Please enter how many dollars to input.\nOnly";
		String[] acceptedBills = VendingUIConstants.ACCEPTED_BILLS;
		for (int i = 0; i < acceptedBills.length; i++) {
			if (i == acceptedBills.length-1) {
				titleString += " and";
			}
			titleString += " $" + acceptedBills[i];
			if (i != acceptedBills.length-1) {
				titleString += ",";
			}
		};
		titleString += " bills are accepted.";
		TextResponseLabel<VendingMachine> purchaseChoices = new TextResponseLabel<VendingMachine>(titleString, acceptedBills);
		addNewElement(purchaseChoices);
	}
	
	@Override
	public ActionPackage<VendingMachine> processRequest(String request) {
		return new ActionPackage<VendingMachine>(Actions.ACTION, new ActionPackage<VendingMachine>(Actions.POP));
	}

}
