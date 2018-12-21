package controller;

import java.io.IOException;
import java.util.Scanner;

import com.BankApp.BankApp;

import model.Containers;
import model.CustomerContainer;
import views.Login;
import views.MenuOptions;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;

public class MainMenuController {
	MenuOptions options;
	public enum Menus { DEFAULT, CUSTOMER, EMPLOYEE, ADMIN, EXIT };
	LoginController login;
	private int stop;
	protected Containers containers = null;
	
	CustomerController customerController;
	EmployeeController employeeController;
	
	public MainMenuController(){
		if (LoginController.isLoggedIn() == false) login = new LoginController();
	}
	
	public void selectHomeOption(int selection) throws InterruptedException {
		boolean isVerified = false;
		customerController = new CustomerController();
		employeeController = new EmployeeController();
		if (containers != null) login.passContainers(containers);
		if (selection == 1) {
			RegistrationController registrationController = new RegistrationController();
			registrationController.passContainers(containers);
			registrationController.call();
		} else if (selection == 2) {
			if (LoginController.isLoggedIn()) isVerified = true;
			else while (isVerified == false && login.getNumTries() > 0) { 
				isVerified = login.call(2); 
			}
			if (isVerified == true) {
				customerController.passContainers(containers);
				customerController.begin(CustomerMenus.ACCOUNTS);
			} else System.out.println("Error. Customer could not be verified.");
			return;
		} else if (selection == 3) {
			if (LoginController.isLoggedIn()) isVerified = true; 
			else while (isVerified == false && login.getNumTries() > 0) isVerified = login.call(3);
			if (isVerified == true) {
				employeeController.passContainers(containers);
				employeeController.begin(EmployeeMenus.EMPLOYEE);
			} else System.out.println("Error. Employee could not be verified.");
			return;
		} else if (selection == 4) {
			if (LoginController.isLoggedIn()) isVerified = true; 
			else while (isVerified == false && login.getNumTries() > 0) {
				isVerified = login.call(4); 
			}
			return;
		}
		
		else if (selection == stop) System.out.println("Shutting down...");
		else System.out.println(selection + " is not a valid input.\n");
	}
	
	public void begin(Menus menuType) throws InterruptedException {
		options = new MenuOptions(menuType);
		stop = options.getEndCondition();
		int selection = -1;
		while (selection != stop) {
			if (selection == stop) selectHomeOption(stop);
			else selection = -1;
			try {
				selection = options.displayHomeMenu();
				if (options.inBounds(selection)) selectHomeOption(selection);
				else continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
}
