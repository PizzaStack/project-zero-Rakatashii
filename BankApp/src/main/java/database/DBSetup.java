package database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import DAO.AccountDAO;
import DAO.AdminDAO;
import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import DAO.UnverifiedCustomerDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import model.AccountContainer;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;

public class DBSetup {
	private static Schemas schemas = new Schemas();
	
	public void initializeSampleTables() throws ClassNotFoundException {
  		//final String dir = "/c/Users/Associate/java/project-zero-Rakatashii";
 
    	System.out.println("SAMPLE SCHEMAS:");
    	schemas.createSampleTables();
 
    	AccountContainer accountContainer = new AccountContainer();
		CustomerContainer customerContainer = new CustomerContainer();
		UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers sampleContainers = new Containers();
    	sampleContainers.setCustomerContainer(customerContainer);
    	sampleContainers.setUnverifiedContainer(unverifiedContainer);
    	sampleContainers.setEmployeeContainer(employeeContainer);
    	sampleContainers.setAdminContainer(adminContainer);
    	
    	UnverifiedCustomer.passUnverifiedContainer(sampleContainers.getUnverifiedContainer());
    	Customer.passCustomerContainer(sampleContainers.getCustomerContainer());
    	Employee.passEmployeeContainer(sampleContainers.getEmployeeContainer());
    	Admin.passAdminContainer(sampleContainers.getAdminContainer());
    	
    	DBUtil dbUtil = new DBUtil();
    	
    	CustomerDAO customerDAO = new CustomerDAO();
    	AccountDAO accountDAO = new AccountDAO();
    	int numSampleCustomersInDB = customerDAO.getNumSampleCustomers();
    	int numSampleAccountsInDB = accountDAO.getNumSampleAccounts();
    	
    	if (dbUtil.tableExists("sample_customers") == false || (numSampleCustomersInDB <= 1) || dbUtil.tableExists("sample_accounts") == false || (numSampleAccountsInDB <= 1)) {
        	
        	accountContainer.readIn(new File("text_files/account_sample.txt"));
        	ArrayList<CheckingAccount> checkingAccounts = accountContainer.getCheckingAccounts();
        	ArrayList<SavingsAccount> savingsAccounts = accountContainer.getSavingsAccounts();
        	System.out.println("i == 0");
        	for (int i = 0; i < accountContainer.getSize(); i++) {
        		CheckingAccount c = checkingAccounts.get(i);
        		SavingsAccount s = savingsAccounts.get(i);
        	}
    		
    		try {
    			customerContainer.readIn(new File("text_files/customer_sample.txt"));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	customerContainer.setTextFileName("text_files/formatted_customer_sample.txt");
        	try {
    			customerContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        	int i = 0;
        	ArrayList<Customer> customers = customerContainer.getArrayList();
        	for (Customer c : customers) {
        		
        		if ((customers.size() == checkingAccounts.size()) && (customers.size() == savingsAccounts.size())) {
        			if (i == 0) System.out.println("Customer and Account Containers are the same size as expected...");
        			SavingsAccount savings = savingsAccounts.get(i);
        			CheckingAccount checking = checkingAccounts.get(i);
        			
        			c.setSavingsAccount(savings);
        			c.setCheckingAccount(checking);
        			c.getSavingsAccount().setPairedAccount(c.getCheckingAccount());
        			c.getCheckingAccount().setPairedAccount(c.getSavingsAccount());
        			
        			accountDAO.addSampleAccounts(savings, checking);
        		} else {
        			System.out.println("Customer and Account Containers are not the same size!");
        		} ++i;
        		
        		customerDAO.addSampleCustomer(c);
        		c.printRowWithAccountInfo();
        		 		
        		//TODO make join table for customers and accounts
        	} 
        	numSampleCustomersInDB = customerDAO.getNumSampleCustomers();
        	numSampleAccountsInDB = accountDAO.getNumSampleAccounts();
        	System.out.println();
        	System.out.println("updated sample_customers table...");
    		System.out.println("sample_customers count = " + numSampleCustomersInDB);
    		//System.out.println("accountContainer (savings) size = " + accountContainer.getSavingsAccounts().size());
    		//System.out.println("accountContainer (checking) size = " + accountContainer.getCheckingAccounts().size());
    		System.out.println("updated sample_accounts table...");
    		System.out.println("sample_accounts count = " + numSampleAccountsInDB);
    	} else {
    		System.out.println();
    		System.out.println("sample_customers table is not empty (OR)");
    		System.out.println("sample_customers count = " + numSampleCustomersInDB);
    		System.out.println("sample_accounts table is not empty");
    		System.out.println("sample_accounts count = " + numSampleAccountsInDB);
    	}
    	
    	UnverifiedCustomerDAO unverifiedCustomerDAO = new UnverifiedCustomerDAO();
    	int numSampleUnverifiedCustomersInDB = unverifiedCustomerDAO.getNumSampleUnverifiedCustomers();
    	
    	if (dbUtil.tableExists("sample_unverified_customers") == false || numSampleUnverifiedCustomersInDB <= 1) {
        	try {
    			unverifiedContainer.readIn(new File("text_files/unverified_customer_sample.txt"));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	unverifiedContainer.setTextFileName("text_files/formatted_unverified_customer_sample.txt");
        	try {
    			unverifiedContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        	ArrayList<UnverifiedCustomer> unverifiedCustomers = unverifiedContainer.getArrayList();
        	for (UnverifiedCustomer c : unverifiedCustomers) {
        		c.printRow();
        		unverifiedCustomerDAO.addSampleUnverifiedCustomer(c);
        	}
        	
        	System.out.println();
    		System.out.println("updated sample_unverified_customers...");
    		System.out.println("sample_unverified_customers count = " + numSampleUnverifiedCustomersInDB);
    	} else {
    		System.out.println();
    		System.out.println("sample_unverified_customers table is not empty");
    		System.out.println("sample_unverified_customers count = " + numSampleUnverifiedCustomersInDB);
    	}
    	    	
    	EmployeeDAO employeeDAO = new EmployeeDAO();
    	int numSampleEmployeesInDB = employeeDAO.getNumSampleEmployees();
    	AdminDAO adminDAO = new AdminDAO();
    	int numSampleAdminsInDB = adminDAO.getNumSampleAdmins();
    	
    	
    	if (dbUtil.tableExists("sample_employees") == false || numSampleEmployeesInDB <= 1 || dbUtil.tableExists("sample_admins") == false || numSampleAdminsInDB <= 1) {
        	try {
    			employeeContainer.readIn(new File("text_files/employee_sample.txt"));
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	employeeContainer.setTextFileName("text_files/formatted_employee_sample.txt");
        	try {
    			employeeContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        	ArrayList<Employee> employees = employeeContainer.getArrayList();
        	ArrayList<Employee> admins = adminContainer.getArrayList();
        	for (Employee e : employees) {
        		e.printRow();
        		employeeDAO.addSampleEmployee(e);
        	}
        	for (Employee a : admins) {
        		a.printRow();
        		adminDAO.addSampleAdmin(a);
        	}
        	
        	System.out.println();
    		System.out.println("updated sample_employees table...");
    		System.out.println("sample_employees count = " + numSampleEmployeesInDB);
    		System.out.println("updated sample_admins table...");
    		System.out.println("sample_admins count = " + numSampleAdminsInDB);
    	} else {
    		System.out.println();
    		System.out.println("sample_employees table is not empty (OR)");
    		System.out.println("sample_employees count = " + numSampleEmployeesInDB);
    		System.out.println("sample_admins table is not empty");
    		System.out.println("sample_admins count = " + numSampleAdminsInDB);
    	}
    	    	    	
    	System.out.println();
    	System.out.println("Container Sizes (should be empty unless tables were updated):");
    	sampleContainers.printContainerSizes();
	}
	
	private Containers containers = null;
	
	public void initializeActualTables() throws ClassNotFoundException {
		DBUtil util = new DBUtil();
		System.out.println("ACTUAL SCHEMAS");
		schemas.createActualTables();
		containers.printContainerSizes();
		
		System.out.println("\nActual Table Sizes:");
		util.printActualTableSizes();
		System.out.println("\nSample Table Sizes:");
		util.printSampleTableSizes();
	}
	
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
}
