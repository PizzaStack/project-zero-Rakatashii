package com.BankApp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import customers.Customer;
import customers.CustomerBuilder;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import employees.EmployeeBuilder;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;
import views.Registration;

import java.util.ArrayList;

public class BankAppTest
{	
	CustomerContainer customers;
	ArrayList<Customer> customerContainer;
	Customer customer1, customer2;
	
	EmployeeContainer<Employee> admins;
	ArrayList<Employee> adminContainer;
	Admin admin1, admin2;
	
	EmployeeContainer<Employee> employees;
	ArrayList<Employee> employeeContainer;
	Employee employee1, employee2;
	
	UnverifiedCustomerContainer<UnverifiedCustomer> unverified;
	ArrayList<UnverifiedCustomer> unverifiedContainer;
	UnverifiedCustomer unverifiedCustomer1, unverifiedCustomer2;
	
	boolean match1, match2, match3, match4, match5, match6, match7, match8;
	String name1, name2, name3, name4, name5, name6, name7, name8;
	
	@Before
	public void SetUp() {
    	customers = new CustomerContainer();
    	customerContainer = customers.getArrayList();
		unverified = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	unverifiedContainer = unverified.getArrayList();
    	employees = new EmployeeContainer<Employee>();
    	employeeContainer = employees.getArrayList();
    	admins = new EmployeeContainer<Employee>();
    	adminContainer = admins.getArrayList();

		Customer.passCustomerContainer(customers);
		UnverifiedCustomer.passUnverifiedContainer(unverified);
		Admin.passAdminContainer(admins);
		Employee.passEmployeeContainer(employees);
		
		customer1 = new CustomerBuilder()
				.withID(0)
				.withUsername("user")
				.withPassword("password")
				.withFirstName("firstname")
				.withLastName("lastname")
				.withTelephone("1111111111")
				.withEmail("newcustomer@test.com")
				.withIsCitizen(false)
				.withIsEmployed(true)
				.withEmployer("TestClass")
				.makeCustomer();
		unverifiedCustomer1 = new CustomerBuilder()
				.withID(0)
				.withFirstName("Mark")
				.withLastName("Sagger")
				.withTelephone("2342342345")
				.withEmail("Marks@gmail.com")
				.withIsCitizen(false)
				.withIsEmployed(false)
				.withEmployer(null)
				.makeUnverifiedCustomer();
    	employee1 = new EmployeeBuilder()
    			.withUsername("tompickle")
    			.withPassword("chuckycheese")
    			.withIsAdmin(false)
    			.makeEmployee();
    	admin1 = new EmployeeBuilder()
    			.withUsername("crazyhacker")
    			.withPassword("IWillHackYou")
    			.withIsAdmin(true)
    			.makeAdmin();
	}
	
	@Test
	public void checkPersonSubclassCountsDoNotInterfere() {
		assertTrue(employeeContainer.size() == employees.getSize());
		assertTrue(adminContainer.size() == admins.getSize());
		assertTrue(employeeContainer.size() > adminContainer.size());
		assertTrue(customerContainer.size() == customers.getSize());
		assertTrue(unverifiedContainer.size() == unverified.getSize());
	}
	
	@Test
	public void checkRegistrationValidationFunctions() {
    	Registration register = new Registration();

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
    	tele3 = "123 123 2392";
    	tele4 = "123-sdkj2392";
    	tele5 = "123456789";
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
    	assertTrue(match3);
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
	@Test
	public void TestJointAccounts() {
		customer1 = new Customer("user1", "password");
		customer2 = new CustomerBuilder()
				.withUsername("user2")
				.withPassword("password")
				.makeCustomer(customer1);
		customer1.makeNewAccounts();
		assertTrue(customer1.getSavingsAccount() != null && customer2.getCheckingAccount() != null);
		assertTrue(customer1.getSavingsAccount().getPairedAccount() == customer1.getCheckingAccount());
		assertTrue(customer1.getCheckingAccount().getPairedAccount() == customer1.getSavingsAccount());
	}
}





