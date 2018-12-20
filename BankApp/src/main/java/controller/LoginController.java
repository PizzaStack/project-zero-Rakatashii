package controller;

import employees.Employee;
import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.Login;

public class LoginController {
	private boolean verified = false;
	private Containers containers;
	private CustomerContainer customers;
	private EmployeeContainer<Employee> employees;
	private Login login = new Login();
	
	public void passContainers(Containers containers) {
		if (containers != null) this.containers = containers;
	}
	public boolean call(int selection) {
		boolean invalid = true;
		int tries = 1;
		while (invalid || tries <= 5) {
			String username = login.getUsername();
			String password = login.getPassword();
			if (selection == 2) {
				if (containers != null) customers = containers.getCustomerContainer();
			} else if (selection == 3) {
				if (containers != null) employees = containers.getEmployeeContainer();
			} else if (selection == 4) {
				if (containers != null) employees = containers.getAdminContainer();
			}
		}
		return verified;
	}
}
