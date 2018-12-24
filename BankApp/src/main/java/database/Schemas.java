package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Schemas {
	final String customerSchema = "CREATE TABLE customers ("
			+ "customer_id INTEGER PRIMARY KEY, " //1
			+ "username VARCHAR(255), " //2
			+ "password VARCHAR(255), " //3
			+ "first_name VARCHAR(255), " //4
			+ "last_name VARCHAR(255), " //5
			+ "telephone VARCHAR(255), " //6
			+ "email VARCHAR(255), " //7
			+ "us_citizen BOOLEAN, " //8
			+ "employed BOOLEAN, " //9
			+ "employer VARCHAR(255)" //10
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
	final String[] actualSchemas = { customerSchema, accountSchema, employeeSchema, employeeSampleSchema };
	final String[] sampleSchemas = { customerSampleSchema, accountSampleSchema, employeeSampleSchema };
	final String[] actualSchemaNames = {"customers", "accounts", "employees"};
	final String[] sampleSchemaNames = {"sample_customers", "sample_accounts", "sample_employees"};
	public Schemas() { }
	
	public void createActualTables() throws ClassNotFoundException {
		for (int i = 0; i < sampleSchemas.length; i++) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				
			    DatabaseMetaData dbm = connection.getMetaData();
			    ResultSet rs = dbm.getTables(null, null, actualSchemaNames[i], null);
			    if (rs.next()) {
			    	System.out.println("Tables " + actualSchemaNames[i] + " already exits.");
			    } else {
			    	System.out.println("Table " + actualSchemaNames[i] + " does not exist"); 
			    	String sql = sampleSchemas[i];
					
					System.out.println("Creating table " + actualSchemaNames[i] + "...");
					statement.execute(sql);
					
					System.out.println("Table " + actualSchemaNames[i] + " was created.");
					statement.close();
					//connection.close();
			    }
			} catch (SQLException e) {
				System.out.println("Failed to create " + actualSchemaNames[i] + " table from schema");
				//e.printStackTrace();
			} 
		}
	}
	public void createSampleTables() throws ClassNotFoundException {
		for (int i = 0; i < sampleSchemas.length; i++) {
			try {
				Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				
			    DatabaseMetaData dbm = connection.getMetaData();
			    ResultSet rs = dbm.getTables(null, null, sampleSchemaNames[i], null);
			    if (rs.next()) {
			    	System.out.println("Tables " + sampleSchemaNames[i] + " already exits.");
			    } else {
			    	System.out.println("Table " + sampleSchemaNames[i] + " does not exist"); 
			    	String sql = sampleSchemas[i];
					
					System.out.println("Creating table " + sampleSchemaNames[i] + "...");
					statement.execute(sql);
					
					System.out.println("Table " + sampleSchemaNames[i] + " was created.");
					statement.close();
					//connection.close();
			    }
			} catch (SQLException e) {
				System.out.println("Failed to create " + sampleSchemaNames[i] + " table from schema");
				//e.printStackTrace();
			} 
		}
	}		
	/*
	public static boolean tableExists(Connection connection, String tableName) throws SQLException {
	    boolean tExists = false;
	    try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName, null)) {
	        while (rs.next()) { 
	            String tName = rs.getString("TABLE_NAME");
	            if (tName != null && tName.equals(tableName)) {
	                tExists = true;
	                break;
	            }
	        }
	    }
	    return tExists;
	}
	*/
}
