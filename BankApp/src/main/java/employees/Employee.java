package employees;

import people.Person;

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
	@Override
	public void printRow() {
		// TODO Auto-generated method stub
	}
	@Override
	public String getRow() {
		// TODO Auto-generated method stub
		return null;
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
