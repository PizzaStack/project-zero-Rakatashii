package customers;

import employees.Employee;
import people.Person;

public class UnverifiedCustomer extends Person<Object>{
	String firstName, lastName;
	protected static int numUnverifiedCustomers = 0;
	private int unverifiedCustomerID = numUnverifiedCustomers;
	boolean verified = false;
	
	public UnverifiedCustomer() { 
		super(); 
		unverifiedCustomerID = numUnverifiedCustomers;
		//++numUnverifiedCustomers;
	}
	public UnverifiedCustomer(UnverifiedCustomer b) {
		b.firstName = this.firstName;
		b.lastName = this.lastName;
		b.unverifiedCustomerID = this.unverifiedCustomerID;
		++numUnverifiedCustomers;
	}
	public UnverifiedCustomer(String fName, String lName) {
		super(fName, lName);
		firstName = fName;
		lastName = lName;
		unverifiedCustomerID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getCount() {
		return numUnverifiedCustomers;
	}
	@Override
	public int getID() {
		return unverifiedCustomerID;
	}
	
	protected boolean verify() {
		this.verified = true;
		return verified;
	}
	
	Customer makeVerifiedCustomer() {
		Customer verifiedCustomer = new Customer(this.firstName, 
				this.lastName);
		// TODO find customer by ID and remove that customer.
		--numUnverifiedCustomers;
		Customer.numCustomers++;
		return verifiedCustomer;
	}
	

}
