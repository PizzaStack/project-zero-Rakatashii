package inspection;

import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import people.Person;

public class Helpers {
	private Person<?> sampleCustomer = new Customer();
	private Person<?> sampleUnverified = new UnverifiedCustomer();
	private Person<?> sampleEmployee = new Employee();
	private Person<?> sampleAdmin = new Admin();
	Person<?>[] people = { sampleUnverified, sampleCustomer, sampleAdmin, sampleEmployee };
	
	public Helpers(){
		super();
	}
	
	public void printPeopleCounts() {
		for (Person<?> p : people) {
			System.out.println(p.getPersonClass() + " Count: " + p.getCount());
		}
	}
	public int[] getPeopleCounts(){
		int[] arr = {sampleCustomer.getCount(), sampleUnverified.getCount(), sampleEmployee.getCount(), sampleAdmin.getCount()};
		return arr;
	}
	public int boolToInt(boolean b){
		return (b) ? 1 : 0;
	}
	
	

}
