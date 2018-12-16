
package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import customers.UnverifiedCustomer;
import customers.UnverifiedCustomerBuilder;

public class UnverifiedCustomerData{
	static ArrayList<UnverifiedCustomer> unverified = new ArrayList<UnverifiedCustomer>();
	
	//private static UnverifiedCustomer nullCustomer = new UnverifiedCustomerBuilder().makeUnverifiedCustomer();
	
	public static void initiate() {
		clear();
	}
	public static ArrayList<UnverifiedCustomer> getArrayList(){
		return unverified;
	}
	public static void printAll() {
		UnverifiedCustomer.printColumnNames();
		for (int i = 0; i < unverified.size(); i++) {
			unverified.get(i).printRow();
		}
	}
	public static void push(UnverifiedCustomer person) {
 		unverified.add(person);
		if (person.getID() < unverified.size()-1) reIndex(0);
	}
	public static void removeAt(int index) {
		unverified.remove(index);
		reIndex(index);
	}
	public static void clear() {
		unverified.clear();
	}
	public static int getSize() {
		return unverified.size();
	}
	public static void reIndex(int start) {
		if (start >= unverified.size()) return;
		for (int i = start; i < unverified.size(); i++) {
			unverified.get(i).setID(i);
		}
		UnverifiedCustomer.setCount(unverified.size());
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
			reIndex(oldArraySize);
			cin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
