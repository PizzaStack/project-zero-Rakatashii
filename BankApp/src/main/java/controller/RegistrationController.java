package controller;

import DAO.UnverifiedCustomerDAO;
import customers.UnverifiedCustomer;
import model.Containers;
import views.MenuOptions;
import views.Registration;

public class RegistrationController {
	Containers containers;
	String lineSeparator = MenuOptions.getLineSeparator();
	//String menuEndLine = MenuOptions.getMenuEndLine();
	public void call() throws InterruptedException {
		Registration newCustomerInfo = new Registration();
		UnverifiedCustomer newUnverifiedCustomer = newCustomerInfo.beginForm();
		if (newUnverifiedCustomer != null) {
		
			UnverifiedCustomerDAO unverifiedDAO = new UnverifiedCustomerDAO();
			unverifiedDAO.addUnverifiedCustomer(newUnverifiedCustomer,  false);
			System.out.println();
			System.out.println("Success! Your Application Is Pending Administrative Approval.");
			Thread.sleep(2500);
			System.out.println();
		} else System.out.println("Failed to Register New Customer.\n");
	}
	public void passContainers(Containers containers) {
		if (containers != null) this.containers = containers;
	}
}
