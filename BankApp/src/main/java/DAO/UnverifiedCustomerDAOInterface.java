package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.UnverifiedCustomer;
import database.DBConnection;

public interface UnverifiedCustomerDAOInterface {
	public boolean addUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer, boolean toSampleTable);
	public int getNumUnverifiedCustomers(boolean fromSampleTable);
	public ArrayList<String> getAllRecords(boolean fromSampleTable);
	public void printAllUnverifiedCustomers(boolean fromSampleTable);
}
