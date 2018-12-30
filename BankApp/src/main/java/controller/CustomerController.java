package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.CustomerDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import model.Containers;
import model.CustomerContainer;
import views.CustomerOptions;
import controller.MainMenuController.Menus;
import customers.Customer;

public class CustomerController{
	CustomerOptions customerOptions;
	public enum CustomerMenus { SELECTION, CHECKING, SAVINGS, LOGOUT };
	public enum CheckingMenus { BALANCE, DESPOSIT, WITHDRAW, GOBACK };
	private int stop;
	
	Customer customer;
	SavingsAccount savings;
	CheckingAccount checking;
	MainMenuController mainMenu = null;
	
	private CustomerContainer customerContainer;
	
	public CustomerController(MainMenuController mainMenu, CustomerContainer customerContainer){
		if (LoginController.isLoggedIn()) this.customer = LoginController.getLoggedInCustomer();
		this.mainMenu = mainMenu;
		this.customerContainer = customerContainer;
	}
	public void selectCheckingOption(int selection) throws InterruptedException {
		//boolean isVerified = false;
		//login = new LoginController();
		if (selection == 1) {
			if (checking != null) System.out.println("Checking Balance: $" + checking.getBalance());
			else System.out.println("Sorry! No account found for " + customer.getUsername());
			
			Scanner cin = new Scanner(System.in);
			String cont = "";
			while (!cont.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				cont = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == 2) {
			if (checking == null) System.out.println("Sorry! No Account Found For " + customer.getUsername());
			else {
				if (customer.isFlagged() == true) {
					System.out.println("Your Account Is Currently Disabled. Please Contact An Administrator.");
					return;
				}
				else System.out.print("Enter The Amount You Would Like To Deposit: $");
			}
		
			double depositAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			depositAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Deposit, $" + depositAmount + " Into Checking Account");
				System.out.println("Your Account Has Been Disabled. Please Contact An Administrator.");
			}
			else {
				checking.deposit(depositAmount);
				// TODO update records
				System.out.println("Success! $" + depositAmount + " Has Been Added To Your Checking Account.");
			}
			String cont = "";
			while (!cont.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				cont = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == stop) {
			LoginController.logout();
			begin(CustomerMenus.LOGOUT);
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(CustomerMenus.SELECTION);
		}
	}
	public void selectCustomerOption(int selection) throws InterruptedException {
		//boolean isVerified = false;
		//login = new LoginController();
		if (selection == 1) {
			System.out.println("View Checking Account\n");
			begin(CustomerMenus.CHECKING);
			
			
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
		customer = LoginController.getLoggedInCustomer();
		if (customer != null) savings = customer.getSavingsAccount();
		if (customer != null) checking = customer.getCheckingAccount();
		
		if (customerMenu == CustomerMenus.LOGOUT) {
			LoginController.logout();
			mainMenu.begin(Menus.DEFAULT); 
			return;
		} else if (customerMenu == CustomerMenus.CHECKING) {
			if (customer == null || customer.hasCheckingAccount() == false) {
				System.out.println("Error. No Account Found Customer."); 
				return;
			}
			else {
				customerOptions = new CustomerOptions(CustomerMenus.CHECKING);
				stop = customerOptions.getEndCondition();
				int selection = -1;
				while (selection != stop) {
					if (selection == stop) selectCustomerOption(stop);
					else selection = -1;
					try {
						while (customerOptions.inBounds(selection) == false) {
							selection = customerOptions.displayAccountsMenu();
							if (selection == stop) return;
							else if (customerOptions.inBounds(selection) == true) {
								selectCheckingOption(selection);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					/*
					try {
						selection = customerOptions.displayAccountsMenu();
						if (this.customerOptions.inBounds(selection)) selectCustomerOption(selection);
						else continue;
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/
				}
			}
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
					while (customerOptions.inBounds(selection) == false) {
						selection = customerOptions.displayAccountsMenu();
						if (selection == stop) return;
						else if (customerOptions.inBounds(selection) == true) {
							selectCustomerOption(selection);
							return;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				/*
				try {
					selection = customerOptions.displayAccountsMenu();
					if (this.customerOptions.inBounds(selection)) selectCustomerOption(selection);
					else continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
				*/
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
