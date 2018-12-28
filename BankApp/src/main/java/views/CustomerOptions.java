package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.LoginController;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import controller.CustomerController.CustomerMenus;


public class CustomerOptions{
	private int endCondition;
	ArrayList<String> customerOptions = new ArrayList<String>();
	private int size;
	
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	MainMenuController mainMenuController;
	LoginController loginStatus;
	
	//ArrayList<String> accountOptions;
	//ArrayList<String> checkingsAccountOptions;
	//ArrayList<String> savingsAccountOptions;
	//Map<customerMenuOptions, ArrayList<String>> optionsMap = new HashMap<customerMenuOptions, ArrayList<String>>();
	
	public CustomerOptions() { }
	public CustomerOptions(CustomerMenus customerMenuOption) throws InterruptedException {
		mainMenuController = new MainMenuController();
		//customerOptions.passLoginInfo(loginStatus);
		if (customerOptions != null && customerOptions.size() > 0) customerOptions.clear();
		if (customerMenuOption == CustomerMenus.SELECTION) {
			setAccountsViewArrayValues();
		} else {
			mainMenuController.begin(Menus.DEFAULT);
		}
	}

	private void setAccountsViewArrayValues(/*customerMenuOptions optionType*/) {
		//ArrayList<String> option = optionsMap.get(optionType);
		addFormattedOption(1, "to View Checkings Account");
		addFormattedOption(2, "to View Savings Account");
		addFormattedOption(3, "to Logout");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Account Type:";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.customerOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = customerOptions.size();
		//addFormattedOption(customerOptions.size(), "Go back to Main Menu");
		if (endCondition == customerOptions.size()) endCondition--;
		this.customerOptions.add(customerOptions.size(), menuEndLine);
	} 
	public int displayAccountsMenu(/*customerMenuOptions whichOptions*/) throws IOException {
		
		for (int i = 0; i < customerOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				System.out.println("* (Logged in as " + LoginController.getLoggedInUsername() + ")");
				System.out.print(lineSeparator);
			}
			System.out.print(customerOptions.get(i));
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
	public int maxOptionLength() {
		int max = 0;
		for (String option : customerOptions) {
			max = (option.length() >= max) ? option.length() : max;
		}
		return max;
	}
	public void addFormattedOption(int i, String optionName) {
		customerOptions.add(String.format("* Select Option - %-2d- %-41s *\n", i, optionName));
	}
	public int getEndCondition() {
		return endCondition;
	}
	public int getSize() {
		return customerOptions.size();
	}
	public boolean inBounds(int selection) {
		return (selection > 0 && selection <= endCondition) ? true : false;
	}
	public void passLoginInfo(LoginController loginInfo){
		this.loginStatus = loginInfo;
	}
}
