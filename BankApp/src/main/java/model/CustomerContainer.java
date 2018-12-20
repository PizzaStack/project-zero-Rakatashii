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

import customers.Customer;
import customers.CustomerBuilder;
import people.Person;
import people.PersonContainer;

public class CustomerContainer implements PersonContainer<Person>{

	private ArrayList<Customer> customers = new ArrayList<Customer>(); 
	private Class<?> type = new Customer().getClass();
	private String sampleTextFileName = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/text_files/sample_customers.txt";
	private String textFileName = "no_text_file_destination_set";
	private String binaryFileName = "no_binary_file_destination_set";
	
	public CustomerContainer() {
		super();
	}
	public Person Get(int index){
		return customers.get(index);
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
		System.out.printf("%-4s%-20s-20s-15s%-15s%-14s%-35s%-10s%-10s%-35s\n", "ID", "USERNAME", "PASSWORD", "FIRST_NAME", "LAST_NAME", "TELEPHONE", "EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER");
	}
	public void printAll(boolean columnHeaders) {
		if (columnHeaders) printColumnNames();
		for (int i = 0; i < customers.size(); i++) {
			customers.get(i).printRow();
		}
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
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		}
		String line;
		String[] fields = new String[7];
    	try {
			Scanner cin = new Scanner(file, "UTF-8");
			int oldArraySize = getSize();
			while (cin.hasNextLine()) {
				line = cin.nextLine();
				String delimiters = "\\|";
				fields = line.split(delimiters);
				Customer newUnverified = new CustomerBuilder()
						.withID(Integer.parseInt(fields[0]))
						.withFirstName(fields[1])
						.withLastName(fields[2])
						.withTelephone(fields[3])
						.withEmail(fields[4])
						.withIsCitizen(Boolean.parseBoolean(fields[5]))
						.withIsEmployed(Boolean.parseBoolean(fields[6]))
						.withEmployer(fields[7])
						.makeCustomer();
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
	@Override
	public boolean verifyLoginCredentials(String username, String password) {
		for (Customer c : customers) {
			//System.out.println("Got here          (c): c.getUsername() = " + c.getUsername() + " - c.getPassword() = " + c.getPassword());
			//System.out.println("Got here (from login):        username = " +        username + " -        password = " + password);
			if (c.getUsername().equals(username) && c.getPassword().equals(password))
				return true;
		}
		return false;
	}
	
	/* // TODO Implement these
	public boolean hasDuplicate(Employee e) { return false; } 
	public static void rebase(String fileName) throws IOException {}
	*/
}
