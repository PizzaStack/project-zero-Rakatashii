package com.BankApp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import controller.RegistrationController;
import people.Person;
import views.Registration;
import customers.Customer;
import customers.UnverifiedCustomer;
import customers.UnverifiedCustomerBuilder;
import data.UnverifiedCustomerData;
import employees.Admin;
import employees.Employee;
import inspection.Helpers;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	// Sample data - don't erase for now..
    	ArrayList<Person<?>> people = new ArrayList<Person<?>>();
    	people.add(new UnverifiedCustomer("Larry", "Lorry"));
    	people.add(new UnverifiedCustomer("Fred", "Saggin"));
    	people.add(new Customer("Dave", "Lennin"));
    	people.add(new Customer("Jake", "Bone"));
    	people.add(new Employee("Mark", "Pale"));
    	people.add(new Employee("Sara", "Tera"));
    	people.add(new Admin("Don", "Quervo"));
    	people.add(new Admin("Jane", "Bora"));
    	
    	//RegistrationController.call();
    	
    	ArrayList<UnverifiedCustomer> unverified = UnverifiedCustomerData.getArrayList();
    	//UnverifiedCustomerData.printAll();
    	
		UnverifiedCustomer anotherUnverified = new UnverifiedCustomerBuilder()
				.withUnverifiedCustomerID(UnverifiedCustomer.getNumUnverifiedCustomers())
				.withFirstName("Mark")
				.withLastName("b")
				.withTelephone("2342342345")
				.withEmail("Markg@gmail.com")
				.withIsCitizen(false)
				.withIsEmployed(false)
				.withEmployer(null)
				.makeUnverifiedCustomer();

    	System.out.println();
    	//Helpers.printPeopleCounts();
    	//System.out.println();
    	
    	//File sampleUnverified = new File(project_dir + "text_files/sample_unverified.txt");
    	unverified = UnverifiedCustomerData.getArrayListFromSample();
    	UnverifiedCustomerData.printAll();
    	
    	//UnverifiedCustomerData.readIn(new File(UnverifiedCustomerData.getSampleFileName()));
    	//^ Do not do this manually! Already set #getArrayListFromSample to readin the sample so that I don't have to worry about it anymore.
    	
    	String unverifiedCustomersTextTable = project_dir + "text_files/unverified_customers_text_file_table.txt";
    	UnverifiedCustomerData.setTextFileName(unverifiedCustomersTextTable);
    	String unverifiedCustomersBinaryTable = project_dir + "text_files/unverified_customers_binary_file_table.bin";
    	UnverifiedCustomerData.setBinaryFileName(unverifiedCustomersBinaryTable);
    	UnverifiedCustomerData.writeToTextFile(true, true);
    	System.out.println();
    	System.out.println("done");
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