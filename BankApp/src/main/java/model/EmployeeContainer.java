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
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import employees.EmployeeBuilder;
import people.Person;
import people.PersonContainer;
import utility.Helpers;

public class EmployeeContainer<T> implements PersonContainer<Person>{
	private ArrayList<Employee> employees = new ArrayList<Employee>(); 
	private Class<? extends Person> type = new Employee().getClass();
	private String sampleTextFileName = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/text_files/sample_umployees.txt";
	private String textFileName = "no_text_file_destination_for_employees";
	private String binaryFileName = "no_binary_file_destination_for_employees";

	public EmployeeContainer() {
		super();
	}
	public Class<? extends Person> getType(){
		return type;
	}
	public ArrayList<Employee> getArrayList(){
		return this.employees;
	}
	public void setArrayList(ArrayList<Employee> employees) {
		this.employees = employees;
	}
	public void setSampleFile(String sampleFile) {
		sampleTextFileName = sampleFile;
	}
	/*
	public ArrayList<Employee> getArrayListFromSample(boolean adminOnly) { 
		File file = new File(this.sampleTextFileName);
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			readIn(new File(sampleTextFileName), adminOnly);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.employees;
	} */
	//@Override
	public void printColumnNames() {
		System.out.printf("%-10s%-20s%-20s%-10s%-10s\n", "EMPL_ID", "USERNAME", "PASSWORD", "ADMIN", "ADMIN_ID");
	}
	public void printAll() {
		printColumnNames();
		for (int i = 0; i < employees.size(); i++) {
			this.employees.get(i).printRow();
		}
	}
	public void printNthRow(int index) {
		this.employees.get(index).printRow();
	}
	public void push(Employee person) {
 		this.employees.add(person);
		if (person.getID() < employees.size()-1) reindex(0);
	}
	public void removeAt(int index) {
		this.employees.remove(index);
		reindex(index);
	}
	public void clear() {
		this.employees.clear();
	}
	public int getSize() {
		return this.employees.size();
	}
	public void reindex(int start) {
		if (start >= this.employees.size()) return;
		for (int i = start; i < employees.size(); i++) {
			this.employees.get(i).setID(i);
		}
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
	
	public void readIn(File file) throws IOException {
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		}
		String line;
		String[] fields = new String[3];
    	try {
			Scanner cin = new Scanner(file);
			int oldArraySize = getSize();
			while (cin.hasNextLine()) {
				line = cin.nextLine();
				String delimiters = "\\|";
				fields = line.split(delimiters);
				Helpers helper = new Helpers();
				boolean adminStatus = Boolean.parseBoolean(fields[2]);
				if (adminStatus == true) {
					Admin newAdmin = new EmployeeBuilder()
							.withUsername(fields[0])
							.withPassword(fields[1])
							.withIsAdmin(adminStatus)
							.makeAdmin();
				} else {
					Employee newEmployee = new EmployeeBuilder()
							//.withEmployeeID(Integer.parseInt(fields[0]))
							.withUsername(fields[0])
							.withPassword(fields[1])
							.withIsAdmin(adminStatus)
							.makeEmployee();
				} 
			}
			//reindex(oldArraySize);
			//TODO this.employees.add(newUnverified)
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
        	for (int i = 0; i < employees.size(); i++) {
        		String row = employees.get(i).getRow();
        		ps.print(row);
        	}
	    } catch (IOException e) {
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
		for (int i = 0; i < employees.size(); i++) {
			int size = employees.get(i).getRow().length();
			String row = employees.get(i).getRow();
			data = new byte[size];
			data = row.getBytes();
			if (i == 0 && row.charAt(0) != 0) {
				// TODO LOG: //System.out.println("reindexing EmployeeArray...");
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
			if ((i == employees.size() - 1) && (lastIndex > getSize())) {
				reindex(0);
				writeToBinaryFile(true);
			}
		}
	}
	public void appendToTextFile(Employee employee, boolean binary) {
		File file = new File(textFileName);
		try (
    		FileOutputStream fos = new FileOutputStream(file, true);
    		PrintStream ps = new PrintStream(fos);
    	){
        	System.setOut(ps);
    		String row = employee.getRow();
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
	public void appendToBinaryFile(Employee employee, boolean create) throws IOException {
		File file = new File(binaryFileName);
		Path path = Paths.get(binaryFileName);
		reindex(0);
		byte data[];
		
		int size = employee.getRow().length();
		String row = employee.getRow();
		data = new byte[size];
		data = row.getBytes();
		try {
			Files.write(path, data, StandardOpenOption.APPEND);
		} catch ( NoSuchFileException e) {
			file.createNewFile();
			writeToBinaryFile(false);
		} 
	}
	
	public boolean verifyLoginCredentials(String username, String password) {
		for (Employee e : employees) {
			if (e.getUsername().equals(username) && e.getPassword().equals(password))
				/*if (e.getClass() == Employee.class) return e.getID();
				else if (e.getClass() == Admin.class) return ((Admin)e).getID();
			//WATCH e.getClass may still interpret all admins as Employees. 
				*/
				return true;
		}
		return false;
	}

	/* // TODO Implement these
	public boolean hasDuplicate(Employee e) { return false; } 
	public static void rebase(String fileName) throws IOException {}
	*/
}
