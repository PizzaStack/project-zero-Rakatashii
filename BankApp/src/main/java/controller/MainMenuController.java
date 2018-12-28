package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.BankApp.BankApp;

import DAO.CustomerDAO;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.Login;
import views.MenuOptions;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;
import customers.Customer;
import employees.Employee;

public class MainMenuController {
	MenuOptions options = null;
	public enum Menus { DEFAULT, CUSTOMER, EMPLOYEE, ADMIN, EXIT };
	LoginController login = null;
	private int stop = 0;
	protected Containers containers = null;
	
	CustomerController customerController = null;
	EmployeeController employeeController = null;
	
	CustomerDAO customerDAO = new CustomerDAO();
	ArrayList<Customer> DBCustomers;
	
	public MainMenuController(){ }
	
	public void selectHomeOption(int selection) throws InterruptedException {
		boolean isVerified = false;
		
		login = new LoginController();
		
		if (selection == 1) {
			
			RegistrationController registrationController = new RegistrationController();
			registrationController.passContainers(containers);
			registrationController.call();
			
		} else if (selection == 2) { 
			
			this.DBCustomers = customerDAO.getAllCustomers(false);
			CustomerContainer customerContainer = containers.getCustomerContainer();
			customerController = new CustomerController(this, customerContainer);
			if (LoginController.isLoggedIn()) { System.out.println("Customer already logged in."); isVerified = true; }
			else while (isVerified == false && login.getNumTries() > 0) { 
				isVerified = login.loginAsCustomer(customerContainer);
			}
			if (isVerified == true) {
				customerController.passCustomerContainer(containers.getCustomerContainer());
				customerController.begin(CustomerMenus.SELECTION);
			} else System.out.println("Error. Customer could not be verified.");
			System.out.println("END - selection = 2");
			return;
			
		} else if (selection == 3) {
	
			EmployeeContainer<Employee> employeeContainer = containers.getEmployeeContainer();
			employeeController = new EmployeeController(this, employeeContainer);
			if (LoginController.isLoggedIn()) { System.out.println("Customer already logged in."); isVerified = true; }
			else while (isVerified == false && login.getNumTries() > 0) 
				isVerified = login.loginAsEmployee(containers.getEmployeeContainer());
			if (isVerified == true) {
				employeeController = new EmployeeController();
				employeeController.begin(EmployeeMenus.SELECTION);
			} else System.out.println("Error. Employee could not be verified.");
			System.out.println("END - selection = 3");
			return;
			
		} else if (selection == 4) {
			// ADMIN
			if (LoginController.isLoggedIn()) isVerified = true; 
			else while (isVerified == false && login.getNumTries() > 0) {
				isVerified = login.loginAsAdmin(containers.getAdminContainer());
			}
			return;
		}
		else if (selection == stop) { System.out.println("Shutting down..."); return; }
		else System.out.println(selection + " is not a valid input.\n");
	}
	
	public void begin(Menus menuType) throws InterruptedException {
		if (menuType == Menus.DEFAULT) {
			login = null;
			options = new MenuOptions(menuType);
			stop = options.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) { selectHomeOption(stop); return; }
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
	}
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
}
