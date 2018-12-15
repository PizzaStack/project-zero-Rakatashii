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

public class BankAppTest
{
	private static ArrayList<Person> people;
	private static Admin admin;
	private static Employee employee;
	private static UnverifiedCustomer unverifiedCustomer;
	private static Customer customer;
	
	@BeforeClass
	public static void setUp() {
		people = new ArrayList<Person>();
		admin = new Admin ("Dr.", "Evil"); people.add(admin);
		employee = new Employee("Jake", "Dog"); people.add(employee);
		customer = new Customer("Lindsay", "Lohan"); people.add(customer);
		unverifiedCustomer = new UnverifiedCustomer("Harry", "Hacker"); people.add(unverifiedCustomer);
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
		/** based on order of instantiations in .setUp test meth
		all Person IDs should begin indexing at 0
		employees. classes differ from customers. classes. because
		an Admin is an Employee but an UnverifiedCustomer is not 
		truly a Customer */
		
		assertEquals(admin.getID(), 0);
		assertEquals(admin.getCount(), 1);
		assertEquals(employee.getID(), 1);
		assertEquals(employee.getCount(), 2);
		
		assertEquals(customer.getID(), 0);
		assertEquals(customer.getCount(), 1);
		assertEquals(unverifiedCustomer.getID(), 0);
		assertEquals(unverifiedCustomer.getCount(), 1);
		
		/* Counts should still be correct for more than one of each type of Person object" */
		Admin newAdmin = new Admin ("Dr.", "Evil");
		Employee newEmployee = new Employee("Jake", "Dog");  
		Customer newCustomer = new Customer("Lindsay", "Lohan"); 
		UnverifiedCustomer newUnverifiedCustomer = new UnverifiedCustomer("Harry", "Hacker");
		
		assertEquals(newAdmin.getID(), 1);
		assertEquals(newAdmin.getCount(), 2);
		assertEquals(newEmployee.getID(), 3);
		assertEquals(newEmployee.getCount(), 4);
		
		assertEquals(newCustomer.getID(), 1);
		assertEquals(newCustomer.getCount(), 2);
		assertEquals(newUnverifiedCustomer.getID(), 1);
		assertEquals(newUnverifiedCustomer.getCount(), 2);
	}
}





