package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Schemas {
	final String customerSchema = "CREATE TABLE customers ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255), "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
	final String customerSampleSchema = "CREATE TABLE sample_customers ("
			+ "customer_id INTEGER PRIMARY KEY, "
			+ "username VARCHAR(255), "
			+ "password VARCHAR(255), "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
	final String unverifiedCustomerSchema = "CREATE TABLE unverified_customers ("
			+ "unverified_customer_id INTEGER PRIMARY KEY, "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
	final String unverifiedCustomerSampleSchema = "CREATE TABLE sample_unverified_customers ("
			+ "unverified_customer_id INTEGER PRIMARY KEY, "
			+ "first_name VARCHAR(255), "
			+ "last_name VARCHAR(255), "
			+ "telephone VARCHAR(255), "
			+ "email VARCHAR(255), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(255)"
			+ ");";
	final String accountSchema = "CREATE TABLE accounts ("
			+ "customer_id INT PRIMARY KEY, "
			+ "saccount_number VARCHAR(266), "
			+ "sbalance FLOAT, "
			+ "caccount_number VARCHAR(266), "
			+ "cbalance FLOAT"
			+ ");";
	final String accountSampleSchema = "CREATE TABLE sample_accounts ("
			+ "customer_id INT PRIMARY KEY, "
			+ "saccount_number VARCHAR(266), "
			+ "sbalance FLOAT, "
			+ "caccount_number VARCHAR(266), "
			+ "cbalance FLOAT"
			+ ");";
    final String employeeSchema = "CREATE TABLE employees ("
			+ "employee_id INT PRIMARY KEY, "
			+ "username VARCHAR(55), " 
			+ "password VARCHAR(55), " 
			+ "admin BOOLEAN"
			+ ");";
	final String employeeSampleSchema = "CREATE TABLE sample_employees ("
			+ "employee_id INT PRIMARY KEY, "
			+ "username VARCHAR(55), " 
			+ "password VARCHAR(55), " 
			+ "admin BOOLEAN"
			+ ");";
	final String[] actualSchemas = { unverifiedCustomerSchema, customerSchema, accountSchema, employeeSchema };
	final String[] sampleSchemas = { unverifiedCustomerSampleSchema, customerSampleSchema, accountSampleSchema, employeeSampleSchema };
	final String[] actualSchemaNames = {"unverified_customers", "customers", "accounts", "employees"};
	final String[] sampleSchemaNames = {"sample_unverified_customers", "sample_customers", "sample_accounts", "sample_employees"};
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
