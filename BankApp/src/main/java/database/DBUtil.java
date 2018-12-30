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

public class DBUtil {
	CustomerDAO customerDAO;
	UnverifiedCustomerDAO unverifiedCustomerDAO;
	EmployeeDAO employeeDAO;
	AdminDAO adminDAO;
	
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
	
	private void setDAOs() {
		customerDAO = new CustomerDAO();
		unverifiedCustomerDAO = new UnverifiedCustomerDAO();
		employeeDAO = new EmployeeDAO();
		adminDAO = new AdminDAO();
	}
	
	public void printActualTableSizes() {
		setDAOs();
		System.out.println("Number of           Customer Records: " + customerDAO.getNumCustomers(true, false));
		System.out.println("Number of UnverifiedCustomer Records: " + unverifiedCustomerDAO.getNumUnverifiedCustomers(false));
		System.out.println("Number of           Employee Records: " + employeeDAO.getNumEmployees(false));
		System.out.println("Number of              Admin Records: " + adminDAO.getNumAdmins(false));
	}
	public void printSampleTableSizes() {
		setDAOs();
		System.out.println("Number of           Customer (sample) Records: " + customerDAO.getNumCustomers(true, true));
		System.out.println("Number of UnverifiedCustomer (sample) Records: " + unverifiedCustomerDAO.getNumUnverifiedCustomers(true));
		System.out.println("Number of           Employee (sample) Records: " + employeeDAO.getNumEmployees(true));
		System.out.println("Number of              Admin (sample) Records: " + adminDAO.getNumAdmins(true));
	}
	
}
