package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.gcit.lms.domain.*;

public class AuthorDAO extends BaseDAO {

	public AuthorDAO(Connection conn) throws Exception {
		super(conn);
	}

	//BookDAO is already adding all its authors should author also deal with adding all its books?
	public void create(Author author) throws Exception {
		save("insert into tbl_author (authorName) values(?)",
				new Object[] { author.getAuthorName() });
	}

	public void update(Author author) throws Exception {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });

	}

	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}

	public List<Author> readAll() throws Exception{
		return (List<Author>) read("select * from tbl_author", null);
		
	}

	public Author readOne(int authorId) throws Exception {
		List<Author> authors = (List<Author>) read("select * from tbl_author where authorId = ?", new Object[] {authorId});
		if(authors!=null && authors.size()>0){
			return authors.get(0);
		}
		return null;
	}

	public List<Author> readByAuthorName(String searchString) throws Exception{
		searchString = "%"+searchString+"%";
		return (List<Author>) read("select * from tbl_author where authorName like ?", new Object[] {searchString});
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		BookDAO bDao = new BookDAO(getConnection());
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao.readFirstLevel("select * from tbl_book where bookId In"
					+ "(select bookId from tbl_book_authors where authorId=?)", new Object[] {rs.getInt("authorId")});
			a.setBooks(books);
			authors.add(a);
		}
		return authors;
	}
	
	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Author> authors =  new ArrayList<Author>();
		//BookDAO bDao = new BookDAO(getConnection());
		
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}
}
