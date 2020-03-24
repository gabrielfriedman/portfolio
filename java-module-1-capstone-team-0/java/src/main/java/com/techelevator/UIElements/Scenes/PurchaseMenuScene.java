package com.techelevator.UIElements.Scenes;

import java.util.ArrayList;
import java.util.List;

import uitextlib.ActionPackage;
import uitextlib.Scene;
import uitextlib.ActionPackage.*;
import uitextlib.Labels.MultipleChoiceLabel;
import uitextlib.Labels.TextLabel;
import uitextlib.Labels.TitleLabel;

import com.techelevator.Hardware.VendingMachine;

public class PurchaseMenuScene extends Scene<VendingMachine> {
	@SuppressWarnings("serial")
	private final List<String> CHOICES = new ArrayList<String>() {
		{
			add("Feed Money");
			add("Select Product");
			add("Finish Transaction");
		}
	};
	
	@Override
	protected void init() {
		String titleString = "PURCHASE";
		TitleLabel titleLabel = new TitleLabel(titleString);
		addNewElement(titleLabel);
		
		MultipleChoiceLabel<VendingMachine> purchaseChoices = new MultipleChoiceLabel<VendingMachine>(CHOICES);
		addNewElement(purchaseChoices);
		
		String moneyLabelText = "\nCurrent Money Provided: "+ object.getMoneyBox().toString();
		TextLabel moneyLabel = new TextLabel(moneyLabelText);
		addNewElement(moneyLabel);
	}
	
	private ActionPackage<VendingMachine> feedMoney() {
		return new ActionPackage<VendingMachine>(Actions.PUSH, new FeedMoneyScene());
	}
	
	private ActionPackage<VendingMachine> selectProduct() {
		return new ActionPackage<VendingMachine>(Actions.PUSH, new SelectProductScene());
	}
	
	private ActionPackage<VendingMachine> finish() {
		return new ActionPackage<VendingMachine>(Actions.ACTION, new ActionPackage<VendingMachine>(Actions.POP));
	}
	
	@Override
	public ActionPackage<VendingMachine> processRequest(String stringInput) {
		int userInput = Integer.parseInt(stringInput);
		if (userInput == 0) {
			return feedMoney();
		}else if (userInput == 1) {
			return selectProduct();
		}else if(userInput == 2) {
			return finish();
		}
		return null;
	}
}
