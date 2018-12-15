package consoleOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

import customers.Customer;

public class Registration /* extends Customer */ {
	final String yes_pattern = "^[Yy][Ee]?[Ss]?$";
	final String no_pattern = "^[Nn][Oo]?$";
	final String name_pattern = "[a-zA-Z]{0,20}";
	
	private String firstName = null;
	private String lastName = null;
	private String telephone = null;
	private String email = null;
	
	private boolean US_citizen = false;
	private String citizenAnswer = null;
	private boolean employed = false;
	private String employedAnswer = null;
	private String employerName = null;

	static enum codes { FN, LN, T, EM, C, EMPLD, EMPLR };
	static Hashtable<codes, String> errorMessages = new Hashtable<codes, String>();	
	private ArrayList<String> registrationErrors = new ArrayList<String>();
	
	public Registration() { 
		errorMessages.put(codes.FN, "Invalid first name.");
		errorMessages.put(codes.LN, "Invalid last name.");
		errorMessages.put(codes.T, "Invalid telephone number.");
		errorMessages.put(codes.EM, "Invalid email address.");
		errorMessages.put(codes.EMPLD, "Invalid input (\"Are you currently employed\").");
		errorMessages.put(codes.EMPLR, "If you selected \"Yes\" for \'Employed\' field, you must specify employer.");
	}
	
	public void beginForm() {
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
			this.employedAnswer = cin.nextLine();
			if (!(validEmployedAnswer(employedAnswer))) {
				errors = true;
				this.registrationErrors.add(errorMessages.get(codes.EMPLD));
				continue;
			} 
			
			if (this.employed) {
				System.out.print("Employer Name? ");
				this.employerName = cin.nextLine();
				if (!(validEmployerName(employerName))){
					errors = true;
					this.registrationErrors.add(errorMessages.get(codes.EMPLR));
					continue;
				} 
			}
			System.out.println();
			
			if (errors == false) {
				System.out.println("Success! Your application is pending administrative approval.");
				// TODO: make unverifiedEmployee object from data fields;
				cin.close();
			} 
			
			this.registrationErrors.clear();
		}
		cin.close();
	}
	
	boolean validFirstName(String firstName) {
		if (firstName != null && firstName.length() > 0 && firstName.length() <= 20) {
			if (firstName.matches(name_pattern))
				return true;
		}
		return false;
	}
	boolean validLastName(String lastName) {
		String name_pattern = "[a-zA-Z]{0,20}";
		if (lastName != null && lastName.length() > 0 && lastName.length() <= 20) {
			if (lastName.matches(name_pattern))
				return true;
		}
		return false;
	}
	boolean validTelephone(String telephone) {
		String pattern = "[\\d]{3}-?[\\d]{3}-?[\\d]{4}";
		if (telephone != null && telephone.length() >=10 && telephone.length() <= 12) {
			if (telephone.matches(pattern))
				return true;
		}
		return false;
	}
	boolean validEmail(String email) {
		String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
		if (email.matches(pattern)) 
			return true;
		return false;
	}
	boolean validCitizenAnswer(String US_citizenString) {
		if (US_citizenString.matches(yes_pattern)) {
			this.US_citizen = true;
			return true;
		} else if (US_citizenString.matches(no_pattern)) {
			this.US_citizen = false;
			return true;
		}
		return false;
	}
	boolean validEmployedAnswer(String employedString) {
		if (employedString.matches(yes_pattern)) {
			this.employed = true;
			return true;
		} else if (employedString.matches(no_pattern)) {
			this.employed = false;
			return true;
		}
		return false;
	}
	boolean validEmployerName(String employerName) {
		if (employerName != null && employerName.length() > 0 && employerName.length() <= 30) {
			if (employerName.matches(name_pattern))
				return true;
		}
		return false;
	}
	
	public void printFields() {
		System.out.println("Fields:");
		if (firstName != null) System.out.println("First name: " + firstName);
		if (lastName != null) System.out.println("First name: " + lastName);
		if (telephone != null) System.out.println("Telephone: " + firstName);
		if (email != null) System.out.println("email: " + email);
		if (citizenAnswer != null) 
			if (US_citizen)
				System.out.println("US Citizen: true");
			else 
				System.out.println("US Citizen: false");
		if (employedAnswer != null) {
			if (employed) {
				System.out.println("Currently Employed: true");
				if (employerName != null)
					System.out.println("Employer: " + employerName);
			}
			else 
				System.out.println("Currently Employed: false");
		}
	}

}

