package customers;

import inspection.Helpers;
import people.Person;

public class UnverifiedCustomer extends Person{
	protected String firstName = null;
	protected String lastName = null;
	protected String telephone = null;
	protected String email = null;
	protected boolean isCitizen = false;
	protected boolean isEmployed = false;
	protected String employer = null;
	protected boolean verified = false;
	
	protected static int numUnverifiedCustomers = 0;
	private int unverifiedCustomerID = numUnverifiedCustomers;
	protected static int numTotalCustomers = 0;
	
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
	public Customer convertToCustomer(String username, String password) {
		Customer newCustomer = new CustomerBuilder()
				.withUsername(username)
				.withPassword(password)
				.makeCustomer(this);
		numUnverifiedCustomers--;
		return newCustomer;
	}
	public int getID() {
		return unverifiedCustomerID;
	}
	public void setID(int id) {
		this.unverifiedCustomerID = id;
	}
	public int getCount() {
		return numUnverifiedCustomers;
	}
	public void setCount(int count) {
		numUnverifiedCustomers = count;
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
		System.out.printf("%-4s%-15s%-15s%-14s%-35s%-10s%-10s%-35s\n", "ID", "FIRST_NAME", "LAST_NAME", "TELEPHONE", "EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER");
	}
	public void printRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		System.out.printf("%-4d%-15s%-15s%-14s%-35s%-10d%-10d%-35s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	public String getRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		return String.format("%-4d%-15s%-15s%-14s%-35s%-10d%-10d%-35s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	@Override
	public String getUsername() { return null; }
	@Override
	public String getPassword() { return null; }

}
