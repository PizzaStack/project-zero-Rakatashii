package DAO;

import accounts.CheckingAccount;
import accounts.SavingsAccount;

public interface AccountDAOInterface {
	public boolean addAccounts(SavingsAccount savings, CheckingAccount checking);
	public boolean addSampleAccounts(SavingsAccount savings, CheckingAccount checking);
	public int getNumAccounts();
	public int getNumSampleAccounts();
}
