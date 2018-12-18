package controller;

public class MainMenuController {
	public MainMenuController(){
		
	}
	public void selectOption(int selection) {
		if (selection == 1)
			RegistrationController.call();
	}
}
