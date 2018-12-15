package customers;

import people.Person;

public class UnverifiedCustomer extends Person{
	String firstName, lastName;
	static int numUnverifiedCustomers = 0;
	int custID = numUnverifiedCustomers;
	boolean verified = false;
	
	public UnverifiedCustomer() { 
		super(); 
		custID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
	}
	public UnverifiedCustomer(String fName, String lName) {
		super(fName, lName);
		firstName = fName;
		lastName = lName;
		custID = numUnverifiedCustomers;
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
