package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import database.DBUtil;
import employees.Employee;

public class EmployeeDAO implements EmployeeDAOInterface{
	private Connection connection;
	private PreparedStatement ps;
	private DBUtil util;
	
	@Override
	public boolean addEmployee(Employee employee) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO employees VALUES(?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setBoolean(4, employee.getIsAdmin());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			System.out.println("SQLException in EmployeeDAO#addEmployee");
			return false;
		}
	}
	@Override
	public boolean addSampleEmployee(Employee employee) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_employees VALUES(?,?,?,?)";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setBoolean(4, employee.getIsAdmin());
		
			if (ps.executeUpdate() != 0) {
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			System.out.println("SQLException in EmployeeDAO#addSampleEmployee");
			return false;
		}
	}
	@Override
	public int getNumEmployees() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM employees;";
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
	public int getNumSampleEmployees() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_employees;";
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
