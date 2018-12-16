package employees;

public class Admin extends Employee{
	String firstName, lastName;
	protected static int numAdmins = 0;
	protected int adminID = numAdmins; // is this really needed for admins?
	
	public Admin() { 
		super(); 
		adminID = numAdmins;
		//++numAdmins;
	}
	public Admin(Admin b) {
		b.firstName = this.firstName;
		b.lastName = this.lastName;
		b.adminID = this.adminID;
		this.adminID = -1;
	}
	public Admin(String fName, String lName) {
		super(fName, lName);
		adminID = numAdmins;
		numAdmins++;
	}

	@Override
	public void getInfo() {
		// TODO Auto-generated method stub
	}
	public int getCount() {
		return numAdmins;
	}
	@Override
	public int getID() {
		return adminID;
	}
	
	
}
