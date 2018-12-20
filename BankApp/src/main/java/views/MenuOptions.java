package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.BankApp.BankApp;

import controller.LoginController;
import controller.MainMenuController;

public class MenuOptions { // TODO refacter without statics
	private ArrayList<String> mainOptions = new ArrayList<String>();
	private int endCondition;
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	LoginController loginStatus;
	
	public MenuOptions() {
		setArrayValues();
	}
	private void setArrayValues() {
		addFormattedOption(1, "To Register (If You Are A New Customer)");
		addFormattedOption(2, "For Customer Login");
		addFormattedOption(3, "For Employee Login");
		addFormattedOption(4, "For Admin Login");
		
		maxLineLength = maxOptionLength()-1;
		lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		int halfMaxLineLength = (maxLineLength / 2) - 6;
		String menuSideLine = String.join("", Collections.nCopies(halfMaxLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		mainOptions.add(0, String.join(" ", menuLeftHalf + "Main Menu" + menuRightHalf) + "-\n");
		
		endCondition = mainOptions.size();
		addFormattedOption(mainOptions.size(), "Save Progress Quit");
		mainOptions.add(mainOptions.size(), menuEndLine);
	} 
	
	public int maxOptionLength() {
		int max = 0;
		for (String option : mainOptions) {
			max = (option.length() >= max) ? option.length() : max;
		}
		return max;
	}
	public void addFormattedOption(int i, String optionName) {
		mainOptions.add(String.format("* Select Option - %-2d- %-41s *\n", i, optionName));
	}
	
	public int getEndCondition() {
		return endCondition;
	}
	public boolean inBounds(int selection) {
		return (selection > 0 && selection <= endCondition) ? true : false;
	}
	
	public int display() throws IOException {
		for (int i = 0; i < mainOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			System.out.print(mainOptions.get(i));
		}
		int selection = 0;
		while (!inBounds(selection)) {
			
			selection = getSelection();
			//System.out.println("selection in display: " + selection);
		
			if (inBounds(selection))
				return selection;
			else {
				System.out.println("* " + selection + " is not a valid option.");
				selection = 0;
			}
		} 
		return -1;
	}
	
	public int getSelection() throws IOException {
		@SuppressWarnings("resource")
		Scanner cin = new Scanner(System.in);
		
		int selection = 0;
		while (!(inBounds(selection))) {
			selection = 0;
			System.out.print(lineSeparator);
			System.out.print("* Select option number: "); 
			
			selection = cin.nextInt();
			System.out.print("- Selection = " + selection + lineSeparator.substring(15));
	    	//System.out.print("Selection = " + selection + " (Press Enter Twice)\n");
	    	if (inBounds(selection)) { System.out.println(); return selection; }
		}
		System.out.print("Should not be at the end of getSelection()");
		return 0;
	}	
}
