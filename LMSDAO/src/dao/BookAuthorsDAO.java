package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.BookAuthors;

public class BookAuthorsDAO extends BaseDAO{

	public void create(BookAuthors bookAuthors) throws Exception {
		save("insert into tbl_book_authors (bookId, authorId) values(?,)",
				new Object[] { bookAuthors.getBookId(), bookAuthors.getAuthorId() });
	}

	public void updateAuthor(BookAuthors bookAuthors) throws Exception {
		save("update tbl_book_authors set bookId = ? where authorId = ?",
				new Object[] { bookAuthors.getBookId(), bookAuthors.getAuthorId() });

	}

	public void updateBook(BookAuthors bookAuthors) throws Exception {
		save("update tbl_book_authors set authorId = ? where bookId = ?",
				new Object[] { bookAuthors.getAuthorId(), bookAuthors.getBookId() });

	}

	
	public void delete(BookAuthors bookAuthors) throws Exception {
		save("delete from tbl_book_authors where bookId = ? authorId = ?",
				new Object[] { bookAuthors.getBookId(), bookAuthors.getAuthorId() });
	}

	public List<BookAuthors> readAll() throws Exception{
		return (List<BookAuthors>) read("select * from tbl_book_authors", null);
		
	}

	public BookAuthors readOne(int bookId, int authorId) throws Exception {
		List<BookAuthors> bookAuthors = (List<BookAuthors>) read("select * from tbl_book_authors", new Object[] {bookId, authorId});
		if(bookAuthors!=null && bookAuthors.size()>0){
			return bookAuthors.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<BookAuthors> bookAuthors =  new ArrayList<BookAuthors>();
		
		while(rs.next()){
			BookAuthors ba = new BookAuthors();
			ba.setAuthorId(rs.getInt("authorId"));
			ba.setBookId(rs.getInt("bookId"));
			
			bookAuthors.add(ba);
		}
		return bookAuthors;
	}
}
