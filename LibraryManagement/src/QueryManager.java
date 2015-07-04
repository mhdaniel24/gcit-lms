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

	private ArrayList<Book> books;
	private String numberOfCopies;

	//Librarian and Borrower------------
	private ArrayList<LibraryBranch> allBranches;

	//Administrator-----------
	ArrayList<Author> authors;
	ArrayList<Publisher> publishers;
	ArrayList<Borrower> borrowers;
	ArrayList<Loan> loans;


	//General instance variables-------------
	//TODO: get connection variables
	private Connection conn;
	//TODO: remember to use a PreparedStatement
	private PreparedStatement pstmt;
	private ResultSet rs;

	public QueryManager()
	{
		//TODO: set connection
		libraryBranchNameLocationId = new ArrayList<ArrayList<String>>();
		books = new ArrayList<Book>();
		allBranches = new ArrayList<LibraryBranch>();
		authors = new ArrayList<Author>();
		publishers = new ArrayList<Publisher>();
		borrowers = new ArrayList<Borrower>();
		loans = new ArrayList<Loan>();
	}

	//--------------------------------Borrower Methods------------------------------------------
	public ArrayList<Book> getBooksBorrowedByInBranch(Borrower b)
	{
		String query = "SELECT * FROM ((tbl_book NATURAL JOIN tbl_book_loans) NATURAL JOIN tbl_borrower) WHERE cardNo = " +b.getCardNo() + " AND branchId = " + b.getLibraryBranch().getBranchId();
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("bookId");
		columnsOfInterest.add("title");
		columnsOfInterest.add("pubId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery(query, columnsOfInterest,new ArrayList<String>());

		ArrayList<String> bookIds = data.get("bookId");
		ArrayList<String> titles = data.get("title");
		ArrayList<String> pubIds = data.get("pubId");

		books =new ArrayList<Book>();
		for(int i = 0; i < bookIds.size(); i++){
			books.add(new Book(bookIds.get(i), titles.get(i), pubIds.get(i)));
		}

		return books;
	}
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
	public void deleteLoan(Loan loan)
	{
		String query = "DELETE FROM tbl_book_loans WHERE bookId = "+loan.getBookId() + " AND branchId = " + loan.getBranchId() +" AND cardNo = " + loan.getCardNo();
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
			//System.out.println("Error reading the ResultSet");
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
			}else if(query.charAt(0) == 'U' || query.charAt(0) == 'u' || query.charAt(0) == 'I' || query.charAt(0) == 'i' || query.charAt(0) == 'd' || query.charAt(0) == 'D'){
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
			//rs.close();
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






	//---------------------------Administrator---------------------

	//----Adding Methods-----
	public void addBook(Book b)
	{
		String query = "INSERT INTO tbl_book (title, pubId) VALUES('" + b.getTitle() + "', '" + b.getPubId() + "')";
		//TODO: delete the line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void addAuthor(Author a)
	{
		String query = "INSERT INTO tbl_author (authorName) VALUES('" + a.getAuthorName() + "')";
		//TODO: delete the line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void addPublisher(Publisher p)
	{
		String query = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES('" + p.getPublisherName() + "', '" + p.getPublisherAddress() + "', '" + p.getPublisherPhone()+ "')";
		//TODO: delete the line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void addLibraryBranch(LibraryBranch lb)
	{
		String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES('" + lb.getName() + "', '" + lb.getLocation() +"')";
		//TODO: delete the line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void addBorrower(Borrower b)
	{
		String query = "INSERT INTO tbl_borrower (name, address, phone) VALUES('" + b.getName() + "', '" + b.getAddress() +"', '"+b.getPhone() +"')";
		//TODO: delete the line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}

	//-------Delete Methods----------
	public void deleteBook(Book b)
	{
		String query = "DELETE FROM tbl_book_authors WHERE bookId = " + b.getBookId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_book_copies WHERE bookId = " + b.getBookId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_book_loans WHERE bookId = " + b.getBookId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_book_genres WHERE bookId = " + b.getBookId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_book WHERE bookId = " + b.getBookId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void deleteAuthor(Author a)
	{
		String query = "DELETE FROM tbl_book_authors WHERE authorId = " + a.getAuthorId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
		query = "DELETE FROM tbl_author WHERE authorId = " + a.getAuthorId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void deletePublisher(Publisher p)
	{
		//also delete related books or leave the foreing key in books null and allow administrator to change it latter
		//I will choose to delete all because if the publisher is deleted it makes no sence to have that copy of the book borrowed for example. the administrator can latter add a new copy of the book that will come up in the future with a new publisher
		ArrayList<Book> allBooks = getAllBooks();
		for(Book book : allBooks){
			if(book.getPubId().equals(p.getPublisherId())){
				executeUpdateQuery("UPDATE tbl_book SET pubId = NULL WHERE  bookId = " + book.getBookId(), new ArrayList<String>());
			}
		}
		String query = "DELETE FROM tbl_publisher WHERE publisherId = " + p.getPublisherId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

	}
	public void deleteLibraryBranch(LibraryBranch lb)
	{
		String query = "DELETE FROM tbl_book_loans WHERE branchId = " + lb.getBranchId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_book_copies WHERE branchId = " + lb.getBranchId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_library_branch WHERE branchId = " + lb.getBranchId();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void deleteBorrower(Borrower b)
	{
		String query = "DELETE FROM tbl_book_loans WHERE cardNo = " + b.getCardNo();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());

		query = "DELETE FROM tbl_borrower WHERE cardNo = " + b.getCardNo();
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	//-----Update Methods-----
	public void updateBookTitle(Book b, Book newBook)
	{
		String query = "Update tbl_book SET title = '" + newBook.getTitle() +  "' WHERE bookId = " + b.getBookId();
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updateBookPublisher(Book b, Book newBook)
	{
		String query = "Update tbl_book SET pubId = " + newBook.getPubId() +  " WHERE bookId = " + b.getBookId();
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updateAuthor(Author a, Author newAuthor)
	{
		String query = "Update tbl_author SET authorName = '" + newAuthor.getAuthorName() + "' WHERE " + "authorId = " + a.getAuthorId();
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updatePublisher(Publisher p, Publisher newPublisher)
	{
		String query = "UPDATE tbl_publisher SET publisherName = '" + newPublisher.getPublisherName() + "', publisherAddress = '" + newPublisher.getPublisherAddress() + "', publisherPhone = '" + newPublisher.getPublisherPhone() + "' WHERE " + " publisherId = " + p.getPublisherId();
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updateLibraryBranch(LibraryBranch lb, LibraryBranch newLibraryBranch)
	{
		String query = "UPDATE tbl_library_branch SET branchName = '" + newLibraryBranch.getName() + "', branchAddress = '" + newLibraryBranch.getLocation() + "' WHERE branchId = " + lb.getBranchId(); 
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updateBorrower(Borrower b, Borrower newBorrower)
	{
		String query = "UPDATE tbl_borrower SET name = '" + newBorrower.getName() + "', address = '" + newBorrower.getAddress() + "', phone = '" + newBorrower.getPhone() + "' WHERE cardNo = " + b.getCardNo();  
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public void updateLoan(Loan l, Loan newLoan)
	{
		//to over-ride the due date of the loan
		String query = "UPDATE tbl_book_loans SET dueDate = '" + newLoan.getDueDateAsString() + "' WHERE bookId = " + l.getBookId() + " AND branchId = " + l.getBranchId() + " AND cardNo = " + l.getCardNo(); 
		//TODO: delete this line bellow
		//System.out.println(query);
		executeUpdateQuery(query, new ArrayList<String>());
	}
	//------Get All-----
	//books is already implemented
	//Library Branches is already implemented
	public ArrayList<Loan> getAllLoans()
	{
		//TODO: Implement this function. It will require:(Remember to use the formats of the previous methods)
		//1. Implement another constructor in Loans that accept Strings to build dates
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("bookId");
		columnsOfInterest.add("branchId");
		columnsOfInterest.add("cardNo");
		columnsOfInterest.add("dateOut");
		columnsOfInterest.add("dueDate");
		columnsOfInterest.add("dateIn");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_book_loans", columnsOfInterest, new ArrayList<String>());

		loans = new ArrayList<Loan>();
		ArrayList<String> bookIds = data.get("bookId");
		ArrayList<String> branchIds = data.get("branchId");
		ArrayList<String> cardNos = data.get("cardNo");
		ArrayList<String> dateOuts = data.get("dateOut");
		ArrayList<String> dueDates = data.get("dueDate");
		//System.out.println("++++++++++++++In queries bookId.size = " + bookIds.size());
		for(int i = 0; i< bookIds.size(); i++){
			loans.add(new Loan(bookIds.get(i), branchIds.get(i), cardNos.get(i), dateOuts.get(i), dueDates.get(i)));
		}
		//System.out.println("++++++++++++++In queries loans.size = " + loans.size());
		return loans;
	}
	public ArrayList<Loan> getPrevAllLoans()
	{
		return loans;
	}
	public ArrayList<Author> getAllAuthors()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("authorId");
		columnsOfInterest.add("authorName");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_author", columnsOfInterest, new ArrayList<String>());

		authors = new ArrayList<Author>();
		ArrayList<String> authorIds = data.get("authorId");
		ArrayList<String> authorNames = data.get("authorName");

		for(int i = 0; i< authorIds.size(); i++){
			authors.add(new Author(authorIds.get(i), authorNames.get(i)));
		}

		return authors;
	}
	public ArrayList<Author> getPrevAllAuthors()
	{
		return authors;
	}
	public ArrayList<Publisher> getAllPublisher()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("publisherId");
		columnsOfInterest.add("publisherName");
		columnsOfInterest.add("publisherAddress");
		columnsOfInterest.add("publisherPhone");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_publisher", columnsOfInterest, new ArrayList<String>());

		publishers = new ArrayList<Publisher>();
		ArrayList<String> publisherIds = data.get("publisherId");
		ArrayList<String> publisherNames = data.get("publisherName");
		ArrayList<String> publisherAddresses = data.get("publisherAddress");
		ArrayList<String> publisherPhones = data.get("publisherPhone");

		for(int i = 0; i< publisherIds.size(); i++){
			publishers.add(new Publisher(publisherIds.get(i),publisherNames.get(i), publisherAddresses.get(i), publisherPhones.get(i)));
		}

		return publishers;
	}
	public ArrayList<Publisher> getPrevAllPublisher()
	{
		return publishers;
	}
	public ArrayList<Borrower> getAllBorrowers()
	{
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("cardNo");
		columnsOfInterest.add("name");
		columnsOfInterest.add("address");
		columnsOfInterest.add("phone");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_borrower", columnsOfInterest, new ArrayList<String>());

		borrowers = new ArrayList<Borrower>();
		ArrayList<String> cardNos = data.get("cardNo");
		ArrayList<String> names = data.get("name");
		ArrayList<String> addresses = data.get("address");
		ArrayList<String> phones = data.get("phone");

		for(int i = 0; i< cardNos.size(); i++){
			borrowers.add(new Borrower(cardNos.get(i), names.get(i), addresses.get(i), phones.get(i)));
		}

		return borrowers;
	}
	public ArrayList<Borrower> getPrevAllBorrowers()
	{
		return borrowers;
	}
	public void insertBookAuthor(BookAuthors ba){
		String query = "INSERT INTO tbl_book_authors VALUES(" + ba.getBookId() + ", " + ba.getAuthorId() +")";
		//System.out.println(query + " +++++++++++++++");
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public ArrayList<BookAuthors> getAllBookAuthor(){
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("bookId");
		columnsOfInterest.add("authorId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_book_authors", columnsOfInterest, new ArrayList<String>());

		ArrayList<BookAuthors> ba = new ArrayList<BookAuthors>();
		ArrayList<String> bookIds = data.get("bookId");
		ArrayList<String> authorIds = data.get("authorId");


		for(int i = 0; i< bookIds.size(); i++){
			ba.add(new BookAuthors(bookIds.get(i), authorIds.get(i)));
		}

		return ba;
	}
	public void insertBookGenre(BookGenres bg){
		String query = "INSERT INTO tbl_book_genres VALUES(" + bg.getGenreId() + ", " + bg.getBookId() +")";
		//System.out.println(query + " +++++++++++++++");
		executeUpdateQuery(query, new ArrayList<String>());
	}
	public ArrayList<Genre> getAllGenres(){
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("genre_id");
		columnsOfInterest.add("genre_name");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_genre", columnsOfInterest, new ArrayList<String>());

		//System.out.println("UUUUUUUU " + data.size());
		ArrayList<Genre> genres = new ArrayList<Genre>();
		ArrayList<String> genre_ids = data.get("genre_id");
		ArrayList<String> genere_names = data.get("genre_name");
		//System.out.println("---------------- " + genre_ids.size());

		for(int i = 0; i< genre_ids.size(); i++){
			genres.add(new Genre(genere_names.get(i), genre_ids.get(i)));
		}

		return genres;
	}
	public ArrayList<BookGenres> getAllBookGenres(){
		ArrayList<String> columnsOfInterest = new ArrayList<String>();
		columnsOfInterest.add("genre_id");
		columnsOfInterest.add("bookId");

		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM tbl_book_genres", columnsOfInterest, new ArrayList<String>());

		ArrayList<BookGenres> bookGenres = new ArrayList<BookGenres>();
		ArrayList<String> genre_ids = data.get("genre_id");
		ArrayList<String> bookIds = data.get("bookId");


		for(int i = 0; i< genre_ids.size(); i++){
			bookGenres.add(new BookGenres(bookIds.get(i), genre_ids.get(i)));
		}

		return bookGenres;
	}


	//	public void deleteBookAndAuthor(Book b, Author a){
	//		deleteAuthor(a);
	//		deleteBook(b);
	//	}
}
