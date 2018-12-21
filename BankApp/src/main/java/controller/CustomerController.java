package controller;

import java.io.IOException;

import model.Containers;
import views.CustomerOptions;
import controller.MainMenuController.Menus;

public class CustomerController{
	CustomerOptions customerOptions;
	public enum CustomerMenus { ACCOUNTS, CHECKINGS, SAVINGS, LOGOUT };
	private int stop;
	
	//private LoginController login;
	private Containers containers;
	
	public CustomerController(){
		//containers = super.containers;
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
			return;
		}
		else System.out.println(selection + " is not a valid input.\n");
	}
	public void begin(CustomerMenus customerMenu) throws InterruptedException {
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
		//return customerOption;
	}
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	/*
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
	*/
}
