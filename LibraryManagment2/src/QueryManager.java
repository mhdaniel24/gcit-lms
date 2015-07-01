import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


public class QueryManager {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//TODO: Add question mark for the prepare statement!!!
	//----------------------------------Insert----------------------------------
		public void inserNewLoan(Loan loan)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(loan.getBookId());
			variables.add(loan.getBranchId());
			variables.add(loan.getCardNo());
			variables.add(loan.getDateOutAsString());
			variables.add(loan.getDueDateAsString());
			
			String query = "INSERT INTO tbl_book_loans VALUES ("+loan.getBookId() +", " + loan.getBranchId() + ", " + loan.getCardNo() + ", '" + loan.getDateOutAsString()+"', '" + loan.getDueDateAsString() +"', NULL)";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void insertBook(Book b)
		{
			String query = "INSERT INTO tbl_book (title, pubId) VALUES('" + b.getTitle() + "', '" + b.getPubId() + "')";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void insertAuthor(Author a)
		{
			String query = "INSERT INTO tbl_author (authorName) VALUES('" + a.getAuthorName() + "')";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void insertPublisher(Publisher p)
		{
			String query = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES('" + p.getPublisherName() + "', '" + p.getPublisherAddress() + "', '" + p.getPublisherPhone()+ "')";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void insertLibraryBranch(Branch lb)
		{
			String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES('" + lb.getBranchName() + "', '" + lb.getBranchAddress() +"')";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void insertBorrower(Borrower b)
		{
			String query = "INSERT INTO tbl_borrower (name, address, phone) VALUES('" + b.getName() + "', '" + b.getAddress() +"', '"+b.getPhone() +"')";
			executeUpdateQuery(query, new ArrayList<String>());
		}
		
		//-------Delete Methods----------
		public void deleteBook(Book b)
		{
			String query = "DELETE FROM tbl_book_authors WHERE bookId = " + b.getBookId();
			//System.out.println(query);
			executeUpdateQuery(query, new ArrayList<String>());
			
			query = "DELETE FROM tbl_book_copies WHERE bookId = " + b.getBookId();

			executeUpdateQuery(query, new ArrayList<String>());
			
			query = "DELETE FROM tbl_book_loans WHERE bookId = " + b.getBookId();
			executeUpdateQuery(query, new ArrayList<String>());
			
			query = "DELETE FROM tbl_book_genres WHERE bookId = " + b.getBookId();
			executeUpdateQuery(query, new ArrayList<String>());
			
			query = "DELETE FROM tbl_book WHERE bookId = " + b.getBookId();
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void deleteAuthor(Author a)
		{
			String query = "DELETE FROM tbl_book_authors WHERE authorId = " + a.getAuthorId();
			executeUpdateQuery(query, new ArrayList<String>());
			query = "DELETE FROM tbl_author WHERE authorId = " + a.getAuthorId();
			executeUpdateQuery(query, new ArrayList<String>());
		}
		public void deletePublisher(Publisher p)
		{
			ArrayList<Book> allBooks = getAllBooks();
			for(Book book : allBooks){
				if(book.getPubId().equals(p.getPublisherId())){
					executeUpdateQuery("UPDATE tbl_book SET pubId = NULL WHERE  bookId = " + book.getBookId(), new ArrayList<String>());
				}
			}
			String query = "DELETE FROM tbl_publisher WHERE publisherId = " + p.getPublisherId();
			executeUpdateQuery(query, new ArrayList<String>());
			
		}
		public void deleteLibraryBranch(Branch lb)
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
		public void deleteLoan(Loan loan)
		{
			String query = "DELETE FROM tbl_book_loans WHERE bookId = "+loan.getBookId() + " AND branchId = " + loan.getBranchId() +" AND cardNo = " + loan.getCardNo();
			executeUpdateQuery(query, new ArrayList<String>());
		}
	//---------------------------------Get All Methods-------------------------------------------------
	public ArrayList<Book> getAllBooks()
	{
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Book.getTableName(), Book.getColumnNames(), new ArrayList<String>());

		ArrayList<Book> books = new ArrayList<Book>();

		for(int i = 0; i< data.get(Loan.getColumnNames().get(0)).size(); i++){
			books.add(new Book(data.get(Book.getColumnNames().get(0)).get(i),data.get(Book.getColumnNames().get(1)).get(i), data.get(Book.getColumnNames().get(2)).get(i)));
		}

		return books;
	}
	
	
	public ArrayList<Branch> getAllBranches()
	{	
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Branch.getTableName(), Branch.getColumnNames(), new ArrayList<String>());

		ArrayList<Branch> branches = new ArrayList<Branch>();

		for(int i = 0; i< data.get(Branch.getColumnNames().get(0)).size(); i++){
			branches.add(new Branch(data.get(Branch.getColumnNames().get(0)).get(i),data.get(Branch.getColumnNames().get(1)).get(i), data.get(Branch.getColumnNames().get(2)).get(i)));
		}

		return branches;
	}
	
	public ArrayList<Loan> getAllLoans()
	{
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Loan.getTableName(), Loan.getColumnNames(), new ArrayList<String>());

		ArrayList<Loan> loans = new ArrayList<Loan>();

		for(int i = 0; i< data.get(Loan.getColumnNames().get(0)).size(); i++){
			loans.add(new Loan(data.get(Loan.getColumnNames().get(0)).get(i),data.get(Loan.getColumnNames().get(1)).get(i), data.get(Loan.getColumnNames().get(2)).get(i), data.get(Loan.getColumnNames().get(3)).get(i), data.get(Loan.getColumnNames().get(4)).get(i), data.get(Loan.getColumnNames().get(5)).get(i)));
		}

		return loans;
	}
	public ArrayList<Author> getAllAuthors()
	{
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Author.getTableName(), Author.getColumnNames(), new ArrayList<String>());

		ArrayList<Author> authors = new ArrayList<Author>();

		for(int i = 0; i< data.get(Author.getColumnNames().get(0)).size(); i++){
			authors.add(new Author(data.get(Author.getColumnNames().get(0)).get(i),data.get(Author.getColumnNames().get(1)).get(i)));
		}

		return authors;
	}
	public ArrayList<Publisher> getAllPublisher()
	{		
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Publisher.getTableName(), Publisher.getColumnNames(), new ArrayList<String>());

		ArrayList<Publisher> publishers = new ArrayList<Publisher>();

		for(int i = 0; i< data.get(Publisher.getColumnNames().get(0)).size(); i++){
			publishers.add(new Publisher(data.get(Publisher.getColumnNames().get(0)).get(i),data.get(Publisher.getColumnNames().get(1)).get(i), data.get(Publisher.getColumnNames().get(2)).get(i), data.get(Publisher.getColumnNames().get(3)).get(i)));
		}

		return publishers;
	}
	public ArrayList<Borrower> getAllBorrowers()
	{
		HashMap<String, ArrayList<String>> data =  executeSelectQuery("SELECT * FROM "+Borrower.getTableName(), Borrower.getColumnNames(), new ArrayList<String>());

		ArrayList<Borrower> borrowers = new ArrayList<Borrower>();

		for(int i = 0; i< data.get(Borrower.getColumnNames().get(0)).size(); i++){
			borrowers.add(new Borrower(data.get(Borrower.getColumnNames().get(0)).get(i),data.get(Borrower.getColumnNames().get(1)).get(i), data.get(Borrower.getColumnNames().get(2)).get(i), data.get(Borrower.getColumnNames().get(3)).get(i)));
		}

		return borrowers;
	}	
	//-------------------------------------------------Helper Methods-----------------------------------------------------------
	
	
	/**
	 * @param updateQuery
	 * @param queryVariables
	 */
	private void executeUpdateQuery(String updateQuery, ArrayList<String> queryVariables)
	{
		executeQuery(updateQuery, queryVariables);
		closeConnection();
	}
	

	/**
	 * @param selectQuery
	 * @param columnsOfInterest
	 * @param queryVariables
	 * @return
	 */
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
	

	/**
	 * @param query
	 * @param queryVariables
	 */
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
	
	/**
	 * 
	 */
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
}
