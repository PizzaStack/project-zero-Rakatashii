package employees;

public class EmployeeBuilder {
	private String username = null;
	private String password = null;
	private boolean admin = false;
	
	int employeeID = -1;
	int adminID = -1;

	public EmployeeBuilder withEmployeeID(int id) {
		this.employeeID = id;
		return this;
	}
	public EmployeeBuilder withAdminID(int id) {
		this.adminID = id;
		return this;
	}
	public EmployeeBuilder withUsername(String username) {
		this.username = username;
		return this;
	}
	public EmployeeBuilder withPassword(String password) {
		this.password = password;
		return this;
	}
	public EmployeeBuilder withIsAdmin(boolean isAdmin) {
		this.admin = isAdmin;
		return this;
	}
	
	public Employee makeEmployee() {
		Employee newEmployee;
		if (employeeID == -1) newEmployee = new Employee(username, password, admin);
		else {
			newEmployee = new Employee(username, password, admin);
			newEmployee.setID(employeeID);
		}
		return newEmployee;
	}
	public Admin makeAdmin() {
		this.admin = true;
		Admin newAdmin;
		if (employeeID == -1) newAdmin = new Admin(username, password, admin);
		else {
			newAdmin = new Admin(username, password, admin);
			newAdmin.setID(adminID);
			newAdmin.setEmployeeID(employeeID);
		}
		return newAdmin;
	}
}