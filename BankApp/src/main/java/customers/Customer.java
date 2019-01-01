package customers;

import java.util.ArrayList;

import DAO.CustomerDAO;
import accounts.Account;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import model.CustomerContainer;
import people.Person;
import utility.Helpers;

public class Customer extends UnverifiedCustomer{
	private static CustomerDAO customerDAO = new CustomerDAO();
	private static ArrayList<Integer> openIDs = null;
	public static boolean sampleMode;
	
	private String username, password;
	private String firstName, lastName;
	private String telephone, email;
	private boolean isCitizen, isEmployed;
	private String employer;
	protected boolean verified = true;
	
	private boolean flagged = false;
	private boolean joint = false;
	private Customer jointCustomer = null;
	private int jointCustomerID = -1;
	
	// TODO private static int numCustomers = customerDAO.getNumCustomersInDB();
	//protected static int numCustomers = customerDAO.getNumCustomers(true, sampleMode)+1;
	protected static int numCustomers = customerDAO.getMaxID(sampleMode)+1;
	private int custID = numCustomers;
	private int adminID = -1;
	
	static CustomerContainer customerContainer;
	static boolean customerContainerIsSet = false;
	
	SavingsAccount savingsAccount = null;
	CheckingAccount checkingAccount = null;
	
	public Customer() { 
		//super();
		custID = -1;
	}
	public Customer(String username, String password) {
		//super();
		this.username = username;
		this.password = password;
		custID = nextOpenID();
		if (custID == numCustomers) {
			if (custID > numCustomers) numCustomers = custID;
			++numCustomers;
		} 
		if (customerContainerIsSet) customerContainer.push(this);
		if (this.hasSavingsAccount() == false) makeNewAccounts();
	}
	public Customer(int id, String username, String password, String firstName, String lastName, String telephone, String email, boolean citizen, boolean employed, String employer, boolean joint, int jointCustomerID) {
		//super(firstName, lastName, telephone, email, citizen, employed, employer);
		if (id == -1) {
			custID = numCustomers;
			++numCustomers;
		} else this.custID = id;
		//System.out.println("customer " + username + " has id = " + this.custID);
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = citizen;
		this.isEmployed = employed;
		this.employer = employer;
		this.joint = joint;
		this.jointCustomerID = jointCustomerID;
		if (customerContainer.checkUniqueCustomerInfo(this)) {
			if (customerContainerIsSet) customerContainer.push(this);
		}
			/*if (custID != -1) {
				custID = numCustomers - 1;
				++numCustomers;
			} else custID = customerDAO.getNumCustomers(true, sampleMode) - 1;
		} else custID = -1;*/
		--numUnverifiedCustomers;
		if (this.hasSavingsAccount() == false) makeNewAccounts();
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
	public int getCustomerID() {
		return custID;
	}
	public void setCustomerID(int id) {
		custID = id;
	}
	public int getAdminID() {
		return adminID;
	}
	public void setAdminID(int id) {
		adminID = id;
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
	public String getUsername()    { return this.username; }
	@Override
	public String getPassword()    { return this.password; }
	public String getFirstname()   { return this.firstName; }
	public String getLastname()    { return this.lastName; }
	public String getTelephone()   { return this.telephone; }
	public String getEmail() 	   { return this.email; }
	public boolean getIsCitizen()  { return this.isCitizen; }
	public boolean getIsEmployed() { return this.isEmployed; }
	public String getEmployer()    { return this.employer; }

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
	/*
	@Override
	public void printRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		System.out.printf(String.format("%-10d%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-15s\n", 
				this.getID(), this.username, this.password, this.firstName, this.lastName, 
				this.telephone, this.email, citizen, employed, this.employer, 
				helper.boolToString(this.flagged)));
	} */
	public void printRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);

		SavingsAccount savings = this.getSavingsAccount();
		CheckingAccount checking = this.getCheckingAccount();
		System.out.printf(String.format("%-10d%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-20s$%-19.2f%-20s$%-19.2f%-20s%-20s%-20d\n", 
				this.getID(), this.username, this.password, this.firstName, this.lastName, 
				this.telephone, this.email, citizen, employed, this.employer, 
				savings.getID(), savings.getBalance(), checking.getID(), checking.getBalance(),
				helper.boolToString(this.isFlagged()), helper.boolToString(this.hasJointAccounts()), 
				this.getJointCustomerID()));

	}
	public void printRowWithAccountInfo() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		System.out.printf(String.format("%-10d%-20s%-20s%-15s%-15s%-14s%-40s%-10s%-10s%-35s%-10s\n", 
				this.getID(), this.username, this.password, this.firstName, this.lastName, 
				this.telephone, this.email, citizen, employed, this.employer, 
				helper.boolToString(this.flagged)));
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		SavingsAccount savings = this.getSavingsAccount();
		CheckingAccount checking = this.getCheckingAccount();
		return String.format("%-10d%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-20s$%-19.2f%-20s$%-19.2f%-20s%-20s%-20d\n", 
				this.getID(), this.username, this.password, this.firstName, this.lastName, 
				this.telephone, this.email, citizen, employed, this.employer, 
				savings.getID(), savings.getBalance(), checking.getID(), checking.getBalance(),
				helper.boolToString(this.isFlagged()), helper.boolToString(this.hasJointAccounts()), 
				this.getJointCustomerID());
	}
	
	public void setSavingsAccount(SavingsAccount savings) {
		this.savingsAccount = savings;
		this.savingsAccount.setOwner(this);
	}
	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}
	public void setCheckingAccount(CheckingAccount checking) {
		this.checkingAccount = checking;
		this.checkingAccount.setOwner(this);
	}
	public CheckingAccount getCheckingAccount() {
		return checkingAccount;
	}
	public boolean hasSavingsAccount() {
		if (savingsAccount == null) return false;
		return true;
	}
	public boolean hasCheckingAccount() {
		if (checkingAccount == null) return false;
		return true;
	}
	public void makeNewAccounts() {
		this.savingsAccount = new SavingsAccount(this);
		this.checkingAccount = new CheckingAccount(this);
		this.savingsAccount.setPairedAccount(this.checkingAccount);
		this.checkingAccount.setPairedAccount(this.savingsAccount);
	}
	public void flag() {
		this.flagged = true;
		if (this.hasSavingsAccount() && this.savingsAccount.isFlagged() == false) this.savingsAccount.flag();
		if (this.hasCheckingAccount() && this.checkingAccount.isFlagged() == false) this.checkingAccount.flag();
		if (this.jointCustomer != null && this.jointCustomer.isFlagged() == false) jointCustomer.flag();
	}
	public void unflag() {
		flagged = false;
		if (this.hasSavingsAccount() && this.savingsAccount.isFlagged() == true) this.savingsAccount.unflag();
		if (this.hasCheckingAccount() && this.checkingAccount.isFlagged() == true) this.checkingAccount.unflag();
		if (this.jointCustomer != null && this.jointCustomer.isFlagged() == true) jointCustomer.unflag();
		//log? System.out.println(this.username + " WAS FLAGGED IN CUSTOMER!!!");
	}
	public boolean isFlagged() {
		return flagged;
	}
	public static void synchronizeIDsWithDB() {
		openIDs = customerDAO.getOpenIDs(sampleMode);
	}
	public static void sampleModeOn() {
		if (openIDs != null) openIDs.clear();
		sampleMode = true;
		numCustomers = customerDAO.getNumCustomers(sampleMode);
	}
	public static void sampleModeOff() {
		if (openIDs != null) openIDs.clear();
		sampleMode = false;
		numCustomers = 0;
		numCustomers = customerDAO.getNumCustomers(sampleMode);
	}
	public int nextOpenID() {
		int openID = numCustomers;
		if (openIDs != null && openIDs.size() <= 1) synchronizeIDsWithDB();
		if (openIDs != null && openIDs.size() >= 1) {
			openID = openIDs.get(0);
			openIDs.remove(0);
			if (openIDs.size() == 0) openID = customerDAO.getMaxID(sampleMode);
			return openID;
		}
		return openID;
	}
	public boolean hasJointAccounts(){
		return joint;
	}
	public void setHasJointAccounts(boolean joint) {
		this.joint = joint;
	}
	public Customer getJointCustomer() {
		if (hasJointAccounts() && jointCustomer != null) return jointCustomer;
		else return null;
	}
	public void setJointCustomer(Customer c) {
		this.jointCustomer = c;
		if (jointCustomer != null) {
			joint = true;
			this.jointCustomerID = jointCustomer.getCustomerID();
			this.savingsAccount.setJointCustomer(c);
			this.checkingAccount.setJointCustomer(c);
		}
	}
	public int getJointCustomerID() {
		return jointCustomerID;
	}
	public void setJointCustomerID(int jointCustomerID) {
		this.jointCustomerID = jointCustomerID;
	}
	public void unjoin() {
		if (this.hasJointAccounts()) {
			if (this.getJointCustomer() != null) {
				this.jointCustomer.setHasJointAccounts(false);
				this.jointCustomer.setJointCustomerID(-1);
				if (this.jointCustomer.getJointCustomer() != null)
					this.jointCustomer.makeNewAccounts();
					this.jointCustomer.setJointCustomer(null);
			}
			setJointCustomer(null);
			this.joint = false;
			this.jointCustomerID = -1;
		}
	}
}
