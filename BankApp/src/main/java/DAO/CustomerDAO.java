package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.Customer;
import customers.CustomerBuilder;
import database.DBConnection;
import database.DBUtil;
import utility.Helpers;

public class CustomerDAO implements CustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	private Helpers helper;
	
	public CustomerDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addCustomer(Customer customer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_customers" : "customers";
		//if (isUnique(customer, toSampleTable) == false) return false;
		//else 
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?,?,?,?);";
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
			if (customer.hasSavingsAccount() && customer.hasCheckingAccount()){
				if (customer.getSavingsAccount().isFlagged() || customer.getCheckingAccount().isFlagged()) {
					customer.flag();
				}
			}
			ps.setBoolean(11, customer.isFlagged());
		
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
	@Override
	public int getNumCustomers(boolean fromSampleTable) {
		Connection connection;
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM " + tableName + ";";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close(); rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return 0;
	}
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		ArrayList<String> records = new ArrayList<String>();
		try {
			Connection connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-10s", 
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6), rs.getString(7), 
						helper.boolToString(rs.getBoolean(8)), helper.boolToString(rs.getBoolean(9)), 
						rs.getString(10), helper.boolToString(rs.getBoolean(11)));
				records.add(record);
				record = null;
			}
			statement.close(); rs.close();
			return records;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return records;
	}
	public ArrayList<Customer> getAllCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		ArrayList<Customer> customers = new ArrayList<Customer>();
		try {
			Connection connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				Customer c = new CustomerBuilder()
						.withID(rs.getInt(1))
						.withUsername(rs.getString(2))
						.withPassword(rs.getString(3)) 
						.withFirstName(rs.getString(4))
						.withLastName(rs.getString(5))
						.withTelephone(rs.getString(6))
						.withEmail(rs.getString(7))
						.withIsCitizen(rs.getBoolean(8))
						.withIsEmployed(rs.getBoolean(9))
						.withEmployer(rs.getString(10)) 
						.withIsFlagged(rs.getBoolean(11))
						.makeCustomer();
				customers.add(c);
			}
			statement.close(); rs.close();
			return customers;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return customers;
	}

	public void printAllCustomers(boolean fromSampleTable) {
		ArrayList<String> sampleCustomerRecords = getAllRecords(fromSampleTable);
		for (String sampleCustomerRecord : sampleCustomerRecords) {
			System.out.println(sampleCustomerRecord);
		}
	}
	
	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_customers" : "customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumCustomers(fromSampleTable)) {
				id = rs.getInt(1);
				if (++i != id) openIDs.add(i);
				i = id;
			}
			statement.close(); rs.close();
			return openIDs;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return openIDs;
	}
	public boolean isUnique(Customer c, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_customers" : "customers";
		boolean unique = false;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName
					+ " WHERE customer_id=?"
					+ " AND username=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, c.getID());
			preparedStatement.setString(2,  c.getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next() && (rs.getInt(1) == c.getID() || rs.getString(2).equals(c.getUsername()))) {
				System.out.println("Customer with customer_id " + c.getID() + " and username " + c.getUsername() + " is not unique.");
				unique = false;
			}
			else {
				System.out.println("Customer with customer_id " + c.getID() + " and username " + c.getUsername() + " is unique.");
				unique = true;
			}

			preparedStatement.close(); rs.close();
			return unique;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return unique;
	}
	public int getMaxID(boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_customers" : "customers";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(customer_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(1);
			statement.close(); rs.close();
			return maxID;
			
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
}
