package com.BankApp;

import org.junit.Test;
//import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.After;

import static org.junit.Assert.*;

import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import people.Person;

import java.util.ArrayList;

public class AppTest /*extends TestClass*/
{
	private static ArrayList<Person> people;
	private static Employee employee;
	private static Admin admin;
	private static UnverifiedCustomer unverifiedCustomer;
	private static Customer customer;
	
	@BeforeClass
	public static void setUp() {
		people = new ArrayList<Person>();
		employee = new Employee("Jake", "Dog"); people.add(employee);
		admin = new Admin ("Dr.", "Evil"); people.add(admin);
		unverifiedCustomer = new UnverifiedCustomer("Harry", "Hacker"); people.add(unverifiedCustomer);
		customer = new Customer("Lindsay", "Lohan"); people.add(customer);
	}

	@Test
	public void checkPeopleNames() {
		System.out.println(people.size());
		assertTrue(people.size() == 4);
		for (Person p : people) {
			String first = p.getFirstName();
			String last = p.getLastName();
			if (first == null || first.length() == 0 || first.length() > 15)
				fail();
			if (last == null || last.length() == 0 || last.length() > 15)
				fail();
		}
	}
	
	@Test
	public void checkClassSizes() {
		
	}
}





