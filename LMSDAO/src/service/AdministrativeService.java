package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.BookLoanDAO;
import dao.BorrowerDAO;
import dao.GenreDAO;
import dao.LibraryBranchDAO;
import dao.PublisherDAO;
import domain.Author;
import domain.Book;
import domain.BookLoan;
import domain.Borrower;
import domain.Genre;
import domain.LibraryBranch;
import domain.Publisher;

public class AdministrativeService {

	//-----------------------------Author---------------------------------------
	public void createAuthor(Author author) throws Exception {
		checkAuthorAnd(author, "create");
	}

	public void deleteAuthor(Author author) throws Exception{
		checkAuthorAnd(author, "delete");
	}

	public void updateAuthor(Author author) throws Exception{
		checkAuthorAnd(author, "update");
	}

	private void checkAuthorAnd(Author author, String action) throws Exception, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				AuthorDAO adao = new AuthorDAO(conn);
				if(action.equals("create")){
					adao.create(author);
				}else if(action.equals("update")){
					adao.update(author);
				}else if(action.equals("delete")){
					adao.delete(author);
				}

				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Author> readAllAuthors() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			AuthorDAO adao = new AuthorDAO(conn);
			List<Author> allAuthors = adao.readAll();
			conn.commit();//not sure if needed
			return allAuthors;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public Author readOneAuthor(int authorId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			AuthorDAO adao = new AuthorDAO(conn);
			Author author = adao.readOne(authorId);
			conn.commit();//not sure if needed
			return author;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}


	//--------------------------------------Genre------------------------------------//
	public void createGenre(Genre genre) throws Exception {
		checkGenreAnd(genre, "create");
	}

	public void updateGenre(Genre genre) throws Exception {
		checkGenreAnd(genre, "update");
	}

	public void deleteGenre(Genre genre) throws Exception {
		checkGenreAnd(genre, "delete");
	}

