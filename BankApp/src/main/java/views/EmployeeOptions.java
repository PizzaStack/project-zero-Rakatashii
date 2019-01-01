package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controller.LoginController;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import employees.Employee;
import utility.Symbols;
import controller.EmployeeController.EmployeeMenus;


public class EmployeeOptions{
	private int endCondition;
	ArrayList<String> employeeOptions = new ArrayList<String>();
	
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	MainMenuController mainMenuController;
	LoginController loginStatus;
	
	Employee employee;
	
	public EmployeeOptions() { }
	public EmployeeOptions(EmployeeMenus employeeMenuOption) throws InterruptedException {
		if (employeeOptions != null && employeeOptions.size() > 0) employeeOptions.clear();
		if (employeeMenuOption == EmployeeMenus.SELECTION) {
			setEmployeeViewArrayValues();
		} else if (employeeMenuOption == EmployeeMenus.CUSTOMERS){
			setCustomersViewArrayValues();
		} else if (employeeMenuOption == EmployeeMenus.UNVERIFIED){
			setUnverifiedViewArrayValues();
		} else {
			mainMenuController.begin(Menus.DEFAULT);
		}
	}
	private void setUnverifiedViewArrayValues() {
		if (employeeOptions.size() > 0) employeeOptions.clear();
		addFormattedOption(1, "View All Applicants");
		addFormattedOption(2, "View Applicant By ID");
		//addFormattedOption(3, "Commit Changes");
		addFormattedOption(3, "Go Back To Employee Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "New Customer Applications";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.employeeOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = employeeOptions.size();
		if (endCondition == employeeOptions.size()) endCondition--;
		this.employeeOptions.add(employeeOptions.size(), menuEndLine);
	} 	
	private void setCustomersViewArrayValues() {
		if (employeeOptions.size() > 0) employeeOptions.clear();
		addFormattedOption(1, "View All Customers");
		addFormattedOption(2, "View Customer By ID");
		//addFormattedOption(3, "Commit Changes");
		addFormattedOption(3, "Go Back To Employee Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Customer Database";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.employeeOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = employeeOptions.size();
		if (endCondition == employeeOptions.size()) endCondition--;
		this.employeeOptions.add(employeeOptions.size(), menuEndLine);
	} 
	private void setEmployeeViewArrayValues() {
		if (employeeOptions.size() > 0) employeeOptions.clear();
		addFormattedOption(1, "Customer Database");
		addFormattedOption(2, "Applicant Database");
		addFormattedOption(3, "Logout And Return To Main Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Employee Menu";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.employeeOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = employeeOptions.size();
		if (endCondition == employeeOptions.size()) endCondition--;
		this.employeeOptions.add(employeeOptions.size(), menuEndLine);
	} 
	public int displayAccountsMenu() throws IOException {
		
		for (int i = 0; i < employeeOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				String loginStatement = Symbols.whiteStar + "  [Logged In As: " + LoginController.getLoggedInUsername() + "]";
				System.out.println(loginStatement
						+ String.join("", Collections.nCopies(maxLineLength-(loginStatement.length()), " "))
						+ Symbols.whiteStar);
				System.out.print(lineSeparator);	
			}
			//System.out.print(adminOptions.get(i));
			if (i != 0 && i != employeeOptions.size()-1) System.out.print(Symbols.diamond 
					+ employeeOptions.get(i).substring(0,  employeeOptions.get(i).length()-2) 
					+ Symbols.diamond + "\n");
			else System.out.print(employeeOptions.get(i));
		}
		int selection = 0;
		while (!inBounds(selection)) {
			selection = getSelection();
			if (inBounds(selection))
				return selection;
			else {
				System.out.println("* " + selection + " Is Not a Valid Option.");
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
		for (String option : employeeOptions) {
			max = (option.length() >= max) ? option.length() : max;
		}
		return max;
	}
	public void addFormattedOption(int i, String optionName) {
		employeeOptions.add(String.format("  Select Option - %-2d- %-43s ", i, optionName));
	}
	public int getEndCondition() {
		return endCondition;
	}
	public int getSize() {
		return employeeOptions.size();
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
