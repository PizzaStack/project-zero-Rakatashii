package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DAO.CustomerDAO;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.CustomerOptions;
import views.EmployeeOptions;
import controller.CustomerController.CustomerMenus;
import controller.MainMenuController.Menus;
import customers.Customer;
import employees.Employee;

public class EmployeeController{
	EmployeeOptions employeeOptions;
	public enum EmployeeMenus { SELECTION, LOGOUT, CUSTOMERS, UNVERIFIED };
	private int stop;
	
	Employee employee;
	MainMenuController mainMenu = null;
	
	private Containers containers;
	
	CustomerDAO customerDAO;
	
	public EmployeeController() { }
	public EmployeeController(MainMenuController mainMenu, Containers containers) { 
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
			System.out.println("From DAO: ");
			ArrayList<Customer> customers = customerDAO.getAllCustomers(false);
			for (Customer c : customers) {
				c.printRow(true);
			}
			System.out.println();
			System.out.println("From Containers");
			containers.getCustomerContainer().printAll(true);
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
			
			int customerID;
			System.out.print("Enter Customer_ID: ");
			customerID = Integer.parseInt(cin.nextLine());
			Customer customer = null;
			System.out.println();
			
			customer = customerDAO.findCustomerByID(customerID, false);
			if (customer != null) {
				containers.getCustomerContainer().printColumnNames();
				customer.printRow();
			} else System.out.println("No Customer Was Found With Customer_ID: " + customerID);
			
			String done = "";
			while (!done.toLowerCase().contains("c")) {
				System.out.print("Enter \"c\" To Continue. ");
				done = cin.next();
			}

			System.out.println();
			return;
		} else if (selection == 3) {
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
	public void selectEmployeeOption(int selection) throws InterruptedException {
		if (selection == 1) {
			begin(EmployeeMenus.CUSTOMERS);
		} else if (selection == 2) {
		} else if (selection == stop) {
			System.out.println("Bye " + employee.getUsername() + "!\n");
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
