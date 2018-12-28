package accounts;

import customers.Customer;

public class SavingsAccount implements Account{
	private final double maxDepositAmmount = 1000000.0;
	private final double monthlyInterestRate = 0.05;
	private final double minInitialBalance = 1000.0;
	private final double minimumBalance = 0.0;
	
	protected String savingsID;
	// Watch, and switch back to int if needed
	// protected int savingsID; 
	private double balance;
	protected Customer primaryHolder, sharedHolder;
	private boolean joint;
	protected boolean flagged = false;
	
	CheckingAccount pairedCheckingAccount;
	
	public SavingsAccount(){
		// TODO savingsID = customerContainer.generateUniqueSavingsID();
		joint = false;
		flagged = false;
		balance = 0.0;
	}
	public SavingsAccount(String accNumber, double initialBalance){
		flagged = false;
		joint = false;
		if (initialBalance > minInitialBalance) balance = initialBalance;
		else flagged = true;
		savingsID = accNumber;
	}
	public SavingsAccount(String accNumber, double initialBalance, Customer primary){
		// TODO savingsID = customerContainer.generateUniqueSavingsID();
		joint = false;
		primaryHolder = primary;
		if (primaryHolder.hasCheckingAccount()) {
			pairedCheckingAccount = primaryHolder.getCheckingAccount();
		}
		
		savingsID = accNumber;
		
		if (initialBalance >= minInitialBalance) {
			balance = initialBalance;
			flagged = false;
		} else {
			balance = 0.0;
			flagged = true;
		}
	}
	public SavingsAccount(String accNumber, double initialBalance, Customer primary, Customer shared){
		// TODO savingsID = customerContainer.generateUniqueSavingsID();
		joint = true;
		primaryHolder = primary;
		if (primaryHolder.hasCheckingAccount()) {
			pairedCheckingAccount = primaryHolder.getCheckingAccount();
		} 
		
		savingsID = accNumber;
		
		sharedHolder = shared;
		if (sharedHolder.hasSavingsAccount() == false) sharedHolder.setSavingsAccount(this);
		
		if (sharedHolder.hasCheckingAccount() == false && pairedCheckingAccount != null) {
			sharedHolder.setCheckingAccount(this.pairedCheckingAccount);
			pairedCheckingAccount.sharedHolder = this.sharedHolder;
		} else if (pairedCheckingAccount.sharedHolder == null) 
			pairedCheckingAccount.sharedHolder = this.sharedHolder;
		
		if (initialBalance >= minInitialBalance) {
			balance = initialBalance;
			flagged = false;
		} else {
			balance = 0.0;
			flagged = true;
		}
	}
	@Override
	public void setBalance(double b) {
		if (b >= minimumBalance) balance = b;
		else return;
	}
	@Override
	public double getBalance() {
		if (balance < minimumBalance) flagged = true;
		return balance;
	}
	@Override
	public void deposit(double d) {
		if (d < maxDepositAmmount && flagged == false) balance -= d;
		else flagged = true;
	}
	@Override
	public void withdraw(double w) {
		if (balance - w > minimumBalance && flagged == false) balance -= w;
		else flagged = true;
	}
	@Override
	public void setID(String id) {
		this.savingsID = id;
		// TODO id = CustomerContainers.generateUniqueSavingsID;
	}
	@Override
	public String getID() {
		return savingsID;
	}
	@Override
	public boolean isJoint() {
		if (joint == false && primaryHolder.hasCheckingAccount() && primaryHolder.getCheckingAccount().isJoint()) {
			this.sharedHolder = primaryHolder.getCheckingAccount().sharedHolder;
			if (sharedHolder.hasSavingsAccount() == false) sharedHolder.setSavingsAccount(this);
			if (sharedHolder.hasCheckingAccount() == false) sharedHolder.setCheckingAccount(this.pairedCheckingAccount);
			joint = true;
		}
		return joint;
	}
	@Override
	public void setJointCustomer(Customer c) {
		sharedHolder = c;
		if (sharedHolder.hasSavingsAccount() == false) sharedHolder.setSavingsAccount(this);
		if (sharedHolder.hasCheckingAccount() == false && primaryHolder.hasCheckingAccount()) sharedHolder.setCheckingAccount(this.pairedCheckingAccount);
		if (this.pairedCheckingAccount != null) pairedCheckingAccount.setJointCustomer(c);
		joint = true;
	}
	@Override
	public Customer getJointCustomer() {
		if (isJoint()) return sharedHolder;
		else return null;
	}
	@Override
	public boolean verifyID() {
		//String pattern = "\/^[\\d]{7}$\/";
		return false;
	}
	@Override
	public void setOwner(Customer c) {
		primaryHolder = c;
	}
	@Override
	public Customer getOwner() {
		if (primaryHolder != null) return primaryHolder;
		else return null;
	}
	public void compoundMonthlyInterest() {
		balance *= monthlyInterestRate;
	}
	public void setPairedAccount(CheckingAccount account) {
		this.pairedCheckingAccount = account;
		if (this.pairedCheckingAccount.pairedSavingsAccount == null) this.pairedCheckingAccount.setPairedAccount(this);
	}
	public CheckingAccount getPairedAccount() {
		if (this.pairedCheckingAccount != null) return this.pairedCheckingAccount;
		else return null;
	}
	public void flag() {
		this.flagged = true;
	}
	public void unflag() {
		if (this.flagged == true) this.flagged = false;
	}
	public boolean isFlagged() {
		return flagged;
	}
}
