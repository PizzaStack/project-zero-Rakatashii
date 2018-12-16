package customers;

import people.Person;

public class UnverifiedCustomer extends Person<Object>{
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	private boolean isCitizen = false;
	private boolean isEmployed = false;
	private String employer = null;
	
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
	public UnverifiedCustomer(String firstName, String lastName, String telephone, String email, boolean isCitizen, boolean isEmployed, String employer) {
		super(firstName, lastName);
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = isCitizen;
		this.isEmployed = isEmployed;
		this.employer = employer;
		
		unverifiedCustomerID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
	}

	@Override
	public void getInfo() {
		if (firstName != null) System.out.println("First name: " + firstName);
		if (lastName != null) System.out.println("First name: " + lastName);
		if (telephone != null) System.out.println("Telephone: " + firstName);
		if (email != null) System.out.println("email: " + email);
		if (isCitizen)
			System.out.println("US Citizen: true");
		else 
			System.out.println("US Citizen: false");

		if (isEmployed) {
			System.out.println("Currently employed: true");
			if (employer != null)
				System.out.println("Employer: " + employer);
		}
		else 
			System.out.println("Currently employed: false");
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
	
	/*
	Customer makeVerifiedCustomer() {
		Customer verifiedCustomer = new Customer(this.firstName, 
				this.lastName);
		// TODO find customer by ID and remove that customer.
		--numUnverifiedCustomers;
		//Customer.numCustomers++;
		return verifiedCustomer;
	} */
	
}
