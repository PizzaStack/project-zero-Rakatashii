package customers;

import data.UnverifiedCustomerData;
//import data.UnverifiedCustomerData;
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
		//unverifiedCustomerID = numUnverifiedCustomers;
		//++numUnverifiedCustomers;
	}
	/*
	public UnverifiedCustomer(UnverifiedCustomer b) {
		b.firstName = this.firstName;
		b.lastName = this.lastName;
		b.unverifiedCustomerID = this.unverifiedCustomerID;
		++numUnverifiedCustomers;
		//UnverifiedCustomerData.push(b);
	}
	*/
	public UnverifiedCustomer(String fName, String lName) { 
		//super(fName, lName);
		firstName = fName;
		lastName = lName;
		
		this.unverifiedCustomerID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
		UnverifiedCustomerData.push(this);
	}
	public UnverifiedCustomer(int unverifiedCustomerID, String firstName, String lastName, String telephone, String email, boolean isCitizen, boolean isEmployed, String employer) {
		//super(firstName, lastName);
		this.unverifiedCustomerID = UnverifiedCustomer.getNumUnverifiedCustomers();
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = isCitizen;
		this.isEmployed = isEmployed;
		this.employer = employer;
		
		//this.unverifiedCustomerID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
		UnverifiedCustomerData.push(this);
	}

	@Override
	public void getInfo() {
		System.out.println("ID: " + this.unverifiedCustomerID);
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
	int boolToInt(boolean b) {
		int i = (b) ? 1 : 0;
		return i;
	}
	public static void printColumnNames() {
		System.out.printf("%-4s%-20s%-20s%-14s%-40s%-10s%-10s%-40s\n", "ID", "FIRST_NAME", "LAST_NAME", "TELEPHONE", "EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER");
	}
	public void printRow() {
		int citizen = boolToInt(this.isCitizen);
		int employed = boolToInt(this.isEmployed);
		//System.out.printf("%*s%*s%*s%*s%*i%*i%*s\n", 20, this.firstName, 20, this.lastName, 12, this.telephone, 30, this.email, 2, boolToInt(this.isCitizen), 2, boolToInt(this.isCitizen), 30, this.employer);
		System.out.printf("%-4d%-20s%-20s%-14s%-40s%-10d%-10d%-40s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	public String getRow() {
		int citizen = boolToInt(this.isCitizen);
		int employed = boolToInt(this.isEmployed);
		//System.out.printf("%*s%*s%*s%*s%*i%*i%*s\n", 20, this.firstName, 20, this.lastName, 12, this.telephone, 30, this.email, 2, boolToInt(this.isCitizen), 2, boolToInt(this.isCitizen), 30, this.employer);
		String row = String.format("%-4d%-20s%-20s%-14s%-40s%-10d%-10d%-40s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
		//System.out.printf("%-4d%-20s%-20s%-14s%-40s%-10d%-10d%-40s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
		return row;
	}
	public int getCount() {
		return numUnverifiedCustomers;
	}
	public static int getNumUnverifiedCustomers() {
		return numUnverifiedCustomers;
	}
	@Override
	public int getID() {
		return unverifiedCustomerID;
	}
	public void setID(int id) {
		this.unverifiedCustomerID = id;
	}
	public static void setCount(int count) {
		numUnverifiedCustomers = count;
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
