package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.Customer;
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
	public boolean addCustomer(Customer customer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
			System.out.println("SQLException in CustomerDAO#addCustomer");
			return false;
		}
	}
	@Override
	public boolean addSampleCustomer(Customer customer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_customers VALUES(?,?,?,?,?,?,?,?,?,?,?)";
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
			if (customer.hasSavingsAccount() && customer.hasCheckingAccount() && customer.getSavingsAccount().isFlagged() == customer.getCheckingAccount().isFlagged()) {
				if ( customer.getSavingsAccount().isFlagged() ) 
					customer.flag();
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
			System.out.println("SQLException in CustomerDAO#addSampleCustomer");
			return false;
		}
	}
	@Override
	public int getNumCustomers() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM customers;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getNumSampleCustomers() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_customers;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public ArrayList<String> getActualRecordsAsStrings() {
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM customers ORDER BY customer_id;";
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
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
	public ArrayList<String> getSampleRecordsAsStrings() {
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM sample_customers ORDER BY customer_id;";
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
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
}
