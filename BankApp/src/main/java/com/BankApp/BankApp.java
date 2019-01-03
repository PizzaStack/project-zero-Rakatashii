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
	/* LOGIN INFO:
	 *  Customer  username = "Guy.Fieri",    password = "password"
	 *  Customer  usernmae = "Guy.Harvey",   password = "password"
    	Employee  username = "pokemonfreak", password = "charmanderbulbasaur"
    	Admin     username = "rakatashii",   password = "password"
	*/
    public static void main( String[] args ) throws SQLException, FileNotFoundException, ClassNotFoundException, InterruptedException {
    	PropertyConfigurator.configure(System.getProperty("user.dir") + File.separator +
    			"\\src\\main\\java\\resources\\log4j.properties");
    	final Logger log = Logger.getLogger(BankApp.class);
    	log.debug("Program Started. ");
    	
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
    	
    	setup.finishDBSetup();
    	setup = null;
    	System.gc();
    	
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
    	System.out.println("Program Terminated.");
    	log.debug("Program Terminated.");
    }
}