package controller;

import customers.UnverifiedCustomer;
import views.Registration;

public class RegistrationController {
	public static void call() {
		Registration newCustomerInfo = new Registration();
		UnverifiedCustomer newUnverifiedCustomer = newCustomerInfo.beginForm();
		System.out.println();
		// TODO: append newUnverifiedCustomer to file. */
	}
}
