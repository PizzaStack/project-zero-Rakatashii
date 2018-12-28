package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.Customer;
import database.DBConnection;

public interface CustomerDAOInterface {
	public boolean addCustomer(Customer customer, boolean toSampleTable);
	public int getNumCustomers(boolean fromSampleTable);
	public ArrayList<String> getAllRecords(boolean fromSampleTable);
	public void printAllCustomers(boolean fromSampleTable);
	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable);
}
