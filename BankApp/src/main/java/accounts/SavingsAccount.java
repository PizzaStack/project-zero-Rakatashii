package accounts;

import DAO.AccountDAO;
import customers.Customer;
import model.AccountContainer;

public class SavingsAccount implements Account{
	private final double maxDepositAmmount = 1000000.0;
	private final double monthlyInterestRate = 0.05;
	private final double minInitialBalance = 1000.0;
	private final double minimumBalance = 0.0;
	
	protected String savingsID;
	private double balance;
	protected Customer primaryHolder, sharedHolder;
	private boolean joint;
	protected boolean flagged = false;
	CheckingAccount pairedCheckingAccount;
	
	private static AccountDAO accountDAO = new AccountDAO();
	
	public SavingsAccount(){
		savingsID = AccountContainer.generateNewID();
		// TODO check if ID already exists
		joint = false;
		flagged = false;
		balance = 0.0;
	}
	public SavingsAccount(Customer customer){
		this.primaryHolder = customer;
		savingsID = AccountContainer.generateNewID();
		// TODO check if ID already exists
		joint = false;
		flagged = false;
		balance = 0.0;
	}
	public SavingsAccount(String accNumber, double initialBalance){
		flagged = false;
		joint = false;
		if (initialBalance >= minInitialBalance) balance = initialBalance;
		else flagged = true;
		savingsID = accNumber;
	}
	public SavingsAccount(String accNumber, double initialBalance, Customer primary){
		// TODO savingsID = customerContainer.generateUniqueSavingsID();
		joint = false;
		primaryHolder = primary;
		
		if (primaryHolder.hasSavingsAccount() == false) primaryHolder.setSavingsAccount(this);
		if (primaryHolder.hasCheckingAccount()) {
			pairedCheckingAccount = primaryHolder.getCheckingAccount();
		} 
		
		if (pairedCheckingAccount != null && pairedCheckingAccount.isJoint()) {
			joint = true;
			this.setJointCustomer(pairedCheckingAccount.getJointCustomer());
			sharedHolder.setSavingsAccount(this);
		}
		
		savingsID = accNumber;
		
		if (initialBalance >= minInitialBalance) {
			balance = initialBalance;
			flagged = false;
		} else {
			balance = 0.0;
			flagged = true;
		}
		/*
		if ((primaryHolder != null && primaryHolder.isFlagged()) || (sharedHolder != null && sharedHolder.isFlagged()) || this.isFlagged() || pairedCheckingAccount.isFlagged()) {
			primaryHolder.flag();
			this.flag();
			if (pairedCheckingAccount != null) pairedCheckingAccount.flag();
			if (joint) sharedHolder.flag();
		}
		*/
	}
	public SavingsAccount(String accNumber, double initialBalance, Customer primary, Customer shared){
		// TODO savingsID = customerContainer.generateUniqueSavingsID();
		joint = true;
		primaryHolder = primary;
		savingsID = accNumber;
		sharedHolder = shared;
		
		primaryHolder.setHasJointAccounts(true);
		sharedHolder.setHasJointAccounts(true);
		primaryHolder.setSharedCustomer(sharedHolder);
		sharedHolder.setSharedCustomer(primaryHolder);
		
		if (primaryHolder.hasSavingsAccount() == false) primaryHolder.setSavingsAccount(this);
		if (this.pairedCheckingAccount != null) {
			if (primaryHolder.hasCheckingAccount() == false) primaryHolder.setCheckingAccount(pairedCheckingAccount);
			pairedCheckingAccount.setOwner(primaryHolder);
			if (sharedHolder.hasCheckingAccount() == false) sharedHolder.setCheckingAccount(pairedCheckingAccount);
			pairedCheckingAccount.setJointCustomer(sharedHolder);
		} else if (primaryHolder.hasCheckingAccount()) {
			pairedCheckingAccount = primaryHolder.getCheckingAccount();
			pairedCheckingAccount.setJointCustomer(sharedHolder);
			sharedHolder.setCheckingAccount(pairedCheckingAccount);
		} 
		
		if (initialBalance >= minInitialBalance) {
			balance = initialBalance;
			flagged = false;
		} else {
			balance = 0.0;
			flagged = true;
		}
		
		if (primaryHolder.isFlagged() || sharedHolder.isFlagged() || this.isFlagged() || pairedCheckingAccount.isFlagged()) {
			primaryHolder.flag();
			sharedHolder.flag();
			this.flag();
			pairedCheckingAccount.flag();
		}
	}
	/*
	public static void passAccountContainer(AccountContainer accounts) {
		accountContainer = accounts;
	}
	*/
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
		if (d < maxDepositAmmount && flagged == false) balance += d;
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
		return joint;
	}
	@Override
	public void setJointCustomer(Customer c) {
		if (joint == false) joint = true;
		sharedHolder = c;
		if (sharedHolder.hasSavingsAccount() == false) sharedHolder.setSavingsAccount(this);
		sharedHolder.setHasJointAccounts(true);
		if (this.pairedCheckingAccount != null) {
			pairedCheckingAccount.setJointCustomer(sharedHolder);
		}
		if (primaryHolder != null) {
			primaryHolder.setHasJointAccounts(true);
			primaryHolder.setSharedCustomer(sharedHolder);
			sharedHolder.setSharedCustomer(primaryHolder);
		}
	}
	@Override
	public Customer getJointCustomer() {
		if (isJoint()) 
			if (sharedHolder != null) return sharedHolder;
			else joint = false;
		return null;
	}
	@Override
	public boolean verifyID() {
		String pattern = "^[\\d]{7}$";
		if (savingsID.matches(pattern)) return true;
		else return false;
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
