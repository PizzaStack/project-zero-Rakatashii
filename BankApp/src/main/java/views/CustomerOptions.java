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
import customers.Customer;
import utility.Symbols;
import controller.CustomerController.CustomerMenus;


public class CustomerOptions{
	private int endCondition;
	ArrayList<String> customerOptions = new ArrayList<String>();
	
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	MainMenuController mainMenuController;
	LoginController loginStatus;
	
	Customer customer;

	public CustomerOptions() { }
	public CustomerOptions(CustomerMenus customerMenuOption) throws InterruptedException {
		if (customerOptions != null && customerOptions.size() > 0) customerOptions.clear();
		if (customerMenuOption == CustomerMenus.SELECTION) {
			setAccountsViewArrayValues();
		} else if (customerMenuOption == CustomerMenus.CHECKING) {
			setCheckingAccountViewArrayValues();
		} else if (customerMenuOption == CustomerMenus.SAVINGS) {
			setSavingsAccountViewArrayValues();
		} 
		else {
			mainMenuController.begin(Menus.DEFAULT);
		}
	}

	private void setAccountsViewArrayValues(/*customerMenuOptions optionType*/) {
		if (customerOptions.size() > 0) customerOptions.clear();
		addFormattedOption(1, "to View Checkings Account");
		addFormattedOption(2, "to View Savings Account");
		addFormattedOption(3, "to Add A Joint Customer");
		addFormattedOption(4, "Logout And Return To Main Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Accounts";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + "- ";
		String menuRightHalf = " --" + menuSideLine;
		
		this.customerOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "\n");
		this.endCondition = customerOptions.size();
		if (endCondition == customerOptions.size()) endCondition--;
		this.customerOptions.add(customerOptions.size(), menuEndLine);
	} 

	private void setSavingsAccountViewArrayValues() {
		if (customerOptions.size() > 0) customerOptions.clear();
		addFormattedOption(1, "Check Balance");
		addFormattedOption(2, "Deposit");
		addFormattedOption(3, "Withdraw");
		addFormattedOption(4, "Transfer To Checking Account");
		addFormattedOption(5, "Go Back To Accounts Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Savings Account";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.customerOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = customerOptions.size();
		if (endCondition == customerOptions.size()) endCondition--;
		this.customerOptions.add(customerOptions.size(), menuEndLine);
	} 
	private void setCheckingAccountViewArrayValues() {
		addFormattedOption(1, "Check Balance");
		addFormattedOption(2, "Deposit");
		addFormattedOption(3, "Withdraw");
		addFormattedOption(4, "Transfer To Savings Account");
		addFormattedOption(5, "Go Back To Accounts Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Checking Account";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + "- ";
		String menuRightHalf = " -" + menuSideLine;
		
		this.customerOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = customerOptions.size();
		if (endCondition == customerOptions.size()) endCondition--;
		this.customerOptions.add(customerOptions.size(), menuEndLine);
	} 
	public int displayAccountsMenu() throws IOException {
		
		for (int i = 0; i < customerOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				String loginStatement = Symbols.whiteStar + "  [Logged In As: " + LoginController.getLoggedInUsername() + "]";
				System.out.println(loginStatement
						+ String.join("", Collections.nCopies(maxLineLength-(loginStatement.length()), " "))
						+ Symbols.whiteStar);
				System.out.print(lineSeparator);	
			}
			//System.out.print(adminOptions.get(i));
			if (i != 0 && i != customerOptions.size()-1) System.out.print(Symbols.diamond 
					+ customerOptions.get(i).substring(0,  customerOptions.get(i).length()-2) 
					+ Symbols.diamond + "\n");
			else System.out.print(customerOptions.get(i));
		}
		int selection = 0;
		while (!inBounds(selection)) {
			selection = getSelection();
			if (inBounds(selection))
				return selection;
			else {
				System.out.println("* " + selection + " Is Not A Valid Option.");
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
			//System.out.print(lineSeparator);
			System.out.println();
			System.out.print(Symbols.blackDiamond + "  Select Option Number: "); 
			
			selection = cin.nextInt();
			//System.out.print("- Selection = " + selection + lineSeparator.substring(15));
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
		customerOptions.add(String.format("  Select Option - %-2d- %-43s ", i, optionName));
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
	/*
	public void passLoginInfo(LoginController loginInfo){
		this.loginStatus = loginInfo;
	}
	*/
}
