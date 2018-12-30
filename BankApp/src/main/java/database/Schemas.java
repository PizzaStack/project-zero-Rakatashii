package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import customers.Customer;

public class Schemas {

	final String customerWithAccountSchema = "CREATE TABLE customers_with_accounts ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " 
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "savings_number VARCHAR(255), "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255), "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, "
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER"
			+ "); "
	
			+ "CREATE UNIQUE INDEX only_joint_has_duplicates "
			+ "on customers_with_accounts(savings_number, checking_number) "
			+ "WHERE joint=false;";
	
		
	final String sampleCustomerWithAccountSchema = "CREATE TABLE sample_customers_with_accounts ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " 
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "savings_number VARCHAR(255), "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255), "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, "
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER"
			+ "); "
	
			+ "CREATE UNIQUE INDEX only_joint_has_duplicates_in_sample "
			+ "on sample_customers_with_accounts(savings_number, checking_number) "
			+ "WHERE joint=false;";
	
	final String customerSchema = "CREATE TABLE customers ("
			+ "customer_id INTEGER, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "flagged BOOLEAN, "
			+ "FOREIGN KEY (customer_id) "
			+ "		REFERENCES customers_with_accounts(customer_id)"
			+ ");";
	final String customerSampleSchema = "CREATE TABLE sample_customers ("
			+ "customer_id INTEGER, "
			+ "username VARCHAR(255) UNIQUE, "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), " 
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255), "
			+ "flagged BOOLEAN, "
			+ "FOREIGN KEY (customer_id) "
			+ "		REFERENCES sample_customers_with_accounts(customer_id)"
			+ ");";
	final String accountSchema = "CREATE TABLE accounts ("
			+ "customer_id INTEGER, " 
			+ "savings_number VARCHAR(255), "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255), "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, " 
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER, "
			//+ "PRIMARY KEY (savings_number, joint_customer_id), "
			+ "FOREIGN KEY (customer_id) "
			+ "		REFERENCES customers_with_accounts"
			+ ");";
	final String accountSampleSchema = "CREATE TABLE sample_accounts ("
			+ "customer_id INTEGER, "
			+ "savings_number VARCHAR(255), "
			+ "savings_amount DECIMAL(12, 2), "
			+ "checking_number VARCHAR(255), "
			+ "checking_amount DECIMAL(12, 2), "
			+ "flagged BOOLEAN, "
			+ "joint BOOLEAN, "
			+ "joint_customer_id INTEGER, "
			//+ "PRIMARY KEY (savings_number, joint_customer_id), "
			+ "FOREIGN KEY (customer_id) "
			+ "		REFERENCES sample_customers_with_accounts"
			+ ");";
	/*
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
	*/
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
	final String[] actualSchemas = { customerWithAccountSchema, customerSchema, accountSchema, 
			unverifiedCustomerSchema, employeeSchema, adminSchema};
	final String[] sampleSchemas = { sampleCustomerWithAccountSchema, customerSampleSchema, accountSampleSchema,
			 unverifiedCustomerSampleSchema, employeeSampleSchema, adminSampleSchema};
	final String[] actualSchemaNames = {"customers_with_accounts", "customers", "accounts", 
			"unverified_customers", "employees", "admins"};
	final String[] sampleSchemaNames = {"sample_customers_with_accounts", "sample_customers", "sample_accounts", 
			"sample_unverified_customers", "sample_employees", "sample_admins"};
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
