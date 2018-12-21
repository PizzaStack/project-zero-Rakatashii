package views;

import java.util.Scanner;

import customers.Customer;
import employees.Admin;
import employees.Employee;

public class Login {
	Scanner in;
	private String username, password;
	
	public Login() {
		in = new Scanner(System.in);
	}
	public String getUsername() {
		System.out.println();
		//System.out.println(MenuOptions.getLineSeparator());
		System.out.print("Username: ");
		username = in.nextLine();
		return username;
	}
	public String getPassword() {
		System.out.print("Password: ");
		password = in.nextLine();
		return password;
	}
}
