package com.BankApp;

import java.util.ArrayList;

import consoleOutput.Registration;
import people.Person;
import customers.Customer;
import customers.UnverifiedCustomer;
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
    	
    	Registration newCustomerInfo = new Registration();
    	UnverifiedCustomer newUnverifiedCustomer = newCustomerInfo.beginForm();
    	System.out.println();
    	System.out.println("New Candidate Info: ");
    	newUnverifiedCustomer.getInfo();
    }
}