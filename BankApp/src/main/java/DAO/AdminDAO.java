package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import database.DBUtil;
import employees.Admin;
import employees.Employee;

public class AdminDAO implements AdminDAOInterface{
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	
	@Override
	public boolean addAdmin(Employee admin) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO admins VALUES(?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  admin.getID());
			ps.setString(2, admin.getUsername());
			ps.setString(3, admin.getPassword());
			ps.setBoolean(4, admin.getIsAdmin());
		
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
			String sql = "INSERT INTO sample_admins VALUES(?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  admin.getID());
			ps.setString(2, admin.getUsername());
			ps.setString(3, admin.getPassword());
			ps.setBoolean(4, admin.getIsAdmin());
		
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
}
