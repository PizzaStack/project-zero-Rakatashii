package utility;

import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import people.Person;

public class Helpers {
	private Customer sampleCustomer = new Customer();
	private UnverifiedCustomer sampleUnverified = new UnverifiedCustomer();
	private Employee sampleEmployee = new Employee();
	private Admin sampleAdmin = new Admin();
	Person[] people = { sampleUnverified, sampleCustomer, sampleAdmin, sampleEmployee };
	
	public Helpers(){
		super();
	}
	public void printPeopleCounts() {
		for (Person p : people) {
			System.out.println(p.getPersonClass() + " Count: " + p.getCount());
		}
	}
	public int[] getPeopleCounts(){
		int[] arr = {sampleCustomer.getCount(), sampleUnverified.getCount(), sampleEmployee.getCount(), sampleAdmin.getCount()};
		return arr;
	}
	public boolean intToBool(int b) {
		return (b == 0) ? false : true;
	}
	public int boolToInt(boolean b){
		return (b) ? 1 : 0;
	}
	public String boolToString(boolean b){
		return (b) ? "true" : "false";
	}
	public String boolIntToString(int b){
		return (b == 0) ? "false" : "true";
	}
	public String getStringIfNull(String s) {
		if (s == null) return "null";
		else return s;
	}
	public String intStringToBoolString(String b) {
		return (b == "0" || b == "false".toLowerCase()) ? "false" : "true";
	}
	public boolean stringToBool(String b) {
		return (b == "0" || b == "false".toLowerCase()) ? false : true;
	}
	

}
