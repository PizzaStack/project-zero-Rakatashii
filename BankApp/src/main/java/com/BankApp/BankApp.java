package com.BankApp;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import DAO.AccountDAO;
import DAO.AdminDAO;
import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.UnverifiedCustomerDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;
import controller.RegistrationController;
import people.Person;
import utility.Bash;
import utility.Helpers;
import customers.Customer;
import customers.UnverifiedCustomer;
import database.DBConnection;
import database.DBSetup;
import database.DBUtil;
import database.Schemas;
//import database.H2Test;
import customers.CustomerBuilder;
import employees.Admin;
import employees.Employee;
import model.AccountContainer;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;

public class BankApp 
{
	final static String dir = "/c/Users/Associate/java/project-zero-Rakatashii";
    public static void main( String[] args ) throws SQLException, FileNotFoundException, ClassNotFoundException, InterruptedException {
    	DBSetup setup = new DBSetup(true);
    	setup.initializeSampleTables();
    	
    	AccountContainer accountContainer = new AccountContainer();
		CustomerContainer customerContainer = new CustomerContainer();
		UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers containers = new Containers();
    	setup.passContainers(containers);
    	
    	// TODO accounts
    	containers.setUnverifiedContainer(unverifiedContainer);
    	containers.setCustomerContainer(customerContainer);
    	containers.setEmployeeContainer(employeeContainer);
    	containers.setAdminContainer(adminContainer);
    	
    	//SavingsAccount.passAccountContainer(accountContainer);
    	//CheckingAccount.passAccountContainer(accountContainer);
    	Customer.passCustomerContainer(containers.getCustomerContainer());
    	UnverifiedCustomer.passUnverifiedContainer(containers.getUnverifiedContainer());
    	Employee.passEmployeeContainer(containers.getEmployeeContainer());
    	Admin.passAdminContainer(containers.getAdminContainer());
    	
    	System.out.println();
    	setup.passContainers(containers);
    	setup.initializeActualTables();
    	
    	AccountDAO accountDAO = new AccountDAO();
    	CustomerDAO customerDAO = new CustomerDAO();
    	UnverifiedCustomerDAO unverifiedDAO = new UnverifiedCustomerDAO();
    	EmployeeDAO employeeDAO = new EmployeeDAO();
    	AdminDAO adminDAO = new AdminDAO();
    	
    	setup.finishDBSetup();
    	setup = null;
    	System.gc();
    	
    	char c = 0x25A0;
    	String sym = String.valueOf(c);
        System.out.println(sym);
    	
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
    	System.out.println("Program Terminated.");
    	
    	// TODO - option for admin...
    	// unverifiedContainer.updateRows();
    	// customerContainer.updateRows();
    	// employeeContainer.updateRows();
    	// adminContainer.updateRows();
    	
    }
    
    /** TODO Checklist
     + * (Clean up main()) 
     + * Create import/export from  containers to controller in order to populate
     ~ * Create sample data for Admin and Employee, maybe Customer too to verify that Unverified is being converted correctly
       - (need to be able to run ruby to generate sample data) 
     + * Finish login pages for Admin, Employee, Customer
       - would be better to have the sample data first to populate customers with usernames and passwords
     + * Create accounts class and update all customer classes with account numbers and balances (Savings/Checking)
     * Tests for controller and account classes
     ~ * Probably a good time for joint accounts feature (Just give a reference to primary accounts holders account number
     and set the primary account holder field in the Accounts classes to the id of that person
     ~ * Connect to the database and create sample tables to get basic inserts, updates, and removes working
     * Normalize tables
     * Finalize containers for storing and removing persistent data (rebase, reindex, removeduplicates, etc)
     * Give privileges to Employees and Admins to manipulate data
     * More Tests
     
        TODO (OPTIONAL)
     * set up H2
     * DB Testing
     * Look into Serialization
     * Expand database features
    
   		TODO Presentation
     * MVC may not be best here. May be better to organize in a way that would exploit Java's
       strengths - i.e., grouping related classes in the same package. 
       - Evident especially in problems arrising from having the controllers and views
           communicate with one another between packages.
    */
    
    /** TODO Doesn't Work Anymore, but consider if needed:
	Bash bash = new Bash();
	String output;
	try {
		output = bash.getTerminalOutput("cd text_files && ls");
		if (output != null) System.out.println("\n" + output);
		else System.out.println("output is null");
	} catch (IOException e) {
		e.printStackTrace();
	}
	*/
}