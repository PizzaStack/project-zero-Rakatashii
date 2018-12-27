package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import accounts.CheckingAccount;
import accounts.SavingsAccount;

public class AccountContainer {
	private ArrayList<CheckingAccount> checkingAccounts;
	private ArrayList<SavingsAccount> savingsAccounts;
	File sampleAccountsFile = new File("/c/Users/Associate/java/project-zero-Rakatashii/BankApp/text_files/account_sample.txt");
	
	public AccountContainer() { }
	
	public File getSampleAccountsFile() { return sampleAccountsFile; }
	
	public void readIn(File file) {
		if (file == null) file = this.sampleAccountsFile;
		String line;
		Scanner in;
		String[] fields = new String[4];
		String delimitter = "\\|";
		
		try {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				line = in.nextLine();
				fields = line.split(delimitter);
				checkingAccounts.add(new CheckingAccount(fields[0], Double.parseDouble(fields[1]), null));
				savingsAccounts.add(new SavingsAccount(fields[0], Double.parseDouble(fields[1]), null));
			}
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
}
