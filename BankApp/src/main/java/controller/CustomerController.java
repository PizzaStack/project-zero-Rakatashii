package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

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
	private CustomerDAO customerDAO;
	
	static final Logger log = Logger.getLogger(CustomerController.class);
	
	public CustomerController(MainMenuController mainMenu, CustomerContainer customerContainer){
		if (LoginController.isLoggedIn()) this.customer = LoginController.getLoggedInCustomer();
		this.mainMenu = mainMenu;
		this.customerContainer = customerContainer;
	}
	public void selectCheckingOption(int selection) throws InterruptedException {
		customerDAO = new CustomerDAO();
		Customer jointCustomer = null;
		int jointCustomerID = customer.getJointCustomerID();
		if (customer.hasJointAccounts() && jointCustomerID > 0) {
			if (customerDAO.checkIfCustomerExists(jointCustomerID, false)) jointCustomer = customerDAO.findCustomerByID(jointCustomerID, false);
			if (jointCustomer != null) customer.resetJointCustomer(jointCustomer);
		} else {
		}
		customerDAO = null;
		
		if (selection == 1) {
			if (checking != null) System.out.println("Checking Balance: $" + checking.getBalance());
			else System.out.println("Sorry! No account found for " + customer.getUsername());
			
			Scanner cin = new Scanner(System.in);
			String cont = "";
			while (!cont.toLowerCase().contains("c") && cont.length() < 2) {
				System.out.print("Enter \"c\" To Continue. ");
				cont = cin.nextLine();
			}
			System.out.println();
			return;
		} else if (selection == 2) {
			if (checking == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			else System.out.print("Enter The Amount You Would Like To Deposit: $");
			
			double depositAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			depositAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Deposit, $" + depositAmount + " Into Your Checking Account");
				System.out.println("Your Account Has Been Temporarily Disabled. Please Contact An Administrator.");
			}
			else {
				checking.deposit(depositAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to deposit " + depositAmount + " into checking account where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to deposit " + depositAmount + " into joint checking account where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer, false);
				}
				System.out.println("Success! $" + depositAmount + " Has Been Added To Your Checking Account.");
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == 3) {
			if (checking == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			System.out.print("Enter The Amount You Would Like To Withdraw: $");

			double withdrawelAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			withdrawelAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Withdraw, $" + withdrawelAmount + " From Your Checking Account");
				System.out.println("Your Account Has Been Temporarily Disabled. Please Contact An Administrator.");
			}
			else {
				checking.withdraw(withdrawelAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to withdraw " + withdrawelAmount + " from checking account where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to withdraw " + withdrawelAmount + " from joint checking account where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer, false);
				}
				System.out.println("Success! $" + withdrawelAmount + " Has Been Deducted From Your Checking Account.");
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
			
		} else if (selection == 4) {
			if (checking == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			System.out.print("Enter The Amount You Would Like To Transfer: $");
		
			double transferAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			transferAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Transfer $" + transferAmount + " From Your Checking Account");
				System.out.println("Your Account Has Been Temporarily Disabled. Please Contact An Administrator.");
			}
			else {
				checking.transferToSavings(transferAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to transfer " + transferAmount + " from checking account to savings account for customer where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to transfer " + transferAmount + " from checking account to savings account for joint checking account where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer, false);
				}
				System.out.println("Success! $" + transferAmount + " Has Been Deposited To Your Savings Account.");
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == stop) {
			begin(CustomerMenus.SELECTION);
			return;
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(CustomerMenus.SELECTION);
			return;
		}
	}
	public void selectSavingsOption(int selection) throws InterruptedException {
		customerDAO = new CustomerDAO();
		Customer jointCustomer = null;
		int jointCustomerID = customer.getJointCustomerID();
		if (customer.hasJointAccounts() && jointCustomerID > 0) {
			if (customerDAO.checkIfCustomerExists(jointCustomerID, false)) jointCustomer = customerDAO.findCustomerByID(jointCustomerID, false);
			if (jointCustomer != null) customer.resetJointCustomer(jointCustomer);
		} else {
		}
		customerDAO = null;
		
		if (selection == 1) {
			if (checking != null) System.out.println(String.format("Savings Balance: $%.2f", savings.getBalance()));
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
			if (checking == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			else System.out.print("Enter The Amount You Would Like To Deposit: $");
			
			double depositAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			depositAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println(String.format("Unable To Deposit, $%.2f Into Your Savings Account", depositAmount));
				System.out.println("Your Account Has Been Temporarily Disabled. Please Contact An Administrator.");
			}
			else {
				savings.deposit(depositAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to deposit " + depositAmount + " into savings account where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to deposit " + depositAmount + " into joint savings account where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer,  false);
				}
				System.out.println(String.format("Success! $%.2f Has Been Added To Your Savings Account.", depositAmount));
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == 3) {
			if (checking == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			else System.out.print("Enter The Amount You Would Like To Withdraw: $");
			
			double withdrawelAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			withdrawelAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Withdraw, $" + withdrawelAmount + " From Your Savings Account");
				System.out.println("Your Account Has Been Temporarily Disabled. Please Contact An Administrator.");
			}
			else {
				savings.withdraw(withdrawelAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to withdraw " + withdrawelAmount + " from savings account where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to withdraw " + withdrawelAmount + " from joint savings account where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer, false);
				}
				System.out.println("Success! $" + withdrawelAmount + " Has Been Deducted From Your Savings Account.");
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
			
		} else if (selection == 4) {
			if (savings == null) {
				System.out.println("Sorry! No Account Found For " + customer.getUsername());
				return;
			}
			System.out.print("Enter The Amount You Would Like To Transfer: $");
		
			double transferAmount = 0.0;
			Scanner cin = new Scanner(System.in);
			transferAmount = Double.parseDouble(cin.nextLine());
			
			System.out.println();
			if (customer.isFlagged() == true) {
				System.out.println("Unable To Transfer $" + transferAmount + " From Your Savings Account");
				System.out.println("Your Account Is Currently Disabled. Please Contact An Administrator.");
			}
			else {
				savings.transferToChecking(transferAmount);
				customerDAO = new CustomerDAO();
				log.debug("Attempting to transfer " + transferAmount + " from savings account to checking account where customer_id = " + customer.getID());
				customerDAO.updateCustomerAndAccounts(customer, false);
				if (jointCustomer != null) {
					log.debug("Attempting to transfer " + transferAmount + " from savings account to checking account for joint customer where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(jointCustomer, false);
				}
				System.out.println("Success! $" + transferAmount + " Has Been Deposited To Your Checking Account.");
			}
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == stop) {
			begin(CustomerMenus.SELECTION);
			return;
		}
		else {
			System.out.println(selection + " Is Not A Valid Input.\n");
			begin(CustomerMenus.SELECTION);
			return;
		}
	}
	public void selectCustomerOption(int selection) throws InterruptedException {
		if (selection == 1) begin(CustomerMenus.CHECKING);
		else if (selection == 2) begin(CustomerMenus.SAVINGS);
		else if (selection == 3) {
			Scanner cin = new Scanner(System.in);
			System.out.println("If You Know The Unique ID Of The Customer You Wish To Add.. You May Type It Now To Request A Joint Account. ");
			System.out.println();
			System.out.print("(Type \"0\" To Cancel): ");
			int customerID = Integer.parseInt(cin.nextLine());
			if (customerID == 0) {
				System.out.println();
				System.out.println("Returning The Customer Menu...");
				System.out.println();
				begin(CustomerMenus.SELECTION);
				return;
			} else if (customerID == customer.getCustomerID()) {
				System.out.println();
				System.out.println("You Cannot Use Your Own ID In The Joint Customer Application.");
				System.out.println();
				begin(CustomerMenus.SELECTION);
				return;
			}
			else if (customer.getCustomerID() == 0 && customer.getFirstname() == "customer"){
				System.out.println();
				System.out.println("Cannot Link Accounts For This Customer. Returning The Customer Menu...");
			} else {
				customerDAO = new CustomerDAO();
				boolean customerFound = customerDAO.checkIfCustomerExists(customerID, false);
				if (customer.hasJointAccounts()) {
					System.out.println();
					System.out.println("Our Record Show That Another Customer Is Already Linked To Your Account.");
					System.out.println("Customers Are Only Able To Link To One Other Account.");
					System.out.println("Please Contact An Administrator If You Wish To Unlink Accounts.");
				} else if (customerFound) {
					customer.setJointCustomerID(customerID);
					log.debug("Attempting to create new joint customer application for customer where customer_id = " + customer.getID());
					customerDAO.updateCustomerAndAccounts(customer,  false);
					System.out.println();
					System.out.println("Success! Your Application Has Been Submitted For Administrative \nApproval. " + 
							"It May Take A Few Days To Process Your Request.");
				} else {
					System.out.println();
					System.out.println("Unable To Find Customer Account With The ID You Specified. Please Contact An Administrator.");
				}
			}
			System.out.println();
			begin(CustomerMenus.SELECTION);
			return;
		}
		else if (selection == stop) {
			System.out.println("Bye " + customer.getFirstname() + "!\n");
			LoginController.logout();
			begin(CustomerMenus.LOGOUT);
			return;
		} else {
			System.out.println(selection + " Is Not A Valid Option.\n");
			begin(CustomerMenus.SELECTION);
			return;
		}
	}
	public void begin(CustomerMenus customerMenu) throws InterruptedException {
		if (customer == null) {
			customer = LoginController.getLoggedInCustomer();
			if (customer != null) savings = customer.getSavingsAccount();
			if (customer != null) checking = customer.getCheckingAccount();
		}
		
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
					if (selection == stop) selectCheckingOption(stop);
					else selection = -1;
					try {
						while (customerOptions.inBounds(selection) == false) {
							selection = customerOptions.displayAccountsMenu();
							if (selection == stop) {
								selectCheckingOption(stop);
								return;
							}
							else if (customerOptions.inBounds(selection) == true) {
								selectCheckingOption(selection);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (customerMenu == CustomerMenus.SAVINGS) {
			if (customer == null || customer.hasSavingsAccount() == false) {
				System.out.println("Error. No Account Found Customer."); 
				return;
			}
			else {
				customerOptions = new CustomerOptions(CustomerMenus.SAVINGS);
				stop = customerOptions.getEndCondition();
				int selection = -1;
				while (selection != stop) {
					if (selection == stop) selectSavingsOption(stop);
					else selection = -1;
					try {
						while (customerOptions.inBounds(selection) == false) {
							selection = customerOptions.displayAccountsMenu();
							if (selection == stop) {
								selectSavingsOption(stop);
								return;
							}
							else if (customerOptions.inBounds(selection) == true) {
								selectSavingsOption(selection);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		else if (customerMenu == CustomerMenus.SELECTION) {
			customerOptions = new CustomerOptions(CustomerMenus.SELECTION);
			stop = customerOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) {
					selectCustomerOption(stop);
					return;
				}
				else selection = -1;
				try {
					while (customerOptions.inBounds(selection) == false) {
						selection = customerOptions.displayAccountsMenu();
						if (selection == stop) {
							selectCustomerOption(stop); 
							return;
						}
						else if (customerOptions.inBounds(selection) == true) {
							selectCustomerOption(selection);
							return;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void passCustomerContainer(CustomerContainer customerContainer) {
		this.customerContainer = customerContainer;
	}
}
