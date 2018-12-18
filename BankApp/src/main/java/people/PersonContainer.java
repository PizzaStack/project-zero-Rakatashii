package people;

import java.util.ArrayList;
import people.Person;

@SuppressWarnings("hiding")
public class PersonContainer<Person> {
	ArrayList<Person> container;

	public PersonContainer() {
		container = new ArrayList<Person>();
	}
	public ArrayList<? extends Person> getArrayList(){
		return container;
	}
}
