package uitextlib.Labels;

import java.util.List;
import java.util.Scanner;

import uitextlib.ActionPackage;

import java.util.ArrayList;

public class MultipleChoiceLabel<T> implements Showable, Requestable<T>{
	/*
	 * Simply shows n number of choices and allows returnable int from 0->n-1 
	 */
	private List<String> choices = new ArrayList<String>();
	
	/*
	 * Takes in an arraylist of strings that are going to be its numbered choices. Can prefix
	 * a choice with '-' to indicate that it should be a hidden option.
	 */
	
	public MultipleChoiceLabel(List<String> choices) {
		this.choices = choices;
	}
	
	/*
	 * Ensures that the answer put in through the request is a valid choice 
	 * E.G. Non-negative & in the range of the choices
	 */
	private boolean isValidNumber(int answer) {
		return answer - 1 >= 0 && answer - 1 < choices.size();
	}
	
	@Override
	public void show() {
		for (int i = 0; i < choices.size(); i++) { 
			String choice = choices.get(i);
			if (choice.length() > 0 && choice.substring(0, 1).equals("-")) { continue; }
			System.out.println("(" + (i + 1) + ") " + choice); 
		}
	}
	
	@Override
	public String request() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer answer = null;
		while (answer == null) {
			try {
				System.out.print("INPUT: ");
				answer = Integer.parseInt(scanner.nextLine().trim());
			}catch(NumberFormatException e){
			}finally {
				if (answer == null || !isValidNumber(answer)) {
					System.out.println("That was not a number option.");
					answer = null;
				}
			}
		}
		return Integer.toString(answer - 1);
	}

	@Override
	public ActionPackage<T> processRequest(String userInput) {
		return null;
	}
}
