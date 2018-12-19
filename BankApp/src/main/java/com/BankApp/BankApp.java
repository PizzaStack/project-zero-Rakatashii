package com.BankApp;

import java.io.IOException;
import java.util.*;

import controller.MainMenuController;
import controller.RegistrationController;
import people.Person;
import views.MenuOptions;
import customers.Customer;
import customers.UnverifiedCustomer;
import customers.CustomerBuilder;
import data.CustomerContainer;
import data.EmployeeContainer;
import data.UnverifiedCustomerContainer;
import employees.Admin;
import employees.Employee;
import inspection.Helpers;

/**
 * Hello world!
 *
 */
public class BankApp 
{
    public static void main( String[] args ) throws IOException {
    	
		UnverifiedCustomerContainer<UnverifiedCustomer> unverified = new UnverifiedCustomerContainer<UnverifiedCustomer>();
		ArrayList<UnverifiedCustomer> unverifiedContainer = unverified.getArrayList();
    	unverified.push(new UnverifiedCustomer("Larry", "Lorry"));
    	unverified.push(new UnverifiedCustomer("Fred", "Saggin"));
    	int idx = 0;
    	System.out.println("UNVERIFIED");
    	unverifiedContainer.get(0).printColumnNames();
    	for (UnverifiedCustomer uc : unverifiedContainer) {
    		System.out.println("unverifiedContainer[" + (idx++) +"]: ");
    		uc.printRow(); 
    	}
    	System.out.println();
    	System.out.println("UnverifiedCustomer Sizes");
    	System.out.println("unverifiedContainer.size() = " + unverifiedContainer.size());
    	System.out.println("unverified.getSize() = " + unverified.getSize());
    	System.out.println("unverifiedContainer.get(0).getCount() = " + unverifiedContainer.get(0).getCount());
    	System.out.println();
    	
		CustomerContainer customers = new CustomerContainer();
		ArrayList<Customer> customerContainer = customers.getArrayList();
    	customers.push(new Customer("heytherer23", "qwertyu789"));
    	customers.push(new Customer("redlion23", "Saggin"));
    	idx = 0;
    	System.out.println("CUSTOMERS");
    	customerContainer.get(0).printColumnNames();
    	for (Customer c : customerContainer) {
    		System.out.println("customerContainer[" + (idx++) +"]: ");
    		c.printRow(); 
    	}
    	System.out.println();
    	System.out.println("Customer Sizes");
    	System.out.println("customerContainer.size() = " + customerContainer.size());
    	System.out.println("customers.getSize() = " + customers.getSize());
    	System.out.println("customersContainer.get(0).getCount() = " + customerContainer.get(0).getCount());
    	System.out.println();
    	
    	EmployeeContainer<Employee> employees = new EmployeeContainer<Employee>();
    	ArrayList<Employee> employeeContainer = employees.getArrayList();
    	employees.push(new Employee("Mark", "Pale"));
    	employeeContainer.add(new Employee("Sara", "Tera"));
    	employees.push(new Admin("Don", "Quervo"));
    	employeeContainer.add(new Admin("Jane", "Bora"));
    	idx = 0;
    	System.out.println("EMPLOYEES");
    	employeeContainer.get(0).printColumnNames();
    	for (Employee c : employeeContainer) {
    		System.out.println("employeesContainer[" + (idx++) + "]: ");
    		c.printRow();
    	}
    	System.out.println();
    	System.out.println("Employee Sizes");
    	System.out.println("employeeContainer.size() = " + employeeContainer.size());
    	System.out.println("employees.getSize() = " + employees.getSize());
    	System.out.println("employeeContainer.get(0).getCount() = " + employeeContainer.get(0).getCount());
    	System.out.println();
    	
    	EmployeeContainer<Employee> admins = employees.getAdminArrayList();
    	ArrayList<Employee> adminContainer = admins.getArrayList();
    	idx = 0;
    	System.out.println("ADMINS");
    	adminContainer.get(0).printColumnNames();
    	for (Employee a : adminContainer) {
    		System.out.println("adminsContainer[" + (idx++) + "]: ");
    		a.printRow();
    	}
    	System.out.println();
    	System.out.println("Admins Sizes");
    	System.out.println("adminContainer.size() = " + adminContainer.size());
    	System.out.println("admins.getSize() = " + admins.getSize());
    	System.out.println("adminContainer.get(0).getCount() = " + adminContainer.get(0).getCount());
    	System.out.println();
    	
    	Helpers helper = new Helpers(); 
    	helper.printPeopleCounts();
    	
    	
    	//MainMenuController mainController = new MainMenuController();
    	//mainController.begin();
    	
    	/*
		UnverifiedCustomer anotherUnverified = new UnverifiedCustomerBuilder()
				.withFirstName("Mark")
				.withLastName("b")
				.withTelephone("2342342345")
				.withEmail("Markg@gmail.com")
				.withIsCitizen(false)
				.withIsEmployed(false)
				.withEmployer(null)
				.makeUnverifiedCustomer();
		unverifiedContainer.add(anotherUnverified);
		unverified.printAll();
		System.out.println("Unverified Class = " + unverified.getType().getSimpleName());
		System.out.println("Number of Unverified Employees = " + unverified.getSize());
		*/
    	//Helpers.printPeopleCounts();
    	//System.out.println();
		/*
		boolean newMenu = true;
		while (newMenu) {
			newMenu = MenuOptions.display();
			if (!newMenu) {
				System.out.println("Program Terminated.");
				break;
			}
		}
		*/

    	//RegistrationController.call();
    	
    	/*
    	try {
    		MenuOptions.display();
    	} catch ( NullPointerException e ) {
    		throw e;
    	}
    	*/
    	
  
    	//File sampleUnverified = new File(project_dir + "text_files/sample_unverified.txt");
    	//unverified = UnverifiedCustomerData.getArrayListFromSample();
    	//UnverifiedCustomerData.printAll();
    	
    	//UnverifiedCustomerData.readIn(new File(UnverifiedCustomerData.getSampleFileName()));
    	//^ Do not do this manually! Already set #getArrayListFromSample to readin the sample so that I don't have to worry about it anymore.
    	
    	/*
    	String unverifiedCustomersTextTable = project_dir + "text_files/unverified_customers_text_file_table.txt";
    	UnverifiedCustomerData.setTextFileName(unverifiedCustomersTextTable);
    	String unverifiedCustomersBinaryTable = project_dir + "text_files/unverified_customers_binary_file_table.bin";
    	UnverifiedCustomerData.setBinaryFileName(unverifiedCustomersBinaryTable);
    	UnverifiedCustomerData.writeToTextFile(true, true);
    	*/
    	
    	/*
    	//UnverifiedCustomerData.writeToBinaryFile(unverifiedCustomersBinaryTable, true);
    	
		UnverifiedCustomer anotherUnverified2 = new UnverifiedCustomerBuilder()
				.withUnverifiedCustomerID(UnverifiedCustomer.getNumUnverifiedCustomers())
				.withFirstName("Jackie")
				.withLastName("Chan")
				.withTelephone("1233454567")
				.withEmail("N@ninja.com")
				.withIsCitizen(false)
				.withIsEmployed(true)
				.withEmployer("Hollywood")
				.makeUnverifiedCustomer();
		
		UnverifiedCustomerData.appendToTextFile(anotherUnverified2, true);
    	
    	System.out.println();
    	System.out.println("5th row:");
    	UnverifiedCustomerData.printNthRow(5);
    	
    	System.out.println();
    	UnverifiedCustomerData.printAll();
    	*/
    }
    
    
    
    final static String project_dir = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/";
    
}