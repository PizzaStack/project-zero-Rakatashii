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
    	UnverifiedCustomer unverified1 = new UnverifiedCustomer("Larry", "Lorry");
    	people.add(unverified1);
    	UnverifiedCustomer unverified2 = new UnverifiedCustomer("Fred", "Saggin");
    	people.add(unverified2);
    	Customer cust1 = new Customer("Dave", "Lennin");
    	people.add(cust1);
    	Customer cust2 = new Customer("Jake", "Bone");
    	people.add(cust1);
    	Employee employee1 = new Employee("Mark", "Pale");
    	people.add(employee1);
    	Employee employee2 = new Employee("Sara", "Tera");
    	people.add(employee2);
    	Admin admin1 = new Admin("Don", "Quervo");
    	people.add(admin1);
    	Admin admin2 = new Admin("Jane", "Bora");
    	people.add(admin2);
		
    	
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
