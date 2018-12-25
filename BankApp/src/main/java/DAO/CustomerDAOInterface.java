package DAO;

import customers.Customer;

public interface CustomerDAOInterface {
	public boolean addSampleCustomer(Customer customer);
	public boolean addCustomer(Customer customer);
	public int getNumSampleCustomers();
	public int getNumCustomers();
}
