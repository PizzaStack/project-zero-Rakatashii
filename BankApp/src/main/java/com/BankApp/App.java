package com.BankApp;

import java.util.ArrayList;

import people.Person;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	ArrayList<Person> people = new ArrayList<Person>();
    	people.add(new UnverifiedCustomer("Larry", "Lorry"));
    	people.add(new UnverifiedCustomer("Fred", "Saggin"));
    	people.add(new Customer("Dave", "Lennin"));
    	people.add(new Customer("Jake", "Bone"));
    	people.add(new Employee("Mark", "Pale"));
    	people.add(new Employee("Sara", "Tera"));
    	Admin admin1 = new Admin("Don", "Quervo");
    	people.add(admin1);
    	people.add(new Admin("Jane", "Bora"));
    	
    	System.out.println("Person Count: " + Person.numPeople + "\n");
    	String thisType = null, lastType = null;
    	for (Person p : people) {
    		thisType = p.getType();
    		
    		if (!(thisType.equals(lastType))) {
    			System.out.println(p.getType() + " Count: " + p.getCount());
    			System.out.println("  * " + p.getFirstName() + ' ' + p.getLastName());
    		} else {
    			System.out.println("  * " + p.getFirstName() + ' ' + p.getLastName());
    			System.out.println();
    		}
    		lastType = thisType;
    	}
    	
    	
    }
}

/*
UnverifiedCustomer unverified1 = new UnverifiedCustomer("Larry", "Lorry");
UnverifiedCustomer unverified2 = new UnverifiedCustomer("Fred", "Saggin");
Customer cust1 = new Customer("Dave", "Lennin");
Customer cust2 = new Customer("Jake", "Bone");
Employee employee1 = new Employee("Mark", "Pale");
Employee employee2 = new Employee("Sara", "Tera");
Admin admin1 = new Admin("Don", "Quervo");
Admin admin2 = new Admin("Jane", "Bora");
*/