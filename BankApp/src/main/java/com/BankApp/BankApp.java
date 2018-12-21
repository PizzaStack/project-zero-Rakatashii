package com.BankApp;

import java.io.IOException;
import java.util.*;

import controller.MainMenuController;
import controller.MainMenuController.Menus;
import controller.CustomerController.CustomerMenus;
import controller.EmployeeController.EmployeeMenus;
import controller.RegistrationController;
import people.Person;
import utility.Helpers;
import customers.Customer;
import customers.UnverifiedCustomer;
import customers.CustomerBuilder;
import employees.Admin;
import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;

public class BankApp 
{
    public static void main( String[] args ) throws IOException, InterruptedException {

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
    	
    	UnverifiedCustomer u1 = new UnverifiedCustomer("unverified", "customer");
    	UnverifiedCustomer u2 = new UnverifiedCustomer("redlion", "saggin5");
    	
    	Customer c1 = new Customer("customer", "password");
    	Customer c2 = new Customer("heytherer23", "qwertyu789");
    	
    	Employee e1 = new Employee("employee", "password");
    	Employee e2 = new Employee("Tom", "Kats");
    	
    	Admin a1 = new Admin("admin", "password");
    	Admin a2 = new Admin("Jay", "Kool");
    	Admin a3 = new Admin("Glen", "Baxter");
    	Admin a4 = new Admin("Hell", "Boy");
    	
    	MainMenuController mainController = new MainMenuController();
    	mainController.passContainers(containers);
    	mainController.begin(Menus.DEFAULT);
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