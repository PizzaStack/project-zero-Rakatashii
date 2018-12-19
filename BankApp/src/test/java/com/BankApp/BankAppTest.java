package com.BankApp;

import org.junit.Test;

import static org.junit.Assert.*;

import customers.Customer;
import customers.UnverifiedCustomer;
import customers.UnverifiedCustomerBuilder;
import data.EmployeeContainer;
import data.UnverifiedCustomerContainer;
import employees.Admin;
import employees.Employee;
import employees.EmployeeBuilder;
import views.Registration;

import java.util.ArrayList;

public class BankAppTest
{	
	EmployeeContainer<Employee> admins;
	ArrayList<Employee> adminContainer;
	Admin admin1, admin2;
	
	EmployeeContainer<Employee> employees;
	ArrayList<Employee> employeeContainer;
	Employee employee1, employee2;
	
	UnverifiedCustomerContainer<UnverifiedCustomer> unverified;
	ArrayList<UnverifiedCustomer> unverifiedContainer;
	UnverifiedCustomer unverifiedCustomer1, unverifiedCustomer2;
	
	//TODO include Customer Container and add to test.
	Customer customer1, customer2;
	
	//@BeforeClas
	/* public void setUp() { Apparently @BeforeClass -> setUp must be static... @Before prob worse } */
	// opting to redefine in each @test..
	
	@Test
	public void checkPersonSubclassCountsDoNotInterfere() {
		unverified = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	unverifiedContainer = unverified.getArrayList();
    	employees = new EmployeeContainer<Employee>();
    	employeeContainer = employees.getArrayList();
    	
		customer1 = new Customer("Lindsay", "Lohan"); 
		unverifiedCustomer1 = new UnverifiedCustomer("Harry", "Hacker"); 
		unverifiedCustomer2 = new UnverifiedCustomerBuilder()
				.withFirstName("Mark")
				.withLastName("b")
				.withTelephone("2342342345")
				.withEmail("Markg@gmail.com")
				.withIsCitizen(false)
				.withIsEmployed(false)
				.withEmployer(null)
				.makeUnverifiedCustomer();

		employee1 = new Employee("Jake", "Dog"); //people.add(employee);
    	Employee employee2 = new EmployeeBuilder()
    			.withUsername("crazyhacker")
    			.withPassword("Illhacku")
    			.withIsAdmin(false)
    			.makeEmployee();
    	admin1 = new Admin ("Dr.", "Evil"); //people.add(admin);
    	Admin admin2 = new EmployeeBuilder()
    			.withUsername("crazyhacker")
    			.withPassword("Illhacku")
    			.withIsAdmin(true)
    			.makeAdmin();
		
		unverifiedContainer.add(unverifiedCustomer1);
		unverifiedContainer.add(unverifiedCustomer2);
		
		employeeContainer.add(employee1); 
		employeeContainer.add(employee2);
		employeeContainer.add(admin1); 
		employeeContainer.add(admin2);
		
		admins = employees.getAdminArrayList();
    	adminContainer = admins.getArrayList();

		assertTrue(employeeContainer.size() == employees.getSize() && employeeContainer.size() == employeeContainer.get(0).getCount());
		assertTrue(unverifiedContainer.size() == unverifiedContainer.get(0).getCount() && unverifiedContainer.size() == unverified.getSize());
		assertTrue(adminContainer.size() == adminContainer.get(0).getCount() && adminContainer.size() == admins.getSize());
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





