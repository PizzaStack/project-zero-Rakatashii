package customers;

public class UnverifiedCustomerBuilder {
	private int unverifiedCustomerID = 0;
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	private boolean isCitizen = false;
	private boolean isEmployed = false;
	private String employer = null;
	
	public UnverifiedCustomerBuilder withUnverifiedCustomerID(int id) {
		this.unverifiedCustomerID = UnverifiedCustomer.getNumUnverifiedCustomers();
		return this;
	} 
	public UnverifiedCustomerBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public UnverifiedCustomerBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public UnverifiedCustomerBuilder withTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}
	public UnverifiedCustomerBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	public UnverifiedCustomerBuilder withIsCitizen(boolean isCitizen) {
		this.isCitizen = isCitizen;
		return this;
	}
	public UnverifiedCustomerBuilder withIsEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
		return this;
	}
	public UnverifiedCustomerBuilder withEmployer(String employer) {
		this.employer = employer;
		return this;
	}
	public UnverifiedCustomer makeUnverifiedCustomer() {
		UnverifiedCustomer newUnverifiedCustomer = new UnverifiedCustomer(unverifiedCustomerID, firstName, lastName, telephone, email, isCitizen, isEmployed, employer);
		return newUnverifiedCustomer;
	}
}