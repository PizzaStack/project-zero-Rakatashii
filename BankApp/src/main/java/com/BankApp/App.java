package com.BankApp;

import java.io.File;
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
    public static void main( String[] args )
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
    	
    	for (int i = 0; i < unverified.size(); i++) {
    		unverified.get(i).getInfo();
    	}
    	
    	System.out.println();
    	Helpers.printPeopleCounts();
    	
    	File sampleUnverified = new File(project_dir + "text_files/sample_unverified.txt");
    	UnverifiedCustomerData.readIn(sampleUnverified);
    
    	unverified = UnverifiedCustomerData.getArrayList();
    	UnverifiedCustomerData.printAll();
    	/*
    	for (int i = 0; i < unverified.size(); i++) {
    		unverified.get(i).getInfo();
    	}
    	*/
    }
    
    
    
    final static String project_dir = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/";
}