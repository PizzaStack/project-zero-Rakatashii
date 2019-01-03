package com.BankApp;

import org.junit.Before;
import org.junit.Test;

import controller.LoginController;

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
	CustomerContainer customerContainer;
	ArrayList<Customer> customers;
	Customer customer1, customer2;
	
	EmployeeContainer<Employee> adminContainer;
	ArrayList<Employee> admins;
	Admin admin1, admin2;
	
	EmployeeContainer<Employee> employeeContainer;
	ArrayList<Employee> employees;
	Employee employee1, employee2;
	
	UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer;
	ArrayList<UnverifiedCustomer> unverifiedCustomers;
	UnverifiedCustomer unverifiedCustomer1, unverifiedCustomer2;
	
	boolean match1, match2, match3, match4, match5, match6, match7, match8;
	String name1, name2, name3, name4, name5, name6, name7, name8;
	
	@Before
	public void SetUp() {
    	customerContainer = new CustomerContainer();
    	customers = customerContainer.getArrayList();
		unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	unverifiedCustomers = unverifiedContainer.getArrayList();
    	employeeContainer = new EmployeeContainer<Employee>();
    	employees = employeeContainer.getArrayList();
    	adminContainer = new EmployeeContainer<Employee>();
    	admins = adminContainer.getArrayList();

		Customer.passCustomerContainer(customerContainer);
		UnverifiedCustomer.passUnverifiedContainer(unverifiedContainer);
		Admin.passAdminContainer(adminContainer);
		Employee.passEmployeeContainer(employeeContainer);
		
		customer1 = new CustomerBuilder()
				.withID(0)
				.withUsername("customer")
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
				.withFirstName("unverified")
				.withLastName("customer")
				.withTelephone("2342342345")
				.withEmail("unverified@customer.com")
				.withIsCitizen(false)
				.withIsEmployed(false)
				.withEmployer(null)
				.makeUnverifiedCustomer();
    	employee1 = new EmployeeBuilder()
    			.withUsername("employee")
    			.withPassword("password")
    			.withIsAdmin(false)
    			.makeEmployee();
    	admin1 = new EmployeeBuilder()
    			.withUsername("admin")
    			.withPassword("password")
    			.withIsAdmin(true)
    			.makeAdmin();
	}
	
	@Test
	public void checkPersonSubclassCountsDoNotInterfere() {
		assertTrue(employeeContainer.getSize() > 0);
		assertTrue(employeeContainer.getSize() == employees.size());
		assertTrue(adminContainer.getSize() > 0);
		assertTrue(adminContainer.getSize() == admins.size());
		assertTrue(employeeContainer.getSize() > adminContainer.getSize());
		assertTrue(customerContainer.getSize() > 0);
		assertTrue(customerContainer.getSize() == customers.size());
		assertTrue(unverifiedContainer.getSize() > 0);
		assertTrue(unverifiedContainer.getSize() == unverifiedCustomers.size());
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
		customer2 = new CustomerBuilder()
				.withID(0)
				.withUsername("user2")
				.withPassword("password2")
				.withFirstName("firstname2")
				.withLastName("lastname2")
				.withTelephone("1111111111")
				.withEmail("newcustomer2@test.com")
				.withIsCitizen(false)
				.withIsEmployed(true)
				.withEmployer("TestClass2")
				.makeCustomer();
		
		customer1.setJointCustomer(customer2);		

		assertTrue(customer1.getSavingsAccount() != null && customer2.getCheckingAccount() != null);
		assertTrue(customer1.getSavingsAccount().getPairedAccount() == customer1.getCheckingAccount());
		assertTrue(customer1.getCheckingAccount().getPairedAccount() == customer1.getSavingsAccount());
		assertTrue(customer1.getID() == customer2.getJointCustomerID());
		
		assertTrue(customer1.getSavingsAccount().getBalance() == 0.0);
		assertTrue(customer1.getCheckingAccount().getBalance() == 0.0);
		customer1.getSavingsAccount().deposit(100);
		assertTrue(customer1.getSavingsAccount().getBalance() == customer2.getSavingsAccount().getBalance() 
				&& customer1.getSavingsAccount().getBalance() == 100);
		
		customer2.getCheckingAccount().deposit(100);
		assertTrue(customer1.getCheckingAccount().getBalance() == customer2.getCheckingAccount().getBalance() 
				&& customer1.getCheckingAccount().getBalance() == 100);
		
		assertTrue(customer1.getSavingsAccount().getID() == customer2.getSavingsAccount().getID());
		assertTrue(customer1.getCheckingAccount().getID() == customer2.getCheckingAccount().getID());
		
		customer1.flag();
		assertTrue(customer1.isFlagged() == customer2.isFlagged());
		customer2.unflag();
		assertTrue(customer1.isFlagged() == customer2.isFlagged());
	}
	
	@Test
	public void TestAccountTransfers() {
		assertTrue(customer1.hasSavingsAccount());
		assertTrue(customer1.hasCheckingAccount());
		assertTrue(customer1.getSavingsAccount().getBalance() == 0.0);
		assertTrue(customer1.getCheckingAccount().getBalance() == 0.0);
		
		assertTrue(customer1.getSavingsAccount().getOwner() == customer1);
		assertTrue(customer1.getCheckingAccount().getOwner() == customer1);
		
		customer1.flag();
		assertTrue(customer1.isFlagged());
		assertTrue(customer1.getSavingsAccount().isFlagged());
		assertTrue(customer1.getCheckingAccount().isFlagged());
		customer1.unflag();
		assertFalse(customer1.isFlagged());
		assertFalse(customer1.getSavingsAccount().isFlagged());
		assertFalse(customer1.getCheckingAccount().isFlagged());
		
		customer1.getSavingsAccount().deposit(-1.0);
		assertTrue(customer1.isFlagged());
		customer1.unflag();
		assertFalse(customer1.isFlagged());
		assertTrue(customer1.getSavingsAccount().getBalance() == 0.0);
		customer1.getCheckingAccount().deposit(-1);
		assertTrue(customer1.isFlagged());
		customer1.unflag();
		
		customer1.getSavingsAccount().withdraw(1.0);
		assertTrue(customer1.isFlagged());
		assertTrue(customer1.getSavingsAccount().getBalance() == 0.0);
		customer1.unflag();
		customer1.getCheckingAccount().withdraw(1.0);
		assertTrue(customer1.isFlagged());
		assertTrue(customer1.getCheckingAccount().getBalance() == 0.0);
		customer1.unflag();
		
		customer1.getSavingsAccount().deposit(1000.0);
		customer1.getSavingsAccount().transferToChecking(500.0);
		assertTrue(customer1.getSavingsAccount().getBalance() == customer1.getCheckingAccount().getBalance());
		assertFalse(customer1.isFlagged());
		
		String savingsID = customer1.getSavingsAccount().getID();
		String checkingID = customer1.getCheckingAccount().getID();
		customer1.makeNewAccounts();
		assertFalse(customer1.getSavingsAccount().getID() == savingsID);
		assertFalse(customer1.getCheckingAccount().getID() == checkingID);
		assertTrue(customer1.getSavingsAccount().getBalance() == customer1.getCheckingAccount().getBalance() 
				&& customer1.getSavingsAccount().getBalance() == 0.0);
	}
	
	@Test 
	public void testLogins() throws InterruptedException {
		LoginController login = new LoginController();
		assertTrue(login.loginAsCustomer(customerContainer, "customer", "password") == true);
		assertTrue(LoginController.getLoggedInCustomer().getCustomerID() == customer1.getCustomerID());
		
		login = new LoginController();
		assertTrue(login.loginAsEmployee(employeeContainer, "employee", "password") == true);
		assertTrue(LoginController.getLoggedInEmployee().getEmployeeID() == employee1.getEmployeeID());
		
		login = new LoginController();
		assertTrue(login.loginAsAdmin(adminContainer, "admin", "password") == true);
		assertTrue(LoginController.getLoggedInAdmin().getAdminID() == admin1.getAdminID());
		
		assertTrue(LoginController.getLoggedInUsername() == "admin");
		assertTrue(LoginController.isLoggedIn() == true);
		LoginController.logout();
		assertTrue(LoginController.getLoggedInAdmin() == null);
		assertTrue(LoginController.getLoggedInUsername() == null);
		assertTrue(LoginController.isLoggedIn() == false);
	}
}





