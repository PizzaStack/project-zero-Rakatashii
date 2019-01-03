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

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
    public static void main( String[] args ) throws SQLException, FileNotFoundException, ClassNotFoundException, InterruptedException {
    	PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator +
    			"\\src\\main\\java\\resources\\log4j.properties");
    	final Logger log = Logger.getLogger(BankApp.class);
    	log.debug("New Run: ");
    	
    	DBSetup setup = new DBSetup(true);
    	setup.initializeSampleTables();
    	
    	AccountContainer accountContainer = new AccountContainer();
		CustomerContainer customerContainer = new CustomerContainer();
		UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers containers = new Containers();
    	setup.passContainers(containers);
    	
    	containers.setUnverifiedContainer(unverifiedContainer);
    	containers.setCustomerContainer(customerContainer);
    	containers.setEmployeeContainer(employeeContainer);
    	containers.setAdminContainer(adminContainer);
    	
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
    	
    	Customer customer = new Customer();
    	
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
    	System.out.println("Program Terminated.");
    	log.debug("Program Reached End.");
    }
    
    /** TODO Checklist
     * Normalize tables (prob skip at this point...)
     * Way More More Tests
     * May want to add triggers to sample tables as well
       - may want to automatically generate triggers if possible
     
        TODO (OPTIONAL)
     * set up H2
     * DB Testing
     * Look into Serialization
     * Expand database features
    
   		TODO Presentation
     * MVC may not be optimal. May be better to organize in a way that would exploit Java's
       strengths - i.e., grouping related classes in the same package. 
       - Evident especially in problems arising from having the controllers and views
           communicate with one another between packages.
     * Stored Procedure Is Utilized in Triggers - one for accounts one for customers, which have the same fields as 
       customers_with_accounts.
       - Using the triggers, queries are faster on the customers and accounts tables, while can still insert to 
       all three by inserting to customers_with_accounts
    */
    
}