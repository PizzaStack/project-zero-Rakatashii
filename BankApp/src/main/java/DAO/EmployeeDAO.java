package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import customers.Customer;
import customers.CustomerBuilder;
import database.DBConnection;
import database.DBUtil;
import employees.Employee;
import employees.EmployeeBuilder;
import utility.Helpers;

public class EmployeeDAO implements EmployeeDAOInterface{
	private Connection connection;
	private PreparedStatement ps;
	private Helpers helper;
	static final Logger log = Logger.getLogger(EmployeeDAO.class);
	
	public EmployeeDAO() {
		helper = new Helpers();
	}
	
	@Override
	public boolean addEmployee(Employee employee, boolean toSampleTable) {
		String tableName = (toSampleTable) ? "sample_employees" : "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "INSERT INTO " + tableName + " VALUES(?,?,?,?,?);";
			ps = connection.prepareStatement(sql);
			ps.setInt(1,  employee.getEmployeeID());
			ps.setString(2, employee.getUsername());
			ps.setString(3, employee.getPassword());
			ps.setBoolean(4, employee.getIsAdmin());
			ps.setInt(5,  employee.getAdminID());
		
			if (ps.executeUpdate() != 0) {
				if (!toSampleTable) log.debug("Inserted Into " + tableName + " Values(" + employee.getID() + ", " 
						+ employee.getUsername() + ", ... )");
				ps.close();
				return true;
			} else {
				ps.close();
				return false;
			} 
		} catch (SQLException e) {
			//System.out.println("SQLException in EmployeeDAO#addEmployee"); System.out.println();
			return false;
		}
	}
	@Override
	public int getNumEmployees(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_employees" : "employees";
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
			System.out.println();
		}
		return 0;
	}
	@Override
	public ArrayList<String> getAllRecords(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_employees" : "employees";
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
			System.out.println();
		}
		return records;
	}
	public void printAllEmployees(boolean fromSampleTable) {
		ArrayList<String> actualEmployeeRecords = getAllRecords(fromSampleTable);
		for (String actualEmployeeRecord : actualEmployeeRecords) {
			System.out.println(actualEmployeeRecord);
		}
	}
	public ArrayList<Employee> getAllEmployees(boolean fromSampleTable) {
		String tableName = (fromSampleTable) ? "sample_employees" : "employees";
		ArrayList<Employee> employees = new ArrayList<Employee>();
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY employee_id;";
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Employee c = new EmployeeBuilder()
						.withEmployeeID(rs.getInt(1))
						.withUsername(rs.getString(2))
						.withPassword(rs.getString(3)) 
						.withIsAdmin(rs.getBoolean(4))
						.withAdminID(rs.getInt(5))
						.makeEmployee();
				employees.add(c);
			}
			statement.close(); rs.close();
			return employees;
		} catch (SQLException e) {
			e.printStackTrace(); System.out.println();
		}
		return employees;
	}
	public ArrayList<Integer> getOpenIDs(boolean fromSampleTable){
		ArrayList<Integer> openIDs = new ArrayList<Integer>();
		String tableName = (fromSampleTable) ? "sample_employees" : "employees";
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName + " ORDER BY employee_id;";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			
			int id, i = -1;
			while (rs.next() && i < getNumEmployees(fromSampleTable)) {
				id = rs.getInt(1);
				if (++i != id) openIDs.add(i);
				i = id;
			}
			statement.close(); rs.close();
			return openIDs;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println();
		}
		return openIDs;
	}
	public boolean isUnique(Employee e, boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_employees" : "employees";
		boolean unique = false;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT * FROM " + tableName
					+ " WHERE employee_id=?"
					+ " AND username=?";
					
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, e.getEmployeeID());
			preparedStatement.setString(2, e.getUsername());
			
			ResultSet rs = preparedStatement.executeQuery();
			
			String printString = "Employee with employee_id " + e.getEmployeeID() + " and username " + e.getUsername();
			
			while (rs.next()) {
				if (rs.getInt(1) == e.getEmployeeID() || rs.getString(2).equalsIgnoreCase(e.getUsername())) {
					System.out.println(printString + " is not unique.");
				}
				unique = false;
				preparedStatement.close(); rs.close();
				return unique;
			}
			System.out.println(printString + " is unique.");
			unique = true;
			preparedStatement.close(); rs.close();
			return unique;
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println();
		}
		return unique;
	}
	public int getMaxEmployeeID(boolean inSampleTable) {
		String tableName = (inSampleTable) ? "sample_employees" : "employees";
		int maxID = 0;
		try {
			connection = DBConnection.getConnection();
			String sql = "SELECT MAX(employee_id) FROM " + tableName + ";";
		
			Statement statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
				    ResultSet.CONCUR_READ_ONLY );
			ResultSet rs = statement.executeQuery(sql);
			
			if (rs.next()) maxID = rs.getInt(1);
			
			statement.close(); rs.close();
			return maxID;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println();
		}
		return maxID;
	}
	
}
