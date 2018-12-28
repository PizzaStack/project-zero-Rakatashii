package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import accounts.CheckingAccount;
import accounts.SavingsAccount;

public class AccountContainer {
	private ArrayList<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();
	private ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
	//String accountsFileName = "/c/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/account_sample.txt";
	File sampleAccountsFile = new File("text_files/account_sample.txt");
	public AccountContainer() { }
	
	public File getSampleAccountsFile() { return sampleAccountsFile; }
	
	public void readIn(File file) {
		if (file == null) file = this.sampleAccountsFile;
		String line;
		String[] fields = new String[4];
		String delimitter = "\\|";
		
		try {
			Scanner cin = new Scanner(sampleAccountsFile);
			while (cin.hasNextLine()) {
				line = cin.nextLine();
				fields = line.split(delimitter);
				while ((validAccountID(fields[0]) == false) || (verifyUniqueCheckingID(fields[0]) == false)) { 
					System.out.println("infinite"); 
					fields[0] = generateNewID();
				}
				CheckingAccount checkingAccount = new CheckingAccount(fields[0], Double.parseDouble(fields[1]));
				while ((validAccountID(fields[0]) == false) || (verifyUniqueSavingsID(fields[0]) == false)) {
					System.out.println("infinite");
					fields[2] = generateNewID();
				}
				SavingsAccount savingsAccount = new SavingsAccount(fields[2], Double.parseDouble(fields[3]));
				
				checkingAccounts.add(checkingAccount);
				savingsAccounts.add(savingsAccount);
			}
			cin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<CheckingAccount> getCheckingAccounts() { return checkingAccounts; }
	public ArrayList<SavingsAccount> getSavingsAccounts() { return savingsAccounts; }
	
	public int getSize() {
		if (checkingAccounts.size() == savingsAccounts.size()) return checkingAccounts.size();
		else return 0;
	}
	
	public boolean validAccountID(String id) {
		String pattern = "^[\\d]{7}$";
		if (id.matches(pattern)) return true;
		else return false;
	}
	public String generateNewID() {
		String numChars = "Z1234567890";
        StringBuilder idBuild = new StringBuilder();
        Random rand = new Random();
        while (idBuild.length() < 7) { // length of the random string.
            int index = (int) (rand.nextFloat() * numChars.length());
            idBuild.append(numChars.charAt(index));
        }
        String newID = idBuild.toString();
        return newID;
	}
	public boolean verifyUniqueCheckingID(String id) {
		System.out.println("Checking ID is not unique");
		for (CheckingAccount c : checkingAccounts) {
			if (id == c.getID()) return false;
		}
		return true;
	}
	public boolean verifyUniqueSavingsID(String id) {
		System.out.println("Savings ID is not unique");
		for (SavingsAccount s : savingsAccounts) {
			if (id == s.getID()) return false;
		}
		return true;
	}
}
