package controller;

import customers.Customer;
import employees.Admin;
import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.Login;

public class LoginController {
	private boolean verified;
	private Containers containers;
	private CustomerContainer customers;
	private EmployeeContainer<Employee> employees;
	private EmployeeContainer<Employee> admins;
	
	String username;
	String password;
	
	private Login login = new Login();
	
	static boolean loggedIn = false;
	static String loggedInUsername = null;
	
	public void logInAs(String username) {
		loggedIn = true;
		loggedInUsername = username;
	}
	public boolean isLoggedIn() { return loggedIn; } // only static so far
	public String getLoggedInUsername() { 
		if (loggedInUsername != null) 
			return loggedInUsername; 
		else loggedIn = false;
		return "";
	}
	public void logout() { 
		loggedIn = false; 
		loggedInUsername = null; 
	}
	
	public boolean call(int selection) throws InterruptedException{
		int tries = 3;
		while (!verified && tries > 0) {
			username = login.getUsername();
			password = login.getPassword();
			if (selection == 2) {
				if (containers != null) customers = containers.getCustomerContainer();
				if (customers.verifyLoginCredentials(username, password)) { 
					System.out.println("\nSuccess! Welcome " + username + "."); 
					Thread.sleep(3000); 
					this.loggedIn = true;
					this.loggedInUsername = username;
					return true; 
				}
				//else System.out.println("Invalid Login Credentials, " + tries + " attempts remaining.");
			} else if (selection == 3) {
				if (containers != null) employees = containers.getEmployeeContainer();
				if (employees.verifyLoginCredentials(username,  password)) { 
					System.out.println("\nSuccess! Welcome " + username + "."); 
					Thread.sleep(3000); 
					this.loggedIn = true;
					this.loggedInUsername = username;
					return true;
				}
				//else System.out.println("Invalid Login Credentials, " + tries + " attempts remaining.");
			} else if (selection == 4) {
				if (containers != null) admins = containers.getAdminContainer();
				if (admins.verifyLoginCredentials(username, password)) { 
					System.out.println("\nSuccess! Welcome " + username + "."); 
					Thread.sleep(3000); 
					System.out.println();
					this.loggedIn = true;
					this.loggedInUsername = username;
					
					return true; 
				}
				//else System.out.println("Invalid Login Credentials, " + tries + " attempts remaining.");
			}
			tries--;
			if (tries > 0) System.out.println("\nInvalid Login Credentials. " + tries + " attempts remaining.\n");
			else {
				System.out.println("\nToo many login attempts. Returning to the Main Menu.");
				Thread.sleep(3000);
				System.out.println("\n");
				return false;
			}
		}
		return false;
	}
	public void passContainers(Containers containers) {
		if (containers != null) this.containers = containers;
	}
}
