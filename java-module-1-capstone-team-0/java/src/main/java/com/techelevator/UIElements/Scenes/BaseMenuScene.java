package com.techelevator.UIElements.Scenes;

import java.util.ArrayList;
import java.util.List;

import uitextlib.ActionPackage;
import uitextlib.Scene;
import uitextlib.ActionPackage.*;
import uitextlib.Labels.MultipleChoiceLabel;
import uitextlib.Labels.TitleLabel;

import com.techelevator.GlobalObjects.Logger;
import com.techelevator.Hardware.VendingMachine;

public class BaseMenuScene extends Scene<VendingMachine> {
	
	@SuppressWarnings("serial")
	private final List<String> CHOICES = new ArrayList<String>() {
		{
			add("Display Vending Machine Items");
			add("Purchase");
			add("Exit");
			add("-Update Log File");
		}
	};
	
	@Override
	protected void init() {
		String titleString = "MENU";
		TitleLabel titleLabel = new TitleLabel(titleString);
		addNewElement(titleLabel);
	
		MultipleChoiceLabel<VendingMachine> baseMenuChoices = new MultipleChoiceLabel<VendingMachine>(CHOICES);
		addNewElement(baseMenuChoices);
	}

	@Override
	public ActionPackage<VendingMachine> processRequest(String stringInput) {
		int userInput = Integer.parseInt(stringInput);
		if (userInput == 0) {
			return displayItems();
		}else if (userInput == 1) {
			return purchase();
		}else if(userInput == 2) {
			exit();
		}else if(userInput == 3) {
			createLogFile();
		}
		return null;
	}
	
	private void exit() {
		String exitMessage = "Thank you for using the Vendo-Matic 800! We hope the rest of your day is vendo-tastic!";
		System.out.println(exitMessage);
		System.exit(0);
	}
	
	private ActionPackage<VendingMachine> purchase() {
		return new ActionPackage<VendingMachine>(Actions.PUSH, new PurchaseMenuScene());
	}

	private ActionPackage<VendingMachine> displayItems() {
		return new ActionPackage<VendingMachine>(Actions.PUSH, new DisplayFoodScene());
	}

	private ActionPackage<VendingMachine> createLogFile() {
		dumpSalesReport();
		return new ActionPackage<VendingMachine>(Actions.REFRESH);
	}
	
	private void dumpSalesReport() {
		Logger.getInstance().dumpSalesReport(object);
		String msg = "Dumping sales report...";
		System.out.println(msg);
	}

}
