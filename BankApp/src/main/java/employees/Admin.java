package employees;

import model.CustomerContainer;
import model.EmployeeContainer;
import people.Person;
import utility.Helpers;

public class Admin extends Employee{
	private String username, password;
	private String firstName, lastName;
	private boolean admin = true;
	private static int numAdmins = 0;
	private int adminID = numAdmins; // is this really needed for admins?
	private int employeeID;
	
	static EmployeeContainer<? extends Employee> adminContainer;
	static boolean adminContainerIsSet = false;
	
	public boolean isAdmin() { return super.isAdmin(); }
	public Admin() { 
		super();
		//adminID = numAdmins;
		//++numAdmins;
	}
	public Admin(String username, String password) {
		super(username, password);
		this.username = username;
		this.password = password;
		adminID = numAdmins;
		this.employeeID = super.employeeID;
		numAdmins++;
		if (adminContainerIsSet) adminContainer.push(this);
	}
	public Admin(String username, String password, boolean isAdmin) {
		super(username, password, true);
		this.username = username;
		this.password = password;
		adminID = numAdmins;
		this.employeeID = super.employeeID;
		numAdmins++;
		if (adminContainerIsSet) adminContainer.push(this);
	}
	
	public static void passAdminContainer(EmployeeContainer<? extends Employee> admins) {
		adminContainer = admins;
		adminContainerIsSet = true;
	}

	@Override
	public void getInfo() {
		System.out.println("AdminID: " + this.adminID);
		System.out.println("EmployeeID: " + this.employeeID);
		if (username != null) System.out.println("Username: " + username);
		if (password != null) System.out.println("Password: " + password);
		if (admin) 
			System.out.println("isAdmin?: " + Boolean.toString(admin));
	}
	@Override
	public void printRow() {
		Helpers helper = new Helpers();
		int isAdmin = helper.boolToInt(this.admin);
		System.out.printf("%-10d%-20s%-20s%-8d%-10d\n", this.employeeID, this.username, this.password, isAdmin, this.adminID);
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		int isAdmin = helper.boolToInt(this.admin);
		return String.format("%-10d%-20s%-20s%-8d-10d\n", this.employeeID, this.username, this.password, isAdmin, this.adminID);
	}
	@Override
	public int getCount() {
		return numAdmins;
	}
	@Override
	public int getID() {
		return adminID;
	}
	public void setID(int id) {
		this.adminID = id;
	}
	public void setEmployeeID(int id) {
		this.employeeID = id;
	}
	@Override
	public String getUsername() {
		return this.username;
	}
	@Override
	public String getPassword() {
		return this.password;
	}
	public int getEmployeeID() {
		return this.employeeID;
	}
	
	
}
