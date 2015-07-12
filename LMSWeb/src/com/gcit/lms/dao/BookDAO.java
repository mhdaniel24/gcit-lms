package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.*;

//TODO: Leave this one for last because it depends on many others and it is also the most complicated one
public class BookDAO extends BaseDAO {

	public BookDAO(Connection conn) throws Exception {
		super(conn);
	}

	//I am allowing the publisher to be updated since when the book is created it adds no 
	//publisher also if a publisher is deleted the book's publisher becomes null. 
	//So I am giving the ability in update to modify that null value
	public void update(Book book) throws Exception {
		save("update tbl_book set title = ? where bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });

	}

	public void delete(Book book) throws Exception {
		save("delete from tbl_book where bookId = ?",
				new Object[] { book.getBookId() });
	}

	public void create(Book book) throws Exception {
		int bookId = saveWithID("insert into tbl_book (title, pubId) values(?,?)",
				new Object[] { book.getTitle(), book.getPublisher().getPublisherId()});

		for(Author a: book.getAuthors()){
			save("insert into tbl_book_authors (bookId, authorId) values (?,?)", 
					new Object[]{bookId, a.getAuthorId()});
		}

		for(Genre g: book.getGenres()){
			save("insert into tbl_book_genres (bookId, genre_id) values (?,?)", 
					new Object[]{bookId, g.getGenreId()});
		}

		//TODO: should I also create the publisher or add the publisher id in this method or that will happen in update?
	}

	public List<Book> readAll() throws Exception{
		return (List<Book>) read("select * from tbl_book", null);

	}

	public Book readOne(int bookId) throws Exception {
		List<Book> books = (List<Book>) read("select * from tbl_book where bookId = ?", new Object[] {bookId});
		if(books!=null && books.size()>0){
			return books.get(0);
		}
		return null;
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();
		PublisherDAO pdao = new PublisherDAO(getConnection());
		AuthorDAO aDao = new AuthorDAO(getConnection());
		GenreDAO gDao = new GenreDAO(getConnection());

		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			if(rs.getInt("pubId") == 0){
				b.setPublisher(null);
			}else{
			b.setPublisher(pdao.readOne(rs.getInt("pubId")));
			}
			@SuppressWarnings("unchecked")
			List<Author> authors = (List<Author>) aDao.readFirstLevel("select * from tbl_author where authorId In"
					+ "(select authorId from tbl_book_authors where bookId=?)", new Object[] {rs.getInt("bookId")});
			b.setAuthors(authors);
			@SuppressWarnings("unchecked")
			List<Genre> genres = (List<Genre>) gDao.readFirstLevel("select * from tbl_genre where genre_id In"
					+ "(select genre_id from tbl_book_genres where bookId=?)", new Object[]{rs.getInt("bookId")});
			b.setGenres(genres);

			books.add(b);
		}

		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Book> books = new ArrayList<Book>();
		//PublisherDAO pdao = new PublisherDAO(getConnection());
		//AuthorDAO aDao = new AuthorDAO(getConnection());
		//GenreDAO gD
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}

		return books;
	}

}
