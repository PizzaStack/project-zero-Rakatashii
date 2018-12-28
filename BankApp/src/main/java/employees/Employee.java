package employees;

import java.util.ArrayList;

import DAO.CustomerDAO;
import DAO.EmployeeDAO;
import model.EmployeeContainer;
import people.Person;
import utility.Helpers;

public class Employee extends Person{
	private static EmployeeDAO employeeDAO = new EmployeeDAO();
	private static ArrayList<Integer> openIDs = null;
	private static boolean sampleMode;
	
	private String username, password;
	protected boolean isAdmin = false;
	
	// TODO private static int numEmployees = employeeDAO.getNumEmployeesInDB();
	protected static int numEmployees = 0;
	protected int employeeID; //= numEmployees; // is this really needed for Employees?
	protected int adminID = -1;
	
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
		--Admin.numAdmins;
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
		--Admin.numAdmins;
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
	public void setID(int id) {
		this.employeeID = id;	
	}
	public int getEmployeeID() {
		return this.employeeID;
	}
	public void setEmployeeID(int id) {
		this.employeeID = id;
	}
	public int getAdminID() {
		return this.adminID;
	}
	public void setAdminID(int id) {
		this.adminID = id;
	}
	@Override
	public String getUsername() { return this.username; }
	@Override
	public String getPassword() { return this.password; }
	public boolean getIsAdmin() { return this.isAdmin; }
	public void setNumEmployees(int count) {
		numEmployees = count;
	}
	
	public void printColumnNames() {
		System.out.printf("%-10s%-20s%-20s%-10s%-10s\n", "ID", "USERNAME", "PASSWORD", "ADMIN", "ADMIN_ID");
	}
	@Override
	public void printRow() {
		// If going to make employee:admin_id null by default, may as well set isAdmin to false by default
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		   System.out.printf("%-10d%-20s%-20s%-10s%-10s\n", this.getEmployeeID(), this.username, this.password, isAdminStr, String.valueOf(this.adminID));
	}
	@Override
	public String getRow() {
		Helpers helper = new Helpers();
		String isAdminStr = helper.boolToString(this.isAdmin);
		return String.format("%-10d%-20s%-20s%-10s%-10s\n", this.getEmployeeID(), this.username, this.password, isAdminStr, String.valueOf(this.adminID));
	}
	public static void synchronizeIDsWithDB() {
		openIDs = employeeDAO.getOpenIDs(sampleMode);
	}
	public static void sampleModeOn() {
		if (openIDs != null) openIDs.clear();
		sampleMode = true;
		numEmployees = 0;
	}
	public static void sampleModeOff() {
		if (openIDs != null) openIDs.clear();
		sampleMode = false;
		numEmployees = 0;
	}
	public int nextOpenID() {
		int openID = numEmployees;
		if (openIDs == null && openIDs.size() == 0) synchronizeIDsWithDB();
		if (openIDs != null && openIDs.size() >= 1) {
			openID = openIDs.get(0);
			openIDs.remove(0);
			if (openIDs.size() == 0) openID = employeeDAO.getMaxEmployeeID(sampleMode);
			return openID;
		}
		return openID;
	}
}
