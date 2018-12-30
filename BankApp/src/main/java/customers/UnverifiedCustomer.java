package customers;

import DAO.UnverifiedCustomerDAO;
import model.CustomerContainer;
import model.UnverifiedCustomerContainer;
import people.Person;
import utility.Helpers;

public class UnverifiedCustomer extends Person{
	UnverifiedCustomerDAO unverifiedCustomerDAO = new UnverifiedCustomerDAO();
	
	protected String firstName = null;
	protected String lastName = null;
	protected String telephone = null;
	protected String email = null;
	protected boolean isCitizen = false;
	protected boolean isEmployed = false;
	protected String employer = null;
	protected boolean verified = false;
	
	// TODO private static int numUnverifiedCustomers = unverifiedCustomerDAO.getNumUnverifiedCustomersInDB() + 1;
	protected static int numUnverifiedCustomers = 0;
	private int unverifiedID = numUnverifiedCustomers;
	protected static int numTotalCustomers = 0;
	
	static UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer;
	static boolean unverifiedContainerIsSet = false;
	
	public UnverifiedCustomer() { 
		super(); 
		unverifiedID = -1;
	}
	public UnverifiedCustomer(String fName, String lName) { 
		super();
		firstName = fName;
		lastName = lName;
		this.unverifiedID = numUnverifiedCustomers;
		++numUnverifiedCustomers;
		++numPeople;
		if (unverifiedContainerIsSet && this.getClass() == UnverifiedCustomer.class) unverifiedContainer.push(this); // when customers call super, don't push them into unverified
	}
	public UnverifiedCustomer(int id, String firstName, String lastName, String telephone, String email, boolean isCitizen, boolean isEmployed, String employer) {
		super();
		if (unverifiedID == -1) {
			unverifiedID = numUnverifiedCustomers;
			++numUnverifiedCustomers;
		} else this.unverifiedID = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.telephone = telephone;
		this.email = email;
		this.isCitizen = isCitizen;
		this.isEmployed = isEmployed;
		this.employer = employer;
		if (unverifiedContainerIsSet && this.getClass() == UnverifiedCustomer.class && (unverifiedContainer.checkUniqueUnverifiedCustomerInfo(this))) 
			unverifiedContainer.push(this); 
	}
	public Customer convertToCustomer(String username, String password) {
		if (unverifiedContainerIsSet) unverifiedContainer.Remove(this);
		Customer newCustomer = new CustomerBuilder()
				.withUsername(username)
				.withPassword(password)
				.makeCustomer(this);
		numUnverifiedCustomers--;
		return newCustomer;
	}
	public static void passUnverifiedContainer(UnverifiedCustomerContainer<UnverifiedCustomer> unverified) {
		unverifiedContainer = unverified;
		unverifiedContainerIsSet = true;
	}
	public int getID() 			   { return unverifiedID; }
	public String getFirstname()   { return this.firstName; }
	public String getLastname()    { return this.lastName; }
	public String getTelephone()   { return this.telephone; }
	public String getEmail() 	   { return this.email; }
	public boolean getIsCitizen()  { return this.isCitizen; }
	public boolean getIsEmployed() { return this.isEmployed; }
	public String getEmployer()    { return this.employer; }
	
	public void setID(int id) {
		this.unverifiedID = id;
	}
	public int getCount() {
		return numUnverifiedCustomers;
	}
	public void setCount(int count) {
		numUnverifiedCustomers = count;
	}
	public void getInfo() {
		System.out.println("ID: " + this.unverifiedID);
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
	public void printRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		System.out.printf("%-4d%-15s%-15s%-14s%-35s%-10s%-10s%-35s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	public String getRow() {
		Helpers helper = new Helpers();
		String citizen = helper.boolToString(this.isCitizen);
		String employed = helper.boolToString(this.isEmployed);
		return String.format("%-4d%-15s%-15s%-14s%-35s%-10s%-10s%-35s\n", this.getID(), this.firstName, this.lastName, this.telephone, this.email, citizen, employed, this.employer);
	}
	@Override
	public String getUsername() { return null; }
	@Override
	public String getPassword() { return null; }

}
