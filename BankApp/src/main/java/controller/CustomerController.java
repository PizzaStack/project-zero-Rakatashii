package controller;

import java.io.IOException;
import java.util.ArrayList;

import DAO.CustomerDAO;
import model.Containers;
import model.CustomerContainer;
import views.CustomerOptions;
import controller.MainMenuController.Menus;
import customers.Customer;

public class CustomerController{
	CustomerOptions customerOptions;
	public enum CustomerMenus { SELECTION, CHECKINGS, SAVINGS, LOGOUT };
	private int stop;
	
	Customer customer;
	MainMenuController mainMenu = null;
	private CustomerContainer customerContainer;
	
	public CustomerController(MainMenuController mainMenu, CustomerContainer customerContainer){
		if (LoginController.isLoggedIn()) this.customer = LoginController.getLoggedInCustomer();
		this.mainMenu = mainMenu;
		this.customerContainer = customerContainer;
	}
	public void selectCustomerOption(int selection) throws InterruptedException {
		//boolean isVerified = false;
		//login = new LoginController();
		if (selection == 1) {
			System.out.println("View Checking Account\n");
		} else if (selection == 2) {
			System.out.println("View Savings Account\n");
		} else if (selection == stop) {
			LoginController.logout();
			begin(CustomerMenus.LOGOUT);
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(CustomerMenus.SELECTION);
		}
	}
	public void begin(CustomerMenus customerMenu) throws InterruptedException {
		if (customerMenu == CustomerMenus.LOGOUT) {
			LoginController.logout();
			mainMenu.begin(Menus.DEFAULT); 
			return;
		} else if (customerMenu == CustomerMenus.CHECKINGS) {
			if (customer.hasCheckingAccount() == false); 
		}
		else if (customerMenu == CustomerMenus.SELECTION) {
			customerOptions = new CustomerOptions(customerMenu);
			//customerOptions.passLoginInfo(login);
			stop = customerOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectCustomerOption(stop);
				else selection = -1;
				try {
					selection = customerOptions.displayAccountsMenu();
					if (this.customerOptions.inBounds(selection)) selectCustomerOption(selection);
					else continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//return customerOption;
	}
	public void passCustomerContainer(CustomerContainer customerContainer) {
		this.customerContainer = customerContainer;
	}
	/*
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
	*/
}
