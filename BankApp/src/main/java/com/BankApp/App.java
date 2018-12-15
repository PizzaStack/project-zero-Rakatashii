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
    	
    	//Helpers.printPeopleCounts();
    	
    	/*
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
    	*/
    	System.out.println();
    	/*
    	String pattern;
    	String name1, name2, name3, name4;
    	boolean match1, match2, match3, match4;
    	
    	System.out.println("NAME");
    	pattern = "[a-zA-Z]{0,20}";
    	name1 = "Dave";
    	name2 = "B";
    	name3 = "B9";
    	name4 = "23498";
    	match1 = name1.matches(pattern);
    	match2 = name2.matches(pattern);
    	match3 = name3.matches(pattern);
    	match4 = name4.matches(pattern);
    	System.out.println("match1 = " + match1);
    	System.out.println("match2 = " + match2);
    	System.out.println("match3 = " + match3);
    	System.out.println("match4 = " + match4);
    	System.out.println();
    	
    	System.out.println("TELEPHONE");
    	pattern = "[\\d]{3}-?[\\d]{3}-?[\\d]{4}";
    	String tele1 = "321-321-1234";
    	String tele2 = "3213211234";
    	String tele3 = "123-sdkj2392";
    	String tele4 = "123456789";
    	match1 = tele1.matches(pattern);
    	match2 = tele2.matches(pattern);
    	match3 = tele3.matches(pattern);
    	match4 = tele4.matches(pattern);
    	System.out.println("match1 = " + match1);
    	System.out.println("match2 = " + match2);
    	System.out.println("match3 = " + match3);
    	System.out.println("match4 = " + match4);
    	System.out.println();
    	
    	System.out.println("EMAIL");
    	pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    	String email1 = "hello.word@revature.com";
    	String email2 = "danJones.what@valid.edu";
    	String email3 = "danJones.?what@invalid.net";
    	String email4 = "danJones.what@invalid.schoolers";
    	match1 = email1.matches(pattern);
    	match2 = email2.matches(pattern);
    	match3 = email3.matches(pattern);
    	match4 = email4.matches(pattern);
    	System.out.println("match1 = " + match1);
    	System.out.println("match2 = " + match2);
    	System.out.println("match3 = " + match3);
    	System.out.println("match4 = " + match4);
    	System.out.println();
    	*/
    	
    	Registration newCustomerInfo = new Registration();
    	newCustomerInfo.beginForm();
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