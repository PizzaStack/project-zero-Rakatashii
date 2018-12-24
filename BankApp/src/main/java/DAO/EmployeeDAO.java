package DAO;

import java.sql.Connection;
import java.sql.SQLException;

import database.DBConnection;

public class EmployeeDAO {
	private Connection connection;
	public void getConnection(Connection connection) {
		this.connection = connection;
	}
	public void createTable() throws SQLException, ClassNotFoundException {
		connection = DBConnection.getConnection();
	}
	
}
