package customers;

import inspection.Helpers;
import people.Person;

public class UnverifiedCustomer<T> extends Person{
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
	}
	public UnverifiedCustomer(String fName, String lName) { 
		super();
		firstName = fName;
		lastName = lName;
		this.unverifiedCustomerID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
		++numPeople;
	}
	public UnverifiedCustomer(String firstName, String lastName, String telephone, String email, boolean isCitizen, boolean isEmployed, String employer) {
		super();
		this.unverifiedCustomerID = numUnverifiedCustomers;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = isCitizen;
		this.isEmployed = isEmployed;
		this.employer = employer;
		++numUnverifiedCustomers;
		++numPeople;
	}

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
	public void printColumnNames() {
		System.out.printf("%-4s%-20s%-20s%-14s%-40s%-10s%-10s%-40s\n", "ID", "FIRST_NAME", "LAST_NAME", "TELEPHONE", "EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER");
	}
	public void printRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		System.out.printf("%-4d%-20s%-20s%-14s%-40s%-10d%-10d%-40s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	public String getRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		String row = String.format("%-4d%-20s%-20s%-14s%-40s%-10d%-10d%-40s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
		return row;
	}
	public int getCount() {
		return numUnverifiedCustomers;
	}
	public int getID() {
		return unverifiedCustomerID;
	}
	public void setID(int id) {
		this.unverifiedCustomerID = id;
	}
	public void setCount(int count) {
		numUnverifiedCustomers = count;
	}
	protected boolean verify() {
		this.verified = true;
		return verified;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return "";
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return "";
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
}
