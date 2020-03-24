package uitextlib.Labels;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import uitextlib.ActionPackage;
import uitextlib.ActionPackage.*;
 
public class TextResponseLabel<T> implements Showable, Requestable<T>{
	private String[] validInputs = null;
	private String text;
	
	/*
	 * can be initialized with any String being valid or only certain String being valid depending on constructor used 
	 */
	public TextResponseLabel(String text) {
		this.text = text;
	}
	
	public TextResponseLabel(String text, String[] validInputs) {
		this.text = text;
		this.validInputs = validInputs;
	}
	
	private boolean isValid(String userInput) {
		if (validInputs == null || validInputs.length == 0) { return true; }
		String[] sortedArray = validInputs.clone();
		Arrays.sort(sortedArray);
		List<String> list = Arrays.stream(sortedArray).map(s -> s.toUpperCase()).collect(Collectors.toList());
		return list.contains(userInput.toUpperCase());
	}
	
	@Override
	public String request() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String userInput = null;
		while (userInput == null) {
			System.out.print("INPUT: ");
			userInput = scanner.nextLine().trim();
			if (userInput == null || !isValid(userInput)) {
				System.out.println("That was not a valid option.");
				userInput = null;
			}
		}
		return userInput;
	}
	
	@Override
	public void show() {
		System.out.println(text); 
	}

	@Override
	public ActionPackage<T> processRequest(String userInput) {
		return new ActionPackage<T>(Actions.POP);
	}
}
