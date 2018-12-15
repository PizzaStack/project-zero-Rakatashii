package inspection;

import customers.Customer;
import customers.UnverifiedCustomer;
import employees.Admin;
import employees.Employee;
import people.Person;

public class Helpers {
	static Person<?> sampleCustomer = new Customer();
	static Person<?> sampleUnverified = new UnverifiedCustomer();
	static Person<?> sampleEmployee = new Employee();
	static Person<?> sampleAdmin = new Admin();
	static Person<?>[] people = {sampleUnverified, sampleCustomer, sampleAdmin, sampleEmployee };
	
	public static void printPeopleCounts() {
		for (Person<?> p : people) {
			System.out.println(p.getType() + " Count: " + p.getCount());
		}
	}
	public static Person<?>[] getPeopleSampleArray(){
		return people;
	}

}
