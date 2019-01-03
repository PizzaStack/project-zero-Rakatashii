package views;

import java.util.Scanner;

import customers.Customer;
import employees.Admin;
import employees.Employee;
import utility.Symbols;

public class Login {
	Scanner in;
	private String username, password;
	
	public Login() {
		in = new Scanner(System.in);
	}
	public String getUsername() {
		//System.out.println();
		System.out.print(Symbols.boxVertRight + " Username: ");
		username = in.nextLine();
		return username;
	}
	public String getPassword() {
		System.out.print(Symbols.boxVertRight + " Password: ");
		password = in.nextLine();
		return password;
	}
}
