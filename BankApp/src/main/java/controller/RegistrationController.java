package controller;

import customers.UnverifiedCustomer;
import views.Registration;

public class RegistrationController {
	public void call() {
		Registration newCustomerInfo = new Registration();
		UnverifiedCustomer newUnverifiedCustomer = newCustomerInfo.beginForm();
		System.out.println();
		// TODO: append newUnverifiedCustomer to file. */
	}
}
