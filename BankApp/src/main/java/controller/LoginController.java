package controller;

import customers.Customer;
import employees.Admin;
import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.Login;
import views.MenuOptions;

public class LoginController {
	private boolean verified = false;

	private CustomerContainer customerContainer;
	private EmployeeContainer<Employee> employeeContainer = null;
	private EmployeeContainer<Employee> adminContainer = null;
	
	static Customer loggedInCustomer = null;
	static Employee loggedInEmployee = null;
	static Employee loggedInAdmin = null;
	
	int customerID = -1;
	int employeeID = -1;
	
	String username;
	String password;
	
	private int tries = 3;
	private Login login = new Login();
	
	// Only one person, whatever type, can be logged in at once - so makes sense to make static
	static boolean loggedIn;
	static String loggedInUsername;
	
	public static boolean isLoggedIn() { 
		return loggedIn; 
	} 
	public static String getLoggedInUsername() { 
		if (loggedInUsername != null) 
			return loggedInUsername; 
		else loggedIn = false;
		return "";
	}
	public static void logout() { 
		loggedIn = false; 
		loggedInUsername = null; 
	}
	public void logInAs(String username) {
		loggedIn = true;
		loggedInUsername = username;
	}
	
	public boolean loginAsCustomer(CustomerContainer customerContainer) throws InterruptedException{
		this.customerContainer = customerContainer;
		//Customer loggedInCustomer = null;
		while (!verified && tries > 0) {
			
			username = login.getUsername();
			password = login.getPassword();
			
			if (customerContainer == null) System.out.println("customerContainer is null in Login Controller.");
			
			loggedInCustomer = customerContainer.verifyLoginCredentials(username, password);
			
			verified = (loggedInCustomer != null) ? true : false;
			if (verified) { 
				System.out.println("\nSuccess! Welcome " + username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(username);
				System.out.println();
				return true; 
			}
		
			tries--;
			if (tries > 0) System.out.println("\nInvalid Login Credentials. " + tries + " attempts remaining.");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2000);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public boolean loginAsEmployee(EmployeeContainer<Employee> employeeContainer) throws InterruptedException{
		this.employeeContainer = employeeContainer;
		while (!verified && tries > 0) {
			
			username = login.getUsername();
			password = login.getPassword();
			
			if (employeeContainer == null) System.out.println("employeeContainer is null in Login Controller");
			
			loggedInEmployee = employeeContainer.verifyLoginCredentials(username, password);
			verified = (loggedInEmployee != null) ? true : false;
			
			if (verified) { 
				System.out.println("\nSuccess! Welcome " + username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(username);
				System.out.println();
				return true;
			}
			
			tries--;
			if (tries > 0) System.out.println("\nInvalid Login Credentials. " + tries + " attempts remaining.");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2000);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public boolean loginAsAdmin(EmployeeContainer<Employee> adminContainer) throws InterruptedException{
		this.adminContainer = adminContainer;
		while (!verified && tries > 0) {
			
			username = login.getUsername();
			password = login.getPassword();
			
			if (adminContainer == null) System.out.println("adminContainer is null in Login Controller");

			loggedInAdmin = adminContainer.verifyLoginCredentials(username, password);
			verified = (loggedInAdmin != null) ? true : false;
			
			if (verified) { 
				System.out.println("\nSuccess! Welcome " + username + "."); 
				if (!isLoggedIn()) Thread.sleep(2000); 
				logInAs(username);
				System.out.println();
				return true;
			}
			
			tries--;
			if (tries > 0) System.out.println("\nInvalid Login Credentials. " + tries + " attempts remaining.");
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
