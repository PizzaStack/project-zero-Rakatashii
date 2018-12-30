package model;

import java.util.ArrayList;

import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;

public class Containers {
	/* small chance this will be useful
	ArrayList<Customer> customers;
	ArrayList<UnverifiedCustomer> unverified;
	ArrayList<Employee> employees;
	ArrayList<Employee> admins;
	*/
	public Containers getContainers() {
		return this;
	}
	
	private AccountContainer accountContainer;
	private CustomerContainer customerContainer;
	private UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer;
	private EmployeeContainer<Employee> employeeContainer;
	private EmployeeContainer<Employee> adminContainer;
	
	boolean customerIsSet = false;
	boolean unverifiedIsSet = false;
	boolean employeeIsSet = false;
	boolean adminIsSet = false;
	
	public void setAccountContainer(AccountContainer accountContainer) {
		this.accountContainer = accountContainer;
	}
	public void setCustomerContainer(CustomerContainer customersContainer) {
		this.customerContainer = customersContainer;
	}
	public void setCustomerArray(ArrayList<Customer> customers) {
		this.customerContainer.clear();
		this.customerContainer.setArrayList(customers);
	}
	public void setUnverifiedContainer(UnverifiedCustomerContainer<UnverifiedCustomer> unverifiedContainer) {
		this.unverifiedContainer = unverifiedContainer;
	}
	public void setUnverifiedArray(ArrayList<UnverifiedCustomer> unverified) {
		this.unverifiedContainer.clear();
		this.unverifiedContainer.setArrayList(unverified);
	}
	public void setEmployeeContainer(EmployeeContainer<Employee> employeeContainer) {
		this.employeeContainer = employeeContainer;
	}
	public void setEmployeeArray(ArrayList<Employee> employees) {
		this.employeeContainer.clear();
		this.employeeContainer.setArrayList(employees);
	}
	public void setAdminContainer(EmployeeContainer<Employee> adminContainer) {
		this.adminContainer = adminContainer;
	}
	public void setAdminArray(ArrayList<Employee> admins) {
		this.adminContainer.clear();
		this.adminContainer.setArrayList(admins);
	}
	
	public AccountContainer getAccountContainer() {
		return this.accountContainer;
	}
	public CustomerContainer getCustomerContainer(){
		return this.customerContainer;
	}
	public UnverifiedCustomerContainer<UnverifiedCustomer> getUnverifiedContainer(){
		return this.unverifiedContainer;
	}
	public EmployeeContainer<Employee> getEmployeeContainer(){
		return this.employeeContainer;
	}
	public EmployeeContainer<Employee> getAdminContainer(){
		return this.adminContainer;
	}
	
	public void printContainerSizes() {
		if (this.accountContainer != null) System.out.println("accountContainer.getSize() = " + this.accountContainer.getSize());
		else System.out.println("accountContainer.getSize() == null");
		if (this.customerContainer != null) System.out.println("customerContainer.getSize() = " + this.customerContainer.getSize());
		else System.out.println("customerContainer.getSize() == null");
		if (this.unverifiedContainer != null) System.out.println("unverifiedContainer.getSize() = " + this.unverifiedContainer.getSize());
		else System.out.println("unverifiedContainer.getSize() == null");
		if (this.employeeContainer != null) System.out.println("employeeContainer.getSize() = " + this.employeeContainer.getSize());
		else System.out.println("employeeContainer.getSize() == null");
		if (this.adminContainer != null) System.out.println("adminContainer.getSize() = " + this.adminContainer.getSize());
		else System.out.println("adminContainer.getSize() == null");
	}
}
