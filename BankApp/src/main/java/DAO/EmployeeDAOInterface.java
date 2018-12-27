package DAO;

import java.sql.Connection;

import employees.Employee;

public interface EmployeeDAOInterface {
	public boolean addEmployee(Employee employee);
	public boolean addSampleEmployee(Employee employee);
	public int getNumEmployees();
	public int getNumSampleEmployees();
}
