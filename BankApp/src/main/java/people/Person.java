package people;

public abstract class Person {
	String firstName;
	String lastName;
	public static int numPeople = 0;
	
	public Person() {}
	public Person(String fName, String lName){
		this.firstName = fName;
		this.lastName = lName;
		numPeople++;
	}
	
	public abstract void getInfo();
	
	public String getFirstName() {
		if (firstName != null && firstName.length() > 0)
			return firstName;
		else
			return "";
	}
	public String getLastName() {
		if (lastName != null && lastName.length() > 0)
			return lastName;
		else
			return "";
	}
	
	
	public String getType() { 
		return this.getClass().getSimpleName();
	}
	public int getCount() {
		return numPeople;
	}
}















