package employees;

public class Admin extends Employee{
	String firstName, lastName;
	static int numAdmins = 0;
	int adminID = numAdmins; // is this really needed for admins?
	
	public Admin() { 
		super(); 
		adminID = numAdmins;
		++numAdmins;
	}
	public Admin(String fName, String lName) {
		super(fName, lName);
		adminID = numAdmins;
		++numAdmins;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	@Override
	public int getCount() {
		return numAdmins;
	}
	
}
