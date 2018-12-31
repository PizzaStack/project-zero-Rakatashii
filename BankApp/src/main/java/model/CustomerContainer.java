package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DAO.CustomerDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import customers.Customer;
import customers.CustomerBuilder;
import customers.UnverifiedCustomer;
import people.Person;
import people.PersonContainer;
import utility.Helpers;

public class CustomerContainer implements PersonContainer<Person>{

	private ArrayList<Customer> customers = new ArrayList<Customer>(); 
	private Class<?> type = new Customer().getClass();
	private String sampleTextFileName = "/c/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/customer_sample.txt";
	//private String sampleAccountFileName = "/c/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/account_sample.txt";
	private String sampleAccountFileName = "text_files/account_sample.txt";
	private String textFileName = "no_text_file_destination_for_customers";
	private String binaryFileName = "no_binary_file_destination_for_customers";
	
	private CustomerDAO customerDAO;
	private ArrayList<Customer> DBCustomers;
	
	public CustomerContainer() {
		super();
	}
	
	public static void refreshDBCustomers() {
		//DBCustomers = new CustomerDAO().getAllCustomers(Customer.sampleMode);
	}
	
	public Person Get(int index){
		Customer customer = null;
		if (index < customers.size()) customer = customers.get(index);
		if (customer != null)
			System.out.println("applicantID = " + customer.getID() + " | index = " + index);
		if (customer != null && customer.getID() == index) return customer;
		else {
			System.out.println("Applicant Not Found At Index = " + index + "\nIterating Through CustomersIDs...");
			for (Customer c : customers) {
				if (c.getID() == index) return c;
			}
		}
		return null;
		// OLD DEF: return customers.get(index);
	}
	public ArrayList<Customer> getArrayList(){
		return customers;
	}
	public void setArrayList(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	public Class<?> getType(){
		return type;
	}
	public boolean userExists(Customer customer) {
		for (Customer c : customers) {
			if (customer.getUsername() == c.getUsername()) {
				return true;
			}
		}
		return false;
	}
	public ArrayList<Customer> getArrayListFromSample() {
		File file = new File(this.sampleTextFileName);
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			readIn(new File(sampleTextFileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customers;
	}
	public void printColumnNames() {
		System.out.printf("%-10s%-20s%-20s%-15s%-15s%-15s%-40s%-10s%-10s%-35s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n", 
				"ID", "USERNAME", "PASSWORD", "FIRST_NAME", "LAST_NAME", "TELEPHONE", 
				"EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER", "SAVINGS_NUMBER", "SAVINGS_AMOUNT", 
				"CHECKING_NUMBER", "CHECKING_AMOUNT", "FLAGGED", "JOINT", "JOINT_CUST_ID");
	}
	public void push(Customer customer) {
		/*if (this.type != person.getClass()) {
			System.out.println("Failed to push. Object must be of same type as Container class.");
			return;
		}*/
 		customers.add(customer);
		if (customer.getID() < customers.size()-1) reindex(0);
	}
	public void removeAt(int index) {
		customers.remove(index);
		reindex(index);
	}
	public void clear() {
		customers.clear();
	}
	public int getSize() {
		if (customers != null) return customers.size();
		else return 0;
	}
	public void reindex(int start) {
		if (start >= customers.size()) return;
		for (int i = start; i < customers.size(); i++) {
			customers.get(i).setID(i);
		}
		new Customer().setCount(customers.size());
		//System.gc();
	}
	public void setTextFileName(String textName) {
		textFileName = textName;
	}
	public void setBinaryFileName(String binaryName) {
		binaryFileName = binaryName;
	}
	public String getSampleFileName() {
		return sampleTextFileName;
	}
	public void printAll() {
		for (Customer c : customers) {
			 c.printRow();
		}
	}
	public void readIn(File file) throws IOException {
		/*
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		}*/
		String line;//, line2;
		File accountFile = new File(sampleAccountFileName);
		
		String[] fields = new String[9];
		//String[] accFields = new String[4];
    	try {
    		// Need to figure out safest way to scan two files at once so that customer
    		// can be synchronized with account
			Scanner cin = new Scanner(file);
			//Scanner cin2 = new Scanner(accountFile);
			int oldArraySize = getSize();
			while (cin.hasNextLine() /*&& cin2.hasNextLine()*/) {
				line = cin.nextLine();
				//line2 = cin2.nextLine();
				
				String delimiters = "\\|";
				fields = line.split(delimiters);
				Helpers helper = new Helpers();
				Customer customer = new CustomerBuilder()
						//.withID(Integer.parseInt(fields[0]))
						.withUsername(fields[0])
						.withPassword(fields[1])
						.withFirstName(fields[2])
						.withLastName(fields[3])
						.withTelephone(fields[4])
						.withEmail(fields[5])
						.withIsCitizen(Boolean.parseBoolean(fields[6]))
						.withIsEmployed(Boolean.parseBoolean(fields[7]))
						.withEmployer(fields[8])
						.makeCustomer();
				
				//accFields = line2.split(delimiters);
				//CheckingAccount checkingAccount = new CheckingAccount(accFields[0], Double.parseDouble(accFields[1]), customer);
				//customer.setCheckingAccount(checkingAccount);
				//SavingsAccount savingsAccount = new SavingsAccount(accFields[2], Double.parseDouble(accFields[3]), customer);
				//customer.setSavingsAccount(savingsAccount);
				
				// TODO // May be best to do this in a separate accountContainer, then 
				// iterate through the customers Array and create the accounts there.
				/*
				CheckingAccount checkingAccount = new CheckingAccount(fields[0], Integer.parseInt(fields[1]);
				SavingsAccount savingsAccount = new SavingsAccount(fields[2], Integer.parseInt(fields[3]);
				customer.setCheckingAccount(checkingAccount);
				customer.setSavingsAccount(savingsAccount);
				*/
			}
			reindex(oldArraySize);
			// TODO - add newUnverified to this.customers
			cin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getRowIndex(String row) {
		String firstIntInLinePattern = "^\\d+(?=\\W|\\|)";
        Pattern pattern = Pattern.compile(firstIntInLinePattern);
        Matcher matcher = pattern.matcher(row);
        String numString;
		numString = matcher.matches() ? matcher.group(1) : "0";
		return Integer.parseInt(numString);
	}
	public void writeToTextFile(boolean truncate, boolean binary) throws IOException {
		File file = new File(textFileName);
		boolean append = (truncate) ? false : true;
	
		if (file.exists() == false) file.createNewFile();
		
	    PrintStream ps = null;
	    try {
	        ps = new PrintStream(new FileOutputStream(textFileName, append));
        	for (int i = 0; i < customers.size(); i++) {
        		String row = customers.get(i).getRow();
        		ps.print(row);
        	}
	    } catch (IOException e) {
	        // TODO System.err.println("Error in writing to file");
	        throw e;
	    } finally {
	        if (ps != null) ps.close();
	    }

    	if (binary) try {
    		writeToBinaryFile(binary);
    	} catch (IOException e) {
    		System.out.println("Failed to write to binary file from UnverifiedEmployee.writeToTextFile method.");
    	} 
	}
	public void writeToBinaryFile(boolean truncate) throws IOException {
		File file = new File(binaryFileName);
		Path path = Paths.get(binaryFileName);
		
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		}
		reindex(0);
		
		byte data[];
		for (int i = 0; i < customers.size(); i++) {
			int size = customers.get(i).getRow().length();
			String row = customers.get(i).getRow();
			data = new byte[size];
			data = row.getBytes();
			if (i == 0 && row.charAt(0) != 0) {
				// TODO LOG: //System.out.println("reindexing CustomerArray...");
				reindex(0);
			}
			if (i == 0 && truncate) {
				try {
					Files.write(path, data, StandardOpenOption.TRUNCATE_EXISTING);
				} catch ( NoSuchFileException e) {
					file.createNewFile();
					writeToBinaryFile(false);
				}
			}
			else Files.write(path, data, StandardOpenOption.APPEND);
			int lastIndex = getRowIndex(row);
			if ((i == customers.size() - 1) && (lastIndex > getSize())) {
				reindex(0);
				writeToBinaryFile(true);
			}
		}
	}
	public void appendToTextFile(Customer customer, boolean binary) {
		File file = new File(textFileName);
		try (
    		FileOutputStream fos = new FileOutputStream(file, true);
    		PrintStream ps = new PrintStream(fos);
    	){
        	System.setOut(ps);
    		String row = customer.getRow();
    		System.out.print(row);
        	fos.flush();
        	ps.flush();
        	fos.close();
        	ps.close();
    	} catch (FileNotFoundException e) {
    		System.out.println("File could not be opened.");
    	} catch (IOException e) {
    		System.out.println("IOexception.");
    	} catch (Exception e) {
    		System.out.println("Some other error.");
    	} 
    	
    	if (binary) try {
    		writeToBinaryFile(false);
    	} catch (IOException e) {
    		System.out.println("Failed to write to binary file from UnverifiedEmployee.writeToTextFile method.");
    	} 
	}
	public void appendToBinaryFile(Customer customer, boolean create) throws IOException {
		File file = new File(binaryFileName);
		Path path = Paths.get(binaryFileName);
		reindex(0);
		byte data[];
		
		int size = customer.getRow().length();
		String row = customer.getRow();
		data = new byte[size];
		data = row.getBytes();
		try {
			Files.write(path, data, StandardOpenOption.APPEND);
		} catch ( NoSuchFileException e) {
			file.createNewFile();
			writeToBinaryFile(false);
		} 
	}

	public Customer verifyLoginCredentials(String username, String password) {
		for (Customer c : customers) {
			if (c.getUsername().equals(username) && c.getPassword().equals(password))
				return c;
		}
		DBCustomers = new CustomerDAO().getAllCustomers(Customer.sampleMode);
		for (Customer c : DBCustomers) {
			if (c.getUsername().equals(username) && c.getPassword().equals(password))
				return c;
		}
		return null;
	} 
	
	public boolean checkUniqueCustomerInfo(Customer customer) {
		for (Customer c : customers) {
			if (customer.getUsername().equals(c.getUsername()))
				return false;
		}
		return true;
	}
	public boolean checkUniqueAccountInfo(Customer customer) {
		for (Customer c : customers) {
			if (customer.getSavingsAccount().getID() == c.getSavingsAccount().getID())
				if (customer.getJointCustomerID() != c.getCustomerID())
					return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
