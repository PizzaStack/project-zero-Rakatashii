package accounts;

import DAO.AccountDAO;
import customers.Customer;
import model.AccountContainer;

public class CheckingAccount implements Account {
	protected final double maxDepositAmmount = 100000.0;
	private final double minBalance = 0.0;
	//public static boolean sampleMode;
	
	String checkingID;
	double balance;
	Customer primaryHolder = null, sharedHolder = null;
	boolean joint;
	boolean flagged;
	
	private static AccountDAO accountDAO = new AccountDAO();
	SavingsAccount pairedSavingsAccount;
	
	public CheckingAccount(){
		checkingID = AccountContainer.generateNewID(10);
		balance = 0.0;
		joint = false;
	}
	public CheckingAccount(Customer customer){
		this.primaryHolder = customer;
		checkingID = AccountContainer.generateNewID(10);
		balance = 0.0;
		joint = false;
	}
	public CheckingAccount(String acc_num, double initial_balance){
		joint = false;
		if (initial_balance >= minBalance) balance = initial_balance;
		else this.flag();
		
		checkingID = acc_num;
	}
	public CheckingAccount(String acc_num, double initial_balance, Customer primary){
		primaryHolder = primary;
		checkingID = acc_num;
		
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
		if (primary.hasSavingsAccount()) {
			pairedSavingsAccount = primary.getSavingsAccount();
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
		} 
		
		if (initial_balance >= minBalance) balance = initial_balance;
		else {
			this.flag();
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
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
		}
		
		joint = true;
		if (sharedHolder.hasJointAccounts() == false) sharedHolder.setHasJointAccounts(true);
		if (sharedHolder.hasCheckingAccount() == false) sharedHolder.setCheckingAccount(this);
		
		if (pairedSavingsAccount != null) {
			this.sharedHolder.setSavingsAccount(pairedSavingsAccount);
			pairedSavingsAccount.sharedHolder = this.sharedHolder;
		}
		
		if (initial_balance >= minBalance) balance = initial_balance;
		else {
			this.balance = 0.0;
			this.flag();
		}
	}
	/*
	public static void passAccountContainer(AccountContainer accounts) {
		accountContainer = accounts;
	}
	*/
	@Override
	public void setBalance(double b) {
		if (b >= minBalance) {
			if (this.flagged == false) balance = b;
		} else this.flag();
	}
	@Override
	public double getBalance() {
		return balance;
	}
	@Override
	public void deposit(double d) {
		if (d <= maxDepositAmmount && d >= 0.0){
			if (this.flagged == false) balance += d;
		} else this.flag();
	}
	@Override
	public void withdraw(double w) {
		if ((balance - w) >= minBalance) {
			if (this.flagged == false) balance -= w;
		} else this.flag();
	}
	public void transferToSavings(double amount) {
		if ((balance - amount) > minBalance)
			if (pairedSavingsAccount != null){
				if (this.flagged == false) {
					double prev_balance = pairedSavingsAccount.getBalance();
					pairedSavingsAccount.deposit(amount);
					if (pairedSavingsAccount.getBalance() > prev_balance) this.withdraw(amount);
				}
		} else this.flag();
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
		sharedHolder.setHasJointAccounts(true);
		sharedHolder.setCheckingAccount(this);
		
		if (this.pairedSavingsAccount != null) {
			if (pairedSavingsAccount.isJoint() == false) 
				pairedSavingsAccount.setJointCustomer(sharedHolder);
		}
		
		if (primaryHolder != null) {
			if (primaryHolder.hasJointAccounts() == false) primaryHolder.setHasJointAccounts(true);
			if (primaryHolder.getJointCustomer() == null) primaryHolder.setJointCustomer(sharedHolder);
			if (sharedHolder.getJointCustomer() == null) sharedHolder.setJointCustomer(primaryHolder);
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
		if (pairedSavingsAccount != null && pairedSavingsAccount.isFlagged() == false) pairedSavingsAccount.flag();
		if (primaryHolder != null && primaryHolder.isFlagged() == false) primaryHolder.flag();
		if (sharedHolder != null && sharedHolder.isFlagged() == false) sharedHolder.flag();
		//log? System.out.println(this.primaryHolder.getUsername() + " WAS FLAGGED IN CHECKING!!!");
	}
	public void unflag() {
		if (this.flagged == true) this.flagged = false;
		if (pairedSavingsAccount != null && pairedSavingsAccount.isFlagged() == true) pairedSavingsAccount.unflag();
		if (primaryHolder != null && primaryHolder.isFlagged() == true) primaryHolder.unflag();
		if (sharedHolder != null && sharedHolder.isFlagged() == true) sharedHolder.unflag();
	}
	public boolean isFlagged() {
		return this.flagged;
	}

}
