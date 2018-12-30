package accounts;

import DAO.AccountDAO;
import customers.Customer;
import model.AccountContainer;

public class CheckingAccount implements Account {
	private final double maxDepositAmmount = 100000.0;
	private final double minInitialBalance = 0.0;
	private final double minBalance = -1000.0;
	//public static boolean sampleMode;
	
	String checkingID;
	double balance;
	Customer primaryHolder = null, sharedHolder = null;
	boolean joint;
	boolean flagged;
	
	private static AccountDAO accountDAO = new AccountDAO();
	SavingsAccount pairedSavingsAccount;
	
	public CheckingAccount(){
		checkingID = AccountContainer.generateNewID();
		balance = 0.0;
		joint = false;
		flagged = false;
	}
	public CheckingAccount(Customer customer){
		this.primaryHolder = customer;
		checkingID = AccountContainer.generateNewID();
		balance = 0.0;
		joint = false;
		flagged = false;
	}
	public CheckingAccount(String acc_num, double initial_balance){
		flagged = false;
		joint = false;
		if (initial_balance >= minInitialBalance) balance = initial_balance;
		else flagged = true;
		checkingID = acc_num;
	}
	public CheckingAccount(String acc_num, double initial_balance, Customer primary){
		primaryHolder = primary;
		checkingID = acc_num;
		
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
		if (primary.hasSavingsAccount()) {
			pairedSavingsAccount = primary.getSavingsAccount();
			if (pairedSavingsAccount.isFlagged()) if (primary.isFlagged() == false) primary.flag();
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
			if (pairedSavingsAccount.isFlagged()) if (primary.isFlagged() == false)primary.flag();
		} else flagged = false;
		
		if (initial_balance >= minInitialBalance) balance = initial_balance;
		else {
			flag();
			balance = 0.0;
		}
	}
	public CheckingAccount(String acc_num, double initial_balance, Customer primary, Customer shared){
		primaryHolder = primary;
		sharedHolder = shared;
		checkingID = acc_num;
		
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
		
		if (primary.hasSavingsAccount()) {
			pairedSavingsAccount = primary.getSavingsAccount();
			flagged = pairedSavingsAccount.flagged;
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
			flagged = pairedSavingsAccount.flagged;
		} else flagged = false;
		
		joint = true;
		if (sharedHolder.hasJointAccounts() == false) sharedHolder.setHasJointAccounts(true);
		if (sharedHolder.hasCheckingAccount() == false) sharedHolder.setCheckingAccount(this);
		
		if (pairedSavingsAccount != null) {
			this.sharedHolder.setSavingsAccount(pairedSavingsAccount);
			pairedSavingsAccount.sharedHolder = this.sharedHolder;
		}
		
		if (initial_balance >= minInitialBalance) balance = initial_balance;
		else {
			this.balance = 0.0;
			flag();
		}
	}
	/*
	public static void passAccountContainer(AccountContainer accounts) {
		accountContainer = accounts;
	}
	*/
	@Override
	public void setBalance(double b) {
		if (pairedSavingsAccount != null && pairedSavingsAccount.flagged == true) this.flagged = true;
		if (b > minBalance && this.flagged == false) balance = b;
	}
	@Override
	public double getBalance() {
		if (balance < 0) {
			flagged = true; 
			if (pairedSavingsAccount != null) pairedSavingsAccount.flagged = true;
		}
		return balance;
	}
	@Override
	public void deposit(double d) {
		if (pairedSavingsAccount != null && pairedSavingsAccount.isFlagged()) flag();
		if (d > maxDepositAmmount){
			flagged = true;
			return;
		} 
		if (this.flagged == false) balance += d;
	}
	@Override
	public void withdraw(double w) {
		if (pairedSavingsAccount != null && pairedSavingsAccount.flagged == true) this.flagged = true;
		if ((balance - w) > minBalance && this.flagged == false) balance -= 2;
	}
	@Override
	public void setID(String id) {
		this.checkingID = id;
		// TODO checkingID = CustomerContainers.generateUniqueID()
	}
	@Override
	public String getID() {
		return checkingID;
	}
	@Override
	public boolean isJoint() {
		if (sharedHolder != null) joint = true;
		return joint;
	}
	@Override
	public void setJointCustomer(Customer c) {
		if (joint == false) joint = true;
		sharedHolder = c;
		if (sharedHolder.hasCheckingAccount() == false) sharedHolder.setCheckingAccount(this);
		sharedHolder.setHasJointAccounts(true);
		if (primaryHolder != null) {
			primaryHolder.setHasJointAccounts(true);
			primaryHolder.setSharedCustomer(sharedHolder);
			sharedHolder.setSharedCustomer(primaryHolder);
		}
	}
	@Override
	public Customer getJointCustomer() {
		if (sharedHolder != null) {
			if (joint == false) joint = true;
			return sharedHolder;
		}
		else return null;
	}
	@Override
	public boolean verifyID() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setOwner(Customer c) {
		primaryHolder = c;
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
	}
	@Override
	public Customer getOwner() {
		if (primaryHolder != null) return primaryHolder;
		else return null;
	}
	public void setPairedAccount(SavingsAccount account) {
		this.pairedSavingsAccount = account;
		if (pairedSavingsAccount.pairedCheckingAccount == null) this.pairedSavingsAccount.setPairedAccount(this);
	}
	public SavingsAccount getPairedAccount() {
		if (this.pairedSavingsAccount != null) return this.pairedSavingsAccount;
		else return null;
	}
	public void flag() {
		this.flagged = true;
	}
	public void unflag() {
		this.flagged = false;
	}
	public boolean isFlagged() {
		return flagged;
	}

}
