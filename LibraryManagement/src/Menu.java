import java.util.ArrayList;
import java.util.Iterator;


public class Menu {
	
	private Menu(){}
	
	//it returns the initial menu
	public static String welcomeWhoAreYouMenu()
	{
		return "Welcome to the GCIT Library Management System. Which category of a user are you\n1) Librarian\n2) Administrator\n3) Borrower";
	}
	
	
	//librarian menues
	public static String lib1Menu()
	{
		return "1) Enter Branch you manage\n2) Quite to previous";
	}
	
	public static String lib2Menu(ArrayList<String> branchNames, ArrayList<String> branchLocations)
	{
		
		String menu = "";
		int i;
		for(i = 0; i < branchNames.size(); i++)
		{
			menu = menu + Integer.toString(i+1) + ") " + branchNames.get(i) +","+ branchLocations.get(i) + "\n";  
		}
		menu = menu + Integer.toString(i+1) + ") " + "Quit to previous";
		return menu;
	}
	
	public static String lib3Menu()
	{
		return "1) Update the details of the Library\n2) Add copies of Book to the Branch\n3) Quit to previous";
		
	}
	
	public static String lib3Option1BranchNameDialog(String branchId, String branchName)
	{
		return "You have chosen to update the Branch with Branch Id: " + branchId + " and Branch Name: " + branchName + ".\nEnter ‘quit’ at any prompt to cancel operation.\nPlease enter new branch name or enter N/A for no change:"; 
	}
	public static String lib3Option1BranchAddressDialog()
	{
		return "Please enter new branch address or enter N/A for no change:";
	}
	
	//TODO: Add the \n and the \t to all other menues according to the format in the document
	public static String pickBookMenu(ArrayList<Book> allBooksAvailable, String message)
	{
		String menu = message;
		int i;
		for(i = 0; i < allBooksAvailable.size(); i++){
			menu = menu + "\n\t" + Integer.toString(i+1) + ") " + allBooksAvailable.get(i).getTitle();
		}
		menu = menu + "\n\t" + Integer.toString(i+1) + ") " + "Quit to cancel operation";
		return menu;
	}
	public static String lib3Option2newNumberOfCopies()
	{
		return "Enter new number of copies: ";
	}
	//--------------------------------------------------------
	
	//borrower menues
	public static String borro0ProvideCardNumberDialog()
	{
		return "Enter your Card Number";
	}
	public static String borr1CheckReturnOrQuitMenu()
	{
		return "1) Check out a book\n2) Return a book\n3) Quit to previous";
	}
	public static String borr1Option1CheckOutBookMenu(ArrayList<LibraryBranch> branches){
		String menu = "Pick the Branch you want to check out from:";
		menu = attachToTheEnd(menu, branches);
		return menu;	
	}
	public static String borr1Option2CheckOutBookMenu(ArrayList<LibraryBranch> branches){
		String menu = "Pick the Branch you want to return a book to:";
		menu = attachToTheEnd(menu, branches);
		return menu;	
	}
	private static String attachToTheEnd(String menu, ArrayList<LibraryBranch> branches)
	{
		int i;
		for(i = 0; i < branches.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") " + branches.get(i).getName();
		}
		menu = menu + "\n"+Integer.toString(i+1)+") Quit to previous";
		return menu;
	}
	
	//-----------------------Administrator--------------------
	//It is already implemented for publisher
	public static String administratorMainMenu()
	{
		return "What task would you like to perform?\n1) Add\n2) Delete\n3) Update\n4) Over-ride Due Date for a Book Loan\n5) Quit to previous";
	}
	public static String administratorAddMenu(){
		return "What would you like to add:\n1) Books\n2) Authors\n3) Publishers\n4) Library Branches\n5) Borrowers\n6) Quit to previous";
	}
	public static String administratorDeleteMenu(){
		return "What would you like to delete:\n1) Books\n2) Authors\n3) Publishers\n4) Library Branches\n5) Borrowers\n6) Quit to previous";
	}
	public static String administratorUpdateMenu(){
		return "What would you like to update:\n1) Books\n2) Authors\n3) Publishers\n4) Library Branches\n5) Borrowers\n6) Quit to previous";
	}
	public static String allBookMenu(String beginingClause, ArrayList<Book> books)
	{
		String menu = beginingClause;
		int i;
		for(i = 0; i < books.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") "+books.get(i).getBookId() +", " + books.get(i).getTitle();
		}
		
		menu = addQuitPart(menu, i);
		return menu;
	}
	public static String allAuthorsMenu(String beginingClause, ArrayList<Author> authors)
	{
		String menu = beginingClause;
		int i;
		for(i = 0; i < authors.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") " +authors.get(i).getAuthorId()+", " +authors.get(i).getAuthorName();
		}
		return addQuitPart(menu, i);
	}
	public static String allPublisherMenu(String beginingClause, ArrayList<Publisher> publishers)
	{
		String menu = beginingClause;
		int i;
		for(i = 0; i < publishers.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") "+publishers.get(i).getPublisherId() +", "+publishers.get(i).getPublisherName()+", " +publishers.get(i).getPublisherAddress() + ", " + publishers.get(i).getPublisherPhone();
		}
		return addQuitPart(menu, i);
	}
	public static String allLibraryBranchesMenu(String beginingClause, ArrayList<LibraryBranch> libraryBranches)
	{
		String menu = beginingClause;
		int i;
		for(i = 0; i < libraryBranches.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") " + libraryBranches.get(i).getBranchId()+", "+libraryBranches.get(i).getName()+", " +libraryBranches.get(i).getLocation();
		}
		return addQuitPart(menu, i);
	}
	public static String allBorrowersMenu(String beginingClause, ArrayList<Borrower> borrowers)
	{
		String menu = beginingClause;
		int i;
		for(i = 0; i < borrowers.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") " + borrowers.get(i).getCardNo()+", "+borrowers.get(i).getName()+", " +borrowers.get(i).getAddress() + ", " + borrowers.get(i).getPhone();
		}
		return addQuitPart(menu, i);
	}
	public static String AllLoansMenu(String beginingClause, ArrayList<Loan> loans)
	{
		String menu = beginingClause;
		//System.out.println("Size of loans Array = " + loans.size());
		int i;
		for(i = 0; i < loans.size(); i++){
			menu = menu + "\n"+Integer.toString(i+1)+") " +loans.get(i).getBookId()+", " +loans.get(i).getBranchId() + ", " + loans.get(i).getCardNo() + ", " +loans.get(i).getDueDateAsString();
		}
		return addQuitPart(menu, i);
	}
	private static String addQuitPart(String menu, int i)
	{
		return menu + "\n"+Integer.toString(i+1)+") Quit to previous";
	}
	
	public static String insertBookMenu()
	{
		return "Insert a new book by typing the book's title, publisher's id separated by ',' with no spaces.";
	}
	public static String insertAuthorMenu()
	{
		return "Insert a new author by typing the author's name";
	}
	public static String insertPublisherMenu()
	{
		return "Insert a new publisher by typing the publisher's name, publisher's address, publisher's phone separated by ',' with no spaces.";
	}
	public static String insertBorrowerMenu()
	{
		return "Insert a new borrower by typing his/her name, address, phone number separated by ',' with no spaces.";
	}
	public static String insertLibraryBranchMenu()
	{
		return "Insert a new library branch by typing the branch name and the branch address separated by ',' with no spaces.";
	}
}
