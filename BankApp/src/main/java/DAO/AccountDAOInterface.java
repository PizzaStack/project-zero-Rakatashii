package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import accounts.CheckingAccount;
import accounts.SavingsAccount;
import database.DBConnection;

public interface AccountDAOInterface {
	//public boolean addAccounts(SavingsAccount savings, CheckingAccount checking, boolean toSampleTable);
	public int getNumAccounts(boolean fromSampleTable);
	public ArrayList<String> getAllRecords(boolean fromSampleTable);
	public void printAllAccounts(boolean fromSampleTable);
	
}
