import java.util.ArrayList;


public class LibraryManagerBrain {
	
	//used to control the menu and what to do with the provided input
	//TODO: make sure to reset the variable when user is done
	private ArrayList<String> userInputSoFarArr;
	private String userInputSoFarStr;
	
	
	
	private Borrower borrower;
	private Librarian librarian;
	private Administrator administrator;
	
	QueryManager qm;
	
	public LibraryManagerBrain()
	{
		userInputSoFarArr = new ArrayList<String>();
		userInputSoFarStr = new String();
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
		System.out.println("---------------------User Input So Far-------------------------");
		System.out.println("User Input Size = " + userInputSoFarArr.size());
		System.out.println("User input as a String =" + userInputSoFarStr);
		System.out.println("User input as an Array =" + userInputSoFarArr);
		System.out.println("---------------------------------------------------------------");
		
		if(userInputSoFarArr.size() == 0){
			return Menu.welcomeWhoAreYouMenu();
		}
		else if(userInputSoFarArr.size() >= 1){//TODO: eliminate the >=1 in the future just used now as a guide
			
			//librarian options
			if(userInputSoFarStr.equals("1")){
				return Menu.lib1Menu();
			}
			else if(userInputSoFarStr.equals("11")){
				ArrayList<ArrayList<String>> branchNameAndLocation = qm.getAllBranchNamesAndLocationsQuery();
				return Menu.lib2Menu(branchNameAndLocation.get(0), branchNameAndLocation.get(1));
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 3){
				return Menu.lib3Menu();
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 4 && userInputSoFarArr.get(3).equals("1")){
				return Menu.lib3Option1BranchNameDialog(librarian.idOfBranchWorksFor(), librarian.nameOfBranchWorksFor());
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 5 && userInputSoFarArr.get(3).equals("1")){
				return Menu.lib3Option1BranchAddressDialog();
			}
			//borrower options
			
			//Administrator options
		}
		return null;
		
		
	}
	
	//this is the public interface provided to the user to provide the user input
	//it updates the state variable if the input is valid
	public boolean userInputWas(String userInput)
	{
		//to know if the input is valid or not to add it to the userInputSoFar
		boolean valid = false;
		if(userInputSoFarArr.size() == 0){
			valid = validateUserNumericInput(userInput, 3);
			if (valid) {
				userInputSoFarArr.add(userInput);
				userInputSoFarStr = userInputSoFarStr + userInput;
			}
		}
		else if(userInputSoFarArr.size() >= 1){
			//librarian options
			if(userInputSoFarStr.equals("1")){
				valid = validateUserNumericInput(userInput, 2);
				if (valid) {
					lib1InputHandler(userInput);
				}
			}
			else if(userInputSoFarStr.equals("11")){
				valid = validateUserNumericInput(userInput, qm.getPrevAllBranchNamesAndLocationsQuery().get(0).size()+1);
				if(valid){
					lib2InputHandler(userInput);
				}
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 3){
				valid = validateUserNumericInput(userInput, 3);
				if(valid){
					lib3InputHandler(userInput);
				}
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 4 && userInputSoFarArr.get(3).equals("1")){
				lib3InputOption1Handler1(userInput);
				valid = true;
			}
			else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 5 && userInputSoFarArr.get(3).equals("1")){
				lib3InputOption1Handler2(userInput);
				valid = true;
			}
			//borrower options
			
			//Administrator options
		}
		return valid;
	}
	
	//helpers
	
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
	
	private void rebuildUserInputString()
	{
		userInputSoFarStr = new String();
		for(String element : userInputSoFarArr)
		{
			userInputSoFarStr = userInputSoFarStr + element;
		}
	}
	//-------------------------end of helpers-----------------------
	
	//librarian action handlers
	private void lib1InputHandler(String userInput)
	{
		if(userInput.equals("1")){
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
		else{
			userInputSoFarArr.clear();
			userInputSoFarStr = new String();
		}
	}

	private void lib2InputHandler(String userInput)
	{
		ArrayList<ArrayList<String>> namesAndLocations = qm.getPrevAllBranchNamesAndLocationsQuery();
		if(namesAndLocations.get(0).size()+1 == Integer.parseInt(userInput))
		{
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else
		{
			librarian = new Librarian(namesAndLocations.get(0).get(Integer.parseInt(userInput)-1), namesAndLocations.get(1).get(Integer.parseInt(userInput)-1), namesAndLocations.get(2).get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;

		}
	}

	private void lib3InputHandler(String userInput)
	{
		if(Integer.parseInt(userInput) == 3){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);//
			rebuildUserInputString();
		}
		else if(Integer.parseInt(userInput) == 2)
		{
			//TODO: Add copies of Book to the Branch
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
		else
		{
			//case 1
			//TODO: Update the details of the Library
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}

	}

	private void lib3InputOption1Handler1(String userInput)
	{
		if(userInput.equals("quit")){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			//it can be either N/A or the new name
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
		
		
	}
	
	private void lib3InputOption1Handler2(String userInput)
	{
		if(userInput.equals("quit")){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			//if both choices are not N/A make changes
			if(!userInputSoFarArr.get(userInputSoFarArr.size() -1).equals("N/A") && !userInput.equals("N/A")){
				//make changes
				qm.updateBranchNameAndAddress(userInputSoFarArr.get(userInputSoFarArr.size() -1), userInput, librarian.idOfBranchWorksFor());
			}
			else if(!userInput.equals("N/A")){
				System.out.println("User Input = " + userInput + " size = " + userInput.length() + "+++++++++++++");
				qm.updateBranchAddress(userInput, librarian.idOfBranchWorksFor());
			}
			else if(!userInputSoFarArr.get(userInputSoFarArr.size() -1).equals("N/A")){
				qm.updateBranchName(userInputSoFarArr.get(userInputSoFarArr.size() -1), librarian.idOfBranchWorksFor());
			}
			
			//TODO: handle and display any error related to database connection
			
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
			
		}
		
		
	}
	
	//borrower action handlers

}
