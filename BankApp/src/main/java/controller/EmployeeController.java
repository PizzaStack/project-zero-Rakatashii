package controller;

import java.io.IOException;

import model.Containers;
import views.CustomerOptions;
import views.EmployeeOptions;
import controller.MainMenuController.Menus;

public class EmployeeController{
	EmployeeOptions employeeOptions;
	public enum EmployeeMenus { EMPLOYEE };
	private int stop;
	
	//private LoginController login;
	private Containers containers;
	
	public EmployeeController(){
		
	}
	public void selectEmployeeOption(int selection) throws InterruptedException {
		if (selection == 1) {
			System.out.println("View Checking Account\n");
		} else if (selection == 2) {
			System.out.println("View Savings Account\n");
		} else if (selection == stop) {
			LoginController.logout();
			return;
		}
		else System.out.println(selection + " is not a valid input.\n");
	}
	public void begin(EmployeeMenus employeeMenu) throws InterruptedException {
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
	public void passContainers(Containers containers) {
		this.containers = containers;
	}
	/*
	void passLoginInfo(LoginController loginInfo){
		this.login = loginInfo;
	}
	*/
}
