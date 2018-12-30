package customers;

import model.UnverifiedCustomerContainer;

public class CustomerBuilder {
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	private boolean isCitizen = false;
	private boolean isEmployed = false;
	private String employer = null;
	private String username = UnverifiedCustomerContainer.generateNewUsername();
	private String password = UnverifiedCustomerContainer.generateNewPassword();
	private boolean isFlagged = false;
	private int id = -1; 

	public CustomerBuilder withID(int id) {
		this.id = id;
		return this;
	}
	public CustomerBuilder withUsername(String username) {
		if (username != null) this.username = username;
		return this;
	}
	public CustomerBuilder withPassword(String password) {
		if (password != null) this.password = password;
		return this;
	}
	public CustomerBuilder withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public CustomerBuilder withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public CustomerBuilder withTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}
	public CustomerBuilder withEmail(String email) {
		this.email = email;
		return this;
	}
	public CustomerBuilder withIsCitizen(boolean isCitizen) {
		this.isCitizen = isCitizen;
		return this;
	}
	public CustomerBuilder withIsEmployed(boolean isEmployed) {
		this.isEmployed = isEmployed;
		return this;
	}
	public CustomerBuilder withEmployer(String employer) {
		this.employer = employer;
		return this;
	}
	public CustomerBuilder withIsFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
		return this;
	}
	public UnverifiedCustomer makeUnverifiedCustomer() {
		UnverifiedCustomer newUnverifiedCustomer = new UnverifiedCustomer(id, firstName, lastName, telephone, email, isCitizen, isEmployed, employer);
		//if (this.id != -1) newUnverifiedCustomer.setID(this.id); 
		return newUnverifiedCustomer;
	}
	public Customer makeCustomer() {
		Customer newCustomer = new Customer(id, username, password, firstName, lastName, telephone, email, isCitizen, isEmployed, employer);
		/*if (this.id != -1) {
			newCustomer.setID(this.id);
			Customer.numCustomers--;
		}*/
		if (this.isFlagged) newCustomer.flag();
		return newCustomer;
	}
	public Customer makeCustomer(UnverifiedCustomer u) {
		Customer newCustomer = new Customer(id, username, password, u.firstName, u.lastName, u.telephone, u.email, u.isCitizen, u.isEmployed, u.employer);
		/* if (this.id != -1) {
			newCustomer.setID(this.id);
			Customer.numCustomers--;
		} */
		newCustomer.verified = true;
		if (this.isFlagged) newCustomer.flag();
		return newCustomer;
	}
	
}