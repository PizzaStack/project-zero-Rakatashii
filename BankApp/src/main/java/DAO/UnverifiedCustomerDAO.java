package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import customers.UnverifiedCustomer;
import database.DBConnection;

public class UnverifiedCustomerDAO implements UnverifiedCustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	
	@Override
	public boolean addUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO unverified_customers VALUES(?,?,?,?,?,?,?,?)";
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
			System.out.println("SQLException in UnverifiedCustomerDAO#addUnverifiedCustomer");
			return false;
		}
	}
	@Override
	public boolean addSampleUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_unverified_customers VALUES(?,?,?,?,?,?,?,?)";
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
			System.out.println("SQLException in UnverifiedCustomerDAO#addSampleUnverifiedCustomer");
			return false;
		}
	}
	public int getNumUnverifiedCustomers() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM unverified_customers;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close();
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public int getNumSampleUnverifiedCustomers() {
		Connection connection;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT COUNT(*) AS count FROM sample_unverified_customers;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			rs.next();
			int count = rs.getInt("count");
			statement.close();
			rs.close();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
