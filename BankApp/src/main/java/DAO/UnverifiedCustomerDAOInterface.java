package DAO;

import customers.UnverifiedCustomer;

public interface UnverifiedCustomerDAOInterface {
	public boolean addSampleUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer);
	public boolean addUnverifiedCustomer(UnverifiedCustomer unverifiedCustomer);
	public int getNumSampleUnverifiedCustomers();
	public int getNumUnverifiedCustomers();
}