	private void checkGenreAnd(Genre genre, String action) throws Exception, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			if (genre == null || genre.getGenreName() == null
					|| genre.getGenreName().length() == 0
					|| genre.getGenreName().length() > 45) {
				throw new Exception(
						"Genre Name cannot be empty or more than 45 Chars");
			} else {
				GenreDAO gdao = new GenreDAO(conn);
				if(action.equals("create")){
					gdao.create(genre);
				}else if(action.equals("update")){
					gdao.update(genre);
				}else if(action.equals("delete")){
					gdao.delete(genre);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Genre> readAllGenres() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			GenreDAO gdao = new GenreDAO(conn);
			List<Genre> allGenres = gdao.readAll();
			conn.commit();//not sure if needed
			return allGenres;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public Genre readOneGenre(int genreId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			GenreDAO gdao = new GenreDAO(conn);
			Genre genre = gdao.readOne(genreId);
			conn.commit();//not sure if needed
			return genre;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}


	//---------------------------------------Book----------------------------------------
	public void createBook(Book book) throws Exception {
		checkBookAnd(book, "create");
	}

	public void updateBook(Book book) throws Exception {
		checkBookAnd(book, "update");
	}

	public void delteBook(Book book) throws Exception {
		checkBookAnd(book, "delete");
	}

	private void checkBookAnd(Book book, String action) throws Exception, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			if (book == null || book.getTitle() == null
					|| book.getTitle().length() == 0
					|| book.getTitle().length() > 45) {
				throw new Exception(
						"Book title cannot be empty or more than 45 Chars");
			} else {
				BookDAO bdao = new BookDAO(conn);
				if(action.equals("create")){
					bdao.create(book);
				}else if(action.equals("update")){
					bdao.update(book);
				}else if(action.equals("delete")){
					bdao.delete(book);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Book> readAllBooks() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookDAO bdao = new BookDAO(conn);
			List<Book> allBooks = bdao.readAll();
			conn.commit();//not sure if needed
			return allBooks;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public Book readOneBook(int bookId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookDAO bdao = new BookDAO(conn);
			Book book = bdao.readOne(bookId);
			conn.commit();//not sure if needed
			return book;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	//---------------------------------------Publisher----------------------------------------
	public void createPublisher(Publisher publisher) throws Exception {
		checkPublisherAnd(publisher, "create");
	}

	public void updatePublisher(Publisher publisher) throws Exception {
		checkPublisherAnd(publisher, "update");
	}

	public void deletePublisher(Publisher publisher) throws Exception {
		checkPublisherAnd(publisher, "delete");
	}

	private void checkPublisherAnd(Publisher publisher, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		try {
			if (publisher == null || publisher.getPublisherName() == null
					|| publisher.getPublisherName().length() == 0
					|| publisher.getPublisherName().length() > 45) {
				throw new Exception(
						"Publisher name cannot be empty or more than 45 Chars");
			}else if(publisher == null || publisher.getPublisherAddress() == null
					|| publisher.getPublisherAddress().length() == 0
					|| publisher.getPublisherAddress().length() > 45){
				throw new Exception(
						"Publisher address cannot be empty or more than 45 Chars");
			}else if(publisher == null || publisher.getPublisherPhone() == null
					|| publisher.getPublisherPhone().length() == 0
					|| publisher.getPublisherPhone().length() > 45){
				throw new Exception(
						"Publisher phone cannot be empty or more than 45 Chars");

			}else {
				PublisherDAO pdao = new PublisherDAO(conn);
				if(action.equals("create")){
					pdao.create(publisher);
				}else if(action.equals("update")){
					pdao.update(publisher);
				}else if(action.equals("delete")){
					pdao.delete(publisher);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}

	public List<Publisher> readAllPublishers() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			PublisherDAO pdao = new PublisherDAO(conn);
			List<Publisher> allPublishers = pdao.readAll();
			conn.commit();//not sure if needed
			return allPublishers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public Publisher readOnePublisher(int publisherId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			PublisherDAO pdao = new PublisherDAO(conn);
			Publisher publisher = pdao.readOne(publisherId);
			conn.commit();//not sure if needed
			return publisher;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	//-------------------------------LibraryBranch----------------------------------
	public void createLibraryBranch(LibraryBranch libraryBranch) throws Exception {
		checkLibraryBranchAnd(libraryBranch, "create");
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws Exception {
		checkLibraryBranchAnd(libraryBranch, "update");
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws Exception {
		checkLibraryBranchAnd(libraryBranch, "delete");
	}
	
	
	private void checkLibraryBranchAnd(LibraryBranch librryBranch, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		
		
		
		try {
			if(action.equals("delete")){
				lbdao.delete(librryBranch);
			}
			
			if (librryBranch == null || librryBranch.getBranchName() == null
					|| librryBranch.getBranchName().length() == 0
					|| librryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library branch name cannot be empty or more than 45 Chars");
			}else if(librryBranch == null || librryBranch.getBranchAddress() == null
					|| librryBranch.getBranchAddress().length() == 0
					|| librryBranch.getBranchAddress().length() > 45){
				throw new Exception(
						"Library Branch address cannot be empty or more than 45 Chars");
			}else {
				
				if(action.equals("create")){
					lbdao.create(librryBranch);
				}else if(action.equals("update")){
					lbdao.update(librryBranch);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
//	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
//		ConnectionUtil c = new ConnectionUtil();
//		Connection conn = c.createConnection();
//
//		try {
//			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
//			List<LibraryBranch> allLibraryBranches = lbdao.readAll();
//			conn.commit();//not sure if needed
//			return allLibraryBranches;
//		} catch (Exception e) {
//			e.printStackTrace();
//			conn.rollback();//not sure if needed
//			return null;
//		} finally {
//			conn.close();
//		}
//	}
//
//	public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
//		ConnectionUtil c = new ConnectionUtil();
//		Connection conn = c.createConnection();
//
//		try {
//			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
//			LibraryBranch libraryBranch = lbdao.readOne(branchId);
//			conn.commit();//not sure if needed
//			return libraryBranch;
//		} catch (Exception e) {//not a valid bookId
//			e.printStackTrace();
//			conn.rollback();//not sure if needed
//			return null;
//		} finally {
//			conn.close();
//		}
//	}
	
	//------------------------------------------------Borrower------------------------------------------------

	public void createBorrower(Borrower borrower) throws Exception {
		checkBorrowerAnd(borrower, "create");
	}
	public void updateBorrower(Borrower borrower) throws Exception {
		checkBorrowerAnd(borrower, "update");
	}
	public void deleteBorrower(Borrower borrower) throws Exception {
		checkBorrowerAnd(borrower, "delete");
	}

			
	private void checkBorrowerAnd(Borrower borrower, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);
		
		 if(action.equals("delete")){
				bdao.delete(borrower);
		 }
		
		try {
			if (borrower == null || borrower.getName() == null
					|| borrower.getName().length() == 0
					|| borrower.getName().length() > 45) {
				throw new Exception(
						"Borrower name cannot be empty or more than 45 Chars");
			}else if(borrower == null || borrower.getAddress() == null
					|| borrower.getAddress().length() == 0
					|| borrower.getAddress().length() > 45){
				throw new Exception(
						"Borrower address cannot be empty or more than 45 Chars");
			}else if(borrower == null || borrower.getPhone() == null
					|| borrower.getPhone().length() == 0
					||borrower.getPhone().length() > 45){
				throw new Exception(
						"Borrower phone cannot be empty or more than 45 Chars");
			}else {
				if(action.equals("create")){
					bdao.create(borrower);
				}else if(action.equals("update")){
					bdao.update(borrower);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public List<Borrower> readAllBorrower() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			List<Borrower> allBorrowers = bdao.readAll();
			conn.commit();//not sure if needed
			return allBorrowers;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public Borrower readOneBorrower(int borrowerId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Borrower borrower = bdao.readOne(borrowerId);
			conn.commit();//not sure if needed
			return borrower;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
//-------------------------------------------Book Loans----------------------------------	
	public void createBookLoan(BookLoan bookLoan) throws Exception {
		checkBookLoanAnd(bookLoan, "create");
	}
	public void updateBookLoan(BookLoan bookLoan) throws Exception {
		checkBookLoanAnd(bookLoan, "update");
	}
	public void deleteBookLoan(BookLoan bookLoan) throws Exception {
		checkBookLoanAnd(bookLoan, "delete");
	}

			
	private void checkBookLoanAnd(BookLoan bookLoan, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookLoanDAO bdao = new BookLoanDAO(conn);
		
		if(action.equals("delete")){
			bdao.delete(bookLoan);
		}
		
		try {
			if (bookLoan == null || bookLoan.getDateOut() == null
					|| bookLoan.getDueDate() == null
					|| bookLoan.getDateOut().after(bookLoan.getDueDate())) {
				throw new Exception(
						"The due date of the book most happen after the date out of the book");
			}else {
				
				if(action.equals("create")){
					bdao.create(bookLoan);
				}else if(action.equals("update")){
					bdao.update(bookLoan);
				}
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public List<BookLoan> readAllBookLoans() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			List<BookLoan> allBookLoans = bldao.readAll();
			conn.commit();//not sure if needed
			return allBookLoans;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}

	public BookLoan readOneBookLoan(int branchId, int cardNo, int bookId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookLoanDAO bldao = new BookLoanDAO(conn);
			BookLoan bookLoan = bldao.readOne(bookId,cardNo,branchId);
			conn.commit();//not sure if needed
			return bookLoan;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
}

