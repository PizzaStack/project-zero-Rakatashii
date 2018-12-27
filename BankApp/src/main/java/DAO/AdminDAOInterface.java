package DAO;

import employees.Employee;

public interface AdminDAOInterface {
	public boolean addAdmin(Employee admin);
	public boolean addSampleAdmin(Employee admin);
	public int getNumAdmins();
	public int getNumSampleAdmins();
}
