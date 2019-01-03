package controller;

import DAO.AdminDAO;
import customers.Customer;
import employees.Admin;
import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import utility.Symbols;
import views.Login;
import views.MenuOptions;

public class LoginController {
	private boolean verified = false;

	private CustomerContainer customerContainer = null;
	private EmployeeContainer<Employee> employeeContainer = null;
	private EmployeeContainer<Employee> adminContainer = null;
	
	static Customer loggedInCustomer = null;
	static Employee loggedInEmployee = null;
	static Employee loggedInAdmin = null;
	
	int customerID = -1;
	int employeeID = -1;
	
	String username = null;
	String password = null;
	
	private int tries = 3;
	private Login login = new Login();
	
	// Only one person, whatever type, can be logged in at once - so makes sense to make static
	static boolean loggedIn;
	static String loggedInUsername;
	
	public static boolean isLoggedIn() { 
		return loggedIn; 
	} 
	public static String getLoggedInUsername() { 
		/*if (loggedInUsername != null) 
			return loggedInUsername; 
		else loggedIn = false;
		return "";*/
		return loggedInUsername;
	}
	public static void logout() { 
		loggedIn = false; 
		loggedInUsername = null; 
		loggedInCustomer = null;
		loggedInEmployee = null;
		loggedInAdmin = null;

	}
	public void logInAs(String username) {
		loggedIn = true;
		loggedInUsername = username;
	}
	
	public boolean loginAsCustomer(CustomerContainer customerContainer, String user, String pass) throws InterruptedException{
		this.customerContainer = customerContainer;
		//Customer loggedInCustomer = null;
		while (!verified && tries > 0) {
			this.username = user;
			this.password = pass;
			
			if (username == null) this.username = login.getUsername();
			if (password == null) this.password = login.getPassword();
			
			if (customerContainer == null) System.out.println("customerContainer is null in Login Controller.");
			
			loggedInCustomer = customerContainer.verifyLoginCredentials(this.username, this.password);
			
			verified = (loggedInCustomer != null) ? true : false;
			if (verified) { 
				this.username = loggedInCustomer.getUsername();
				System.out.println("\nSuccess! Welcome " + this.username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(username);
				System.out.println();
				return true; 
			}
		
			tries--;
			if (tries > 0) System.out.println("\n" + Symbols.warning + "  Invalid Login Credentials. [" + tries + " attempts remaining]\n");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2000);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public boolean loginAsEmployee(EmployeeContainer<Employee> employeeContainer, String user, String pass) throws InterruptedException{
		this.employeeContainer = employeeContainer;
		while (!verified && tries > 0) {
			this.username = user;
			this.password = pass;
			
			if (username == null) this.username = login.getUsername();
			if (password == null) this.password = login.getPassword();
			
			if (employeeContainer == null) System.out.println("employeeContainer is null in Login Controller");
			
			loggedInEmployee = employeeContainer.verifyLoginCredentials(this.username, this.password);
			verified = (loggedInEmployee != null) ? true : false;
			
			if (verified) { 
				this.username = loggedInEmployee.getUsername();
				System.out.println("\nSuccess! Welcome " + this.username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(this.username);
				System.out.println();
				return true;
			}
			
			tries--;
			if (tries > 0) System.out.println("\n" + Symbols.warning + "  Invalid Login Credentials. [" + tries + " attempts remaining]\n");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2000);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public boolean loginAsAdmin(EmployeeContainer<Employee> adminContainer, String user, String pass) throws InterruptedException{
		this.adminContainer = adminContainer;
		while (!verified && tries > 0) {
			this.username = user;
			this.password = pass;
			
			if (username == null) this.username = login.getUsername();
			if (password == null) this.password = login.getPassword();
			
			if (adminContainer == null) System.out.println("adminContainer is null in Login Controller");

			loggedInAdmin = adminContainer.verifyLoginCredentials(this.username, this.password);
			verified = (loggedInAdmin != null) ? true : false;
			
			if (verified) { 
				this.username = loggedInAdmin.getUsername();
				System.out.println("\nSuccess! Welcome " + this.username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(this.username);
				System.out.println();
				return true;
			}
			
			tries--;
			if (tries > 0) System.out.println("\n" + Symbols.warning + "  Invalid Login Credentials. [" + tries + " attempts remaining]\n");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2000);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public int getNumTries() {
		return tries;
	}
	
	public static Customer getLoggedInCustomer() {
		if (loggedInCustomer != null) return loggedInCustomer;
		else return null;
	}
	public static Employee getLoggedInEmployee() {
		if (loggedInEmployee != null) return loggedInEmployee;
		else return null;
	}
	public static Employee getLoggedInAdmin() {
		if (loggedInAdmin != null) return loggedInAdmin;
		else return null;
	}
}
