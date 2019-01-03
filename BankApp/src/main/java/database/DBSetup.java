package database;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.BankApp.BankApp;

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
	FileOutputStream fos;
	PrintStream ps;
	boolean printToLog = false;
	
	CustomerDAO customerDAO = null;
	AccountDAO accountDAO = null;
	UnverifiedCustomerDAO unverifiedCustomerDAO = null;
	EmployeeDAO employeeDAO = null;
	AdminDAO adminDAO = null;
	
	final Logger log = Logger.getLogger(DBSetup.class);
	
	public DBSetup(boolean printToLog) {
		customerDAO = new CustomerDAO();
		accountDAO = new AccountDAO();
		unverifiedCustomerDAO = new UnverifiedCustomerDAO();
		employeeDAO = new EmployeeDAO();
		adminDAO = new AdminDAO();
		
		Customer.sampleModeOn();
		Employee.sampleModeOn();
		
		
		this.printToLog = printToLog;
		if (printToLog) {
			File log = new File("log_files/db_setup.txt");
			try {
	    		fos = new FileOutputStream(log, false);
	    		ps = new PrintStream(fos);

	    	} catch (FileNotFoundException e) {
	    		System.out.println("File could not be opened.");
	    	} catch (Exception e) {
	    		System.out.println("Some other error.");
	    	} 
			System.setOut(ps);
		}
	}
	public void finishDBSetup() {
		if (printToLog) {
			if (fos != null)
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			ps.flush();
			if (ps != null) ps.close();
			System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		}
	}
	
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
    	
    	int numSampleCustomersInDB = customerDAO.getNumCustomers(true);
    	int numSampleAccountsInDB = accountDAO.getNumAccounts(true);
    	
    	if (dbUtil.tableExists("sample_customers") == false || (numSampleCustomersInDB <= 1) || dbUtil.tableExists("sample_accounts") == false || (numSampleAccountsInDB <= 1)) {
        	
        	accountContainer.readIn(new File("text_files/account_sample.txt"));
        	ArrayList<CheckingAccount> checkingAccounts = accountContainer.getCheckingAccounts();
        	ArrayList<SavingsAccount> savingsAccounts = accountContainer.getSavingsAccounts();
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
        			
        			customerDAO.addCustomerWithAccount(c, true);
        		} else {
        			System.out.println("Customer and Account Containers are not the same size!");
        		} ++i;
        		
        		c.printRowWithAccountInfo();

        	} 
        	numSampleCustomersInDB = customerDAO.getNumCustomers(true);
        	numSampleAccountsInDB = accountDAO.getNumAccounts(true);
        	System.out.println();
        	System.out.println("updated sample_customers table...");
    		System.out.println("sample_customers count = " + numSampleCustomersInDB);
    		System.out.println("updated sample_accounts table...");
    		System.out.println("sample_accounts count = " + numSampleAccountsInDB);
    	} else {
    		numSampleCustomersInDB = customerDAO.getNumCustomers(true);
        	numSampleAccountsInDB = accountDAO.getNumAccounts(true);
    		System.out.println();
    		System.out.println("sample_customers table is not empty (OR)");
    		System.out.println("sample_customers count = " + numSampleCustomersInDB);
    		System.out.println("sample_accounts table is not empty");
    		System.out.println("sample_accounts count = " + numSampleAccountsInDB);
    	}
    	
    	int numSampleUnverifiedCustomersInDB = unverifiedCustomerDAO.getNumUnverifiedCustomers(true);
    	
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
        		unverifiedCustomerDAO.addUnverifiedCustomer(c, true);
        	}

        	numSampleUnverifiedCustomersInDB = unverifiedCustomerDAO.getNumUnverifiedCustomers(true);
        	System.out.println();
    		System.out.println("updated sample_unverified_customers...");
    		System.out.println("sample_unverified_customers count = " + numSampleUnverifiedCustomersInDB);
    	} else {
    		numSampleUnverifiedCustomersInDB = unverifiedCustomerDAO.getNumUnverifiedCustomers(true);
    		System.out.println();
    		System.out.println("sample_unverified_customers table is not empty");
    		System.out.println("sample_unverified_customers count = " + numSampleUnverifiedCustomersInDB);
    	}
    	    	
    	int numSampleEmployeesInDB = employeeDAO.getNumEmployees(true);
    	int numSampleAdminsInDB = adminDAO.getNumAdmins(true);
    	
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
        		employeeDAO.addEmployee(e, true);
        	}
        	for (Employee a : admins) {
        		a.printRow();
        		adminDAO.addAdmin(a, true);
        	}
        	
        	numSampleEmployeesInDB = employeeDAO.getNumEmployees(true);
        	numSampleAdminsInDB = adminDAO.getNumAdmins(true);
        	System.out.println();
    		System.out.println("updated sample_employees table...");
    		System.out.println("sample_employees count = " + numSampleEmployeesInDB);
    		System.out.println("updated sample_admins table...");
    		System.out.println("sample_admins count = " + numSampleAdminsInDB);
    	} else {
    		numSampleEmployeesInDB = employeeDAO.getNumEmployees(true);
        	numSampleAdminsInDB = adminDAO.getNumAdmins(true);
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
		
		Customer.sampleModeOff();
		Employee.sampleModeOff();
		Admin.sampleModeOff();
		
		Customer firstCustomer = new Customer(0, "customer", "password", "firstname", "lastname", 
    			"000-000-0000", "dontusethisdata@email.com", true, true, "employer", false, -1);
		customerDAO.addCustomerWithAccount(firstCustomer, false);
		firstCustomer.getSavingsAccount().deposit(10000.00);
		firstCustomer.getCheckingAccount().deposit(1500.00);
		customerDAO.updateCustomerAndAccounts(firstCustomer, false);
		
    	UnverifiedCustomer firstUnverified = new UnverifiedCustomer(0, "unverified", "customer", 
    			"000-000-0000", "dontusethisdata@email.com", true, true, "employed");
    	unverifiedCustomerDAO.addUnverifiedCustomer(firstUnverified, false);
    	
    	Employee firstEmployee = new Employee("employee", "password", false);
    	employeeDAO.addEmployee(firstEmployee, false);
    	
    	Admin firstAdmin = new Admin("admin", "password", true);
    	adminDAO.addAdmin(firstAdmin, false);
		
		System.out.println();
		containers.printContainerSizes();
		
		System.out.println();
		System.out.println("Actual Table Sizes:");
		util.printActualTableSizes();
		
		System.out.println();
		System.out.println("Sample Table Sizes:");
		util.printSampleTableSizes();
		System.out.println();
	}
	
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
}
