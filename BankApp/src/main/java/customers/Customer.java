package customers;

import people.Person;

public class Customer extends Person<Object>{
	String firstName, lastName;
	protected static int numCustomers = 0;
	private int custID = numCustomers;
	boolean verified = true;
	
	public Customer() { 
		custID = -1;
		//++numCustomers;
		//--UnverifiedCustomer.numUnverifiedCustomers;
	}
	public Customer(String fName, String lName) {
		super();
		firstName = fName;
		lastName = lName;
		custID = numCustomers;
		++numCustomers;
		++numPeople;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	public int getCount() {
		return numCustomers;
	}
	public int getID() {
		return custID;
	}
	@Override
	public void setID(int id) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
}
