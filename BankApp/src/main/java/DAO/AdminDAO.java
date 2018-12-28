package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import customers.UnverifiedCustomer;
import database.DBConnection;
import database.DBUtil;
import employees.Admin;
import employees.Employee;
import utility.Helpers;

public class AdminDAO implements AdminDAOInterface{
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	private Helpers helper;
	
	public AdminDAO() {
		util = new DBUtil();
		helper = new Helpers();
	}
	
	@Override
	public boolean addAdmin(Employee admin, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_admins" : "admins";
		//if (!isUnique(admin, inSampleTable)) return false;
		//else 
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  admin.getEmployeeID());
			ps.setString(2, admin.getUsername());
			ps.setString(3, admin.getPassword());
			ps.setBoolean(4, admin.getIsAdmin());
			ps.setInt(5,  admin.getAdminID());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			System.out.println("SQLException in AdminDAO#addAdmin");
			return false;
		}
	}
	@Override
	public int getNumAdmins(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_admins" : "admins";
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
		String tableName = (fromSampleTable) ? "sample_admins" : "admins";
		ArrayList<String> records = new ArrayList<String>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY employee_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			String record = null;
			while (rs.next()) {
				record = String.format("%-10d%-20s%-20s%-10s%-10d",
						rs.getInt(1), rs.getString(2), rs.getString(3), 
						helper.boolToString(rs.getBoolean(4)), rs.getInt(5));
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
	public void printAllAdmins(boolean fromSampleTable) {
		ArrayList<String> actualAdminRecords = getAllRecords(fromSampleTable);
		for (String actualAdminRecord : actualAdminRecords) {
			System.out.println(actualAdminRecord);
		}
	}
	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_admins" : "admins";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY admin_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumAdmins(fromSampleTable)) {
				id = rs.getInt(5);
				if (++i != id) openIDs.add(i);
				i = id;
			}
			statement.close(); rs.close();
			return openIDs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return openIDs;
	}
	public boolean isUnique(Employee a, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_admins" : "admins";
		boolean unique = false;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName
					+ " WHERE employee_id=?"
					+ " AND username=?"
					+ " AND admin_id=?;";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, a.getEmployeeID());
			preparedStatement.setString(2, a.getUsername());
			preparedStatement.setInt(5, a.getAdminID());
			ResultSet rs = preparedStatement.executeQuery(sql);
			
			if (rs.next() && (rs.getInt(1) == a.getEmployeeID() || rs.getString(2) == a.getUsername() || rs.getInt(5) == a.getAdminID())) {
				System.out.println("Admin with employee_id " + a.getEmployeeID() 
						+ " and username " + a.getUsername()
						+ " and admin_id " + a.getAdminID() 
						+ " is not unique.");
				unique = false;
			}
			else {
				System.out.println("Admin with employee_id " + a.getEmployeeID() + " and admin_id " + a.getAdminID() + " is unique.");
				unique = true;
			}

			preparedStatement.close(); rs.close();
			return unique;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return unique;
	}
	public int getMaxAdminID(boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_admins" : "admins";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(admin_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(5);
			
			statement.close(); rs.close();
			return maxID;
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		return maxID;
	}


}
