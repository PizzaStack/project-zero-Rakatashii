package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import employees.Employee;

public interface EmployeeDAOInterface {
	public boolean addEmployee(Employee employee, boolean toSampleTable);
	public int getNumEmployees(boolean fromSampleTable);
	public ArrayList<String> getAllRecords(boolean fromSampleTable);
	public void printAllEmployees(boolean fromSampleTable);
}
