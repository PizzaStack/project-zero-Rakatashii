package database;

public class SQLTemplates {
	public SQLTemplates() {}
	final public String customerSchema = "CREATE TABLE customer ("
			+ "customer_id INT PRIMARY KEY, "
			+ "username VARCHAR(55), "
			+ "password VARCHAR(55), "
			+ "first_name VARCHAR(55), "
			+ "last_name VARCHAR(55), "
			+ "telephone VARCHAR(55), "
			+ "email VARCHAR(55), "
			+ "us_citizen BOOLEAN, "
			+ "employed BOOLEAN, "
			+ "employer VARCHAR(55)"
			+ ");";
	final public String accountsSchema = "CREATE TABLE all_accounts ("
			+ "customer_id INT PRIMARY KEY"
			+ "saccount_number VARCHAR(266) FOREIGN KEY "
			+ "sbalance FLOAT, "
			+ "caccount_number VARCHAR(266), "
			+ "cbalance FLOAT"
			+ ");";
	final public String employeeSchema = "CREATE TABLE employees ("
			+ "employee_id INT PRIMARY KEY"
			+ "username VARCHAR(55), " 
			+ "password VARCHAR(55), " 
			+ "admin BOOLEAN"
			+ ");";
}
