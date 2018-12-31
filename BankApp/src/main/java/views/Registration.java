package views;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import com.BankApp.BankApp;

import controller.LoginController;
import controller.MainMenuController;
import customers.CustomerBuilder;
import customers.UnverifiedCustomer;

public class Registration /* extends Customer */ {
	private UnverifiedCustomer unverifiedCustomer;
	
	final String yes_pattern = "^[Yy][Ee]?[Ss]?$";
	final String no_pattern = "^[Nn][Oo]?$";
	final String name_pattern = "[a-zA-Z]{0,20}";
	
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	
	private boolean isCitizen = false;
	private String citizenAnswer = null;
	private boolean isEmployed = false;
	private String isEmployedAnswer = null;
	private String employer = null;

	enum codes { FN, LN, T, EM, C, EMPLD, EMPLR }; // just use exceptions...
	Hashtable<codes, String> errorMessages = new Hashtable<codes, String>();	
	private ArrayList<String> registrationErrors = new ArrayList<String>();
	
	Scanner cin;
	
	public Registration() { 
		errorMessages.put(codes.FN, "Invalid first name.");
		errorMessages.put(codes.LN, "Invalid last name.");
		errorMessages.put(codes.T, "Invalid telephone number.");
		errorMessages.put(codes.EM, "Invalid email address.");
		errorMessages.put(codes.EMPLD, "Invalid input (\"Are you currently isEmployed\").");
		errorMessages.put(codes.EMPLR, "If you selected \"Yes\" for \'isEmployed\' field, you must specify employer.");
	}
	
	public UnverifiedCustomer beginForm() throws InterruptedException {
		boolean errors = true;
		
		@SuppressWarnings("resource")
		Scanner cin = new Scanner(System.in);
		
		//System.out.println();
		while (errors || registrationErrors.size() > 0) {
			
			if (errors && registrationErrors.size() > 0) {
				System.out.println();
				System.out.println("Errors: ");
				for (String registrationError : registrationErrors) {
					System.out.println(registrationError);
				}
				this.registrationErrors.clear();
				System.out.println();
			}
			errors = false;
			
			System.out.println("----- Customer Registration -----");
			System.out.print("First Name: ");
			this.firstName = cin.nextLine();
			if (!(validFirstName(firstName))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.FN));
				continue;
			}
			
			System.out.print("Last Name: ");
			this.lastName = cin.nextLine();
			if (!(validLastName(lastName))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.LN));
				continue;
			}
			System.out.print("Telephone: ");
			this.telephone = cin.nextLine();
			if (!(validTelephone(telephone))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.T));
				continue;
			}
			
			System.out.print("Email: ");
			this.email = cin.nextLine();
			if (!(validEmail(email))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.EM));
				continue;
			}
			
			System.out.print("Are You A US Citizen? ");
			this.citizenAnswer = cin.nextLine();
			if (!(validCitizenAnswer(citizenAnswer))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.C));
				continue;
			} 
			
			System.out.print("Are You Currently Employed? ");
			this.isEmployedAnswer = cin.nextLine();
			if (!(validEmployedAnswer(isEmployedAnswer))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.EMPLD));
				continue;
			} 
			
			if (this.isEmployed) {
				System.out.print("What Is The Name Of Your Employer? ");
				this.employer = cin.nextLine();
				if (!(validEmployer(employer))){
					errors = true;
					this.registrationErrors.add(errorMessages.get(codes.EMPLR));
					continue;
				} 
			}
			//System.out.println();
			
			if (errors == false) {
				unverifiedCustomer = new CustomerBuilder()
						.withFirstName(firstName)
						.withLastName(lastName)
						.withTelephone(telephone)
						.withEmail(email)
						.withIsCitizen(isCitizen)
						.withIsEmployed(isEmployed)
						.withEmployer(employer)
						.makeUnverifiedCustomer();
			} 
			this.registrationErrors.clear();
		}
		return unverifiedCustomer;
	}
	
	public boolean validFirstName(String firstName) {
		if (firstName != null && firstName.length() > 0 && firstName.length() <= 20) {
			if (firstName.matches(this.name_pattern))
				return true;
		}
		return false;
	}
	public boolean validLastName(String lastName) {
		//String name_pattern = "[a-zA-Z]{0,20}";
		if (lastName != null && lastName.length() > 0 && lastName.length() <= 20) {
			if (lastName.matches(this.name_pattern))
				return true;
		}
		return false;
	}
	public boolean validTelephone(String telephone) {
		String pattern = "[\\d]{3}-?[\\d]{3}-?[\\d]{4}";
		if (telephone != null && telephone.length() >= 10 && telephone.length() <= 12) {
			if (telephone.matches(pattern))
				return true;
		}
		return false;
	}
	public boolean validEmail(String email) {
		String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		if (email != null && email.matches(pattern) && email.length() <= 40) 
			return true;
		return false;
	}
	public boolean validCitizenAnswer(String isCitizenString) {
		if (isCitizenString != null) {
			if (isCitizenString.matches(this.yes_pattern)) {
				this.isCitizen = true;
				return true;
			} else if (isCitizenString.matches(this.no_pattern)) {
				this.isCitizen = false;
				return true;
			}
		}
		return false;
	}
	public boolean validEmployedAnswer(String isEmployedString) {
		if (isEmployedString != null) {
			if (isEmployedString.matches(this.yes_pattern)) {
				this.isEmployed = true;
				return true;
			} else if (isEmployedString.matches(this.no_pattern)) {
				this.isEmployed = false;
				return true;
			}
		}
		return false;
	}
	public boolean validEmployer(String employer) {
		if (employer != null && employer.length() > 0 && employer.length() <= 40) {
			if (employer.matches(name_pattern))
				return true;
		}
		return false;
	}
}

