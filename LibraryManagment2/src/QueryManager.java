import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.fabric.xmlrpc.base.Array;


public class QueryManager {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//TODO: Add question mark for the prepare statement!!!
	//----------------------------------Insert----------------------------------
		public void insertLoan(Loan loan)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(loan.getBookId());
			variables.add(loan.getBranchId());
			variables.add(loan.getCardNo());
			variables.add(loan.getDateOutAsString());
			variables.add(loan.getDueDateAsString());
			
			String query = "INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?,?,?,?,?)";
			executeUpdateQuery(query, variables);
		}
		public void insertBook(Book b)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(b.getTitle());
			variables.add(b.getPubId());
			String query = "INSERT INTO "+ Book.getTableName() + " (title, pubId) VALUES(?,?)";
			executeUpdateQuery(query, variables);
		}
		public void insertAuthor(Author a)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(a.getAuthorName());
			String query = "INSERT INTO tbl_author (authorName) VALUES(?)";
			executeUpdateQuery(query, variables);
		}
		public void insertPublisher(Publisher p)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(p.getPublisherName());
			variables.add(p.getPublisherAddress());
			variables.add(p.getPublisherPhone());
			
			String query = "INSERT INTO tbl_publisher (publisherName, publisherAddress, publisherPhone) VALUES(?,?,?)";
			executeUpdateQuery(query, variables);
		}
		public void insertLibraryBranch(Branch lb)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(lb.getBranchName());
			variables.add(lb.getBranchAddress());

			String query = "INSERT INTO tbl_library_branch (branchName, branchAddress) VALUES(?,?)";
			executeUpdateQuery(query, variables);
		}
		public void insertBorrower(Borrower b)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(b.getName());
			variables.add(b.getAddress());
			variables.add(b.getPhone());
			
			String query = "INSERT INTO tbl_borrower (name, address, phone) VALUES(?,?,?)";
			executeUpdateQuery(query, variables);
		}
		
		//-------Delete Methods----------
		public void deleteBook(Book b)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(b.getBookId());
			
			String query = "DELETE FROM tbl_book_authors WHERE bookId = ?";
			//System.out.println(query);
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_book_copies WHERE bookId = ?";

			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_book_loans WHERE bookId = ?";
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_book_genres WHERE bookId = ?";
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_book WHERE bookId = ?";
			executeUpdateQuery(query, variables);
		}
		public void deleteAuthor(Author a)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(a.getAuthorId());
			
			String query = "DELETE FROM tbl_book_authors WHERE authorId = ?";
			executeUpdateQuery(query, variables);
			query = "DELETE FROM tbl_author WHERE authorId = ?";
			executeUpdateQuery(query, variables);
		}
		public void deletePublisher(Publisher p)
		{
			ArrayList<String> variables = new ArrayList<String>();
			
			ArrayList<Book> allBooks = getAllBooks();
			for(Book book : allBooks){
				if(book.getPubId().equals(p.getPublisherId())){
					variables = new ArrayList<String>();
					variables.add(book.getBookId());
					executeUpdateQuery("UPDATE tbl_book SET pubId = NULL WHERE  bookId = ?", variables);
				}
			}
			
			variables = new ArrayList<String>();
			variables.add(p.getPublisherId());
			String query = "DELETE FROM tbl_publisher WHERE publisherId = ?";
			executeUpdateQuery(query, variables);
			
		}
		public void deleteLibraryBranch(Branch lb)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(lb.getBranchId());
			String query = "DELETE FROM tbl_book_loans WHERE branchId = ?";
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_book_copies WHERE branchId = ?";
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_library_branch WHERE branchId = ?";
			executeUpdateQuery(query, variables);
		}
		public void deleteBorrower(Borrower b)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(b.getCardNo());
			
			String query = "DELETE FROM tbl_book_loans WHERE cardNo = ?";
			executeUpdateQuery(query, variables);
			
			query = "DELETE FROM tbl_borrower WHERE cardNo = ?";
			executeUpdateQuery(query, variables);
		}
		public void deleteLoan(Loan loan)
		{
			ArrayList<String> variables = new ArrayList<String>();
			variables.add(loan.getBookId());
			variables.add(loan.getBranchId());
			variables.add(loan.getCardNo());
			
			String query = "DELETE FROM tbl_book_loans WHERE bookId = ? AND branchId = ? AND cardNo = ?";
			executeUpdateQuery(query, variables);
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
			pstmt.close();
			conn.close();
			//rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
			System.out.println("Error clossing DB connection");
		}
	}
}
