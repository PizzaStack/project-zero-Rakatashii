package employees;

public class EmployeeBuilder {
	private String username = null;
	private String password = null;
	private boolean isAdmin = false;
	
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
		this.isAdmin = isAdmin;
		return this;
	}
	
	public Employee makeEmployee() {
		Employee newEmployee = new Employee(username, password, this.isAdmin);
		if (this.employeeID != -1) newEmployee.setID(employeeID);
		return newEmployee;
	}
	public Admin makeAdmin() {
		this.isAdmin = true;
		Admin newAdmin = new Admin(username, password, this.isAdmin);
		if (this.adminID != -1) newAdmin.setID(adminID);
		if (this.employeeID != -1) newAdmin.setEmployeeID(employeeID);
		return newAdmin;
	}
}