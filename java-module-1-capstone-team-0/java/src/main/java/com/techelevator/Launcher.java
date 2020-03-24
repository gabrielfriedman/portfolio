package com.techelevator;

import com.techelevator.GlobalObjects.Logger;
import com.techelevator.Hardware.VendingMachine;
import com.techelevator.Hardware.VendingMachineHandler;
import com.techelevator.UIElements.Scenes.BaseMenuScene;

import uitextlib.ScreenContainer;

public class Launcher {
	
	public static void main(String[] args) {
		Logger.getInstance().delineateNewSession();
		VendingMachine vendingMachine = new VendingMachine();
		VendingMachineHandler vmHandler = new VendingMachineHandler(vendingMachine);
		ScreenContainer<VendingMachine> screenContainer = new ScreenContainer<VendingMachine>(vmHandler, new BaseMenuScene());
		String welcome = "Hello! Welcome to the Vendo-Matic 800! \nPlease choose the one of appropriate options \ngiven to you by typing in the corresponding number.\n";
		System.out.println(welcome);
		screenContainer.run();
	}

}
