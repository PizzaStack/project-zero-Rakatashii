package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import employees.Employee;

public interface AdminDAOInterface {
	public boolean addAdmin(Employee admin, boolean toSampleTable);
	public int getNumAdmins(boolean fromSampleTable);
	public ArrayList<String> getAllRecords(boolean fromSampleTable);
	public void printAllAdmins(boolean fromSampleTable);
}
