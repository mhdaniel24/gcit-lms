package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.GenreDAO;
import dao.PublisherDAO;
import domain.Author;
import domain.Book;
import domain.Genre;
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


	//--------------------------------------Genre(although it is never mentioned in the requirements it is needed for a book)------------------------------------//
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



}
