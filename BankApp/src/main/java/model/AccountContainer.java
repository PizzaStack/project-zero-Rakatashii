package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import DAO.AccountDAO;
import accounts.CheckingAccount;
import accounts.SavingsAccount;
import database.DBConnection;

public class AccountContainer {
	private ArrayList<CheckingAccount> checkingAccounts = new ArrayList<CheckingAccount>();
	private ArrayList<SavingsAccount> savingsAccounts = new ArrayList<SavingsAccount>();
	File sampleAccountsFile = new File("text_files/account_sample.txt");
	
	public AccountContainer(  ) { accountDAO = new AccountDAO(); }
	
	private AccountDAO accountDAO;
	
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
					fields[0] = generateNewID(10);
				}
				CheckingAccount checkingAccount = new CheckingAccount(fields[0], Double.parseDouble(fields[1]));
				while ((validAccountID(fields[0]) == false) || (verifyUniqueSavingsID(fields[0]) == false)) {
					fields[2] = generateNewID(10);
				}
				SavingsAccount savingsAccount = new SavingsAccount(fields[2], Double.parseDouble(fields[3]));
				
				checkingAccounts.add(checkingAccount);
				savingsAccounts.add(savingsAccount);
			}
			cin.close();
		} catch (FileNotFoundException e) {
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
		String pattern = "^[\\d]{10}$";
		if (id.matches(pattern)) return true;
		else return false;
	}
	public boolean verifyUniqueCheckingID(String id) {
		for (CheckingAccount c : checkingAccounts) {
			if (id == c.getID()) return false;
		}
		return true;
	}
	public boolean verifyUniqueSavingsID(String id) {
		for (SavingsAccount s : savingsAccounts) {
			if (id == s.getID()) return false;
		}
		return true;
	}

	public static String generateNewID(int idLength) {
		String numChars = "1234567890";
        StringBuilder idBuild = new StringBuilder();
        Random rand = new Random();
        while (idBuild.length() < idLength) { 
            int index = (int) (rand.nextFloat() * numChars.length());
            idBuild.append(numChars.charAt(index));
        }
        String newID = idBuild.toString();
        return newID;
	}
}
