package employees;

public class EmployeeBuilder {
	private String username = null;
	private String password = null;
	private boolean admin = false;

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
		Employee newEmployee = new Employee(username, password, admin);
		return newEmployee;
	}
}