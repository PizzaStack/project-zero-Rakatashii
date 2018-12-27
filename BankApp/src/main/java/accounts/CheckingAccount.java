package accounts;

import customers.Customer;
import model.AccountContainer;

public class CheckingAccount implements Account {
	private final double maxDepositAmmount = 100000.0;
	private final double minimumInitialBalance = 0.0;
	private final double minimumBalance = -1000.0;
	
	String checkingID;
	// String prob better since there will be a lot of leading zeros
	//int checkingID;
	double balance;
	Customer primaryHolder, sharedHolder;
	boolean joint;
	boolean flagged = false;
	
	SavingsAccount pairedSavingsAccount;
	
	public CheckingAccount(){
		balance = 0.0;
		joint = false;
		flagged = false;
	}
	public CheckingAccount(String acc_num, double initial_balance, Customer primary){
		if (initial_balance > minimumInitialBalance) balance = initial_balance;
		primaryHolder = primary;
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
		joint = false;
		
		checkingID = acc_num;
		
		if (primary.hasSavingsAccount()) {
			pairedSavingsAccount = primary.getSavingsAccount();
			flagged = pairedSavingsAccount.flagged;
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
			flagged = pairedSavingsAccount.flagged;
		} else flagged = false;
	}
	public CheckingAccount(String acc_num, double initial_balance, Customer primary, Customer shared){
		if (initial_balance > minimumInitialBalance) balance = initial_balance;
		primaryHolder = primary;
		if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(this);
		
		checkingID = acc_num;
		
		if (primary.hasSavingsAccount()) {
			pairedSavingsAccount = primary.getSavingsAccount();
			flagged = pairedSavingsAccount.flagged;
		} else if (pairedSavingsAccount != null){
			primary.setSavingsAccount(pairedSavingsAccount);
			flagged = pairedSavingsAccount.flagged;
		} else flagged = false;
		
		sharedHolder = shared;
		if (sharedHolder != null) joint = true;
		sharedHolder.setCheckingAccount(this);
		if (pairedSavingsAccount != null) {
			this.sharedHolder.setSavingsAccount(pairedSavingsAccount);
			pairedSavingsAccount.sharedHolder = this.sharedHolder;
		}
	}
	@Override
	public void setBalance(double b) {
		if (pairedSavingsAccount != null && pairedSavingsAccount.flagged == true) this.flagged = true;
		if (b > minimumBalance && this.flagged == false) balance = b;
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
		if (pairedSavingsAccount != null && pairedSavingsAccount.flagged == true) this.flagged = true;
		if (d > maxDepositAmmount){
			flagged = true;
			return;
		} else if (this.flagged == false)
		balance += d;
	}
	@Override
	public void withdraw(double w) {
		if (pairedSavingsAccount != null && pairedSavingsAccount.flagged == true) this.flagged = true;
		if ((balance - w) > minimumBalance && this.flagged == false) balance -= 2;
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
