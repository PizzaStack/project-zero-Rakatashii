package DAO;

import customers.Customer;

public interface CustomerDAOInterface {
	public boolean addCustomer(Customer customer);
	public boolean addSampleCustomer(Customer customer);
	public int getNumCustomers();
	public int getNumSampleCustomers();
}
