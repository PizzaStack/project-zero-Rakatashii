package controller;

import java.io.IOException;

import model.Containers;
import model.CustomerContainer;
import model.EmployeeContainer;
import views.CustomerOptions;
import views.EmployeeOptions;
import controller.CustomerController.CustomerMenus;
import controller.MainMenuController.Menus;
import employees.Employee;

public class EmployeeController{
	EmployeeOptions employeeOptions;
	public enum EmployeeMenus { SELECTION, LOGOUT };
	private int stop;
	
	MainMenuController mainMenu = null;
	private EmployeeContainer<Employee> employeeContainer = null;
	
	public EmployeeController() { }
	public EmployeeController(MainMenuController mainMenu, EmployeeContainer<Employee> employeeContainer) { 
		this.mainMenu = mainMenu;
		this.employeeContainer = employeeContainer;
	}
	
	public void selectEmployeeOption(int selection) throws InterruptedException {
		if (selection == 1) {
			System.out.println("View Checking Account\n");
		} else if (selection == 2) {
			System.out.println("View Savings Account\n");
		} else if (selection == stop) {
			LoginController.logout();
			begin(EmployeeMenus.LOGOUT);
		}
		else {
			System.out.println(selection + " is not a valid input.\n");
			begin(EmployeeMenus.SELECTION);
		}
	}
	public void begin(EmployeeMenus employeeMenu) throws InterruptedException {
		if (employeeMenu == EmployeeMenus.LOGOUT) {
			LoginController.logout();
			mainMenu.begin(Menus.DEFAULT); 
			return;
		}  else if (employeeMenu == EmployeeMenus.SELECTION) {
			employeeOptions = new EmployeeOptions(employeeMenu);
			//employeeOptions.passLoginInfo(login);
			stop = employeeOptions.getEndCondition();
			int selection = -1;
			while (selection != stop) {
				if (selection == stop) selectEmployeeOption(stop);
				else selection = -1;
				try {
					selection = employeeOptions.displayAccountsMenu();
					if (this.employeeOptions.inBounds(selection)) selectEmployeeOption(selection);
					else continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/*
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	*/
	/*
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
	*/
}
