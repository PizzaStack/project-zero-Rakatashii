package employees;

import people.Person;
import utility.Helpers;

public class Employee extends Person{
	String username, password;
	boolean admin = false;
	//String firstName, lastName;
	public boolean isAdmin() { return admin; }
	
	private static int numEmployees = 0;
	protected int employeeID = numEmployees; // is this really needed for Employees?
	
	public Employee() { 
		super(); 
		//employeeID = numEmployees;
		//++numEmployees;
	}
	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		employeeID = numEmployees;
		
		++numEmployees;
		++numPeople;
	}
	public Employee(String username, String password, boolean isAdmin) {
		super();
		this.employeeID = numEmployees;
		this.username = username;
		this.password = password;
				
		++numEmployees;
		++numPeople;
	}
	@Override
	public void getInfo() {
		System.out.println("EmployeeID: " + this.employeeID);
		if (username != null) System.out.println("Username: " + username);
		if (password != null) System.out.println("Password: " + password);
		if (admin) 
			System.out.println("isAdmin: " + Boolean.toString(admin));
	}
	@Override
	public int getCount() {
		return numEmployees;
	}
	@Override
	public int getID() {
		return employeeID;
	}
	@Override
	public void setID(int id) {
		this.employeeID = id;	
	}
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
	public String getUsername() {
		return "";
	}
	@Override
	public String getPassword() {
		return "";
	}
	
}
