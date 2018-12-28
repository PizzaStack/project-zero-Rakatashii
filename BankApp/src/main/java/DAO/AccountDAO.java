package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import accounts.CheckingAccount;
import accounts.SavingsAccount;
import database.DBConnection;
import database.DBUtil;
import utility.Helpers;

public class AccountDAO implements AccountDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	private Helpers helper;
	
	public AccountDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addAccounts(SavingsAccount savings, CheckingAccount checking) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO accounts VALUES(?, ?, ?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			if (savings.getOwner() == checking.getOwner())
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
			ps.setBoolean(6,  savings.getOwner().isFlagged());
		
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

	@Override
	public boolean addSampleAccounts(SavingsAccount savings, CheckingAccount checking) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_accounts VALUES(?, ?, ?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			
			if (savings.getOwner() == null || checking.getOwner() == null) return false;
			ps.setInt(1, savings.getOwner().getID());
			ps.setString(2, savings.getID());
			ps.setDouble(3, savings.getBalance());
			ps.setString(4, checking.getID());
			ps.setDouble(5, checking.getBalance());
			if (savings.isFlagged() != checking.isFlagged()) {
				System.out.println("savings.isFlagged() = " + savings.isFlagged() + " - checking.isFlagged() = " + checking.isFlagged());
				savings.flag();
				checking.flag();
				savings.getOwner().flag();
			}
			System.out.println("savings.getOwner().isFlagged() = " + savings.getOwner().isFlagged());
			ps.setBoolean(6,  savings.getOwner().isFlagged());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in AccountsDAO#addSampleAccounts");
			return false;
		}
	}
	@Override
	public int getNumAccounts() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM accounts;";
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
	public int getNumSampleAccounts() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_accounts;";
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
			String sql = "SELECT * FROM accounts ORDER BY customer_id;";
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
			String sql = "SELECT * FROM sample_accounts ORDER BY customer_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-15s$%-19.2f%-15s$%-19.2f%-10d", 
						rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), 
						rs.getDouble(5), helper.boolToInt(rs.getBoolean(6)));
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
