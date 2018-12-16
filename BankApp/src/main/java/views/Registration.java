package views;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import customers.UnverifiedCustomer;
import customers.UnverifiedCustomerBuilder;

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

	static enum codes { FN, LN, T, EM, C, EMPLD, EMPLR };
	static Hashtable<codes, String> errorMessages = new Hashtable<codes, String>();	
	private ArrayList<String> registrationErrors = new ArrayList<String>();
	
	public Registration() { 
		errorMessages.put(codes.FN, "Invalid first name.");
		errorMessages.put(codes.LN, "Invalid last name.");
		errorMessages.put(codes.T, "Invalid telephone number.");
		errorMessages.put(codes.EM, "Invalid email address.");
		errorMessages.put(codes.EMPLD, "Invalid input (\"Are you currently isEmployed\").");
		errorMessages.put(codes.EMPLR, "If you selected \"Yes\" for \'isEmployed\' field, you must specify employer.");
	}
	
	public UnverifiedCustomer beginForm() {
		boolean errors = true;
		Scanner cin = new Scanner(System.in);
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
			
			System.out.println("---- Customer Registration -----");
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
			
			System.out.print("Are you a US Citizen? ");
			this.citizenAnswer = cin.nextLine();
			if (!(validCitizenAnswer(citizenAnswer))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.C));
				continue;
			} 
			
			System.out.print("Are you currently employed? ");
			this.isEmployedAnswer = cin.nextLine();
			if (!(validEmployedAnswer(isEmployedAnswer))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.EMPLD));
				continue;
			} 
			
			if (this.isEmployed) {
				System.out.print("What is the name of your employer? ");
				this.employer = cin.nextLine();
				if (!(validEmployer(employer))){
					errors = true;
					this.registrationErrors.add(errorMessages.get(codes.EMPLR));
					continue;
				} 
			}
			System.out.println();
			
			if (errors == false) {
				System.out.println("Success! Your application is pending administrative approval.");
				this.unverifiedCustomer = new UnverifiedCustomerBuilder()
						.withUnverifiedCustomerID(UnverifiedCustomer.getNumUnverifiedCustomers())
						.withFirstName(firstName)
						.withLastName(lastName)
						.withTelephone(telephone)
						.withEmail(email)
						.withIsCitizen(isCitizen)
						.withIsEmployed(isEmployed)
						.withEmployer(employer)
						.makeUnverifiedCustomer();
				cin.close();
			} 
			
			this.registrationErrors.clear();
		}
		cin.close();
		return unverifiedCustomer;
	}
	
	public boolean validFirstName(String firstName) {
		if (firstName != null && firstName.length() > 0 && firstName.length() <= 20) {
			if (firstName.matches(name_pattern))
				return true;
		}
		return false;
	}
	public boolean validLastName(String lastName) {
		String name_pattern = "[a-zA-Z]{0,20}";
		if (lastName != null && lastName.length() > 0 && lastName.length() <= 20) {
			if (lastName.matches(name_pattern))
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
			if (isCitizenString.matches(yes_pattern)) {
				this.isCitizen = true;
				return true;
			} else if (isCitizenString.matches(no_pattern)) {
				this.isCitizen = false;
				return true;
			}
		}
		return false;
	}
	public boolean validEmployedAnswer(String isEmployedString) {
		if (isEmployedString != null) {
			if (isEmployedString.matches(yes_pattern)) {
				this.isEmployed = true;
				return true;
			} else if (isEmployedString.matches(no_pattern)) {
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
	
	/* // Moved to UnverifiedCustomer - TODO: delete when sure 
	public void printFields() {
		if (firstName != null) System.out.println("First name: " + firstName);
		if (lastName != null) System.out.println("First name: " + lastName);
		if (telephone != null) System.out.println("Telephone: " + firstName);
		if (email != null) System.out.println("email: " + email);
		if (citizenAnswer != null) 
			if (isCitizen)
				System.out.println("US Citizen: true");
			else 
				System.out.println("US Citizen: false");
		if (isEmployedAnswer != null) {
			if (isEmployed) {
				System.out.println("Currently employed: true");
				if (employer != null)
					System.out.println("Employer: " + employer);
			}
			else 
				System.out.println("Currently employed: false");
		}
	}
	*/

}

