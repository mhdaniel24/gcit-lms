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
//		System.out.println("---------------------User Input So Far-------------------------");
//		System.out.println("User Input Size = " + userInputSoFarArr.size());
//		System.out.println("User input as a String =" + userInputSoFarStr);
//		System.out.println("User input as an Array =" + userInputSoFarArr);
//		System.out.println("---------------------------------------------------------------");
		
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
					return Menu.AllLoansMenu("Which book loan's due date do you want to over-ride", qm.getAllLoans());
				}else if(userInputSoFarStr.substring(0, 2).equals("24") && userInputSoFarArr.size() == 3){
					Loan l = qm.getPrevAllLoans().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "You have chosen to update a book loan of a book with id " + l.getBookId() + " from branch with id " + l.getBranchId() + " with borrower's card number." + l.getCardNo() + "\nThe current duedate is " +l.getDueDateAsString()+"\nEnter the new duedate following the format yyyy-MM-dd HH:mm:ss.\nYou can omit HH:mm:ss if the hours, minutes and seconds will remain the same";
				}
				else if(userInputSoFarStr.equals("211")){
					//add books
					return Menu.insertBookMenu();
				}else if(userInputSoFarStr.equals("212")){
					//add authors
					return Menu.insertAuthorMenu();
				}else if(userInputSoFarStr.equals("213")){
					//add publishers
					return Menu.insertPublisherMenu();
				}else if(userInputSoFarStr.equals("214")){
					//add library branches
					return Menu.insertLibraryBranchMenu();
				}else if(userInputSoFarStr.equals("215")){
					//add borrowers
					return Menu.insertBorrowerMenu();
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
					//update books 1
					return Menu.allBookMenu("Which book do you want to update:", qm.getAllBooks());
				}else if(userInputSoFarStr.equals("232")){
					//update authors 1
					return Menu.allAuthorsMenu("Which author do you want to update:", qm.getAllAuthors());
				}else if(userInputSoFarStr.equals("233")){
					//update publishers 1
					return Menu.allPublisherMenu("Which publisher do you want to update:", qm.getAllPublisher());
				}else if(userInputSoFarStr.equals("234")){
					//update library branches 1
					return Menu.allLibraryBranchesMenu("Which branch do you want to update:", qm.getAllBranchesQuery());
				}else if(userInputSoFarStr.equals("235")){
					//update borrowers 1
					return Menu.allBorrowersMenu("Which borrower do you want to update:", qm.getAllBorrowers());
				}else if(userInputSoFarStr.substring(0, 3).equals("231") && userInputSoFarArr.size() == 4){
					//update books 2
					Book book = qm.getPrevBooksQuery().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "You have chosen to update book with id " + book.getBookId() + " and name " + book.getTitle() + " and publisher id " + book.getPubId() + "\nEnter the new name for the book, the new author's id, the new publisher's id, and the new genre separated by ',' with no spaces.\nType N/A in for the publisher's id or the author's id if you dot want to update them";
				}else if(userInputSoFarStr.substring(0, 3).equals("232") && userInputSoFarArr.size() == 4){
					//update authors 2
					Author author = qm.getPrevAllAuthors().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "You have chosen to update author with id " + author.getAuthorId() + " and name " + author.getAuthorName() +  "\nEnter a new name for the author and the id for the new book he has written separated by ',' with no spaces. If you dont want to add a new book to the author type N/A";
				}else if(userInputSoFarStr.substring(0, 3).equals("233") && userInputSoFarArr.size() == 4){
					//update publishers 2
					Publisher publisher = qm.getPrevAllPublisher().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "You have chosen to update publisher with id " + publisher.getPublisherId() + ", name " + publisher.getPublisherName() + ", address " + publisher.getPublisherAddress() + ", and phone "+ publisher.getPublisherPhone() + "\nEnter the new name address and phone number separated by ',' with no spaces";
				}else if(userInputSoFarStr.substring(0, 3).equals("234") && userInputSoFarArr.size() == 4){
					//update library branches 2
					LibraryBranch branch = qm.getPrevAllBranchesQuery().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "you have chosen to update the branch with branchId " + branch.getBranchId() + ", name "+branch.getName() + ", and address " + branch.getLocation() + "\nEnter the new name and address separated  by ',' with no spaces";
				}else if(userInputSoFarStr.substring(0, 3).equals("235") && userInputSoFarArr.size() == 4){
					//update borrowers 2
					Borrower borrower = qm.getPrevAllBorrowers().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					return "you have chosen to update the borrower with card number " + borrower.getCardNo() + ", name "+borrower.getName() + ", address " + borrower.getAddress() + ", and phone number " + borrower.getPhone() + "\nEnter the new name, address and phone separated  by ',' with no spaces";
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
					valid = validateUserNumericInput(userInput, qm.getPrevAllLoans().size()+1);
					changeDueDate1(userInput);
					
				}else if(userInputSoFarStr.substring(0, 2).equals("24") && userInputSoFarArr.size() == 3){
					valid = changeDueDate2(userInput);
				}
				else if(userInputSoFarStr.equals("211")){
					//add books
					valid = insertBook(userInput);
				}else if(userInputSoFarStr.equals("212")){
					//add authors
					valid = insertAuthor(userInput);
				}else if(userInputSoFarStr.equals("213")){
					//add publishers
					valid = insertPublisher(userInput);
				}else if(userInputSoFarStr.equals("214")){
					//add library branches
					valid = insertBranch(userInput);
				}else if(userInputSoFarStr.equals("215")){
					//add borrowers
					valid = insertBorrower(userInput);
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
					valid = validateUserNumericInput(userInput, qm.getPrevBooksQuery().size()+1);
					if(valid){
						updateBook1(userInput);
					}
				}else if(userInputSoFarStr.equals("232")){
					//update authors
					valid = validateUserNumericInput(userInput, qm.getPrevAllAuthors().size()+1);
					if(valid){
						updateAuthor1(userInput);
					}
				}else if(userInputSoFarStr.equals("233")){
					//update publishers
					valid = validateUserNumericInput(userInput, qm.getPrevAllPublisher().size()+1);
					if(valid){
						updatePublisher1(userInput);
					}
				}else if(userInputSoFarStr.equals("234")){
					//update library branches
					valid = validateUserNumericInput(userInput, qm.getPrevAllBranchesQuery().size()+1);
					if(valid){
						updateBranch1(userInput);
					}
				}else if(userInputSoFarStr.equals("235")){
					//update borrowers
					valid = validateUserNumericInput(userInput, qm.getPrevAllBorrowers().size()+1);
					if(valid){
						updateBorrower1(userInput);
					}
				}else if(userInputSoFarStr.substring(0, 3).equals("231") && userInputSoFarArr.size() == 4){
					//update books 2
					Book book = qm.getPrevBooksQuery().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					valid = updateBook2(userInput, book);
					
				}else if(userInputSoFarStr.substring(0, 3).equals("232") && userInputSoFarArr.size() == 4){
					//update authors 2
					Author author = qm.getPrevAllAuthors().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					valid = updateAuthor2(userInput, author);
					
				}else if(userInputSoFarStr.substring(0, 3).equals("233") && userInputSoFarArr.size() == 4){
					//update publishers 2
					Publisher publisher = qm.getPrevAllPublisher().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					valid = updatePublisher2(userInput, publisher);
				}else if(userInputSoFarStr.substring(0, 3).equals("234") && userInputSoFarArr.size() == 4){
					//update library branches 2
					LibraryBranch branch = qm.getPrevAllBranchesQuery().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					valid = updateBranch2(userInput, branch);
					
				}else if(userInputSoFarStr.substring(0, 3).equals("235") && userInputSoFarArr.size() == 4){
					//update borrowers 2
					Borrower borrower = qm.getPrevAllBorrowers().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
					valid = updateBorrower2(userInput, borrower);
					
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
	private void updateBook1(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevBooksQuery().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	private void updateAuthor1(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllAuthors().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	private void updatePublisher1(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllPublisher().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	private void updateBranch1(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllBranchesQuery().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	private void updateBorrower1(String userInput){
		if(userInput.equals(Integer.toString(qm.getPrevAllBorrowers().size()+1))){
			userInputSoFarArr.remove(userInputSoFarArr.size()-1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	//---------------------------------------------------------------------second round update-----------------------
	private boolean updateBook2(String userInput, Book book){
		String[] splited = userInput.split(",");
		
		if(splited.length != 4){
			return false;
		}
		//System.out.println(splited[0] +" "+splited[1]+" "+splited[2] + " " + splited[3]+ "+++++++++++++++++++");
		if(!splited[1].equals("N/A")){
			//System.out.println("INSIDE 1++++++++++++");
			//to insert the author mapping
			boolean alreadyExist = false;
			ArrayList<BookAuthors> allBookAuthors = qm.getAllBookAuthor();
			for(BookAuthors ba : allBookAuthors){
				if(ba.getAuthorId().equals(splited[1]) && ba.getBookId().equals(book.getBookId())){
					messageToShowNoSpectedInput = "The book and author match already existed";
					alreadyExist = true;
					break;
				}
			}
			if(!alreadyExist){
				
					try {
						Integer.parseInt(splited[1]);
						alreadyExist = false;
						ArrayList<Author> allAuthors = qm.getAllAuthors();
						for(Author a: allAuthors){
							if(a.getAuthorId().equals(splited[1])){
								alreadyExist = true;
								break;
							}
						}
						if(alreadyExist){
							BookAuthors ba = new BookAuthors(book.getBookId(), splited[1]);
							qm.insertBookAuthor(ba);
						}else{
							messageToShowNoSpectedInput = "The author id you entered does not match any author";
							return true;
						}
					} catch (Exception e) {
						return false;
					}
				}
			}
			
			
		
		
		if(!splited[2].equals("N/A")){
			//System.out.println("INSIDE 2++++++++++++");
			try{
				Integer.parseInt(splited[2]);
				ArrayList<Publisher> allPublishers = qm.getAllPublisher();
				boolean alreadyExists = false;
				for(Publisher p:allPublishers){
					if(p.getPublisherId().equals(splited[2])){
						alreadyExists = true;
					}
				}
				if(alreadyExists){
					//update publisher
					Book newBook = new Book(book.getBookId(), book.getTitle(), splited[2]);
					qm.updateBookPublisher(book, newBook);
				}else{
					messageToShowNoSpectedInput = "The publisher id you selected does not match any publisher";
					return true;
				}
			}catch(Exception e){
				return false;
			}
		}
		
		if(!splited[3].equals("N/A")){
			try{
				Integer.parseInt(splited[3]);
				boolean alreadyExist = false;
				ArrayList<Genre> allGenres = qm.getAllGenres();
				//System.out.println("SIZE = " + allGenres.size());
				for(Genre g : allGenres){
					//System.out.println("genere id = "+g.getGenere_id() + " genere name = " + g.getGenere_name() );
					if(g.getGenere_id().equals(splited[3])){
						alreadyExist = true;
						break;
					}
				}
				if(alreadyExist){
					alreadyExist = false;
					ArrayList<BookGenres> bgs = qm.getAllBookGenres();
					for(BookGenres bg : bgs){
						if(bg.getBookId().equals(book.getBookId()) && bg.getGenreId().equals(splited[3])){
							alreadyExist = true;
							break;
						}
					}
					if(alreadyExist){
						messageToShowNoSpectedInput = "The book and genre match already exist";
					}else{
						BookGenres bg = new BookGenres(book.getBookId(), splited[3]);
						qm.insertBookGenre(bg);
					}
				}else{
					messageToShowNoSpectedInput = "The genre id you selected does not match any genre";
					return true;
				}
				
			}catch(Exception e){
				return false;
			}
			
		}
		
		Book newBook = new Book(book.getBookId(), splited[0], book.getPubId());
		qm.updateBookTitle(book, newBook);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Book updated successfully";
		return true;
	}
	private boolean updateAuthor2(String userInput, Author author){
		String[] splited = userInput.split(",");
		if(splited.length != 2){
			return false;
		}
		
		if(!splited[1].equals("N/A")){
			try{
				Integer.parseInt(splited[1]);
				boolean alreadyExist = false;
				ArrayList<Book> allBooks = qm.getAllBooks();
				for(Book b: allBooks){
					if(b.getBookId().equals(splited[1])){
						alreadyExist = true;
						break;
					}
				}
				if(alreadyExist){
					alreadyExist = false;
					ArrayList<BookAuthors> bas = qm.getAllBookAuthor();
					for(BookAuthors ba:bas){
						if(ba.getAuthorId().equals(author.getAuthorId()) && ba.getBookId().equals(splited[1])){
							alreadyExist = true;
							messageToShowNoSpectedInput = "The book and author match already existed";
							break;
						}
					}
					if(!alreadyExist){
						BookAuthors ba = new BookAuthors(author.getAuthorId(), splited[1]);
						qm.insertBookAuthor(ba);
					}
					
				}else{
					messageToShowNoSpectedInput = "The book id you entered does not match any book";
					return true;
				}
			}catch(Exception e){
				return false;
			}
		}
		
		Author newAuthor = new Author(author.getAuthorId(), splited[0]);
		qm.updateAuthor(author, newAuthor);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Author updated successfully";
		return true;
	}
	private boolean updatePublisher2(String userInput, Publisher publisher){
		String[] splited = userInput.split(",");
		if(splited.length != 3){
			return false;
		}
		Publisher newPublisher = new Publisher(publisher.getPublisherId(), splited[0], splited[1], splited[2]);
		qm.updatePublisher(publisher, newPublisher);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Publisher updated successfully";
		return true;
	}
	private boolean updateBranch2(String userInput, LibraryBranch branch){
		String[] splited = userInput.split(",");
		if(splited.length != 2){
			return false;
		}
		
		LibraryBranch newLibraryBranch = new LibraryBranch(splited[0], splited[1], branch.getBranchId());
		qm.updateLibraryBranch(branch, newLibraryBranch);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Branch updated successfully";
		return true;
	}
	private boolean updateBorrower2(String userInput, Borrower borrower){
		String[] splited = userInput.split(",");
		if(splited.length != 3){
			return false;
		}
		Borrower newBorrower =new Borrower(borrower.getCardNo(), splited[0], splited[1], splited[2]);
		qm.updateBorrower(borrower, newBorrower);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Borrower updated successfully";
		return true;
	}
	private boolean insertBook(String userInput)
	{
		String[] splited = userInput.split(",");
		
		if(splited.length != 2){
			return false;
		}
		try {
			Integer.parseInt(splited[1]);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		boolean flag = false;
		ArrayList<Publisher> ps = qm.getAllPublisher();
		for(Publisher p:ps){
			if(p.getPublisherId().equals(splited[1])){
				flag = true;
				break;
			}
		}
		
		if(!flag){
			messageToShowNoSpectedInput = "There exist no publisher with publisher id " + splited[1];
			return true;
		}
			
		Book b = new Book("", splited[0], splited[1]);
		qm.addBook(b);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Book was added succesfully";
		return true;
	}
	private boolean insertAuthor(String userInput)
	{
		Author a = new Author("", userInput);
		qm.addAuthor(a);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Author was added succesfully";
		return true;
	}
	private boolean insertPublisher(String userInput)
	{
		String[] splited = userInput.split(",");
		if(splited.length != 3){
			return false;
		}
		
		Publisher p = new Publisher("", splited[0], splited[1], splited[2]);
		qm.addPublisher(p);
		
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Publisher was added succesfully";
		return true;
	}
	private boolean insertBranch(String userInput)
	{
		String[] splited = userInput.split(",");
		if(splited.length != 2){
			return false;
		}
		
		LibraryBranch lb = new LibraryBranch(splited[0], splited[1], "");
		
		qm.addLibraryBranch(lb);
		
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Branch was added succesfully";
		return true;
	}
	private boolean insertBorrower(String userInput)
	{
		String[] splited = userInput.split(",");
		if(splited.length != 3){
			return false;
		}
		
		Borrower b = new Borrower("", splited[0], splited[1], splited[2]);
		qm.addBorrower(b);
		
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		messageToShowNoSpectedInput = "Borrower was added succesfully";
		return true;
	}
	private void changeDueDate1(String userInput){
		
		if(userInput.equals(Integer.toString(qm.getPrevAllLoans().size() + 1))){
			userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
			rebuildUserInputString();
		}else{
			userInputSoFarArr.add(userInput);
			userInputSoFarStr = userInputSoFarStr + userInput;
		}
	}
	private boolean changeDueDate2(String userInput){
		
		//TODO: Remember to check the validity of the input before
		Loan l = qm.getPrevAllLoans().get(Integer.parseInt(userInputSoFarArr.get(userInputSoFarArr.size()-1))-1);
		String[] dateAndTime = userInput.split(" ");
		if(dateAndTime.length == 2){
			if(!validateDate(dateAndTime[0]))
				return false;
			if(!validateTime(dateAndTime[1]))
				return false;
		
		}
		
		if(dateAndTime.length == 1){
			
			if(!validateDate(dateAndTime[0]))
				return false;
			userInput = userInput +" " +l.getDueDateAsString().split(" ")[1];
		}
		
		
		
		Loan newLoan = new Loan(l.getBookId(), l.getBranchId(), l.getCardNo(), l.getDateOutAsString(), userInput);
		qm.updateLoan(l, newLoan);
		
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		userInputSoFarArr.remove(userInputSoFarArr.size() - 1);
		rebuildUserInputString();
		return true;
	}
	private boolean validateDate(String date){
		String[] ymd = date.split("-");
		if(ymd[0].length() == 4 && ymd[1].length()==2 &&ymd[2].length()==2){
			try {
				int year = Integer.parseInt(ymd[0]);
				int month = Integer.parseInt(ymd[1]);
				int day = Integer.parseInt(ymd[2]);
				return true;
			} catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}
	}
	private boolean validateTime(String time){
		String[] hms = time.split(":");
		if(hms[0].length() == 2 && hms[1].length() == 2 && hms[2].length() == 2){
			try {
				int h = Integer.parseInt(hms[0]);
				int m = Integer.parseInt(hms[1]);
				int s = Integer.parseInt(hms[2]);
				return true;
			} catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}
	}
	
}
