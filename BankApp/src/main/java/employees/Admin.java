package employees;

import people.Person;

public class Admin <T extends Employee<T>> extends Employee<T>{
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
