package employees;

import people.Person;

public class Employee extends Person<Object>{
	String firstName, lastName;
	String username, password;
	
	protected static int numEmployees = 0;
	private int employeeID = numEmployees; // is this really needed for Employees?
	
	public Employee() { 
		super(); 
		employeeID = numEmployees;
		//++numEmployees;
	}
	public Employee(Employee b) {
		b.firstName = this.firstName;
		b.lastName = this.lastName;
		b.employeeID = this.employeeID;
		++numEmployees;
	}
	public Employee(String fName, String lName) {
		super(fName, lName);
		employeeID = numEmployees;
		++numEmployees;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	public int getCount() {
		return numEmployees;
	}
	public int getID() {
		return employeeID;
	}
	@Override
	public void setID(int id) {
		this.employeeID = id;
		
	}
	
}
