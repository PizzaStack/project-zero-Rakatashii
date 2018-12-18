package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import controller.MainMenuController;

public class MenuOptions { // TODO refacter without statics
	private ArrayList<String> mainOptions = new ArrayList<String>();
	private MainMenuController menuController = new MainMenuController();
	
	private void setArrayValues() {
		addFormattedOption(1, "To Register (If You Are A New Customer)");
		addFormattedOption(2, "For Customer Login");
		addFormattedOption(3, "For Employee Login");
		addFormattedOption(4, "For Admin Login");
		
		int maxLineLength = maxOptionLength()-1;
		String menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		int halfMaxLineLength = (maxLineLength / 2) - 6;
		String menuSideLine = String.join("", Collections.nCopies(halfMaxLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		mainOptions.add(0, String.join(" ", menuLeftHalf + "Main Menu" + menuRightHalf) + "-\n");
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
	
	public boolean display() throws IOException {
		if (mainOptions.size() == 0) setArrayValues();
		
		int maxLineLength = maxOptionLength()-1;
		String lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		
		for (int i = 0; i < mainOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			System.out.print(mainOptions.get(i));
		}

		int selection = 0;
		
		while (selection < 1 || selection > (mainOptions.size() - 2)) {
			
			selection = getSelection();
			while (selection == 0) selection = getSelection();
			//System.out.println("selection in display: " + selection);
			System.out.println("(mainOptions.size() - 2) = " + (mainOptions.size() - 2));
		
			if (selection == mainOptions.size() - 2) {
				return false;
			} else if  (selection < 1 || selection > (mainOptions.size() - 2)) {
				System.out.println("* " + selection + " is not a valid option.");
			}
		} 
		
		if (selection == mainOptions.size() - 2) {
			return false;
		}
		
		menuController.selectOption(selection);
		return true;
	}
	
	public int getSelection() throws IOException {
		while (true) {
			//int maxLineLength = maxOptionLength()-1;
			//String lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
			int selection = 0;

			@SuppressWarnings("resource")
			Scanner cin = new Scanner(System.in);
			
			//cin.useDelimiter("\\n");
			
			System.out.println();
			System.out.print("* Select option number: "); 
			
		    try {
		    	selection = Integer.parseInt(cin.nextLine());
		    	System.out.print("  Selection = " + selection + " (Press Enter Twice)");
		    	return String.valueOf(selection).codePointAt(0) - 48;
		    }
		    catch (NumberFormatException e) {
		        System.out.println("NumberFormatException");
		    } catch (InputMismatchException e) {
		    	System.out.println("InputMismatchException");
		    } catch (NoSuchElementException e) {
		    	System.out.println("NoSuchElementException");
		    	//return String.valueOf(selection).codePointAt(0) - 48
		    } finally {
		    	/* while (cin.hasNext("\n")) {
					cin.next("\n");
				} */
		    	//cin.close();
		    }
		}
	}	
}
