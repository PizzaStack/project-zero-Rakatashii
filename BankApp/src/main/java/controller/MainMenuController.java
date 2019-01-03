package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.BankApp.BankApp;

import DAO.AdminDAO;
import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.UnverifiedCustomerDAO;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.Login;
import views.MenuOptions;
import controller.AdminController.AdminMenus;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Employee;

public class MainMenuController {
	MenuOptions options = null;
	public enum Menus { DEFAULT, CUSTOMER, EMPLOYEE, ADMIN, EXIT };
	LoginController login = null;
	private int stop = 0;
	protected Containers containers = null;
	
	CustomerController customerController = null;
	EmployeeController employeeController = null;
	AdminController adminController = null;
	
	CustomerDAO customerDAO = new CustomerDAO();
	ArrayList<Customer> DBCustomers;
	UnverifiedCustomerDAO unverifiedDAO = new UnverifiedCustomerDAO();
	ArrayList<UnverifiedCustomer> DBApplicants;
	EmployeeDAO employeeDAO = new EmployeeDAO();
	ArrayList<Employee> DBEmployees;
	AdminDAO adminDAO = new AdminDAO();
	ArrayList<Employee> DBAdmins;
	
	public MainMenuController(){ }
	
	public void selectHomeOption(int selection) throws InterruptedException {
		boolean isVerified = false;
		
		if (selection == 1) {
			
			RegistrationController registrationController = new RegistrationController();
			registrationController.passContainers(containers);
			registrationController.call();
			System.out.println();
			begin(Menus.DEFAULT);

		} else if (selection == 2) { 
			
			login = new LoginController();
			this.DBCustomers = customerDAO.getAllCustomers(false);
			CustomerContainer customerContainer = containers.getCustomerContainer();
			
			if (customerContainer != null) customerController = new CustomerController(this, customerContainer);
			if (LoginController.isLoggedIn()) { 
				System.out.println("Customer already logged in."); 
				isVerified = true; 
			}
			else while (isVerified == false && login.getNumTries() > 0)
				isVerified = login.loginAsCustomer(customerContainer, null, null);
			if (isVerified == true) {
				customerController.begin(CustomerMenus.SELECTION);
			} else System.out.println("\nError: Customer could not be verified.");
			return;
			
		} else if (selection == 3) {
	
			login = new LoginController();
			this.DBEmployees = employeeDAO.getAllEmployees(false);
			this.DBApplicants = unverifiedDAO.getAllUnverifiedCustomers(false);
			EmployeeContainer<Employee> employeeContainer = containers.getEmployeeContainer();
			
			if (containers != null) employeeController = new EmployeeController(this, containers);
			if (LoginController.isLoggedIn()) { 
				System.out.println("Employee already logged in."); 
				isVerified = true; 
			}
			else while (isVerified == false && login.getNumTries() > 0) 
				isVerified = login.loginAsEmployee(employeeContainer, null, null);
			if (isVerified == true) {
				employeeController.begin(EmployeeMenus.SELECTION);
			} else System.out.println("\nError: Employee could not be verified.");
			return;
			
		} else if (selection == 4) {

			login = new LoginController();
			this.DBEmployees = employeeDAO.getAllEmployees(false);
			this.DBApplicants = unverifiedDAO.getAllUnverifiedCustomers(false);
			EmployeeContainer<Employee> adminContainer = containers.getAdminContainer();
			
			if (containers != null) adminController = new AdminController(this, containers);
			if (LoginController.isLoggedIn()) { 
				System.out.println("Admin already logged in."); 
				isVerified = true; 
			}
			else while (isVerified == false && login.getNumTries() > 0) 
				isVerified = login.loginAsAdmin(adminContainer, null, null);
			if (isVerified == true) {
				adminController.begin(AdminMenus.SELECTION);
			} else System.out.println("\nError: Admin could not be verified.");
			return;
			
		}
		else if (selection == stop) { System.out.println("Shutting down..."); return; }
		else System.out.println(selection + " is not a valid input.\n");
		return;
	}
	
	public void begin(Menus menuType) throws InterruptedException {
		if (menuType == Menus.DEFAULT) {
			login = null;
			options = new MenuOptions(menuType);
			stop = options.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) { /*selectHomeOption(stop);*/ return; }
				else selection = -1;
				try {
					while (options.inBounds(selection) == false) {
						selection = options.displayHomeMenu();
						if (selection == stop) return;
						else if (options.inBounds(selection) == true) {
							selectHomeOption(selection);
							return;
						}
					}
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
