import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		
		//initializing variables
		//TODO: Modify the initialization of the LibraryManagerController based on the new constructor
		LibraryManagerBrain lmc = new LibraryManagerBrain();
		
		//container for user input (result of readUserInput function)
		String userInput;
		//variable that controls when to stop the main loop
		boolean contin = true;
		//used to keep asking when the provided input is not valid(may be substituted by a funtion in LibraryManagerController)
		boolean invalidInput = true;
		
		do {
			//LibraryManagerController initial Menu message
			System.out.println(lmc.getMenu());
			
			//getting the user input and checking if it is valid based on the value returned by userIsA method
			invalidInput = !lmc.userInputWas(readUserInput());
			while(invalidInput){
				System.out.println("That is not a valid input. Try again");
				System.out.println(lmc.getMenu());
				invalidInput = !lmc.userInputWas(readUserInput());
			}
			
			//TODO: check if continue or stop use thecontin variable for that
		} while (contin);

	}
	
	//function used to read and return the user input
	//it catches any exception but does not deal with the input being the expected string
	public static String readUserInput()
	{
		//initializing variables
		String userInput = "";
		Scanner sc = new Scanner(System.in);
		boolean invalidInput = true;
		
		while (invalidInput) {
			try {
				userInput = sc.nextLine();
				invalidInput = false;
			} catch (Exception e) {
				System.out.println("That is not a valid input. Try again");
			}
		}
		return userInput;
	}

}
