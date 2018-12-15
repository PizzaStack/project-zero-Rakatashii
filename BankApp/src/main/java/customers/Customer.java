package customers;

public class Customer extends UnverifiedCustomer{
	String firstName, lastName;
	protected static int numCustomers = 0;
	int custID = numCustomers;
	boolean verified = true;
	
	public Customer() { 
		super(); 
		custID = numCustomers;
		++numCustomers;
	}
	public Customer(String fName, String lName) {
		super(fName, lName);
		firstName = fName;
		lastName = lName;
		custID = numCustomers;
		++numCustomers;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	@Override
	public int getCount() {
		return numCustomers;
	}
}
