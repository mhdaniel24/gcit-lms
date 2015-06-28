import java.util.ArrayList;

import org.xml.sax.InputSource;


public class LibraryManagerBrain {
	
	//used to control the menu and what to do with the provided input
	//TODO: make sure to reset the variable when user is done
	private ArrayList<String> userInputSoFarArr;
	private String userInputSoFarStr;
	private String messageToShowNoSpectedInput;
	
	
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
		messageToShowNoSpectedInput = new String("");
	}
	
	public String getMessageToShowNoSpectedInput()
	{
		String toBeReturned = messageToShowNoSpectedInput;
		messageToShowNoSpectedInput = new String("");
		return toBeReturned;
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
			if(userInputSoFarArr.get(0).equals("1"))
			{
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
					return Menu.lib3Option1BranchNameDialog(librarian.getLibraryBranch().getBranchId(), librarian.getLibraryBranch().getName());
				}
				else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 5 && userInputSoFarArr.get(3).equals("1")){
					return Menu.lib3Option1BranchAddressDialog();
				}
				else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 4 && userInputSoFarArr.get(3).equals("2")){
					return Menu.pickBookMenu(qm.getAllBooks(), "Pick the Book you want to add copies of, to your branch:");
				}
				else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 5 && userInputSoFarArr.get(3).equals("2")){
					return Menu.lib3Option2newNumberOfCopies();
				}
			}
			//borrower options
			else if(userInputSoFarArr.get(0).equals("3"))
			{
				if(userInputSoFarStr.equals("3")){
					return Menu.borro0ProvideCardNumberDialog();
				}
				else if(userInputSoFarArr.size() == 2){
					return Menu.borr1CheckReturnOrQuitMenu();
				}
				else if(userInputSoFarArr.size() == 3 && userInputSoFarArr.get(2).equals("1")){
					//checkout a book
					return Menu.borr1Option1CheckOutBookMenu(qm.getAllBranchesQuery());
				}
				else if(userInputSoFarArr.size() == 4 && userInputSoFarArr.get(2).equals("1")){
					return Menu.pickBookMenu(qm.getAllBooksInBranch(borrower.getLibraryBranch(), 1), "Pick the Book you want to check out:");
				}
				else if(userInputSoFarArr.size() == 3 && userInputSoFarArr.get(2).equals("2")){
					//return a book
					return Menu.borr1Option2CheckOutBookMenu(qm.getAllBranchesQuery());
					
				}
				else if(userInputSoFarArr.size() == 4 && userInputSoFarArr.get(2).equals("2")){
					//get the correct query
					return Menu.pickBookMenu(qm.getBooksBorrowedByInBranch(borrower), "Pick the book you want to return:");
				}
					
			}
			else if(userInputSoFarArr.get(0).equals("2"))
			{
				//Administrator options
				if(userInputSoFarStr.equals("2")){
					return Menu.administratorMainMenu();
				}else if(userInputSoFarStr.equals("21")){
					//Add option
					return Menu.administratorAddMenu();
				}else if(userInputSoFarStr.equals("22")){
					//Delete option
					return Menu.administratorDeleteMenu();
				}else if(userInputSoFarStr.equals("23")){
					//Update option
					return Menu.administratorUpdateMenu();
				}else if(userInputSoFarStr.equals("24")){
					//Over-ride Due Date for a Book Loan
					//return Menu.AllLoansMenu("Which book loan's due date do you want to over-ride", qm.getAllLoans());
					return "Not implemented yet. Missing qm.getAllLoans() Method";
				}else if(userInputSoFarStr.equals("211")){
					//add books
				}else if(userInputSoFarStr.equals("212")){
					//add authors
				}else if(userInputSoFarStr.equals("213")){
					//add publishers
				}else if(userInputSoFarStr.equals("214")){
					//add library branches
				}else if(userInputSoFarStr.equals("215")){
					//add borrowers
				}else if(userInputSoFarStr.equals("221")){
					//delete books
					return Menu.allBookMenu("Which book do you want to delete:", qm.getAllBooks());
				}else if(userInputSoFarStr.equals("222")){
					//delete authors
					return Menu.allAuthorsMenu("Which author do you want to delete:", qm.getAllAuthors());
				}else if(userInputSoFarStr.equals("223")){
					//delete publishers
					return Menu.allPublisherMenu("Which publisher do you want to delete:", qm.getAllPublisher());
				}else if(userInputSoFarStr.equals("224")){
					//delete library branches
					return Menu.allLibraryBranchesMenu("Which publisher do you want to delete:", qm.getAllBranchesQuery());
				}else if(userInputSoFarStr.equals("225")){
					//delete borrowers
					return Menu.allBorrowersMenu("Which borrower do you want to delete:", qm.getAllBorrowers());
				}else if(userInputSoFarStr.equals("231")){
					//update books
					return Menu.allBookMenu("Which book do you want to update:", qm.getAllBooks());
				}else if(userInputSoFarStr.equals("232")){
					//update authors
					return Menu.allAuthorsMenu("Which author do you want to update:", qm.getAllAuthors());
				}else if(userInputSoFarStr.equals("233")){
					//update publishers
					return Menu.allPublisherMenu("Which publisher do you want to update:", qm.getAllPublisher());
				}else if(userInputSoFarStr.equals("234")){
					//update library branches
					return Menu.allLibraryBranchesMenu("Which publisher do you want to update:", qm.getAllBranchesQuery());
				}else if(userInputSoFarStr.equals("235")){
					//update borrowers
					return Menu.allBorrowersMenu("Which borrower do you want to update:", qm.getAllBorrowers());
				}
				
			}
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
			if(userInputSoFarArr.get(0).equals("1"))
			{
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
				else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 4 && userInputSoFarArr.get(3).equals("2")){
					valid = validateUserNumericInput(userInput, qm.getPrevBooksQuery().size()+1);
					if(valid){
						lib3InputOption2Handler1(userInput);
					}
				}
				else if(userInputSoFarStr.subSequence(0, 2).equals(("11")) && userInputSoFarArr.size() == 5 && userInputSoFarArr.get(3).equals("2")){
					valid = validateNewNumbOfCopies(userInput);
					if(valid){
						lib3InputOption2Handler2(userInput);
					}
				}
			}
			//borrower options
			else if(userInputSoFarArr.get(0).equals("3"))
			{
				if(userInputSoFarStr.equals("3")){
					valid = validateCardNumber(userInput);
					if(valid){
						borr0InputHandler(userInput); 
					}
					else{
						valid = true;//to avoid the input not valid message and print a more descriptive message
						messageToShowNoSpectedInput = "The value you provided does not match any card number. Try Again";
					}
				}
				else if(userInputSoFarArr.size() == 2){
					valid = validateUserNumericInput(userInput, 3);
					if(valid){
						borr1InputHandler(userInput);
					}
				}
				else if(userInputSoFarArr.size() == 3 && userInputSoFarArr.get(2).equals("1")){
					//checkout a book
					valid = validateUserNumericInput(userInput, qm.getPrevAllBranchesQuery().size()+1);
					if(valid){
						borr1Option1And2InputHandler1(userInput);
					}
				}
				else if(userInputSoFarArr.size() == 4 && userInputSoFarArr.get(2).equals("1")){
					valid = validateUserNumericInput(userInput, qm.getPrevBooksQuery().size()+1);
					if(valid){
						borr1Option1And2InputHandler2(userInput);
					}
				}
				else if(userInputSoFarArr.size() == 3 && userInputSoFarArr.get(2).equals("2")){
					//return a book
					valid = validateUserNumericInput(userInput, qm.getPrevAllBranchesQuery().size()+1);
					if(valid){
						borr1Option1And2InputHandler1(userInput);
					}
				}
				else if(userInputSoFarArr.size() == 4 && userInputSoFarArr.get(2).equals("2")){
					valid = validateUserNumericInput(userInput, qm.getPrevBooksQuery().size()+1);
					if(valid){
						borr1Option1And2InputHandler2(userInput);
					}
				}
			}
			else if(userInputSoFarArr.get(0).equals("2"))
			{
				//Administrator options
				if(userInputSoFarStr.equals("2")){
					valid = validateUserNumericInput(userInput, 5);
					if(valid){
						adminMainAddDeleteUpdateInputHandler(userInput, 5);
					}
				}else if(userInputSoFarStr.equals("21")){
					//Add options
					valid = validateUserNumericInput(userInput, 6);
					if(valid){
						adminMainAddDeleteUpdateInputHandler(userInput, 6);
					}
					
				}else if(userInputSoFarStr.equals("22")){
					//Delete options
					valid = validateUserNumericInput(userInput, 6);
					if(valid){
						adminMainAddDeleteUpdateInputHandler(userInput, 6);
					}
					
				}else if(userInputSoFarStr.equals("23")){
					//Update options
					valid = validateUserNumericInput(userInput, 6);
					if(valid){
						adminMainAddDeleteUpdateInputHandler(userInput, 6);
					}
					
				}else if(userInputSoFarStr.equals("24")){
					//Over-ride Due Date for a Book Loan
					
				}else if(userInputSoFarStr.equals("211")){
					//add books
				}else if(userInputSoFarStr.equals("212")){
					//add authors
				}else if(userInputSoFarStr.equals("213")){
					//add publishers
				}else if(userInputSoFarStr.equals("214")){
					//add library branches
				}else if(userInputSoFarStr.equals("215")){
					//add borrowers
				}else if(userInputSoFarStr.equals("221")){
					//delete books
					valid = validateUserNumericInput(userInput, qm.getPrevBooksQuery().size()+1);
					if(valid){
						deleteBook(userInput);
					}
				}else if(userInputSoFarStr.equals("222")){
					//delete authors
					valid = validateUserNumericInput(userInput, qm.getPrevAllAuthors().size()+1);
					if(valid){
						deleteAuthor(userInput);
					}
				}else if(userInputSoFarStr.equals("223")){
					//delete publishers
					valid = validateUserNumericInput(userInput, qm.getPrevAllPublisher().size()+1);
					if(valid){
						deletePublisher(userInput);
					}
				}else if(userInputSoFarStr.equals("224")){
					//delete library branches
					valid = validateUserNumericInput(userInput, qm.getPrevAllBranchesQuery().size()+1);
					if(valid){
						deleteLibraryBranch(userInput);
					}
				}else if(userInputSoFarStr.equals("225")){
					//delete borrowers
					valid = validateUserNumericInput(userInput, qm.getPrevAllBorrowers().size()+1);
					if(valid){
						deleteBorrower(userInput);
					}
				}else if(userInputSoFarStr.equals("231")){
					//update books
				}else if(userInputSoFarStr.equals("232")){
					//update authors
				}else if(userInputSoFarStr.equals("233")){
					//update publishers
				}else if(userInputSoFarStr.equals("234")){
					//update library branches
				}else if(userInputSoFarStr.equals("235")){
					//update borrowers
				}
			}
		}
		return valid;
	}
	
	//helpers
	private boolean validateNewNumbOfCopies(String userInput)
	{
		try {
			//here
			if(Integer.parseInt(userInput) >= 0){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
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
				librarian.getLibraryBranch().setLocation(userInputSoFarArr.get(userInputSoFarArr.size() -1));
				librarian.getLibraryBranch().setName(userInput);
				qm.updateBranchNameAndAddress(userInputSoFarArr.get(userInputSoFarArr.size() -1), userInput, librarian.getLibraryBranch().getBranchId());
			}
			else if(!userInput.equals("N/A")){
				qm.updateBranchAddress(userInput, librarian.getLibraryBranch().getBranchId());
				librarian.getLibraryBranch().setName(userInput);
			}
			else if(!userInputSoFarArr.get(userInputSoFarArr.size() -1).equals("N/A")){
				qm.updateBranchName(userInputSoFarArr.get(userInputSoFarArr.size() -1), librarian.getLibraryBranch().getBranchId());
				librarian.getLibraryBranch().setLocation(userInputSoFarArr.get(userInputSoFarArr.size() -1));
			}
			
			//TODO: handle and display any error related to database connection
			
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			messageToShowNoSpectedInput = "Successfully Updated";
			rebuildUserInputString();	
		}
	}
	
	private void lib3InputOption2Handler1(String userInput)
	{
		if(userInput.equals(Integer.toString(qm.getPrevBooksQuery().size() + 1))){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			//query to see the number of copies in the branch of the selected book
			String numbOfCopies = qm.getNumberOfCopiesOfBook(qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1), librarian.getLibraryBranch());
			
			//this is the case when the record is not in the table
			if(numbOfCopies.equals("N")){
				numbOfCopies = "0";
			}
			
			messageToShowNoSpectedInput = "Exesting number of copies is: " + numbOfCopies;
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	
	private void lib3InputOption2Handler2(String userInput){
		
		//TODO: it should be checking for a boolean in case something went wrong
		qm.updateNumberOfCopies(qm.getPrevBooksQuery().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1), librarian.getLibraryBranch(), userInput);
		
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		messageToShowNoSpectedInput = "Successfully Updated";
		rebuildUserInputString();
	}
	
	//borrower action handlers
	private boolean validateCardNumber(String userInput)
	{
		//call query to get borrower or null
		
		try {
			borrower = qm.getBorrowerWithCardNo(Integer.parseInt(userInput));
			if (borrower == null){
				return false;
			}
			else
				return true;
		} catch (Exception e) {
			//case in which the user inputs a string instead of a number and the Integer parsing does not work
			return false;
		}
	}
	
	private void borr0InputHandler(String userInput)
	{
		userInputSoFarArr.add(userInput);
		userInputSoFarStr = userInputSoFarStr + userInput;
	}
	
	private void borr1InputHandler(String userInput)
	{
		if(userInput.equals("3")){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput; 
		}
	}
	
	private void borr1Option1And2InputHandler1(String userInput)
	{	
		if(userInput.equals(Integer.toString((qm.getPrevAllBranchesQuery().size()+1)))){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput; 
			borrower.setLibraryBranch(qm.getPrevAllBranchesQuery().get(Integer.parseInt(userInput) - 1));
		}
	}
	
	private void borr1Option1And2InputHandler2(String userInput)
	{
		int numbOfCopiesChange = 0;
		if(userInputSoFarArr.get(2).equals("2")){
			numbOfCopiesChange = 1;
		}
		else if(userInputSoFarArr.get(2).equals("1")){
			numbOfCopiesChange = -1;
		}
		
		if(userInput.equals(Integer.toString((qm.getPrevBooksQuery().size()+1)))){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}
		else{
			//TODO: check if there is already a loan with these characteristics if yes tell the user he cant reloan the same book else go ahead
			Loan loan = new Loan(qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1).getBookId(), borrower.getLibraryBranch().getBranchId(), borrower.getCardNo());
			boolean loanExists = qm.doesLoanExist(loan);
			if(loanExists && userInputSoFarArr.get(2).equals("1")){
				messageToShowNoSpectedInput = "You already borrowed a copy of " + qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1).getTitle() + " in library branch " + borrower.getLibraryBranch().getName();
			}else{
				//TODO: reduce the available number of copies
				int newNumbCopies = Integer.parseInt(qm.getNumberOfCopiesOfBook(qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1), borrower.getLibraryBranch())) + numbOfCopiesChange;
				qm.updateNumberOfCopies(qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1), borrower.getLibraryBranch(), Integer.toString(newNumbCopies));
				
				//TODO: Add entry to book_loans
				if(userInputSoFarArr.get(2).equals("1")){
					qm.addNewLoan(loan);
					messageToShowNoSpectedInput = "Borrowing Book process terminated succesfully";
				}else if(userInputSoFarArr.get(2).equals("2")){
					qm.deleteLoan(loan);
					//TODO: Set Successful message
					messageToShowNoSpectedInput = "Book returned successfully";
				}
				//TODO: go back to Borr1 Menu
				userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
				userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
				rebuildUserInputString();
				
			}
			
		}
	}
	
	//-----------------------------------------------Administrator----------------------------------------------------
	private void adminMainAddDeleteUpdateInputHandler(String userInput, int quitOption)
	{
		if(userInput.equals(Integer.toString(quitOption))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
		
	}
	private void deleteBook(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevBooksQuery().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			qm.deleteBook(qm.getPrevBooksQuery().get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
			messageToShowNoSpectedInput = "Book deleted successfully";
		}
	}
	private void deleteAuthor(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllAuthors().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			qm.deleteAuthor(qm.getPrevAllAuthors().get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
			messageToShowNoSpectedInput = "Author deleted successfully";
		}
	}
	private void deletePublisher(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllPublisher().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			qm.deletePublisher(qm.getPrevAllPublisher().get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
			messageToShowNoSpectedInput = "Publisher deleted successfully";
		}
	}
	private void deleteLibraryBranch(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllBranchesQuery().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			qm.deleteLibraryBranch(qm.getPrevAllBranchesQuery().get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
			messageToShowNoSpectedInput = "Library Branch deleted successfully";
		}
	}
	private void deleteBorrower(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllBorrowers().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			qm.deleteBorrower(qm.getPrevAllBorrowers().get(Integer.parseInt(userInput)-1));
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
			messageToShowNoSpectedInput = "Borrower deleted successfully";
		}
	}
}
