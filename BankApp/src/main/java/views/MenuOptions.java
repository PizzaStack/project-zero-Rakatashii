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
import controller.MainMenuController.Menus;
import controller.CustomerController.CustomerMenus;

public class MenuOptions { 
	private ArrayList<String> mainOptions = new ArrayList<String>(); 
	private int endCondition;
	private int size;
	
	private int maxLineLength;
	private static String lineSeparator, menuEndLine;
	
	LoginController loginStatus;
	
	public MenuOptions() { 
		
	}
	public MenuOptions(Menus menuType) {
		if (mainOptions.size() > 0) {
			mainOptions.clear();
		}
		if (menuType == Menus.DEFAULT) setHomeViewArrayValues();
		else if (menuType == Menus.EXIT) return;
		size = mainOptions.size();
	}
	private void setHomeViewArrayValues() {
		addFormattedOption(1, "To Register (If You Are A New Customer)");
		addFormattedOption(2, "For Customer Login");
		addFormattedOption(3, "For Employee Login");
		addFormattedOption(4, "For Admin Login");
		
		maxLineLength = maxOptionLength()-1;
		lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Main Menu";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		mainOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		endCondition = mainOptions.size();
		addFormattedOption(mainOptions.size(), "To Quit");
		mainOptions.add(mainOptions.size(), menuEndLine);
	} 
	public int displayHomeMenu() throws IOException {
		for (int i = 0; i < mainOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				System.out.println("* (Logged in as " + LoginController.getLoggedInUsername() + ")");
				System.out.print(lineSeparator);
			} 
			System.out.print(mainOptions.get(i));
		}
		int selection = 0;
		while (!inBounds(selection)) {
			selection = getSelection();
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
			System.out.println();
			//System.out.print(lineSeparator);
			System.out.print("* Select option number: "); 
			selection = cin.nextInt();
			//System.out.println();
			//System.out.print("  --------- Selection = " + selection + lineSeparator.substring(15, lineSeparator.length()-1));
	    	//System.out.print("Selection = " + selection + " (Press Enter Twice)\n");
	    	if (inBounds(selection)) { System.out.println(); return selection; }
		}
		System.out.print("Should not be at the end of getSelection()");
		return 0;
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
	public int getSize() {
		return mainOptions.size();
	}
	public void passLoginInfo(LoginController loginInfo){
		this.loginStatus = loginInfo;
	}
	public static String getLineSeparator() {
		return lineSeparator.substring(0, lineSeparator.length()-1 );
	}
	public static String getMenuEndLine() {
		return menuEndLine.substring(0,  menuEndLine.length() - 1);
	}
}
