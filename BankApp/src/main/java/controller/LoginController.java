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
	private boolean verified;
	private Containers containers;
	private CustomerContainer customers;
	private EmployeeContainer<Employee> employees;
	private EmployeeContainer<Employee> admins;
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
	
	public boolean call(int selection) throws InterruptedException{
		while (!verified && tries > 0) {
			username = login.getUsername();
			password = login.getPassword();
			if (containers == null) System.out.println("CONTAINERS ARE NULL IN LOGIN");
			if (selection == 2) {
				if (containers != null) customers = containers.getCustomerContainer();
				verified = customers.verifyLoginCredentials(username, password);
				if (verified) { 
					System.out.println("\nSuccess! Welcome " + username + "."); 
					if (!isLoggedIn()) Thread.sleep(2500); 
					logInAs(username);
					System.out.println();
					return true; 
				}
			} else if (selection == 3) {
				if (containers != null) employees = containers.getEmployeeContainer();
				verified = employees.verifyLoginCredentials(username,  password);
				if (verified) { 
					System.out.println("\nSuccess! Welcome " + username + "."); 
					if (!isLoggedIn()) Thread.sleep(2500); 
					logInAs(username);
					System.out.println();
					return true;
				}
			} else if (selection == 4) {
				if (containers != null) admins = containers.getAdminContainer();
				verified = admins.verifyLoginCredentials(username, password);
				if (verified) { 
					System.out.println("Success! Welcome " + username + "."); 
					if (!isLoggedIn()) Thread.sleep(2500); 
					logInAs(username);
					System.out.println();
					return true; 
				}
			}
			tries--;
			if (tries > 0) System.out.println("\nInvalid Login Credentials. " + tries + " attempts remaining.");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				if (!isLoggedIn()) Thread.sleep(2500);
				System.out.println();
				return false;
			}
		}
		return false;
	}
	public int getNumTries() {
		return tries;
	}
	public void passContainers(Containers containers) {
		if (containers != null) this.containers = containers;
	}
}
