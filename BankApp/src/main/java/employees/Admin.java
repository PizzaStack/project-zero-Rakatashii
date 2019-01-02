package employees;

import DAO.AdminDAO;
import model.CustomerContainer;
import model.EmployeeContainer;
import people.Person;
import utility.Helpers;

public class Admin extends Employee{
	private AdminDAO adminDAO = new AdminDAO();
	private String username, password;
	private String firstName, lastName;
	protected static int numAdmins = 0;
	private int adminID;
	private boolean isAdmin = true;
	
	static EmployeeContainer<Employee> adminContainer;
	static boolean adminContainerIsSet = false;
	
	public boolean isAdmin() { 
		super.isAdmin = this.isAdmin;
		return super.isAdmin(); 
	}
	public Admin() { 
		super();
		adminID = -1;
		employeeID = -1;
	}
	public Admin(String username, String password) {
		super(username, password);
		this.username = username;
		this.password = password;
		super.isAdmin = true;
		
		adminID = numAdmins;
		super.adminID = this.adminID;
		//this.employeeID = super.employeeID;
		if (adminContainerIsSet) adminContainer.push(this);
		numAdmins++;
	}
	public Admin(String username, String password, boolean isAdmin) {
		super(username, password, true);
		this.username = username;
		this.password = password;
		
		adminID = numAdmins;
		super.adminID = this.adminID;
		//this.employeeID = Employee.numEmployees-1;
		if (adminContainerIsSet && isAdmin == true) adminContainer.push(this);
		numAdmins++;
	}
	
	public static void passAdminContainer(EmployeeContainer<Employee> admins) {
		adminContainer = admins;
		adminContainerIsSet = true;
	}

	@Override
	public void getInfo() {
		System.out.println("AdminID: " + this.adminID);
		System.out.println("EmployeeID: " + this.employeeID);
		if (username != null) System.out.println("Username: " + username);
		if (password != null) System.out.println("Password: " + password);
		if (this.isAdmin) 
			System.out.println("isAdmin?: " + Boolean.toString(this.isAdmin));
	}
	@Override
	public void printRow() {
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		   System.out.printf("%-10d%-20s%-20s%-10s%-10d\n", this.employeeID, this.username, this.password, isAdminStr, this.adminID);
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		return String.format("%-10d%-20s%-20s%-10s%-10d\n", this.employeeID, this.username, this.password, isAdminStr, this.adminID);
	}
	@Override
	public int getCount() {
		return numAdmins;
	}
	@Override
	public int getID() {
		return this.adminID;
	}
	public void setID(int id) {
		this.adminID = id;
	}
	public int getEmployeeID() {
		return super.employeeID;
	}
	public void setEmployeeID(int id) {
		super.employeeID = id;
	}
	public int getAdminID() {
		return this.adminID;
	}
	public void setAdminID(int id) {
		this.adminID = id;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	// This is needed to work with separate containers for sample data and actual data
	public void setNumAdmins(int count) {
		numAdmins = count;
	}
	
	
}
