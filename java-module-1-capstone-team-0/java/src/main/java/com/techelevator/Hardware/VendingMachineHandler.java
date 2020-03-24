package com.techelevator.Hardware;

import com.techelevator.UIElements.Scenes.FeedMoneyScene;
import com.techelevator.UIElements.Scenes.PurchaseMenuScene;
import com.techelevator.UIElements.Scenes.SelectProductScene;

import uitextlib.ObjectHandler;
import uitextlib.Scene;

public class VendingMachineHandler extends ObjectHandler<VendingMachine>{

	public VendingMachineHandler(VendingMachine vendingMachine) {
		super(vendingMachine);
	}

	@Override
	public void performAction(Scene<VendingMachine> scene, String userInput) {
		if (scene == null) { return; }
		/*
		 * is this okay???
		 * 
		 * limited because each scene can only guarantee one action that it could do
		 * fine for now
		 */
		if (scene instanceof FeedMoneyScene) {
			object.feedMoney(userInput);
		}else if (scene instanceof PurchaseMenuScene) {
			object.getChange();
		}else if (scene instanceof SelectProductScene) {
			object.purchaseFood(userInput);
		}
	}

}
