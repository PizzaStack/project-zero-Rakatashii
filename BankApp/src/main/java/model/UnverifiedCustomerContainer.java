
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

import customers.UnverifiedCustomer;
import customers.Customer;
import customers.CustomerBuilder;
import people.Person;
import people.PersonContainer;

public class UnverifiedCustomerContainer<T> implements PersonContainer<Person>{

	private ArrayList<UnverifiedCustomer> unverified = new ArrayList<UnverifiedCustomer>(); 
	private Class<?> type = new UnverifiedCustomer().getClass();
	private String sampleTextFileName = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/text_files/unverified_customer_sample.txt";
	private String textFileName = "no_text_file_destination_for_unverified_customers";
	private String binaryFileName = "no_binary_file_destination_for_unverified_customers";

	public UnverifiedCustomerContainer() {
		super();
	}
	public Person Get(int index){
		return unverified.get(index);
	}
	public void Remove(UnverifiedCustomer unverifiedCustomer) {
		unverified.remove(unverifiedCustomer);
	}
	public ArrayList<UnverifiedCustomer> getArrayList(){
		return unverified;
	}
	public void setArrayList(ArrayList<UnverifiedCustomer> unverified) {
		this.unverified = unverified;
	}
	public Class<?> getType(){
		return type;
	}
	public ArrayList<UnverifiedCustomer> getArrayListFromSample() {
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
		return unverified;
	}
	public void printColumnNames() {
		System.out.printf("%-4s%-15s%-15s%-14s%-35s%-10s%-10s%-35s\n", "ID", "FIRST_NAME", "LAST_NAME", "TELEPHONE", "EMAIL", "CITIZEN?", "EMPLOYED?", "EMPLOYER");
	}
	public void printAll(boolean columnHeaders) {
		if (columnHeaders) printColumnNames();
		for (int i = 0; i < unverified.size(); i++) {
			unverified.get(i).printRow();
		}
	}
	public void printNthRow(int index) {
		unverified.get(index).printRow();
	}
	public void push(UnverifiedCustomer unverifiedCustomer) {
		/*if (this.type != person.getClass()) {
			System.out.println("Failed to push. Object must be of same type as Container class.");
			return;
		}*/
 		unverified.add(unverifiedCustomer);
 		unverifiedCustomer.setID(unverified.size()-1);
		if (unverifiedCustomer.getID() < unverified.size()-1) reindex(0);
	}
	public void removeAt(int index) {
		unverified.remove(index);
		reindex(index);
	}
	
	public void clear() {
		unverified.clear();
	}
	public int getSize() {
		if (unverified != null) return unverified.size();
		else return 0;
	}
	public void reindex(int start) {
		if (start >= unverified.size()) return;
		for (int i = start; i < unverified.size(); i++) {
			unverified.get(i).setID(i);
		}
		new UnverifiedCustomer().setCount(unverified.size());
		System.gc();
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
		/*
		if (file.exists() == false) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw e;
			}
		} */
		String line;
		String[] fields = new String[7];
    	try {
			Scanner cin = new Scanner(file);
			int oldArraySize = getSize();
			while (cin.hasNextLine()) {
				line = cin.nextLine();
				String delimiters = "\\|";
				fields = line.split(delimiters);
				UnverifiedCustomer newUnverified = new CustomerBuilder()
						.withFirstName(fields[0])
						.withLastName(fields[1])
						.withTelephone(fields[2])
						.withEmail(fields[3])
						.withIsCitizen(Boolean.parseBoolean(fields[4]))
						.withIsEmployed(Boolean.parseBoolean(fields[5]))
						.withEmployer(fields[6])
						.makeUnverifiedCustomer();
				//unverified.add(newUnverified);
			}
			reindex(oldArraySize);
			// TODO - add newUnverified to this.unverified
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
        	for (int i = 0; i < unverified.size(); i++) {
        		String row = unverified.get(i).getRow();
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
		for (int i = 0; i < unverified.size(); i++) {
			int size = unverified.get(i).getRow().length();
			String row = unverified.get(i).getRow();
			data = new byte[size];
			data = row.getBytes();
			if (i == 0 && row.charAt(0) != 0) {
				// TODO LOG: //System.out.println("reindexing UnverifiedCustomerArray...");
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
			if ((i == unverified.size() - 1) && (lastIndex > getSize())) {
				reindex(0);
				writeToBinaryFile(true);
			}
		}
	}
	public void appendToTextFile(UnverifiedCustomer unverifiedCustomer, boolean binary) {
		File file = new File(textFileName);
		try (
    		FileOutputStream fos = new FileOutputStream(file, true);
    		PrintStream ps = new PrintStream(fos);
    	){
        	System.setOut(ps);
    		String row = unverifiedCustomer.getRow();
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
	public void appendToBinaryFile(UnverifiedCustomer unverifiedCustomer, boolean create) throws IOException {
		File file = new File(binaryFileName);
		Path path = Paths.get(binaryFileName);
		reindex(0);
		byte data[];
		
		int size = unverifiedCustomer.getRow().length();
		String row = unverifiedCustomer.getRow();
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
	public void printAll() {
		for (UnverifiedCustomer e : this.unverified) {
			e.printRow();
		}
	}
	
	public boolean verifyLoginCredentials(String username, String password) {
		for (UnverifiedCustomer u : unverified) {
			if (u.getUsername() == username && u.getPassword() == password)
					return true;
		}
		return false;
	}
	
	/* // TODO Implement these
	public boolean hasDuplicate(Employee e) { return false; } 
	public static void rebase(String fileName) throws IOException {}
	*/
}
