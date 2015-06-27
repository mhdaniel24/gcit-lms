import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class QueryManager {

	//Borrower Instance variables-----------
	private Borrower borrower;
	
	//Librarian instance variables-------
	//TODO: Handle errors related to no database connection
	//used to store a previous query to ensure that the order of the rows is the same
	private ArrayList<ArrayList<String>> libraryBranchNameLocationId;//TODO: remember to delete this one
	
	ArrayList<Book> books;
	String numberOfCopies;
	
	//Librarian and Borrower------------
	ArrayList<LibraryBranch> allBranches;
	
	//General instance variables-------------
	//TODO: get connection variables
	Connection conn;
	//TODO: remember to use a PreparedStatement
	PreparedStatement pstmt;
	ResultSet rs;
	
	public QueryManager()
	{
		//TODO: set connection
		libraryBranchNameLocationId = new ArrayList<ArrayList<String>>();
		books = new ArrayList<Book>();
		allBranches = new ArrayList<LibraryBranch>();
	}
	
	//--------------------------------Borrower Methods------------------------------------------
	public Borrower getBorrowerWithCardNo(int cardNo)
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("cardNo");
		columnsOfInterest.add("name");
		columnsOfInterest.add("address");
		columnsOfInterest.add("phone");
		
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_borrower WHERE cardNo = "+Integer.toString(cardNo), columnsOfInterest, new ArrayList<String>());
		
		borrower = new Borrower();
		if(data.get("cardNo").size() == 0){
			return null;
		}
		else{
			borrower = new Borrower(data.get("cardNo").get(0), data.get("name").get(0), data.get("address").get(0), data.get("phone").get(0));
			return borrower;
		}
	}
	
	public ArrayList<Book> getAllBooksInBranch(LibraryBranch b, int minNumbCopies)
	{
		String query = "SELECT bookId, title, pubId, noOfCopies FROM ((tbl_book NATURAL JOIN tbl_book_copies ) NATURAL JOIN tbl_library_branch) WHERE BranchId = "+b.getBranchId();
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("bookId");
		columnsOfInterest.add("title");
		columnsOfInterest.add("pubId");
		columnsOfInterest.add("noOfCopies");
		
		HashMap<String, ArrayList<String>> data =  executeSelectQuery(query, columnsOfInterest, new ArrayList<String>());
		
		books = new ArrayList<Book>();
		ArrayList<String> bookIds = data.get("bookId");
		ArrayList<String> titles = data.get("title");
		ArrayList<String> pubIds = data.get("pubId");
		ArrayList<String> numbOfCopies = data.get("noOfCopies");
		
		for(int i = 0; i< bookIds.size(); i++){
			if(Integer.parseInt(numbOfCopies.get(i)) >= 1){
				books.add(new Book(bookIds.get(i), titles.get(i), pubIds.get(i)));
			}
		}
		
		return books;
	}
	

	//--------------------------------Librarian Methods-----------------------------------------
	
	//TODO: it is void but it should return a boolean in case of connection error
	public void updateNumberOfCopies(Book book, LibraryBranch branch, String newNumberOfCopies)
	{
		
		if(numberOfCopies.equals("N")){
			executeUpdateQuery("INSERT INTO tbl_book_copies VALUES(" + book.getBookId()+", "+branch.getBranchId() + ", " +newNumberOfCopies+ ")",new ArrayList<String>());
			
		} else{
			executeUpdateQuery("Update tbl_book_copies SET noOfCopies = " + newNumberOfCopies + " WHERE bookId = " + book.getBookId() + " AND branchId = " + branch.getBranchId(), new ArrayList<String>());
		}
	}
	
	//returns name in the array in position 0 and returns locations in the array in position 1
	//TODO: it should return branches instead of arraylists!!!!!!!!!!!!!!!!!!!!!!!!
	//TODO: remember to delete this function and use the new version getAllBranchesQuery()!!!!!!!!(fix LibraryManagerBrain Client code)
	public ArrayList<ArrayList<String>> getAllBranchNamesAndLocationsQuery()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("branchName");
		columnsOfInterest.add("branchAddress");
		columnsOfInterest.add("branchId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_library_branch", columnsOfInterest, new ArrayList<String>());

		libraryBranchNameLocationId.clear();//TODO: CHange it to new to reinitialize it
		libraryBranchNameLocationId.add(data.get("branchName"));
		libraryBranchNameLocationId.add(data.get("branchAddress"));
		libraryBranchNameLocationId.add(data.get("branchId"));

		return libraryBranchNameLocationId;
	}
	
	//in order to get the data in the same order
	public ArrayList<ArrayList<String>> getPrevAllBranchNamesAndLocationsQuery()
	{
		return libraryBranchNameLocationId;
	}
	
	public ArrayList<Book> getPrevBooksQuery()
	{
		return books;
	}
	
	public String getNumberOfCopiesOfBook(Book book, LibraryBranch branch)
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		//columnsOfInterest.add("bookId");
		//columnsOfInterest.add("branchId");
		columnsOfInterest.add("noOfCopies");
		
		ArrayList<String> queryVariables = new ArrayList<String>();
		queryVariables.add(book.getBookId());
		queryVariables.add(branch.getBranchId());
		
		HashMap<String, ArrayList<String>> data = executeSelectQuery("SELECT * FROM tbl_book_copies WHERE bookId = "+book.getBookId()+" AND branchId = " + branch.getBranchId(), columnsOfInterest, new ArrayList<String>());
		
		if(data.get("noOfCopies").isEmpty()){
			numberOfCopies = "N";
			return numberOfCopies;
		}
		else{
			numberOfCopies = data.get("noOfCopies").get(0);
			return numberOfCopies;
		}
	}
	public String getPrevNumberOfCopies()
	{
		return numberOfCopies;
	}
	
	public boolean doesLoanExist(Loan loan)
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		//columnsOfInterest.add("bookId");
		//columnsOfInterest.add("branchId");
		columnsOfInterest.add("bookId");
		
		
		HashMap<String, ArrayList<String>> data = executeSelectQuery("SELECT * FROM tbl_book_loans WHERE bookId = "+loan.getBookId()+" AND branchId = " + loan.getBranchId() + " AND cardNo = " + loan.getCardNo(), columnsOfInterest, new ArrayList<String>());
		
		if(data.get("bookId").size() == 0){
			return false;
		}
		else{
			return true;
		}
	}
	public void addNewLoan(Loan loan)
	{
		String query = "INSERT INTO tbl_book_loans VALUES ("+loan.getBookId() +", " + loan.getBranchId() + ", " + loan.getCardNo() + ", '" + loan.getDateOutAsString()+"', '" + loan.getDueDateAsString() +"', NULL)";
		executeUpdateQuery(query, new ArrayList<String>());
	}
	
	public ArrayList<Book> getAllBooks()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("bookId");
		columnsOfInterest.add("title");
		columnsOfInterest.add("pubId");
		HashMap<String, ArrayList<String>> data = executeSelectQuery("SELECT * FROM tbl_book", columnsOfInterest, new ArrayList<String>());
		
		books = new ArrayList<Book>();
		ArrayList<String> bookIds = data.get("bookId");
		ArrayList<String> titles = data.get("title");
		ArrayList<String> pubIds = data.get("pubId");
		
		for(int i = 0; i< bookIds.size(); i++){
			books.add(new Book(bookIds.get(i), titles.get(i), pubIds.get(i)));
		}
		
		return books;
	}
	
	//TODO: it is void now but it should return a boolean in case there is a database connection error
	public void updateBranchNameAndAddress(String name, String address, String id)
	{		
		updateBranchName(name, id);
		updateBranchAddress(address, id);
	}
	public void updateBranchAddress(String address, String id)
	{
		ArrayList<String> queryVariables = new ArrayList<String>();
		queryVariables.add(address);
		queryVariables.add(id);
		executeUpdateQuery("UPDATE tbl_library_branch SET branchAddress = ? WHERE branchId = ?", queryVariables);
	}
	public void updateBranchName(String name, String id)
	{	
		ArrayList<String> queryVariables = new ArrayList<String>();
		queryVariables.add(name);
		queryVariables.add(id);
		executeUpdateQuery("UPDATE tbl_library_branch SET branchName = ? WHERE branchId = ?", queryVariables);
	}
	
	//--------------------------------Generic Methods-----------------------------
	
	private void executeUpdateQuery(String updateQuery, ArrayList<String> queryVariables)
	{
		executeQuery(updateQuery, queryVariables);
		closeConnection();
	}
	
	//TODO: create a more general function that can handle any query not just selects that will use this one as a helper
	//able to execute any select query and return the expected columns
	//TODO: avoid reapiting the entire structure of a query every time I do one (latter get entire functionality first)
	private HashMap<String, ArrayList<String>> executeSelectQuery(String selectQuery, ArrayList<String> columnsOfInterest, ArrayList<String> queryVariables)
	{
		HashMap<String, ArrayList<String>> data = new HashMap<String, ArrayList<String>>();
		for(String element: columnsOfInterest){
			data.put(element, new ArrayList<String>());
		}
		
		executeQuery(selectQuery, queryVariables);
		
		try {
			while(rs.next())
			{
				for(String element : columnsOfInterest){
					data.get(element).add(rs.getString(element));
				}
				
			}
		} catch (SQLException e) {
			System.out.println("Error reading the ResultSet");
		}finally{
			closeConnection();
		}
		
		return data;
	}
	
	
	//executes the query
	//TODO: Remember use a prepared statement instead of statement!!!!!!!!!!!!!!
	private void executeQuery(String query, ArrayList<String> queryVariables)
	{
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
			pstmt = conn.prepareStatement(query);
			
			for(int i = 0; i < queryVariables.size(); i++){
				pstmt.setString(i+1, queryVariables.get(i));
			}
			
			if(query.charAt(0) == 'S' || query.charAt(0) == 's'){
				rs = pstmt.executeQuery(query);
			}else if(query.charAt(0) == 'U' || query.charAt(0) == 'u' || query.charAt(0) == 'I' || query.charAt(0) == 'i'){
				pstmt.executeUpdate();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//closes the connection
	private void closeConnection()
	{
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e2) {
			// TODO: handle exception
			System.out.println("Error clossing DB connection");
		}
	}
	
	//Used by both Librarian and Borrower
	public ArrayList<LibraryBranch> getAllBranchesQuery()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("branchName");
		columnsOfInterest.add("branchAddress");
		columnsOfInterest.add("branchId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_library_branch", columnsOfInterest, new ArrayList<String>());

		allBranches = new ArrayList<LibraryBranch>();
		ArrayList<String> branchNames = data.get("branchName");
		ArrayList<String> branchAddress = data.get("branchAddress");
		ArrayList<String> branchIds = data.get("branchId");
		
		for(int i = 0; i< branchIds.size(); i++){
			allBranches.add(new LibraryBranch(branchNames.get(i), branchAddress.get(i), branchIds.get(i)));
		}

		return allBranches;
	}
	//to make sure that the data is in the same order as printed TODO: delete the equivalent for the old version of this method
	public ArrayList<LibraryBranch> getPrevAllBranchesQuery()
	{
		return allBranches;
	}
}
