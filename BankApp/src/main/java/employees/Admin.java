package employees;

import people.Person;
import utility.Helpers;

public class Admin extends Employee{
	private String username, password;
	private String firstName, lastName;
	private boolean admin = true;
	private static int numAdmins = 0;
	private int adminID = numAdmins; // is this really needed for admins?
	private int employeeID;
	
	public boolean isAdmin() { return admin; }
	public Admin() { 
		super();
		//adminID = numAdmins;
		//++numAdmins;
	}
	public Admin(String username, String password) {
		super(username, password);
		adminID = numAdmins;
		this.employeeID = super.employeeID;
		numAdmins++;
	}
	public Admin(String username, String password, boolean isAdmin) {
		super(username, password, true);
		adminID = numAdmins;
		this.employeeID = super.employeeID;
		numAdmins++;
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
	public void printColumnNames() {
		System.out.printf("%-4s%-20s%-20s%-8s\n", "ID", "USERNAME", "PASSWORD", "ADMIN");
	}
	@Override
	public void printRow() {
		Helpers helper = new Helpers();
		int isAdmin = helper.boolToInt(this.admin);
		System.out.printf("%-4d%-20s%-20s%-8d\n", this.getID(), this.username, this.password, isAdmin);
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		int isAdmin = helper.boolToInt(this.admin);
		return String.format("%-4d%-20s%-20s%-8d\n", this.getID(), this.username, this.password, isAdmin);
	}
	@Override
	public int getCount() {
		return numAdmins;
	}
	@Override
	public int getID() {
		return adminID;
	}
	@Override
	public String getUsername() {
		return "";
	}
	@Override
	public String getPassword() {
		return "";
	}
	
	
}
