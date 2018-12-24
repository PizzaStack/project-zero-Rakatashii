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

import DAO.CustomerDAO;
import controller.MainMenuController;
import controller.MainMenuController.Menus;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;
import controller.RegistrationController;
import people.Person;
import utility.Helpers;
import customers.Customer;
import customers.UnverifiedCustomer;
import database.DBConnection;
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
    	Schemas schemas;
    	schemas = new Schemas();
    	schemas.createActualTables();
    	schemas.createSampleTables();
    	
    	//DBConnection.closeConnection();
    	//System.out.println("Connection Closed.");
    	String customerSampleFileName = "text_files/customer_sample.txt";
    	File customerSampleFile = new File(customerSampleFileName);
    	
    	UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
		CustomerContainer customerContainer = new CustomerContainer();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers containers = new Containers();
    	containers.setUnverifiedContainer(unverifiedContainer);
    	containers.setCustomerContainer(customerContainer);
    	containers.setEmployeeContainer(employeeContainer);
    	containers.setAdminContainer(adminContainer);
    	
    	UnverifiedCustomer.passUnverifiedContainer(containers.getUnverifiedContainer());
    	Customer.passCustomerContainer(containers.getCustomerContainer());
    	Employee.passEmployeeContainer(containers.getEmployeeContainer());
    	Admin.passAdminContainer(containers.getAdminContainer());
    	
    	DBUtil dbUtil = new DBUtil();
    	
    	Customer first_customer = new Customer("customer", "password", "firstname", "lastname", 
    			"telephone", "email", true, true, "employer");
    	
    	if (dbUtil.checkIfEmpty("sample_customers") == true) {
        	try {
    			customerContainer.readIn(new File("text_files/customer_sample.txt"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	customerContainer.setTextFileName("text_files/formatted_customer_sample.txt");
        	try {
    			customerContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	CustomerDAO customerDAO = new CustomerDAO();
        	
        	ArrayList<Customer> customers = customerContainer.getArrayList();
        	for (Customer c : customers) {
        		c.printRow();
        		customerDAO.addSampleCustomer(c);
        	}
    	}
    	
    	UnverifiedCustomer u1 = new UnverifiedCustomer("unverified", "customer");
    	Employee e1 = new Employee("employee", "password");
    	Admin a1 = new Admin("admin", "password");
    	
    	
    	
    	
    	/*
		String dbconnectionfile = "preferences/dbconnection.txt";
		File file = new File(dbconnectionfile);
		Scanner in = new Scanner(file);
		String url = in.nextLine();
		System.out.println(url);
		String username = in.nextLine();
		System.out.println(username);
		String password = in.nextLine();
		System.out.println(password);
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not established");
		}
    	try (
		    Connection connection = DriverManager.getConnection(url, username, password);
		    Statement statement = connection.createStatement();
		){
		// STEP 3: Execute a query
		System.out.println("Creating table in given database...");
		String sql = "CREATE TABLE test_table" 
				+ "(id INTEGER PRIMARY KEY, " 
				+ " first VARCHAR(255), "
				+ " last VARCHAR(255), " 
				+ " age INTEGER );";
		statement.execute(sql); //executeUpdate(sql);
		System.out.println("Created table in given database...");

		// STEP 4: Clean-up environment
		statement.close();
		connection.close();
		} catch (SQLException ex) {
		    System.out.println("Unable to establish connection to database");
		}
    	*/
    	
    	
    	/*
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
    	*/
    }
    
    /** TODO
     + * (Clean up main()) 
     + * Create import/export from  containers to controller in order to populate
     * Create sample data for Admin and Employee, maybe Customer too to verify that Unverified is being converted correctly
       - (need to be able to run ruby to generate sample data) 
     * Finish login pages for Admin, Employee, Customer
       - would be better to have the sample data first to populate customers with usernames and passwords
     * Create accounts class and update all customer classes with account numbers and balances (Savings/Checking)
     * Tests for controller and account classes
     * Probably a good time for joint accounts feature (Just give a reference to primary accounts holders account number
     and set the primary account holder field in the Accounts classes to the id of that person
     * Connect to the database and create sample tables to get basic inserts, updates, and removes working
     * Normalize tables
     * Finalize containers for storing and removing persistant data (rebase, reindex, removeduplicates, etc)
     * Give priveleges to Employees and Admins to manipulate data
     * More Tests
     * Look into Serialization
     * Look into Logging
     * Expand database features
     */
    
    /** Pres
     * MVC may not be best here. May be better to organize in a way that would exploit Java's
       strenghts - i.e., grouping related classes in the same package. 
       - Evident especially in problems arrising from having the controllers and views
           communicate with one another between packages.
     * 
     */
}