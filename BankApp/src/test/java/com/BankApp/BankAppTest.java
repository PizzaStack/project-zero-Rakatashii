package com.BankApp;

import org.junit.Test;

import consoleOutput.Registration;

//import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.After;

import static org.junit.Assert.*;

import customers.Customer;
import customers.UnverifiedCustomer;
import customers.UnverifiedCustomerBuilder;
import employees.Admin;
import employees.Employee;
import people.Person;

import java.util.ArrayList;

public class BankAppTest
{
	private static ArrayList<Person<?>> people;
	private static Admin admin;
	private static Employee employee;
	private static UnverifiedCustomer unverifiedCustomer;
	private static Customer customer;
	
	@BeforeClass
	public static void setUp() {
		people = new ArrayList<Person<?>>();
		admin = new Admin ("Dr.", "Evil"); people.add(admin);
		employee = new Employee("Jake", "Dog"); people.add(employee);
		customer = new Customer("Lindsay", "Lohan"); people.add(customer);
		unverifiedCustomer = new UnverifiedCustomer("Harry", "Hacker"); people.add(unverifiedCustomer);
	}

	@Test
	public void checkPeopleNames() {
		System.out.println(people.size());
		assertTrue(people.size() == 4);
		for (Person<?> p : people) {
			String first = p.getFirstName();
			String last = p.getLastName();
			if (first == null || first.length() == 0 || first.length() > 15)
				fail();
			if (last == null || last.length() == 0 || last.length() > 15)
				fail();
		}
	}
	
	@Test
	public void checkPersonSubclassCountsDoNotInterfere() {
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
		
		// Check UnverifiedCustomerBuilder
		UnverifiedCustomer anotherUnverifiedCustomer = new UnverifiedCustomerBuilder()
				.withFirstName("George")
				.withLastName("Bush")
				.withTelephone("394-292-1923")
				.withEmail("president@war.gov")
				.withIsCitizen(true)
				.withIsEmployed(true)
				.withEmployer("SkullBones")
				.makeUnverifiedCustomer();
		
		assertEquals(anotherUnverifiedCustomer.getID(), 2);
		assertEquals(anotherUnverifiedCustomer.getCount(), 3);
		assertEquals(newCustomer.getCount(), 2);
	}
	
	@Test
	public void checkRegistrationValidationFunctions() {
    	boolean match1, match2, match3, match4, match5, match6, match7, match8;
    	
    	Registration register = new Registration();
    	
    	String name1, name2, name3, name4, name5, name6, name7, name8;
    	name1 = "Dave";
    	name2 = "B";
    	name3 = "B9";
    	name4 = "23498";
    	name5 = "qwertyuiopqwertyuiop";
    	name6 = "qwertyuiopqwertyuiopq";
    	name7 = "";
    	name8 = null;
    	match1 = register.validFirstName(name1);
    	match2 = register.validFirstName(name2);
    	match3 = register.validFirstName(name3);
    	match4 = register.validFirstName(name4);
    	match5 = register.validFirstName(name5);
    	match6 = register.validFirstName(name6);
    	match7 = register.validFirstName(name7);
    	match8 = register.validFirstName(name8);
    	assertTrue(match1);
    	assertTrue(match2);
    	assertFalse(match3);
    	assertFalse(match4);
    	assertTrue(match5);
    	assertFalse(match6);
    	assertFalse(match7);
    	assertFalse(match8);
    	
    	String tele1, tele2, tele3, tele4, tele5, tele6, tele7, tele8;
    	tele1 = "321-321-1234";
    	tele2 = "3213211234";
    	tele3 = "123-sdkj2392";
    	tele3 = "123 123 2392";
    	tele4 = "123456789";
    	tele5 = "123//123//1234";
    	tele6 = "12345678901";
    	tele7 = "";
    	tele8 = null;
    	match1 = register.validTelephone(tele1);
    	match2 = register.validTelephone(tele2);
    	match3 = register.validTelephone(tele3);
    	match4 = register.validTelephone(tele4);
    	match5 = register.validTelephone(tele5);
    	match6 = register.validTelephone(tele6);
    	match7 = register.validTelephone(tele7);
    	match8 = register.validTelephone(tele8);
    	assertTrue(match1);
    	assertTrue(match2);
    	assertFalse(match3);
    	assertFalse(match4);
    	assertFalse(match5);
    	assertFalse(match6);
    	assertFalse(match7);
    	assertFalse(match8);
    	
    	String email1, email2, email3, email4, email5, email6;
    	email1 = "hello.word@revature.com";
    	email2 = "danJones.what@valid.edu";
    	email3 = "danJones.?what@invalid.net";
    	email4 = "danJones.what@invalid.schoolers";
    	email5 = "";
    	email6 = null;
    	match1 = register.validEmail(email1);
    	match2 = register.validEmail(email2);
    	match3 = register.validEmail(email3);
    	match4 = register.validEmail(email4);
    	match5 = register.validEmail(email5);
    	match6 = register.validEmail(email6);
    	assertTrue(match1);
    	assertTrue(match2);
    	assertFalse(match3);
    	assertFalse(match4);
    	assertFalse(match5);
    	assertFalse(match6);

	}
}





