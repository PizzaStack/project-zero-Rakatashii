package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controller.LoginController;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import controller.EmployeeController.EmployeeMenus;


public class EmployeeOptions{
	private int endCondition;
	ArrayList<String> employeeOptions = new ArrayList<String>();
	
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	MainMenuController mainMenuController;
	//LoginController loginStatus;
	
	public EmployeeOptions() { }
	public EmployeeOptions(EmployeeMenus employeeMenuOption) throws InterruptedException {
		mainMenuController = new MainMenuController();
		if (employeeOptions != null && employeeOptions.size() > 0) employeeOptions.clear();
		if (employeeMenuOption == EmployeeMenus.EMPLOYEE) {
			setEmployeeViewArrayValues();
		} else {
			mainMenuController.begin(Menus.DEFAULT);
		}
	}

	private void setEmployeeViewArrayValues() {
		addFormattedOption(1, "Employee Option 1");
		addFormattedOption(2, "Employee Option 2");
		addFormattedOption(3, "to Logout");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Employee Database Access";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.employeeOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = employeeOptions.size();
		//addFormattedOption(employeeOptions.size(), "Go back to Main Menu");
		if (endCondition == employeeOptions.size()) endCondition--;
		this.employeeOptions.add(employeeOptions.size(), menuEndLine);
	} 
	public int displayAccountsMenu() throws IOException {
		
		for (int i = 0; i < employeeOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				System.out.println("* (Logged in as  " + LoginController.getLoggedInUsername() + ")");
				System.out.print(lineSeparator);
			}
			System.out.print(employeeOptions.get(i));
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
		for (String option : employeeOptions) {
			max = (option.length() >= max) ? option.length() : max;
		}
		return max;
	}
	public void addFormattedOption(int i, String optionName) {
		employeeOptions.add(String.format("* Select Option - %-2d- %-41s *\n", i, optionName));
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
