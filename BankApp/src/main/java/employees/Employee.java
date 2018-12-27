package employees;

import DAO.EmployeeDAO;
import model.EmployeeContainer;
import people.Person;
import utility.Helpers;

public class Employee extends Person{
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	private String username, password;
	private boolean isAdmin = false;
	
	// TODO private static int numEmployees = employeeDAO.getNumEmployeesInDB();
	private static int numEmployees = 0;
	protected int employeeID = numEmployees; // is this really needed for Employees?
	
	static EmployeeContainer<Employee> employeeContainer;
	static boolean employeeContainerIsSet = false;
	
	public Employee() { 
		super(); 
		employeeID = -1;
	}
	public Employee(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		employeeID = numEmployees;
		++numEmployees;
		++numPeople;
		if (employeeContainerIsSet) employeeContainer.push(this);
	}
	public Employee(String username, String password, boolean isAdmin) {
		super();
		this.employeeID = numEmployees;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
		++numEmployees;
		++numPeople;
		if (employeeContainerIsSet) employeeContainer.push(this);
	}
	public static void passEmployeeContainer(EmployeeContainer<Employee> employees) {
		employeeContainer = employees;
		employeeContainerIsSet = true;
	}
	@Override
	public void getInfo() {
		System.out.println("EmployeeID: " + this.employeeID);
		if (username != null) System.out.println("Username: " + username);
		if (password != null) System.out.println("Password: " + password);
		if (isAdmin) 
			System.out.println("isAdmin: " + Boolean.toString(isAdmin));
	}
	@Override
	public int getCount() {
		return numEmployees;
	}
	@Override
	public int getID() {
		return employeeID;
	}
	@Override
	public String getUsername() { return this.username; }
	@Override
	public String getPassword() { return this.password; }
	public boolean getIsAdmin() { return this.isAdmin; }
	public void setNumEmployees(int count) {
		numEmployees = count;
	}
	
	@Override
	public void setID(int id) {
		this.employeeID = id;	
	}
	public void printColumnNames() {
		System.out.printf("%-4s%-20s%-20s%-10s%-10s\n", "ID", "USERNAME", "PASSWORD", "ADMIN", "ADMIN_ID");
	}
	@Override
	public void printRow() {
		// If going to make employee:admin_id null by default, may as well set isAdmin to false by default
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		System.out.printf("%-4d%-20s%-20s%-10s%-10s\n", this.getID(), this.username, this.password, isAdminStr, "null");
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		return String.format("%-4d%-20s%-20s%-10s%-10s\n", this.getID(), this.username, this.password, isAdminStr, "null");
	}
}
