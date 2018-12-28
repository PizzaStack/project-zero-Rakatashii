package com.BankApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;

public class BankApp 
{
	final static String dir = "/c/Users/Associate/java/project-zero-Rakatashii";
    public static void main( String[] args ) throws SQLException, FileNotFoundException, ClassNotFoundException {
    	DBSetup setup = new DBSetup();
    	// If tables do not exist they will be initialized. 
    	// If sample tables are not filled with sample data, they will be filled.
    	setup.initializeSampleTables();
    	
    	UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
		CustomerContainer customerContainer = new CustomerContainer();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers containers = new Containers();
    	setup.passContainers(containers);
    	containers.setUnverifiedContainer(unverifiedContainer);
    	containers.setCustomerContainer(customerContainer);
    	containers.setEmployeeContainer(employeeContainer);
    	containers.setAdminContainer(adminContainer);
    	
    	UnverifiedCustomer.passUnverifiedContainer(containers.getUnverifiedContainer());
    	Customer.passCustomerContainer(containers.getCustomerContainer());
    	Employee.passEmployeeContainer(containers.getEmployeeContainer());
    	Admin.passAdminContainer(containers.getAdminContainer());
    	
    	Customer first_customer = new Customer("customer", "password", "firstname", "lastname", 
    			"telephone", "email", true, true, "employer");
    	UnverifiedCustomer first_unverified = new UnverifiedCustomer("unverified", "customer", 
    			"000-000-0000", "email@address.com", true, true, "employer");
    	Employee first_employee = new Employee("employee", "password");
    	Admin first_admin = new Admin("admin", "password");
    	
    	setup.initializeActualTables();
    	
    	AccountDAO accountDAO = new AccountDAO();
    	CustomerDAO customerDAO = new CustomerDAO();
    	UnverifiedCustomerDAO unverifiedCustomerDAO = new UnverifiedCustomerDAO();
    	EmployeeDAO employeeDAO = new EmployeeDAO();
    	AdminDAO adminDAO = new AdminDAO();
    	
    	/*
    	ArrayList<String> sampleAccountRecords = accountDAO.getSampleRecordsAsStrings();
    	for (String sampleAccountRecord : sampleAccountRecords) {
    		System.out.println(sampleAccountRecord);
    	}
    	ArrayList<String> sampleCustomerRecords = customerDAO.getSampleRecordsAsStrings();
    	for (String sampleCustomerRecord : sampleCustomerRecords) {
    		System.out.println(sampleCustomerRecord);
    	}
    	ArrayList<String> sampleEmployeeRecords = employeeDAO.getSampleRecordsAsStrings();
    	for (String sampleEmployeeRecord : sampleEmployeeRecords) {
    		System.out.println(sampleEmployeeRecord);
    	}
    	*/
    	ArrayList<String> sampleAdminRecords = adminDAO.getSampleRecordsAsStrings();
    	for (String sampleAdminRecord : sampleAdminRecords) {
    		System.out.println(sampleAdminRecord);
    	}
    	// TODO finish up this process with unverifiedcustomers
    	
  
    	// TODO - option for admin...
    	// unverifiedContainer.updateRows();
    	// customerContainer.updateRows();
    	// employeeContainer.updateRows();
    	// adminContainer.updateRows();
    	
    	/*
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
    	*/
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
     */
    
    /** TODO Presentation
     * MVC may not be best here. May be better to organize in a way that would exploit Java's
       strengths - i.e., grouping related classes in the same package. 
       - Evident especially in problems arrising from having the controllers and views
           communicate with one another between packages.
     * 
     */
    
	/*  TODO Doesn't Work Anymore, but consider if needed:
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