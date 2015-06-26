import java.util.ArrayList;


public class LibraryManagerController {
	
	//used to control the menu and what to do with the provided input
	//TODO: make sure to reset the variable when user is done
	private ArrayList<String> userInputSoFar;
	
	private Borrower borrower;
	private Librarian librarian;
	private Administrator administrator;
	
	QueryManager qm;
	
	public LibraryManagerController()
	{
		userInputSoFar = new ArrayList<String>();
		borrower = new Borrower();
		librarian = new Librarian();
		administrator = new Administrator();
		qm = new QueryManager();
	}
	
	//this is the public interface provided to client code to get the current menu.
	//It returns the String that contains the menu to be displayed
	//it uses the istance variable state of the object to decide which menu
	//this function should be called before userInputWas()
	public String getMenu()
	{
		System.out.println("Size = " + userInputSoFar.size());
		switch (userInputSoFar.size()) {
		case 0:
			return Menu.welcomeWhoAreYouMenu();
		case 1:
			//librarian
			if(userInputSoFar.get(0).equals("1")){
				return Menu.lib1Menu();
			}
			//administrator
			else if(userInputSoFar.get(0).equals("2")){
				//TODO: last one
				return null;
			}
			//borrower
			else{
				return null;
			}
		case 2:
			//librarian
			if(userInputSoFar.get(0).equals("1")){
				ArrayList<ArrayList<String>> branchNameAndLocation = qm.getAllBranchNamesAndLocationsQuery();
				return Menu.lib2Menu(branchNameAndLocation.get(0), branchNameAndLocation.get(1));
			}
			//administrator
			else if(userInputSoFar.get(0).equals("2")){
				//TODO: last one
				return null;
			}
			//borrower
			else{
				return null;
			}
		case 3:
			//librarian
			if(userInputSoFar.get(0).equals("1")){
				return Menu.lib3Menu();
			}
			//TODO: keep adding if else for administrator and borrower
		case 4:
			if(userInputSoFar.get(0).equals("1")){
				if(userInputSoFar.get(2).equals("1")){
					//Update details of the library
					return Menu.lib3Option1BranchNameDialog(librarian.idOfBranchWorksFor(), librarian.nameOfBranchWorksFor());
				}
				else if(userInputSoFar.get(2).equals("1")){
					//Add copies of Book to the Branch
				}
			}
			//TODO: keep adding if else for administrator and borrower
		default:
			return null;
		}
		
		
	}
	
	//this is the public interface provided to the user to provide the user input
	//it updates the state variable if the input is valid
	public boolean userInputWas(String userInput)
	{
		//to know if the input is valid or not to add it to the userInputSoFar
		boolean valid = false;
		
		switch (userInputSoFar.size()) {
		case 0://user is identifying himself as a librarian, administrator or borrower
			valid = validateUserNumericInput(userInput, 3);
			if (valid) {
				userInputSoFar.add(userInput);
			}
			break;
		case 1:
			//librarian
			if(userInputSoFar.get(0).equals("1")){
				valid = validateUserNumericInput(userInput, 2);
				if (valid) {
					lib1InputHandler(userInput);
				}
				break;
			}
			//administrator
			else if(userInputSoFar.get(0).equals("2")){
				//TODO: last one
			}
			//borrower
			else{
				
			}
			break;
		case 2:
			if(userInputSoFar.get(0).equals("1"))
			{
				valid = validateUserNumericInput(userInput, qm.getPrevAllBranchNamesAndLocationsQuery().get(0).size()+1);
				if(valid){
					lib2InputHandler(userInput);
				}
			}
			//TODO: keep adding else if for Administrator and for Borrower
			break;
		case 3:
			if(userInputSoFar.get(0).equals("1"))
			{
				valid = validateUserNumericInput(userInput, 3);
				if(valid){
					lib3InputHandler(userInput);
				}
			}
			//TODO: keep adding else if for Administrator and for Borrower
			break;
		case 4:
			if(userInputSoFar.get(0).equals("1"))
			{
				if(userInputSoFar.get(2).equals("1")){
					//Update details of the library
					//TODO: check if any input should be valid (the librarian can use any name for the branch)
					lib4InputHandler(userInput);
					valid = true;
					
					
					
				}
				else if(userInputSoFar.get(2).equals("2")){
					//add copies of book to the Branch
				}
			}
			//TODO: keep adding else if for Administrator and for Borrower
			break;
			
		default:
			break;
		}
		
		
		
		return valid;
	}
	
	
	//Validates if the user selected one of the possible choices avaliable by using validUpTo in the case of numeric input
	//validUpTo = choices of the user
	private boolean validateUserNumericInput(String userInput, int validUpTo)
	{
		boolean valid = false;
		for (int i = 0; i < validUpTo; i++) {
			if(userInput.equals(Integer.toString(i+1)))
			{
				valid = true;
				break;
			}
		}
		return valid;
	}
	
	//librarian action handlers
	private void lib1InputHandler(String userInput)
	{
		if(userInput.equals("1")){
			userInputSoFar.add(userInput);
		}
		else{
			userInputSoFar.clear();
		}
	}

	private void lib2InputHandler(String userInput)
	{
		ArrayList<ArrayList<String>> namesAndLocations = qm.getPrevAllBranchNamesAndLocationsQuery();
		if(namesAndLocations.get(0).size()+1 == Integer.parseInt(userInput))
		{
			userInputSoFar.remove(userInputSoFar.size() - 1);
		}
		else
		{
			librarian = new Librarian(namesAndLocations.get(0).get(Integer.parseInt(userInput)-1), namesAndLocations.get(1).get(Integer.parseInt(userInput)-1), namesAndLocations.get(2).get(Integer.parseInt(userInput)-1));
			userInputSoFar.add(userInput);

		}
	}

	private void lib3InputHandler(String userInput)
	{
		if(Integer.parseInt(userInput) == 3){
			userInputSoFar.remove(userInputSoFar.size() - 1);//
		}
		else if(Integer.parseInt(userInput) == 2)
		{
			//TODO: Add copies of Book to the Branch
			userInputSoFar.add(userInput);
		}
		else
		{
			//case 1
			//TODO: Update the details of the Library
			userInputSoFar.add(userInput);
		}

	}

	private void lib4InputHandler(String userInput)
	{
		if(userInput.equals("quit")){
			userInputSoFar.remove(userInputSoFar.size() - 1);
		}
		else{
			//it can be either N/A or the new name
			userInputSoFar.add(userInput);
		}
		
		
	}
	
	//borrower action handlers

}
