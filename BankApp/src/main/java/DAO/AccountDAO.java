package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DAO.AccountDAOInterface;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.Customer;
import database.DBConnection;
import database.DBUtil;
import utility.Helpers;

public class AccountDAO implements AccountDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	private Helpers helper;
	
	public AccountDAO() {
		helper = new Helpers();
	}
	
	@Override
	public boolean addAccounts(SavingsAccount savings, CheckingAccount checking, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_accounts" : "accounts";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			if (savings.getOwner() != null && checking.getOwner() != null && savings.getOwner() == checking.getOwner())
				ps.setInt(1, savings.getOwner().getID());
			else 
				ps.setInt(1, -1);
			ps.setString(2, savings.getID());
			ps.setFloat(3, (float) savings.getBalance());
			ps.setString(4, checking.getID());
			ps.setFloat(5, (float) checking.getBalance());
			if (savings.isFlagged() != checking.isFlagged()) {
				savings.flag();
				checking.flag();
				
			}
			boolean flagged = (savings.getOwner() != null) ? savings.getOwner().isFlagged() : false;
			ps.setBoolean(6, flagged);
			ps.setBoolean(7,  savings.isJoint());
			if (savings.isJoint()) ps.setInt(9,  savings.getJointCustomer().getCustomerID());
			else ps.setInt(8, -1);
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in AccountsDAO#addAccounts");
			return false;
		}
	}
	void updateAccounts(Customer customer, boolean toSampleTable) {
		SavingsAccount savings = null;
		CheckingAccount checking = null;
		if (customer.hasSavingsAccount()) {
			savings = customer.getSavingsAccount();
		}
		if (customer.hasCheckingAccount()) {
			checking = customer.getCheckingAccount();
		}
		String tableName = (toSampleTable) ? "sample_accounts" : "accounts";
	    
		try {
	    	connection = DBConnection.getConnection();
	    	
		    String sql = "UPDATE " + tableName + " SET "
		    		+ "customer_id = ?, savings_number = ?, savings_amount = ?, "
		    		+ "checking_number = ?, checking_amount = ?, flagged = ?, "
		    		+ "joint = ?, joint_customer_id = ? "
			    	+ "WHERE customer_id = ?;";
		    PreparedStatement ps = connection.prepareStatement(sql);
		    ps.setInt(1, customer.getCustomerID());
		    if (savings != null) {
		    	ps.setString(2, savings.getID());
			    ps.setDouble(3, savings.getBalance());
		    } else {
		    	ps.setString(2, "null");
			    ps.setDouble(3, 0.0);
		    }
		    if (checking != null) {
		    	ps.setString(4, checking.getID());
				ps.setDouble(5, checking.getBalance());
		    } else {
		    	ps.setString(4, "null");
				ps.setDouble(5, 0.0);
		    }
		    ps.setBoolean(6, customer.isFlagged());
		    ps.setBoolean(7,  customer.hasJointAccounts());
		    ps.setInt(8, customer.getSharedCustomerID());
		    
		    ps.setInt(9, customer.getCustomerID());
		    
		    ps.executeUpdate();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
	}
	@Override
	public int getNumAccounts(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_accounts" : "accounts";
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
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_accounts" : "accounts";
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s$%-19.2f%-20s$%-19.2f%-10d", 
						rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), 
						rs.getDouble(5), helper.boolToInt(rs.getBoolean(6)));
				records.add(record);
				record = null;
			}
			statement.close(); rs.close();
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
	@Override
	public void printAllAccounts(boolean fromSampleTable) {
    	ArrayList<String> actualAccountRecords = getAllRecords(fromSampleTable);
    	for (String actualAccountRecord : actualAccountRecords) {
    		System.out.println(actualAccountRecord);
    	}
	}
	public boolean doesNotContainID(String id, String field, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_customers_with_accounts" : "customers_with_accounts";
		try {
			// This actually may be more efficient, but keep in mind that select all where id = ... is still available
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " WHERE " + field + "=" + id + ";";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (!rs.next()) {
				statement.close(); rs.close();
				return false;
			} 
			statement.close(); rs.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

}
