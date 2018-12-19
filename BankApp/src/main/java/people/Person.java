package people;

import java.util.ArrayList;

public abstract class Person {
	String firstName;
	String lastName;
	public static int numPeople = 0;
	
	public Person() {}
	
	public abstract String getUsername();
	public abstract String getPassword();
	public abstract int getID();
	public abstract void setID(int id);
	public abstract void printRow();
	public abstract String getRow();
	public abstract void getInfo();
	
	public String getPersonClass() { 
		return this.getClass().getSimpleName();
	}
	public int getCount() {
		return numPeople;
	}

	public boolean isAdmin() { return false; } // changed from abstract - watch
}
















