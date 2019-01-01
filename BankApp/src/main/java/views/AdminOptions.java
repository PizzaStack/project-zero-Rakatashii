package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controller.AdminController.AdminMenus;
import controller.LoginController;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import employees.Admin;
import employees.Employee;
import utility.Symbols;


public class AdminOptions{
	private int endCondition;
	ArrayList<String> adminOptions = new ArrayList<String>();
	
	private int maxLineLength;
	private String lineSeparator, menuEndLine;
	
	MainMenuController mainMenuController;
	LoginController loginStatus;
	
	Admin admin;
	
	public AdminOptions() { }
	public AdminOptions(AdminMenus adminMenuOption) throws InterruptedException {
		if (adminOptions != null && adminOptions.size() > 0) adminOptions.clear();
		if (adminMenuOption == AdminMenus.SELECTION) {
			setAdminViewArrayValues();
		} else if (adminMenuOption == AdminMenus.CUSTOMERS){
			setCustomersViewArrayValues();
		} else if (adminMenuOption == AdminMenus.UNVERIFIED){
			setUnverifiedViewArrayValues();
		} else {
			mainMenuController.begin(Menus.DEFAULT);
		}
	}
	private void setUnverifiedViewArrayValues() {
		if (adminOptions.size() > 0) adminOptions.clear();
		addFormattedOption(1, "View All Applicants");
		addFormattedOption(2, "Approve/Deny Applicant");
		//addFormattedOption(3, "Commit Changes");
		addFormattedOption(3, "Go Back To Administrati");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "New Applications";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.adminOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = adminOptions.size();
		if (endCondition == adminOptions.size()) endCondition--;
		this.adminOptions.add(adminOptions.size(), menuEndLine);
	} 	
	private void setCustomersViewArrayValues() {
		if (adminOptions.size() > 0) adminOptions.clear();
		addFormattedOption(1, "View All Customers");
		addFormattedOption(2, "Edit Customer Account");
		addFormattedOption(3, "Enable or Disable Customer Account");
		addFormattedOption(4, "View Joint Account Applications");
		addFormattedOption(5, "Register New Customer");
		//addFormattedOption(3, "Commit Changes");
		addFormattedOption(6, "Go Back To Administrator Options");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "-" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "-\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Customer Database";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.adminOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = adminOptions.size();
		if (endCondition == adminOptions.size()) endCondition--;
		this.adminOptions.add(adminOptions.size(), menuEndLine);
	} 
	private void setAdminViewArrayValues() {
		if (adminOptions.size() > 0) adminOptions.clear();
		addFormattedOption(1, "Customer Database");
		addFormattedOption(2, "Applicant Database");
		addFormattedOption(3, "Logout And Return To Main Menu");
		
		this.maxLineLength = maxOptionLength()-1;
		this.lineSeparator = "|" + String.join("", Collections.nCopies(maxLineLength-2, " ")) + "|\n";
		menuEndLine = String.join("", Collections.nCopies(maxLineLength, "-")) + "\n";
		
		String title = "Admin Options";
		int halfLineLength = (maxLineLength / 2) - (title.length() / 2) - 2;
		
		String menuSideLine = String.join("", Collections.nCopies(halfLineLength, "-"));
		String menuLeftHalf = menuSideLine + " ";
		String menuRightHalf = " " + menuSideLine + "-";
		
		this.adminOptions.add(0, String.join(" ", menuLeftHalf + title + menuRightHalf) + "-\n");
		this.endCondition = adminOptions.size();
		if (endCondition == adminOptions.size()) endCondition--;
		this.adminOptions.add(adminOptions.size(), menuEndLine);
	} 
	public int displayAccountsMenu() throws IOException {
		
		for (int i = 0; i < adminOptions.size(); i++){
			if (i != 0) System.out.print(lineSeparator);
			if (i == 1 && LoginController.isLoggedIn()) {
				String loginStatement = Symbols.whiteKing + " [Logged In As: " + LoginController.getLoggedInUsername() + "]";
				System.out.println(loginStatement
						+ String.join("", Collections.nCopies(maxLineLength-(1+loginStatement.length()), " "))
						+ Symbols.whiteKing);
				System.out.print(lineSeparator);
			}
			//System.out.print(adminOptions.get(i));
			if (i != 0 && i != adminOptions.size()-1) System.out.print(Symbols.diamond 
					+ adminOptions.get(i).substring(0,  adminOptions.get(i).length()-2) 
					+ Symbols.diamond + "\n");
			else System.out.print(adminOptions.get(i));
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
		for (String option : adminOptions) {
			max = (option.length() >= max) ? option.length() : max;
		}
		return max;
	}
	public void addFormattedOption(int i, String optionName) {
		adminOptions.add(String.format("  Select Option - %-2d- %-43s ", i, optionName));
	}
	public int getEndCondition() {
		return endCondition;
	}
	public int getSize() {
		return adminOptions.size();
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
