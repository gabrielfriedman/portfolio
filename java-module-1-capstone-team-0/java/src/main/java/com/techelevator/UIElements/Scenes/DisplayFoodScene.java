package com.techelevator.UIElements.Scenes;

import java.util.Scanner;

import com.techelevator.Hardware.VendingMachine;

import uitextlib.ActionPackage;
import uitextlib.Scene;
import uitextlib.ActionPackage.*;
import uitextlib.Labels.TextLabel;
import uitextlib.Labels.TitleLabel;

public class DisplayFoodScene extends Scene<VendingMachine> {
	
	@Override
	protected void init() {
		String titleString = "We offer the following refreshments: \n";
		TitleLabel titleLabel = new TitleLabel(titleString);
		addNewElement(titleLabel);
		
		String stockText = object.getInventory().stockToString();
		TextLabel stockLabel = new TextLabel(stockText);
		addNewElement(stockLabel);
	}

	@Override 
	public String request(){
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Please press enter when done reading.");
		scanner.nextLine();
		return null;
	}

	@Override
	public ActionPackage<VendingMachine> processRequest(String userInput) {
		return new ActionPackage<VendingMachine>(Actions.POP);
	}
}
