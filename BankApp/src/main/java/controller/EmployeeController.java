package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.CustomerDAO;
import DAO.UnverifiedCustomerDAO;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.CustomerOptions;
import views.EmployeeOptions;
import controller.CustomerController.CustomerMenus;
import controller.MainMenuController.Menus;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Employee;

public class EmployeeController {
	EmployeeOptions employeeOptions;
	public enum EmployeeMenus { SELECTION, LOGOUT, CUSTOMERS, UNVERIFIED };
	private int stop;
	
	Employee employee;
	MainMenuController mainMenu = null;
	
	private Containers containers;
	
	CustomerDAO customerDAO;
	UnverifiedCustomerDAO unverifiedDAO;
	
	public EmployeeController() { }
	public EmployeeController(MainMenuController mainMenu, Containers containers) { 
		if (LoginController.isLoggedIn()) employee = LoginController.getLoggedInEmployee();
		this.mainMenu = mainMenu;
		this.containers = containers;
	}
		
	public void selectCustomerOption(int selection) throws InterruptedException {
			
		if (selection == 1) {
			System.out.println();
			customerDAO = new CustomerDAO();
			containers.getCustomerContainer().printColumnNames();
			//customerDAO.printAllCustomers(false);
			//ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
			//System.out.println("From DAO: ");
			ArrayList<Customer> customers = customerDAO.getAllCustomers(false);
			for (Customer c : customers) {
				c.printRow();
			}
			System.out.println();
			
			/*
			System.out.println("From Containers");
			containers.getCustomerContainer().printAll();
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
		} else if (selection == stop) {
			begin(EmployeeMenus.SELECTION);
		}
		else {
			System.out.println(selection + " Is Not A Valid Input.\n");
			begin(EmployeeMenus.SELECTION);
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
			System.out.println();
			unverifiedDAO = new UnverifiedCustomerDAO();
			containers.getUnverifiedContainer().printColumnNames();
			//customerDAO.printAllCustomers(false);
			//ArrayList<Customer> customers = containers.getCustomerContainer().getArrayList();
			System.out.println("From DAO: ");
			ArrayList<UnverifiedCustomer> unverified = unverifiedDAO.getAllUnverifiedCustomers(false);
			//ArrayList<UnverifiedCustomer> unverified = new ArrayList<UnverifiedCustomer>();
			// Need ^ but have to make changes in DAO  first
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
					
					if (done.toLowerCase().contains("a")) {
						customerDAO = new CustomerDAO();
						Customer newCustomer = applicant.convertToCustomer(null,  null);
						customerDAO.addCustomerWithAccount(newCustomer, false);
						
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomerWithID(applicantID,  false);
						break;
					} else if (done.toLowerCase().contains("d")) {
						containers.getUnverifiedContainer().remove(applicant);
						unverifiedDAO.deleteUnverifiedCustomerWithID(applicantID,  false);
						applicant = null;
						break;
					} else if (done.toLowerCase().contains("c")) {
						break;
					}
				}
			}

			System.out.println();
			return;
		} else if (selection == stop) {
			begin(EmployeeMenus.SELECTION);
		}
		else {
			System.out.println(selection + " Is Not A Valid Input.\n");
			begin(EmployeeMenus.SELECTION);
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
	
	public void selectEmployeeOption(int selection) throws InterruptedException {
		if (selection == 1) {
			begin(EmployeeMenus.CUSTOMERS);
		} else if (selection == 2) {
			begin(EmployeeMenus.UNVERIFIED);
		} else if (selection == stop) {
			if (employee != null) System.out.println("Bye " + employee.getUsername() + "!\n");
			else System.out.println("???");
			LoginController.logout();
			begin(EmployeeMenus.LOGOUT);
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(EmployeeMenus.SELECTION);
		}
	}
	public void begin(EmployeeMenus employeeMenu) throws InterruptedException {
		if (employee == null) {
			employee = LoginController.getLoggedInEmployee();
		}
		
		if (employeeMenu == EmployeeMenus.LOGOUT) {
			LoginController.logout();
			mainMenu.begin(Menus.DEFAULT); 
			return;
		}  else if (employeeMenu == EmployeeMenus.SELECTION) {
			employeeOptions = new EmployeeOptions(EmployeeMenus.SELECTION);
			stop = employeeOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) {
					selectEmployeeOption(stop);
					return;
				}
				else selection = -1;
				try {
					while (employeeOptions.inBounds(selection) == false) {
						selection = employeeOptions.displayAccountsMenu();
						if (selection == stop) {
							selectEmployeeOption(stop); 
							return;
						}
						else if (employeeOptions.inBounds(selection) == true) {
							selectEmployeeOption(selection);
							return;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (employeeMenu == EmployeeMenus.CUSTOMERS) {
			employeeOptions = new EmployeeOptions(EmployeeMenus.CUSTOMERS);
			stop = employeeOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectCustomerOption(stop);
				else selection = -1;
				try {
					while (employeeOptions.inBounds(selection) == false) {
						selection = employeeOptions.displayAccountsMenu();
						if (selection == stop) {
							selectCustomerOption(stop); 
							return;
						}
						else if (employeeOptions.inBounds(selection) == true) {
							selectCustomerOption(selection);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (employeeMenu == EmployeeMenus.UNVERIFIED) {
			employeeOptions = new EmployeeOptions(EmployeeMenus.UNVERIFIED);
			stop = employeeOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectUnverifiedOption(stop);
				else selection = -1;
				try {
					while (employeeOptions.inBounds(selection) == false) {
						selection = employeeOptions.displayAccountsMenu();
						if (selection == stop) {
							selectUnverifiedOption(stop); 
							return;
						}
						else if (employeeOptions.inBounds(selection) == true) {
							selectUnverifiedOption(selection);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	*/
	/*
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
	*/
}
