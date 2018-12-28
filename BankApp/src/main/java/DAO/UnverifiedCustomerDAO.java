package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.Customer;
import customers.UnverifiedCustomer;
import database.DBConnection;
import database.DBUtil;
import utility.Helpers;

public class UnverifiedCustomerDAO implements UnverifiedCustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	DBUtil util;
	Helpers helper;
	
	public UnverifiedCustomerDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		//if (!isUnique(unverifiedCustomer, toSampleTable)) return false;
		//else 
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			
			ps.setInt(1,  unverifiedCustomer.getID());
			ps.setString(2, unverifiedCustomer.getFirstname());
			ps.setString(3, unverifiedCustomer.getLastname());
			ps.setString(4, unverifiedCustomer.getTelephone());
			ps.setString(5, unverifiedCustomer.getEmail());
			ps.setBoolean(6, unverifiedCustomer.getIsCitizen());
			ps.setBoolean(7, unverifiedCustomer.getIsEmployed());
			ps.setString(8, unverifiedCustomer.getEmployer());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in UnverifiedCustomerDAO#addUnverifiedCustomer");
			//e.printStackTrace(); System.out.println();
			return false;
		}
	}
	@Override
	public int getNumUnverifiedCustomers(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
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
	@Override
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		Connection connection;
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY unverified_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-15s%-15s%-15s%-40s%-10s%-10s%-35s", 
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), helper.boolToString(rs.getBoolean(5)), helper.boolToString(rs.getBoolean(7)), 
						rs.getString(8));
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
	@Override
	public void printAllUnverifiedCustomers(boolean fromSampleTable) {
		ArrayList<String> unverifiedCustomerRecords = getAllRecords(fromSampleTable);
		for (String unverifiedCustomerRecord : unverifiedCustomerRecords) {
			System.out.println(unverifiedCustomerRecord);
		}
	}

	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY unverified_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumUnverifiedCustomers(fromSampleTable)) {
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
	public boolean isUnique(UnverifiedCustomer c, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		boolean unique = false;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName
					+ " WHERE unverified_id=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, c.getID());
			ResultSet rs = preparedStatement.executeQuery(sql);
			
			if (rs.next() && rs.getInt(1) == c.getID()) {
				System.out.println("UnverifiedCustomer with id " + c.getID() + " is not unique.");
				unique = false;
			}
			else {
				System.out.println("UnverifiedCustomer with id " + c.getID() + " is unique.");
				unique = true;
			}

			rs.close();
			return unique;
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
		}
		return unique;
	}
	public int getMaxID(boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_unverified_customers" : "unverified_customers";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(unverified_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(1);
			statement.close(); rs.close();
			return maxID;
			
		} catch (SQLException e) {
			//e.printStackTrace(); System.out.println();
		}
		return maxID;
	}
}
