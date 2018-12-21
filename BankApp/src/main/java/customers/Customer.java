package customers;

import accounts.Account;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import model.CustomerContainer;
import people.Person;
import utility.Helpers;

public class Customer extends UnverifiedCustomer{
	String username, password;
	String firstName, lastName;
	String telephone, email;
	boolean isCitizen, isEmployed;
	String employer;
	boolean verified = true;
	private boolean isLocked = false;
	
	private static int numCustomers = 0;
	private int custID = numCustomers;
	
	static CustomerContainer customerContainer;
	static boolean customerContainerIsSet = false;
	
	SavingsAccount savingsAccount = null;
	CheckingAccount checkingAccount = null;
	
	public Customer() { 
		super();		
	}
	public Customer(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		custID = numCustomers;
		++numCustomers;
		++numTotalCustomers;
		++numPeople;
		if (customerContainerIsSet) customerContainer.push(this);
		makeNewAccounts();
	}
	public Customer(String username, String password, String firstName, String lastName, String telephone, String email, boolean citizen, boolean employed, String employer) {
		super(firstName, lastName, telephone, email, citizen, employed, employer);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = citizen;
		this.isEmployed = employed;
		this.employer = employer;
		custID = numCustomers;
		++numCustomers;
		++numTotalCustomers;
		++numPeople;
		--numUnverifiedCustomers;
		if (customerContainerIsSet) customerContainer.push(this);
		makeNewAccounts();
	}
	public static void passCustomerContainer(CustomerContainer customers) {
		customerContainer = customers;
		customerContainerIsSet = true;
	}
	@Override
	public int getID() {
		return custID;
	}
	@Override
	public void setID(int id) {
		custID = id;
	}
	@Override
	public int getCount() {
		return numCustomers;
	}
	@Override
	public void setCount(int count) {
		numCustomers = count;
	}
	@Override
	public void getInfo() {
		System.out.println("ID: " + this.custID);
		if (this.username != null) System.out.println("Username: " + this.username);
		if (this.password != null) System.out.println("Password: " + this.password);
		if (this.firstName != null) System.out.println("First name: " + this.firstName);
		if (this.lastName != null) System.out.println("First name: " + this.lastName);
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
	public void printRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		System.out.printf("%-4d%-20s%-20s%-15s%-15s%-14s%-35s%-10d%-10d%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		int citizen = helper.boolToInt(this.isCitizen);
		int employed = helper.boolToInt(this.isEmployed);
		return String.format("%-4d%-20%-20%-15s%-15s%-14s%-35s%-10d%-10d%-35s\n", this.getID(), this.username, this.password, this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	@Override
	public String getUsername() { return this.username; }
	@Override
	public String getPassword() { return this.password; }
	
	public void setSavingsAccount(SavingsAccount savings) {
		savingsAccount = savings;
		if (checkingAccount != null) {
			if (checkingAccount.isJoint()) savingsAccount.setJointCustomer(checkingAccount.getJointCustomer());
		}
	}
	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}
	public void setCheckingAccount(CheckingAccount checking) {
		checkingAccount = checking;
		if (savingsAccount != null) {
			if (savingsAccount.isJoint()) savingsAccount.setJointCustomer(savingsAccount.getJointCustomer());
		}
	}
	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}
	public boolean hasSavingsAccount() {
		if (savingsAccount != null) return true;
		return false;
	}
	public boolean hasCheckingAccount() {
		if (savingsAccount != null) return true;
		return false;
	}
	private void makeNewAccounts() {
		this.savingsAccount = new SavingsAccount();
		this.checkingAccount = new CheckingAccount();
		this.savingsAccount.setPairedAccount(this.checkingAccount);
		this.checkingAccount.setPairedAccount(this.savingsAccount);
	}
}
