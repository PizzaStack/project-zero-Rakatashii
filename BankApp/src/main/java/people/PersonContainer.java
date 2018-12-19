package people;

import java.util.ArrayList;

import people.Person;

@SuppressWarnings("hiding")
public interface PersonContainer<Person> {
	public abstract ArrayList<? extends Person> getArrayList();
	public abstract Class<?> getType();
	public abstract ArrayList<? extends Person> getArrayListFromSample();
	public abstract void printColumnNames();
	public abstract void printAll();
	public abstract void printNthRow(int index);
	public abstract void removeAt(int index);
	public abstract void clear();
	public abstract int getSize();
	public abstract void reindex(int start);
	public abstract void setTextFileName(String txt);
	public abstract void setBinaryFileName(String bin);
	public abstract String getSampleFileName(); 
	public abstract int getRowIndex(String row);	
	
	// EmployeeContainer will also need push and the 4 file writing meths
	
	/* // TODO Implement these
	public boolean hasDuplicate(Employee e) { return false; } 
	public static void rebase(String fileName) throws IOException {}
	*/	
}
