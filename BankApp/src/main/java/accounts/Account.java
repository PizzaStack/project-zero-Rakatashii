package accounts;

import customers.Customer;

public interface Account {
	void setBalance(double b);
	double getBalance();
	void deposit(double d);
	void withdraw(double w);
	void setID(int id);
	int getID();
	boolean isJoint();
	
	void setOwner(Customer c);
	Customer getOwner();
	void setJointCustomer(Customer c);
	Customer getJointCustomer();
	
	boolean verifyID();
	public void flag();
	public void unflag();
	public boolean isFlagged();
}
