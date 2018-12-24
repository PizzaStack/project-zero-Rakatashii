package database;

import java.io.FileNotFoundException;
import java.sql.Connection;
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
		for (int i = 0; i < actualSchemas.length; i++) {
			try (
					Connection connection = DBConnection.getConnection();
					Statement statement = connection.createStatement();
				){
					//if (connection == null) System.out.println("Connection == null");
					String sql = actualSchemas[i];
					
					System.out.println("Creating table " + "tablename...");
					statement.execute(sql);
					
					System.out.println("Created table in given database...");
					statement.close();
					connection.close();
					
				} catch (SQLException e) {
					//System.out.println("Failed to create customer table from schema");
					//e.printStackTrace();
				} 
		}
	}
	public void createSampleTables() throws ClassNotFoundException {
		for (int i = 0; i < sampleSchemas.length; i++) {
			try (
					Connection connection = DBConnection.getConnection();
					Statement statement = connection.createStatement();
				){
					//if (connection == null) System.out.println("Connection == null");
					String sql = sampleSchemas[i];
					
					System.out.println("Creating table " + "tablename...");
					statement.execute(sql);
					
					System.out.println("Created table in given database...");
					statement.close();
					connection.close();
					
					// TODO - if table exists, SQLException thrown and loop is ended. Need
					// to try to use metadata to find out whether table exists, then create
					// if not.
					
				} catch (SQLException e) {
					//System.out.println("Failed to create customer table from schema");
					//e.printStackTrace();
				} 
		}
	}		
	public static boolean tableExist(Connection connection, String tableName) throws SQLException {
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
}
