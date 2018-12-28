import java.util.ArrayList;

import customers.Customer;

public class useLater() {
	{
		System.out.println();
		Customer customer1 = new Customer("dave", "lennen");
		Customer customer2 = new Customer("Lindsay", "Lohan"); 
		Customer customer3 = new Customer("Harry", "Hacker"); 
		Customer customer4 = new Customer("user", "password");
		Customer customer5 = new Customer("skjdn", "sdklnc");
		customerContainer.printColumnNames();
		customerContainer.printAll();
	}
	
	{
		ArrayList<Integer> openSampleCustomerIDs = customerDAO.getOpenIDs(true);
		for (int id : openSampleCustomerIDs) {
			System.out.println("openID = " + id);
		}
		
		System.out.println();
		Customer customer1 = new Customer("dave", "lennen");
		Customer customer2 = new Customer("Lindsay", "Lohan"); 
		Customer customer3 = new Customer("Harry", "Hacker"); 
		Customer customer4 = new Customer("user", "password");
		Customer customer5 = new Customer("skjdn", "sdklnc");
		customerContainer.printColumnNames();
		customerContainer.printAll();
	}
}
 
