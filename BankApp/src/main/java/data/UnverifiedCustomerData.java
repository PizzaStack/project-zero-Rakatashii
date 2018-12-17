
package data;

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
import customers.UnverifiedCustomerBuilder;

public class UnverifiedCustomerData{
	static ArrayList<UnverifiedCustomer> unverified = new ArrayList<UnverifiedCustomer>();
	static ArrayList<UnverifiedCustomer> unverifiedBackup = new ArrayList<UnverifiedCustomer>();
	
	static String sampleTextFile = "/Users/christianmeyer/java/project-zero-Rakatashii/BankApp/text_files/sample_unverified.txt";
	static String textFileName = "no_text_file_destination_set";
	static String binaryFileName = "no_binary_file_destination_set";
	
	//private static UnverifiedCustomer nullCustomer = new UnverifiedCustomerBuilder().makeUnverifiedCustomer();
	
	public static void initiate() {
		clear();
	}
	public static ArrayList<UnverifiedCustomer> getArrayList(){
		return unverified;
	}
	public static ArrayList<UnverifiedCustomer> getArrayListFromSample(){
		unverifiedBackup = unverified;
		readIn(new File(sampleTextFile)); // CAREFUL, changed readIn parameter to String, change back to File if 
		// the output stream is not working.
		return unverified;
	}
	public static void revert(){
		unverified = unverifiedBackup;
	}
	public static void printAll() {
		UnverifiedCustomer.printColumnNames();
		for (int i = 0; i < unverified.size(); i++) {
			unverified.get(i).printRow();
		}
	}
	public static void printNthRow(int index) {
		unverified.get(index).printRow();
	}
	public static void push(UnverifiedCustomer person) {
 		unverified.add(person);
		if (person.getID() < unverified.size()-1) reindex(0);
	}
	public static void removeAt(int index) {
		unverified.remove(index);
		reindex(index);
	}
	public static void clear() {
		unverified.clear();
	}
	public static int getSize() {
		return unverified.size();
	}
	public static void reindex(int start) {
		if (start >= unverified.size()) return;
		for (int i = start; i < unverified.size(); i++) {
			unverified.get(i).setID(i);
		}
		UnverifiedCustomer.setCount(unverified.size());
	}
	
	public static void setTextFileName(String textName) {
		textFileName = textName;
	}
	public static void setBinaryFileName(String binaryName) {
		binaryFileName = binaryName;
	}
	public static String getSampleFileName() {
		return sampleTextFile;
	}
	
	public static void readIn(File file) {
		String line;
		String[] fields = new String[7];
		ArrayList<String[]> all_fields;
    	try {
			Scanner cin = new Scanner(file, "UTF-8");
			int oldArraySize = getSize();
			while (cin.hasNextLine()) {
				line = cin.nextLine();
				String delimiters = "\\|";
				fields = line.split(delimiters);
				UnverifiedCustomer newUnverified = new UnverifiedCustomerBuilder()
						.withFirstName(fields[0])
						.withLastName(fields[1])
						.withTelephone(fields[2])
						.withEmail(fields[3])
						.withIsCitizen(Boolean.parseBoolean(fields[4]))
						.withIsEmployed(Boolean.parseBoolean(fields[5]))
						.withEmployer(fields[6])
						.makeUnverifiedCustomer();
			}
			reindex(oldArraySize);
			cin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static int getRowIndex(String row) {
		String firstIntInLinePattern = "^\\d+(?=\\W|\\|)";
        Pattern pattern = Pattern.compile(firstIntInLinePattern);
        Matcher matcher = pattern.matcher(row);
        String numString;
		numString = matcher.matches() ? matcher.group(1) : "0";
		return Integer.parseInt(numString);
	}
	
	public static void writeToTextFile(boolean truncate, boolean binary) throws IOException {
		File file = new File(textFileName);
		boolean append = (truncate) ? false : true;
		
	    PrintStream ps = null;
	    try {
	        ps = new PrintStream(new FileOutputStream(textFileName, append));
        	for (int i = 0; i < unverified.size(); i++) {
        		String row = unverified.get(i).getRow();
        		ps.print(row);
        	}
	        System.out.println("Write successful");
	    } catch (IOException e) {
	        System.err.println("Error in writing to file");
	        throw e;
	    } finally {
	        if (ps != null) ps.close();
	    }
		/*
		if (file.exists()){
			try (
		    		FileOutputStream fos = new FileOutputStream(textFileName, truncate);
		    		PrintStream ps = new PrintStream(fos);
		    	){
		        	System.setOut(ps);
		        	for (int i = 0; i < unverified.size(); i++) {
		        		String row = unverified.get(i).getRow();
		        		System.out.print(row);
		        	}
		        	fos.flush();
		        	ps.flush();
		        	fos.close();
		        	ps.close();
		    	} catch (FileNotFoundException e) {
		    		System.out.println("File could not be opened.");
		    	} catch (IOException e) {
		    		System.out.println("IOexception.");
		    	} 
			System.out.println("\nfrom body");
		} else file.createNewFile();
		*/
    	if (binary) try {
    		writeToBinaryFile(binary);
    	} catch (IOException e) {
    		System.out.println("Failed to write to binary file from UnverifiedEmployee.writeToTextFile method.");
    	} 
	}
	public static void writeToBinaryFile(boolean truncate) throws IOException {
		File file = new File(binaryFileName);
		Path path = Paths.get(binaryFileName);
		
		reindex(0);
		
		byte data[];
		for (int i = 0; i < unverified.size(); i++) {
			int size = unverified.get(i).getRow().length();
			String row = unverified.get(i).getRow();
			data = new byte[size];
			data = row.getBytes();
			if (i == 0 && row.charAt(0) != 0) {
				System.out.println("reindexing UnverifiedCustomerArray...");
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
	public static void appendToTextFile(UnverifiedCustomer unverifiedCustomer, boolean binary) {
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
    	System.out.println("?");
    	
    	if (binary) try {
    		writeToBinaryFile(false);
    	} catch (IOException e) {
    		System.out.println("Failed to write to binary file from UnverifiedEmployee.writeToTextFile method.");
    	} 
	}
	public static void appendToBinaryFile(UnverifiedCustomer unverifiedCustomer, boolean create) throws IOException {
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
	
	/* TODO: Don't delete - finish
	public static void rebase(String fileName) throws IOException {
		Path file = Paths.get(fileName);
		byte data[];
		for (int i = 0; i < unverified.size(); i++) {
			int size = unverified.get(i).getRow().length();
			data = new byte[size];
			data = unverified.get(i).getRow().getBytes();
			if (i == 0) Files.write(file, data, StandardOpenOption.CREATE);
			else Files.write(file, data, StandardOpenOption.APPEND);
		}
	}
	*/
}
