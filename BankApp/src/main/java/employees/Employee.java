package employees;

import people.Person;

public class Employee extends Person{
	String firstName, lastName;
	static int numEmployees = 0;
	int EmployeeID = numEmployees; // is this really needed for Employees?
	
	public Employee() { 
		super(); 
		EmployeeID = numEmployees;
		++numEmployees;
	}
	public Employee(String fName, String lName) {
		super(fName, lName);
		EmployeeID = numEmployees;
		++numEmployees;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	@Override
	public int getCount() {
		return numEmployees;
	}
	
}
