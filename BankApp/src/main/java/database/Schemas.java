package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import customers.Customer;

public class Schemas {
	/* Drop All Tables:
	drop table if exists customers, sample_customers, accounts, sample_accounts, unverified_customers, 
	sample_unverified_customers, employees, sample_employees, admins, sample_admins;
	*/
	/*
	public boolean addCustomerWithAccount(Customer customer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		//if (isUnique(customer, toSampleTable) == false) return false;
		//else 
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  customer.getID());
			ps.setString(2, customer.getUsername());
			ps.setString(3, customer.getPassword());
			ps.setString(4, customer.getFirstname());
			ps.setString(5, customer.getLastname());
			ps.setString(6, customer.getTelephone());
			ps.setString(7, customer.getEmail());
			ps.setBoolean(8, customer.getIsCitizen());
			ps.setBoolean(9, customer.getIsEmployed());
			ps.setString(10, customer.getEmployer());
			if (customer.hasSavingsAccount() == false) {
				customer.makeNewAccounts();
			} 
			ps.setString(11, customer.getSavingsAccount().getID());
			ps.setDouble(12, customer.getSavingsAccount().getBalance());
			ps.setString(13, customer.getCheckingAccount().getID());
			ps.setDouble(14, customer.getCheckingAccount().getBalance());
			if (customer.getSavingsAccount().isFlagged() || customer.getCheckingAccount().isFlagged()) {
				customer.flag();
			}
			ps.setBoolean(15, customer.isFlagged());
			ps.setBoolean(16, customer.hasJointAccounts());
			if (customer.hasJointAccounts())
				ps.setInt(17, customer.getSharedCustomer().getCustomerID());
			else ps.setInt(17,  -1);
			
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
			//System.out.println("SQLException in CustomerDAO#addCustomer"); System.out.println();
			return false;
		}
	}
	*/
	final String customerWithAccountSchema = "CREATE TABLE customers_with_accounts ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255), "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " 
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "savings_number VARCHAR(255) UNIQUE, "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255) UNIQUE, "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, "
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER"
			+ ");";
	final String sampleCustomerWithAccountSchema = "CREATE TABLE sample_customers_with_accounts ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255), "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " 
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "savings_number VARCHAR(255) UNIQUE, "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255) UNIQUE, "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, "
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER"
			+ ");";
	final String customerSchema = "CREATE TABLE customers ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " // NOT NULL
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "flagged BOOLEAN"
			+ ");";
	final String customerSampleSchema = "CREATE TABLE sample_customers ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255) NOT NULL, "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " // NOT NULL
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "flagged BOOLEAN"
			+ ");";
	final String accountSchema = "CREATE TABLE accounts ("
			+ "customer_id INTEGER PRIMARY KEY, " //INT PRIMARY KEY, "
			+ "savings_number VARCHAR(255) UNIQUE, "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255) UNIQUE, "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN"
			+ ");";
	final String accountSampleSchema = "CREATE TABLE sample_accounts ("
			+ "customer_id INTEGER PRIMARY KEY, " //PRIMARY KEY, "
			+ "savings_number VARCHAR(255) UNIQUE, "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255) UNIQUE, "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN"
			+ ");";
	final String unverifiedCustomerSchema = "CREATE TABLE unverified_customers ("
			+ "unverified_id INTEGER PRIMARY KEY, "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255) UNIQUE, "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
	final String unverifiedCustomerSampleSchema = "CREATE TABLE sample_unverified_customers ("
			+ "unverified_id INTEGER PRIMARY KEY, "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255) UNIQUE, "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
    final String employeeSchema = "CREATE TABLE employees ("
			+ "employee_id INT PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, " 
			+ "password VARCHAR(255) NOT NULL, " 
			+ "admin BOOLEAN, "
			+ "admin_id INTEGER"
			+ ");";
	final String employeeSampleSchema = "CREATE TABLE sample_employees ("
			+ "employee_id INT PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, " 
			+ "password VARCHAR(255) NOT NULL, " 
			+ "admin BOOLEAN, "
			+ "admin_id INTEGER"
			+ ");";
    final String adminSchema = "CREATE TABLE admins ("
			+ "employee_id INT PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, " 
			+ "password VARCHAR(255) NOT NULL, " 
			+ "admin BOOLEAN, "
			+ "admin_id INTEGER UNIQUE"
			+ ");";
	final String adminSampleSchema = "CREATE TABLE sample_admins ("
			+ "employee_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, " 
			+ "password VARCHAR(255) NOT NULL, " 
			+ "admin BOOLEAN, "
			+ "admin_id INTEGER UNIQUE"
			+ ");";
	final String[] actualSchemas = { unverifiedCustomerSchema, customerSchema, accountSchema, employeeSchema, adminSchema };
	final String[] sampleSchemas = { unverifiedCustomerSampleSchema, customerSampleSchema, accountSampleSchema, employeeSampleSchema, adminSampleSchema };
	final String[] actualSchemaNames = {"unverified_customers", "customers", "accounts", "employees", "admins"};
	final String[] sampleSchemaNames = {"sample_unverified_customers", "sample_customers", "sample_accounts", "sample_employees", "sample_admins"};
	public Schemas() { }
	
	public void createActualTables() throws ClassNotFoundException {
		DBUtil dbUtil = new DBUtil();
		for (int i = 0; i < actualSchemas.length; i++) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
			
			    if (dbUtil.tableExists(actualSchemaNames[i]) == false) {
			    	System.out.println("Table " + actualSchemaNames[i] + " does not exist"); 
			    	String sql = actualSchemas[i];
					
					System.out.println("Creating table " + actualSchemaNames[i] + "...");
					statement.execute(sql);
					
					System.out.println("Table " + actualSchemaNames[i] + " was created.");
					statement.close();
			    } else {
			    	System.out.println("Tables " + actualSchemaNames[i] + " already exists.");
			    }
			} catch (SQLException e) {
				System.out.println("Failed to create " + actualSchemaNames[i] + " table from schema");
				//e.printStackTrace();
			} 
		}
	}
	public void createSampleTables() throws ClassNotFoundException {
		DBUtil dbUtil = new DBUtil();
		for (int i = 0; i < sampleSchemas.length; i++) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				
				if (dbUtil.tableExists(sampleSchemaNames[i]) == false) {
			    	System.out.println("Table " + sampleSchemaNames[i] + " does not exist"); 
			    	String sql = sampleSchemas[i];
					
					System.out.println("Creating table " + sampleSchemaNames[i] + "...");
					statement.execute(sql);
					
					System.out.println("Table " + sampleSchemaNames[i] + " was created.");
					statement.close();
			    } else {
			    	System.out.println("Tables " + sampleSchemaNames[i] + " already exists.");
			    }
			} catch (SQLException e) {
				System.out.println("Failed to create " + sampleSchemaNames[i] + " table from schema");
				//e.printStackTrace();
			} 
		}
	}		
}
