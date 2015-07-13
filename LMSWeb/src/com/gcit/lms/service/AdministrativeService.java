package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import com.gcit.lms.domain.*;
import com.gcit.lms.dao.*;

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
		AuthorDAO adao = new AuthorDAO(conn);
		
		try {
			if(action.equals("delete")){
				if(author == null){
					throw new Exception("The Author parameter cant be null");
				}else{
					adao.delete(author);
					conn.commit();
				}
			}else if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author Name cannot be empty or more than 45 Chars");
			} else {
				if(action.equals("create")){
					adao.create(author);
				}else if(action.equals("update")){
					if(adao.readOne(author.getAuthorId()) == null){
						throw new Exception(
								"The author you are trying to update does not exist");
					}else{
						adao.update(author);
					}
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


	//--------------------------------------Genre(not needed as a service)------------------------------------//
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
		GenreDAO gdao = new GenreDAO(conn);
		
		try {
			if(action.equals("delete")){
				if(genre == null){
					throw new Exception("The Genre parameter can't be null");
				}else{
					gdao.delete(genre);
					conn.commit();
				}
				
			}else if (genre == null || genre.getGenreName() == null
					|| genre.getGenreName().length() == 0
					|| genre.getGenreName().length() > 45) {
				throw new Exception(
						"Genre Name cannot be empty or more than 45 Chars");
			} else {
				if(action.equals("create")){
					gdao.create(genre);
				}else if(action.equals("update")){
					if(gdao.readOne(genre.getGenreId()) == null){
						throw new Exception(
								"The genre you are trying to update is null");
					}else{
						gdao.update(genre);
					}
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

	public void deleteBook(Book book) throws Exception {
		checkBookAnd(book, "delete");
	}

	private void checkBookAnd(Book book, String action) throws Exception, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookDAO bdao = new BookDAO(conn);
		
		try {
			if(action.equals("delete")){
				if(book == null){
					throw new Exception("The book parameter can't be null");
				}else{
					bdao.delete(book);
					conn.commit();
				}
			}else if (book == null || book.getTitle() == null
					|| book.getTitle().length() == 0
					|| book.getTitle().length() > 45) {
				throw new Exception(
						"Book title cannot be empty or more than 45 Chars");
			} else {
				if(action.equals("create")){
					if(book.getAuthors() == null || book.getGenres() == null){
						//TODO: not sure if throw the exception or just initialize them both
						throw new Exception("BookAuthors and Genres can't be null");
					}
					bdao.create(book);
				}else if(action.equals("update")){
					if(bdao.readOne(book.getBookId()) == null){
						throw new Exception("The book you are trying to update does not exist");
					}else{
						bdao.update(book);
					}
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
		PublisherDAO pdao = new PublisherDAO(conn);
		
		try {
			if(action.equals("delete")){
				if(publisher == null){
					throw new Exception("The Publisher parameter can't be null");
				}else{
					pdao.delete(publisher);
					conn.commit();
				}
				
			}else if (publisher == null || publisher.getPublisherName() == null
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
				if(action.equals("create")){
					pdao.create(publisher);
				}else if(action.equals("update")){
					if(pdao.readOne(publisher.getPublisherId()) == null){
						throw new Exception("The publisher you are trying to update does not exist");
					}else{
						pdao.update(publisher);
					}	
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


	private void checkLibraryBranchAnd(LibraryBranch libraryBranch, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);


		try {
			if(action.equals("delete")){
				if(libraryBranch == null){
					throw new Exception("The LibraryBranch parameter can't be null");
				}else{
					lbdao.delete(libraryBranch);
					conn.commit();
				}
				
			}else if (libraryBranch == null || libraryBranch.getBranchName() == null
					|| libraryBranch.getBranchName().length() == 0
					|| libraryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library branch name cannot be empty or more than 45 Chars");
			}else if(libraryBranch == null || libraryBranch.getBranchAddress() == null
					|| libraryBranch.getBranchAddress().length() == 0
					|| libraryBranch.getBranchAddress().length() > 45){
				throw new Exception(
						"Library Branch address cannot be empty or more than 45 Chars");
			}else {

				if(action.equals("create")){
					lbdao.create(libraryBranch);
				}else if(action.equals("update")){
					if(lbdao.readOne(libraryBranch.getBranchId()) == null){
						throw new Exception("The library branch you are trying to update does not exist");
					}else{
						lbdao.update(libraryBranch);
					}
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

		public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.createConnection();
	
			try {
				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				List<LibraryBranch> allLibraryBranches = lbdao.readAll();
				conn.commit();//not sure if needed
				return allLibraryBranches;
			} catch (Exception e) {
				e.printStackTrace();
				conn.rollback();//not sure if needed
				return null;
			} finally {
				conn.close();
			}
		}
	
		public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.createConnection();
	
			try {
				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				LibraryBranch libraryBranch = lbdao.readOne(branchId);
				conn.commit();//not sure if needed
				return libraryBranch;
			} catch (Exception e) {//not a valid bookId
				e.printStackTrace();
				conn.rollback();//not sure if needed
				return null;
			} finally {
				conn.close();
			}
		}

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
			if(action.equals("delete")){
				if(borrower == null){
					throw new Exception("The Borrower parameter can't be null");
				}else{
					bdao.delete(borrower);
					conn.commit();
				}

			}else if (borrower == null || borrower.getName() == null
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
					if(bdao.readOne(borrower.getCardNo()) == null){
						throw new Exception("The borrower you are trying to update does not exist");
					}else{
						bdao.update(borrower);
					}
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
//	public void createBookLoan(BookLoan bookLoan) throws Exception {
//		checkBookLoanAnd(bookLoan, "create");
//	}
	
	//used for over-ride Due DAte for a Book Loan
	public void updateBookLoan(BookLoan bookLoan) throws Exception {
		checkBookLoanAnd(bookLoan, "update");
	}
//	public void deleteBookLoan(BookLoan bookLoan) throws Exception {
//		checkBookLoanAnd(bookLoan, "delete");
//	}


	private void checkBookLoanAnd(BookLoan bookLoan, String action) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookLoanDAO bldao = new BookLoanDAO(conn);



		try {
			if(action.equals("delete")){
				if(bookLoan == null){
					throw new Exception("The BookLoan parameter cant be null");
				}else{
					bldao.delete(bookLoan);
					conn.commit();
				}
			}else if (bookLoan == null || bookLoan.getDateOut() == null
					|| bookLoan.getDueDate() == null
					|| bookLoan.getDateOut().after(bookLoan.getDueDate())) {
				throw new Exception(
						"The due date of the book most happen after the date out of the book");
			}else {

				if(action.equals("create")){
					bldao.create(bookLoan);
				}else if(action.equals("update")){
					if(bldao.readOne(bookLoan.getBook().getBookId(), bookLoan.getBorrower().getCardNo(), bookLoan.getLibraryBranch().getBranchId()) == null){
						throw new Exception("The BookLoan you are trying to update does not exist");
					}else{
						bldao.update(bookLoan);
					}
						
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
	
	public List<Author> searchAuthors(String searchString) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		return adao.readByAuthorName(searchString);
	}
}

