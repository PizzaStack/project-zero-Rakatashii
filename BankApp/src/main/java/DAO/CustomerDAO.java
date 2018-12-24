package DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import customers.Customer;
import database.DBConnection;

public class CustomerDAO implements CustomerDAOInterface {
	private Connection connection;
	private PreparedStatement ps;
	
	@Override
	public boolean addSampleCustomer(Customer customer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO sample_customers VALUES(?,?,?,?,?,?,?,?,?,?)";
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
	public boolean addCustomer(Customer customer) {
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO customers VALUES(?,?,?,?,?,?,?,?,?,?)";
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

}
