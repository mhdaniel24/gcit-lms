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
	public static String lib3Option2PickBookDialogMenu(ArrayList<Book> allBooksAvailable)
	{
		String menu = new String("Pick the Book you want to add copies of, to your branch:");
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
}
