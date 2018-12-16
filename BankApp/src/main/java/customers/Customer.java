package customers;

import java.util.ArrayList;

public class Customer extends UnverifiedCustomer{
	String firstName, lastName;
	protected static int numCustomers = 0;
	private int custID = numCustomers;
	boolean verified = true;
	
	public Customer() { 
		//super(); 
		custID = numCustomers;
		//++numCustomers;
		//--UnverifiedCustomer.numUnverifiedCustomers;
	}
	public Customer(Customer b) {
		b.firstName = this.firstName;
		b.lastName = this.lastName;
		b.custID = this.custID;
		++numCustomers;
	}
	public Customer(String fName, String lName) {
		//super(fName, lName); // Can't do this because that would increase numUnverifiedCustomers
		firstName = fName;
		lastName = lName;
		custID = numCustomers;
		++numCustomers;
		--UnverifiedCustomer.numUnverifiedCustomers;
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
}
