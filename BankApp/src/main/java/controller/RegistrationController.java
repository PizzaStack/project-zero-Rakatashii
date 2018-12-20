package controller;

import customers.UnverifiedCustomer;
import model.Containers;
import views.Registration;

public class RegistrationController {
	Containers containers;
	public void call() {
		Registration newCustomerInfo = new Registration();
		UnverifiedCustomer newUnverifiedCustomer = newCustomerInfo.beginForm();
		containers.getUnverifiedContainer().push(newUnverifiedCustomer);
	}
	public void passContainers(Containers containers) {
		if (containers != null) this.containers = containers;
	}
}
