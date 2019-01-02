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
	private Helpers helper;
	
	public AccountDAO() {
		helper = new Helpers();
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
