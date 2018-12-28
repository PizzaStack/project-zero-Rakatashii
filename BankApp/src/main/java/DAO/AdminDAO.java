package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	public boolean addAdmin(Employee admin) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO admins VALUES(?,?,?,?,?)";
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
	public boolean addSampleAdmin(Employee admin) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_admins VALUES(?,?,?,?,?)";
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
			System.out.println("SQLException in AdminDAO#addSampleAdmin");
			return false;
		}
	}
	@Override
	public int getNumAdmins() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM admins;";
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
	public int getNumSampleAdmins() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_admins;";
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
			String sql = "SELECT * FROM admins ORDER BY employee_id;";
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
			String sql = "SELECT * FROM sample_admins ORDER BY employee_id;";
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
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return records;
	}
}
