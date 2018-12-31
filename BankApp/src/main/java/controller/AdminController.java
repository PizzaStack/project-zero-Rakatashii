package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.CustomerDAO;
import DAO.UnverifiedCustomerDAO;
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
			//customerDAO.printAllCustomers(false);
			
			//System.out.println("From DAO: ");
			ArrayList<Customer> DBcustomers = customerDAO.getAllCustomers(false);
			for (Customer c : DBcustomers) {
				c.printRow();
			}
			System.out.println();
			//containers.getCustomerContainer().printAll();
			
			/*
			System.out.println("From Containers");
			containers.getCustomerContainer().printAll(true);
			System.out.println();
			*/
			
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
					//containers.getCustomerContainer().printColumnNames(true);
					//customer.printRow();
					found = true;
				} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			
			if (found && customer != null){
				//System.out.println("customer username is " + customer.getUsername());
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
							customer.unflag();
							customerDAO.updateCustomerAndAccounts(customer, false);
							//commitCustomerChanges();
						} else if (done.toLowerCase().contains("c"))
							break;
					} else {
						System.out.print("Enter \"c\" To Continue or \"d\" To Disable Customer Account. ");
						done = cin.next();
						if (done.toLowerCase().contains("d")) {
							customer.flag();
							customerDAO.updateCustomerAndAccounts(customer, false);
							//commitCustomerChanges();
						}
						else if (done.toLowerCase().contains("c")) 
							break;
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
		} else if (selection == 3) {
			
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
				System.out.println("Enter \"a\" To Authorize New Customer Application.\n"
						+ "Enter \"d\" To Discard New Customer Application.\n"
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
			
		} else if (selection == 4) {
			RegistrationController registrationController = new RegistrationController();
			registrationController.passContainers(containers);
			registrationController.call();
	
			UnverifiedCustomer applicant = containers.getUnverifiedContainer().getArrayList().get(containers.getUnverifiedContainer().getSize()-1);
			
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
		//ArrayList<Customer> customers = customerDAO.getAllCustomers(false);
		ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
		if (customers != null) {
			for (Customer c : customers) {
				if (c != null) {
					customerDAO.updateCustomerAndAccounts(c, false);
					System.out.println("Updating \'customers_with_accounts\' Table Where customer_id = " + c.getCustomerID() + "...");
				} else System.out.println("Unable to update customer.");
			}
		} else System.out.println("Unable to update customer.");
	
		System.out.println();
		return;
	} 
	
	public void selectUnverifiedOption(int selection) throws InterruptedException {
		
		if (selection == 1) {
			//System.out.println();
			unverifiedDAO = new UnverifiedCustomerDAO();
			containers.getUnverifiedContainer().printColumnNames();
			//customerDAO.printAllCustomers(false);
			//ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
			//System.out.println("From DAO: ");
			ArrayList<UnverifiedCustomer> unverified = unverifiedDAO.getAllUnverifiedCustomers(false);
			//ArrayList<UnverifiedCustomer> unverified = new ArrayList<UnverifiedCustomer>();
			// Need ^ but have to make changes in DAO  first
			for (UnverifiedCustomer c : unverified) {
				c.printRow();
			}
			System.out.println();
			/*
			System.out.println();
			System.out.println("From Containers");
			containers.getUnverifiedContainer().printAll(true);
			*/
			
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
			//ArrayList<Customer> customers = customerDAO.getAllCustomers(false);
			ArrayList<UnverifiedCustomer> unverifiedCustomers = containers.getUnverifiedContainer().getArrayList();
			if (applicantID <= unverifiedDAO.getMaxID(false)) {
				applicant = containers.getUnverifiedContainer().get(applicantID);
				if (applicant != null) {
					//containers.getCustomerContainer().printColumnNames(true);
					//customer.printRow();
					found = true;
				} else System.out.println("No Customer Was Found With Applicant_ID: " + applicantID);
			} else System.out.println("No Customer Was Found With Applicant_ID: " + applicantID);
			
			if (found && applicant != null){
				//System.out.println("customer username is " + customer.getUsername());
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
						customerDAO.addCustomerWithAccount(newCustomer, false);
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
						System.out.println("Success! New Accounts Have Been Established For " + newCustomer.getFirstname());
						System.out.println("     Savings Account: " + newCustomer.getSavingsAccount().getID());
						System.out.println("    Checking Account: " + newCustomer.getCheckingAccount().getID());
						System.out.println();
					} else if (done.toLowerCase().contains("d")) {
						System.out.println("The Application Has Been Erased... ");
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomer(applicant, false);
						applicant = null;
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
		//ArrayList<Customer> customers = customerDAO.getAllCustomers(false);
		ArrayList<UnverifiedCustomer> applicants = containers.getUnverifiedContainer().getArrayList();
		if (applicants != null) {
			for (UnverifiedCustomer c : applicants) {
				if (c != null) {
					unverifiedDAO.updateUnverifiedCustomer(c, false);
					System.out.println("Updating \'unverified_customers\' Table Where unverified_id = " + c.getID() + "...");
				} else System.out.println("Unable to update applicant.");
			}
		} else System.out.println("Unable to update applicant.");	
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
