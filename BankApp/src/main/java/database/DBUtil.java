package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.CustomerDAO;
import DAO.UnverifiedCustomerDAO;
import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import model.UnverifiedCustomerContainer;

public class DBUtil {
	public boolean tableExists(String tableName) {
		try {
			Connection connection = DBConnection.getConnection();
			
			Statement statement = connection.createStatement();
		    //ResultSet rs = statement.executeQuery("SELECT * from " + tableName + ";");
		    DatabaseMetaData dbm = connection.getMetaData();
		    ResultSet rs = dbm.getTables(null, null, tableName, null);
	    
		    if (rs.next() == false) {
		    	System.out.println("ResultSet is empty for table " + tableName);
		    	return false;
		    } 		    
		    statement.close();
		    rs.close();
		    return true;
		    
		} catch (SQLException e) {
			System.out.println("SQLException in DBUtil#checkIfEmpty");
			e.printStackTrace();
		}
		return false;
	}
	
	public void initialize() throws ClassNotFoundException {
		final String dir = "/c/Users/Associate/java/project-zero-Rakatashii";
  
    	Schemas schemas;
    	schemas = new Schemas();
    	schemas.createActualTables();
    	schemas.createSampleTables();
    	System.out.println();
    	
    	//DBConnection.closeConnection();
    	//System.out.println("Connection Closed.");
    	//String customerSampleFileName = "text_files/customer_sample.txt";
    	//File customerSampleFile = new File(customerSampleFileName);
    	
    	UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer = new UnverifiedCustomerContainer<UnverifiedCustomer>();
		CustomerContainer customerContainer = new CustomerContainer();
    	EmployeeContainer<Employee> employeeContainer = new EmployeeContainer<Employee>();
    	EmployeeContainer<Employee> adminContainer = new EmployeeContainer<Employee>();
    	
    	Containers containers = new Containers();
    	containers.setUnverifiedContainer(unverifiedContainer);
    	containers.setCustomerContainer(customerContainer);
    	containers.setEmployeeContainer(employeeContainer);
    	containers.setAdminContainer(adminContainer);
    	
    	UnverifiedCustomer.passUnverifiedContainer(containers.getUnverifiedContainer());
    	Customer.passCustomerContainer(containers.getCustomerContainer());
    	Employee.passEmployeeContainer(containers.getEmployeeContainer());
    	Admin.passAdminContainer(containers.getAdminContainer());
    	
    	DBUtil dbUtil = new DBUtil();
    	
    	CustomerDAO customerDAO = new CustomerDAO();
    	int numSampleCustomersInDB = customerDAO.getNumSampleCustomers();
    	
    	if (dbUtil.tableExists("sample_customers") == false || numSampleCustomersInDB <= 1) {
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
        	
        	ArrayList<Customer> customers = customerContainer.getArrayList();
        	for (Customer c : customers) {
        		c.printRow();
        		customerDAO.addSampleCustomer(c);
        	} 
    	} else {
    		System.out.println("sample_customers table is not empty");
    		System.out.println("sample_customers count = " + numSampleCustomersInDB);
    	}
    	
    	UnverifiedCustomerDAO unverifiedCustomerDAO = new UnverifiedCustomerDAO();
    	int numSampleUnverifiedCustomersInDB = unverifiedCustomerDAO.getNumSampleUnverifiedCustomers();
    	
    	if (dbUtil.tableExists("sample_unverified_customers") == false || numSampleUnverifiedCustomersInDB <= 1) {
        	try {
    			unverifiedContainer.readIn(new File("text_files/unverified_customer_sample.txt"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	unverifiedContainer.setTextFileName("text_files/formatted_unverified_customer_sample.txt");
        	try {
    			unverifiedContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	ArrayList<UnverifiedCustomer> unverifiedCustomers = unverifiedContainer.getArrayList();
        	for (UnverifiedCustomer c : unverifiedCustomers) {
        		c.printRow();
        		unverifiedCustomerDAO.addSampleUnverifiedCustomer(c);
        	}
        	
    	} else {
    		System.out.println("sample_unverified_customers table is not empty");
    		System.out.println("sample_unverified_customers count = " + numSampleUnverifiedCustomersInDB);
    	}
    	
    	EmployeeDAO employeeDAO = new EmployeeDAO();
    	int numSampleEmployeesInDB = employeeDAO.getNumSampleEmployees();
    	
    	if (dbUtil.tableExists("sample_employees") == false || numSampleEmployeesInDB <= 1) {
        	try {
    			employeeContainer.readIn(new File("text_files/employee_sample.txt"));
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	employeeContainer.setTextFileName("text_files/formatted_employee_sample.txt");
        	try {
    			employeeContainer.writeToTextFile(true, false);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	
        	ArrayList<Employee> employees = employeeContainer.getArrayList();
        	for (Employee e : employees) {
        		e.printRow();
        		employeeDAO.addSampleUnverifiedCustomer(e);
        	}
        	
    	} else {
    		System.out.println("sample_employees table is not empty");
    		System.out.println("sample_employees count = " + numSampleEmployeesInDB);
    	}
    	
    	System.out.println();
    	containers.printContainerSizes();
	}
}
