package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import DAO.AdminDAO;
import DAO.CustomerDAO;
import DAO.UnverifiedCustomerDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import model.AccountContainer;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.AdminOptions;
import views.CustomerOptions;
import views.EmployeeOptions;
import controller.CustomerController.CustomerMenus;
import controller.MainMenuController.Menus;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;

public class AdminController{
	AdminOptions adminOptions;
	public enum AdminMenus { SELECTION, LOGOUT, CUSTOMERS, UNVERIFIED };
	private int stop;
	
	Admin admin;
	MainMenuController mainMenu = null;
	
	private Containers containers;
	
	CustomerDAO customerDAO;
	UnverifiedCustomerDAO unverifiedDAO;
	
	static final Logger log = Logger.getLogger(AdminController.class);
	
	public AdminController() { }
	public AdminController(MainMenuController mainMenu, Containers containers) { 
		if (LoginController.isLoggedIn()) admin = (Admin) LoginController.getLoggedInAdmin();
		this.mainMenu = mainMenu;
		this.containers = containers;
	}
		
	public void selectCustomerOption(int selection) throws InterruptedException {
		if (selection == 1) {
			//System.out.println();
			customerDAO = new CustomerDAO();
			containers.getCustomerContainer().printColumnNames();

			ArrayList<Customer> DBcustomers = customerDAO.getAllCustomers(false);
			for (Customer c : DBcustomers) {
				c.printRow();
			}
			System.out.println();
			
			Scanner cin = new Scanner(System.in);
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == 2) {
			Scanner cin = new Scanner(System.in);
			
			String header = "------------------------- Edit Options --------------------------";
			String footer = "-----------------------------------------------------------------";

			System.out.println(header);
			System.out.println();
			int customerID;
			System.out.print("Enter Customer_ID (\"0\" To Return To Menu): ");
			customerID = Integer.parseInt(cin.nextLine());
			boolean found = false;
			
			Customer customer = null;
			customerDAO = new CustomerDAO();
			ArrayList<Customer> DBcustomers = customerDAO.getAllCustomers(false);
			ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
			if (customerDAO.checkIfCustomerExists(customerID, false)) {
				found = true;
				customer = customerDAO.findCustomerByID(customerID, false);
			} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			
			int option = -1;
			int numOptions = 8;
			while (found && customer != null && option != 0){
				if (option != -1) {
					System.out.println();
					System.out.println(header);
				}
				System.out.println();
				System.out.println("Customer Information: ");
				containers.getCustomerContainer().printColumnNames();
				customer.printRow();
				System.out.println();
				System.out.println("Type - \"0\" - To Quit.");
				System.out.println();
				System.out.println("Type - \"1\" - To Generate New Savings Account Number.");
				System.out.println();
				System.out.println("Type - \"2\" - To Add Custom Savings Account Number.");
				System.out.println();
				System.out.println("Type - \"3\" - To Alter Savings Account Balance.");
				System.out.println();
				System.out.println("Type - \"4\" - To Generate New Checking Account Number.");
				System.out.println();
				System.out.println("Type - \"5\" - To Add Custom Checking Account Number.");
				System.out.println();
				System.out.println("Type - \"6\" - To Alter Checking Account Balance.");
				System.out.println();
				System.out.println("Type - \"7\" - To Unlink Joint Accounts (This ID Will Be The New Account Holder).");
				System.out.println();
				System.out.println("Type - \"8\" - To Delete Customer.");
				System.out.println();
				System.out.println("Type - \"9\" - To Select A Different Customer.");
				System.out.println();
				System.out.println(footer);
				System.out.println();
				System.out.print("Select Option: ");
				option = Integer.parseInt(cin.nextLine());
				
				SavingsAccount savings = customer.getSavingsAccount();
				CheckingAccount checking = customer.getCheckingAccount();
				
				Customer jointCustomer = null;
				if (customer.hasJointAccounts()) {
					if (customer.getJointCustomer() == null) {
						if (customerDAO.checkIfCustomerExists(customer.getJointCustomerID(), false)) {
							jointCustomer = customerDAO.findCustomerByID(customer.getJointCustomerID(), false);
							customer.resetJointCustomer(jointCustomer);
						}
					} 
				}
				
				boolean finishedOption = false;
				System.out.println();
				if (option >= 0 && option <= numOptions) {
					if (option == 0) {
						System.out.println("Returning To The Menu.");
						System.out.println();
						break;
					} else if (option == 1) {
						String savingsID = AccountContainer.generateNewID(10);
						savings.setID(savingsID);
						log.debug("Attempting to set savings_id to " + savingsID + " where customerID = " + jointCustomer.getCustomerID());
						customerDAO.updateCustomerAndAccounts(customer, false);
						if (jointCustomer != null) {
							jointCustomer.getSavingsAccount().setID(customer.getSavingsAccount().getID());
							log.debug("Attempting to set savings_id to " + savingsID + " where customerID = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						System.out.println("New Savings Account ID: " + checking.getID());
						System.out.println();
						finishedOption = true;
					} else if (option == 2) {
						String savingsID;
						System.out.print("Enter A New Savings Account Number: ");
						savingsID = cin.nextLine();
						savings.setID(savingsID);
						log.debug("Attempting to set savings_id to " + savingsID + " where customerID = " + customer.getCustomerID());
						customerDAO.updateCustomerAndAccounts(customer, false);
						if (jointCustomer != null) {
							jointCustomer.getSavingsAccount().setID(savingsID);
							log.debug("Attempting to set savings_id to " + savingsID + " where customerID = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						System.out.println();
						finishedOption = true;
					}
					else if (option == 3) {
						if (customer.isFlagged()) {
							customer.unflag();
							System.out.println("WARNING. Customer " + customer.getID() + " Has Been Re-Enabled.");
							System.out.println();
						}
						double newBalance = 0.0;
						System.out.print("Enter A New Savings Account Balance $");
						newBalance = Double.parseDouble(cin.nextLine());
						savings.setBalance(newBalance);
						if (customer.isFlagged()) customer.unflag();
						if (savings.getBalance() == 0 && newBalance != 0) System.out.println("Error. Customer Will Not Be Updated.");
						else {
							log.debug("Attempting to set savings_balance to " + newBalance+ " where customerID = " + customer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(customer, false);
							if (jointCustomer != null) {
								log.debug("Attempting to set savings_balance to " + newBalance+ " where customerID = " + jointCustomer.getCustomerID());
								customerDAO.updateCustomerAndAccounts(jointCustomer, false);
							}
						}
						System.out.println();
						finishedOption = true;
					} else if (option == 4) {
						String checkingID = AccountContainer.generateNewID(10);
						checking.setID(checkingID);
						log.debug("Attempting to set checking_id to " + checkingID + " where customerID = " + customer.getCustomerID());
						customerDAO.updateCustomerAndAccounts(customer, false);
						if (jointCustomer != null) {
							jointCustomer.getCheckingAccount().setID(customer.getCheckingAccount().getID());
							log.debug("Attempting to set checking_id to " + checkingID + " where customerID = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						System.out.println("New Checking Account ID: " + checking.getID());
						System.out.println();
						finishedOption = true;
					} else if (option == 5) {
						String checkingID;
						System.out.print("Enter A New Checking Account Number: ");
						checkingID = cin.nextLine();
						checking.setID(checkingID);
						log.debug("Attempting to set checking_id to " + checkingID + " where customerID = " + customer.getCustomerID());
						customerDAO.updateCustomerAndAccounts(customer, false);
						if (jointCustomer != null) {
							jointCustomer.getCheckingAccount().setID(checkingID);
							log.debug("Attempting to set checking_id to " + checkingID + " where customerID = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						System.out.println();
						finishedOption = true;
					}
					else if (option == 6) {
						if (customer.isFlagged()) {
							customer.unflag();
							System.out.println("WARNING. Customer " + customer.getID() + " Has Been Re-Enabled.");
							System.out.println();
						}
						double newBalance = 0.0;
						System.out.print("Enter A New Checking Account Balance $");
						newBalance = Double.parseDouble(cin.nextLine());
						checking.setBalance(newBalance);
						if (customer.isFlagged()) customer.unflag();
						if (savings.getBalance() == 0 && newBalance != 0) System.out.println("Error. Customer Will Not Be Updated.");
						else {
							log.debug("Attempting to set checking_balance to " + newBalance + " where customerID = " + customer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(customer, false);
							if (jointCustomer != null) {
								log.debug("Attempting to set savings_balance to " + newBalance+ " where customerID = " + jointCustomer.getCustomerID());
								customerDAO.updateCustomerAndAccounts(jointCustomer, false);
							}
						}
						System.out.println();
						finishedOption = true;
					} else if (option == 7) {
						if (customer.hasJointAccounts()) {
							customer.unjoin();
							log.debug("Attempting to unjoin accounts for where customer_id = " + customer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(customer, false);
							System.out.println("Customers " + customer.getUsername() + " And " + jointCustomer.getUsername() + " Have Been Unjoined.");
						} else 
							System.out.println("Customer " + customer.getUsername() + " Is Not Joined To Another Account.");
						if (jointCustomer != null) {
							log.debug("Attempting to unjoin accounts for where customer_id = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						System.out.println();
						finishedOption = true;
					} else if (option == 8) {
						if (jointCustomer != null) {
							jointCustomer.unjoin();
							log.debug("Attempting to unjoin accounts for where customer_id = " + jointCustomer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						}
						customerDAO.deleteCustomer(customer, false);
						System.out.println();
						finishedOption = true;
					} else if (option == 9) {
						finishedOption = true;
						selectCustomerOption(2);
					}
					
					if (finishedOption == true) {
						String done = "";
						while (!done.toLowerCase().contains("m") && !done.toLowerCase().contains("e")) {
							System.out.println("Enter \"m\" To Return To The Main Menu, Or \"e\" To Continue Editing");
							done = cin.nextLine();
							if (done.toLowerCase().contains("m")) {
								return;
							} else if (done.toLowerCase().contains("e")) {
								continue;
							}
						}
					}
				}
			}
			return;
		} else if (selection == 3) {
			Scanner cin = new Scanner(System.in);
			
			int customerID;
			System.out.print("Enter Customer_ID: ");
			customerID = Integer.parseInt(cin.nextLine());
			boolean found = false;
			
			Customer customer = null;
			customerDAO = new CustomerDAO();
			ArrayList<Customer> DBcustomers = customerDAO.getAllCustomers(false);
			ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
			if (customerID < customers.size()) {
				customer = customers.get(customerID);
				if (customer != null) {
					found = true;
				} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			
			if (found && customer != null){
				String done = "";
				while (!done.toLowerCase().contains("c")) {
					containers.getCustomerContainer().printColumnNames();
					customer.printRow();
					System.out.println();
					
					boolean isFlagged = customer.isFlagged();
					if (isFlagged) {
						System.out.print("Enter \"c\" To Continue or \"e\" To Re-Enable Customer Account. ");
						done = cin.next();
						if (done.toLowerCase().contains("e")) {
							Customer jointCustomer = null;
							int jointCustomerID = customer.getJointCustomerID();
							if (customer.hasJointAccounts() && jointCustomerID > 0) {
								if (customerDAO.checkIfCustomerExists(jointCustomerID, false)) jointCustomer = customerDAO.findCustomerByID(jointCustomerID, false);
								if (jointCustomer != null) customer.resetJointCustomer(jointCustomer);
							} 
							
							customer.unflag();
							log.debug("Attempting to unflag customer where customer_id = " + customer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(customer, false);
							
							if (jointCustomer != null) {
								jointCustomer.unflag();
								log.debug("Attempting to flag joint customer where customer_id = " + jointCustomer.getCustomerID());
								customerDAO.updateCustomerAndAccounts(jointCustomer, false);
							}
						} else if (done.toLowerCase().contains("c"))
							break;
					} else {
						System.out.print("Enter \"c\" To Continue or \"d\" To Disable Customer Account. ");
						done = cin.next();
						if (done.toLowerCase().contains("d")) {
							Customer jointCustomer = null;
							int jointCustomerID = customer.getJointCustomerID();
							
							if (customer.hasJointAccounts() && jointCustomerID > 0) {
								if (customerDAO.checkIfCustomerExists(jointCustomerID, false)) jointCustomer = customerDAO.findCustomerByID(jointCustomerID, false);
								if (jointCustomer != null) customer.resetJointCustomer(jointCustomer);
							} 
							customer.flag();
							log.debug("Attempting to flag customer where customer_id = " + customer.getCustomerID());
							customerDAO.updateCustomerAndAccounts(customer, false);
					
							if (jointCustomer != null) {
								jointCustomer.flag();
								log.debug("Attempting to flag joint customer where customer_id = " + jointCustomer.getCustomerID());
								customerDAO.updateCustomerAndAccounts(jointCustomer, false);
							}
						}
						else if (done.toLowerCase().contains("c")) break;
					}
				}
			} else {
				String done = "";
				while (!done.toLowerCase().contains("c")) {
					System.out.print("Enter \"c\" To Continue. ");
					done = cin.next();
				}
			}
			System.out.println();
			return;
		} else if (selection == 4) {
			
			customerDAO = new CustomerDAO();
			ArrayList<Customer> jointApplicants = new ArrayList<Customer>();
			ArrayList<Customer> DBcustomers = customerDAO.getAllCustomers(false);
			for (Customer c : DBcustomers) {
				if (c.getJointCustomerID() > 0 && c.hasJointAccounts() == false) jointApplicants.add(c);
			}
			
			if (jointApplicants.size() > 0) {
				containers.getCustomerContainer().printColumnNames();
				for (Customer c : jointApplicants) {
					c.printRow();
				}
			} else {
				System.out.println("No Active Joint Applications Were Found. ");
				System.out.println();
				return;
			}
			
			Scanner cin = new Scanner(System.in);
			int applicantID = -1;
			String option = "";
			
			boolean foundPrimary = false, foundJointCustomer = false;
			Customer primary = null;
			Customer jointCustomer = null;
			int jointCustomerID = -1;
			while (!foundPrimary && applicantID != 0) {
				System.out.println();
				System.out.print("Enter Customer_ID (\"0\" To Quit): ");
				applicantID = Integer.parseInt(cin.nextLine());
				foundPrimary = customerDAO.checkIfCustomerExists(applicantID, false);
				if (foundPrimary) {
					primary = jointCustomer = customerDAO.findCustomerByID(applicantID, false);
					jointCustomerID = primary.getJointCustomerID();
					foundJointCustomer = customerDAO.checkIfCustomerExists(jointCustomerID, false);
					if (foundJointCustomer) {
						System.out.println();
						System.out.println("Joint Customer Was Located Within The Database: ");
						jointCustomer = customerDAO.findCustomerByID(primary.getJointCustomerID(), false);
						containers.getCustomerContainer().printColumnNames();
						jointCustomer.printRow();
					} else {
						System.out.println("Unable To Locate Customer With The Joint ID Specified.");
						option = "d";
						return;
					}
				}
			}
			while (true) {
				System.out.println();
				System.out.println("Enter \"a\" To Authorize Joint Customer Application.\n"
						+ "Enter \"d\" To Discard Joint Customer Application.\n"
						+ "Enter \"c\" To Continue.");
				System.out.println();
				System.out.print("Choose Option: ");
				if (!option.toLowerCase().contains("a") && !option.toLowerCase().contains("d")) option = cin.next();
				
				System.out.println();

				if (jointCustomer != null) {
					if (option.toLowerCase().contains("a")) {
						System.out.println("Success! " + primary.getUsername() + " And " + jointCustomer.getUsername() + " Now Share The Same Accounts.");
						
						primary.setJointCustomer(jointCustomer);
						jointCustomer.setJointCustomer(primary);
						log.debug("Attempting to join customers where customer_id = " + primary.getCustomerID() + " and joint_customer_id = " + primary.getJointCustomerID());
						customerDAO.updateCustomerAndAccounts(primary, false);
						customerDAO.updateCustomerAndAccounts(jointCustomer, false);
						
						System.out.println();
						break;
					} else if (option.toLowerCase().contains("d")) {
						System.out.println("Application Has Been Erased. ");
						
						primary.setJointCustomerID(-1);
						customerDAO.updateCustomerAndAccounts(primary, false);
						
						System.out.println();
						break;
					} else if (option.toLowerCase().contains("c")) {
						System.out.println();
						break;
					} 
				}
			}
			
		} else if (selection == 5) {
			RegistrationController registrationController = new RegistrationController();
			registrationController.passContainers(containers);
			registrationController.call();
	
			UnverifiedCustomer applicant = containers.getUnverifiedContainer().getArrayList().get(containers.getUnverifiedContainer().getSize()-1);
			containers.getUnverifiedContainer().printAll();
			
			Scanner cin = new Scanner(System.in);
			String option = "";
			
			while (!option.toLowerCase().contains("a") && !option.toLowerCase().contains("d")) {
				System.out.println("Verify Applicant Information Is Correct:");
				containers.getUnverifiedContainer().printColumnNames();
				applicant.printRow();
				System.out.println();
				System.out.println("Enter \"a\" To Authorize New Customer Application.\n"
						+ "Enter \"d\" To Discard New Customer Application.\n"
						+ "Enter \"c\" To Continue.");
				System.out.println();
				System.out.print("Choose Option: ");
				option = cin.next();
				
				System.out.println();
				customerDAO = new CustomerDAO();
				unverifiedDAO = new UnverifiedCustomerDAO();
				if (applicant != null) {
					if (option.toLowerCase().contains("a")) {
						Customer newCustomer = applicant.convertToCustomer(null,  null);
						log.debug("Attempting to approve application where unverified_id = " + applicant.getID() + " and create new customer with customer_id " + newCustomer.getID());
						customerDAO.addCustomerWithAccount(newCustomer, false);
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
						System.out.println("Success! New Accounts Have Been Established For " + newCustomer.getFirstname());
						System.out.println("     Savings Account: " + newCustomer.getSavingsAccount().getID());
						System.out.println("    Checking Account: " + newCustomer.getCheckingAccount().getID());
						System.out.println();
						break;
					} else if (option.toLowerCase().contains("d")) {
						System.out.println("Application Has Been Erased. ");
						log.debug("Attempting to discard application where unverified_id = " + applicant.getID());
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
						System.out.println();
						break;
					} else if (option.toLowerCase().contains("c")) {
						System.out.println();
						break;
					} 
				}
			}
			
		}  else if (selection == stop) {
			begin(AdminMenus.SELECTION);
		}
		else {
			System.out.println(selection + " Is Not A Valid Input.\n");
			begin(AdminMenus.SELECTION);
		}
	}
	
	public void commitCustomerChanges() {
		customerDAO = new CustomerDAO();
		ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
		log.debug("Attempting to update all customers in ArrayList from customerDAO:");
		if (customers != null) {
			for (Customer c : customers) {
				if (c != null) {
					customerDAO.updateCustomerAndAccounts(c, false);
					System.out.println("Updating \'customers_with_accounts\' Table Where customer_id = " + c.getCustomerID() + "...");
				} else System.out.println("Unable to update customer.");
			}
		} else System.out.println("Unable to update customer.");
		log.debug("End update all customers in ArrayList from customerDAO");
	
		System.out.println();
		return;
	} 
	
	public void selectUnverifiedOption(int selection) throws InterruptedException {
		
		if (selection == 1) {
			//System.out.println();
			unverifiedDAO = new UnverifiedCustomerDAO();
			containers.getUnverifiedContainer().printColumnNames();
			ArrayList<UnverifiedCustomer> unverified = unverifiedDAO.getAllUnverifiedCustomers(false);
			for (UnverifiedCustomer c : unverified) {
				c.printRow();
			}
			System.out.println();
			
			Scanner cin = new Scanner(System.in);
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}
			System.out.println();
			return;
		} else if (selection == 2) {
			Scanner cin = new Scanner(System.in);
			
			int applicantID;
			System.out.print("Enter Applicant_ID: ");
			applicantID = Integer.parseInt(cin.nextLine());
			boolean found = false;
			
			UnverifiedCustomer applicant = null;
			unverifiedDAO = new UnverifiedCustomerDAO();

			ArrayList<UnverifiedCustomer> unverifiedCustomers = containers.getUnverifiedContainer().getArrayList();
			if (applicantID <= unverifiedDAO.getMaxID(false)) {
				applicant = unverifiedDAO.findUnverifiedCustomerByID(applicantID, false);
				if (applicant != null) {
					found = true;
				} else System.out.println("No Customer Was Found With Applicant_ID: " + applicantID);
			} else System.out.println("No Customer Was Found With Applicant_ID: " + applicantID);
			
			if (found && applicant != null){
				String done = "";
				while (!done.toLowerCase().contains("a") && !done.toLowerCase().contains("d")) {
					containers.getUnverifiedContainer().printColumnNames();
					applicant.printRow();
					System.out.println();
					System.out.println("Enter \"a\" To Approve Customer Application.\n"
							+ "Enter \"d\" To Deny Customer Application.\n"
							+ "Enter \"c\" To Continue.");
					System.out.println();
					System.out.print("Choose Option: ");
					done = cin.next();
					
					customerDAO = new CustomerDAO();
					if (done.toLowerCase().contains("a")) {
						Customer newCustomer = applicant.convertToCustomer(null,  null);
						log.debug("Attempting to approve application where unverified_id = " + applicant.getID() + " and create new customer with customer_id " + newCustomer.getID());
						customerDAO.addCustomerWithAccount(newCustomer, false);
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
						System.out.println("Success! New Accounts Have Been Established For " + newCustomer.getFirstname());
						System.out.println("     Savings Account: " + newCustomer.getSavingsAccount().getID());
						System.out.println("    Checking Account: " + newCustomer.getCheckingAccount().getID());
					} else if (done.toLowerCase().contains("d")) {
						log.debug("Attempting to discard application where unverified_id = " + applicant.getID());
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
						System.out.println("The Application Has Been Erased... ");
						System.out.println();
					} else if (done.toLowerCase().contains("c")) {
						break;
					}
				}
			}

			System.out.println();
			return;
		} else if (selection == stop) {
			begin(AdminMenus.SELECTION);
		}
		else {
			System.out.println(selection + " Is Not A Valid Input.\n");
			begin(AdminMenus.SELECTION);
		}
	}
	
	public void commitApplicantChanges() {
		unverifiedDAO = new UnverifiedCustomerDAO();

		ArrayList<UnverifiedCustomer> applicants = containers.getUnverifiedContainer().getArrayList();
		log.debug("Attempting to update all unverified customers in ArrayList from unverifiedCustomerDAO:");
		if (applicants != null) {
			for (UnverifiedCustomer c : applicants) {
				if (c != null) {
					unverifiedDAO.updateUnverifiedCustomer(c, false);
					System.out.println("Updating \'unverified_customers\' Table Where unverified_id = " + c.getID() + "...");
				} else System.out.println("Unable to update applicant.");
			}
		} else System.out.println("Unable to update applicant.");	
		log.debug("End update all unverified customers in ArrayList from unverifiedCustomerDAO");
		return;
	}
	
	public void selectAdminOption(int selection) throws InterruptedException {
	
		if (selection == 1) {
			begin(AdminMenus.CUSTOMERS);
		} else if (selection == 2) {
			begin(AdminMenus.UNVERIFIED);
		} else if (selection == stop) {
			if (admin != null) System.out.println("Bye " + admin.getUsername() + "!\n");
			else System.out.println("???");
			LoginController.logout();
			begin(AdminMenus.LOGOUT);
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(AdminMenus.SELECTION);
		}
	}
	
	public void begin(AdminMenus adminMenu) throws InterruptedException {
		if (admin == null) {
			admin = (Admin) LoginController.getLoggedInAdmin();
		}
		
		if (adminMenu == AdminMenus.LOGOUT) {
			LoginController.logout();
			mainMenu.begin(Menus.DEFAULT); 
			return;
		}  else if (adminMenu == AdminMenus.SELECTION) {
			adminOptions = new AdminOptions(AdminMenus.SELECTION);
			stop = adminOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) {
					selectAdminOption(stop);
					return;
				}
				else selection = -1;
				try {
					while (adminOptions.inBounds(selection) == false) {
						selection = adminOptions.displayAccountsMenu();
						if (selection == stop) {
							selectAdminOption(stop); 
							return;
						}
						else if (adminOptions.inBounds(selection) == true) {
							selectAdminOption(selection);
							return;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (adminMenu == AdminMenus.CUSTOMERS) {
			adminOptions = new AdminOptions(AdminMenus.CUSTOMERS);
			stop = adminOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectCustomerOption(stop);
				else selection = -1;
				try {
					while (adminOptions.inBounds(selection) == false) {
						selection = adminOptions.displayAccountsMenu();
						if (selection == stop) {
							selectCustomerOption(stop); 
							return;
						}
						else if (adminOptions.inBounds(selection) == true) {
							selectCustomerOption(selection);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (adminMenu == AdminMenus.UNVERIFIED) {
			adminOptions = new AdminOptions(AdminMenus.UNVERIFIED);
			stop = adminOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectUnverifiedOption(stop);
				else selection = -1;
				try {
					while (adminOptions.inBounds(selection) == false) {
						selection = adminOptions.displayAccountsMenu();
						if (selection == stop) {
							selectUnverifiedOption(stop); 
							return;
						}
						else if (adminOptions.inBounds(selection) == true) {
							selectUnverifiedOption(selection);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
