package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.BookGenres;

public class BookGenresDAO extends BaseDAO{
	
	public void create(BookGenres bookGenres) throws Exception {
		save("insert into tbl_book_genres (genre_id, bookId) values(?,?)",
				new Object[] { bookGenres.getGenreId(), bookGenres.getGenreId() });
	}

	public void updateGenre(BookGenres bookGenres) throws Exception {
		save("update tbl_book_genres set genre_id = ? where bookId = ?",
				new Object[] { bookGenres.getGenreId(), bookGenres.getBookId() });
	}

	public void updateBook(BookGenres bookGenres) throws Exception {
		save("update tbl_book_genres set bookId = ? where genre_id = ?",
				new Object[] { bookGenres.getBookId(), bookGenres.getGenreId() });
	}
	
	public void delete(BookGenres bookGenres) throws Exception {
		save("delete from tbl_book_genres where bookId = ? AND genre_id = ?",
				new Object[] { bookGenres.getBookId(), bookGenres.getGenreId() });
	}

	public List<BookGenres> readAll() throws Exception{
		return (List<BookGenres>) read("select * from tbl_book_genres", null);
		
	}
	//TODO: fix the rest of the readOne methods by adding the where
	public BookGenres readOne(int genreId, int bookId) throws Exception {
		List<BookGenres> bookGenres = (List<BookGenres>) read("select * from tbl_book_genres where genre_d = ? and bookId = ?", new Object[] {genreId, bookId});
		if(bookGenres!=null && bookGenres.size()>0){
			return bookGenres.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<BookGenres> bookGenres =  new ArrayList<BookGenres>();
		
		while(rs.next()){
			BookGenres bg = new BookGenres();
			bg.setBookId(rs.getInt("bookId"));
			bg.setGenreId(rs.getInt("genre_id"));
			
			bookGenres.add(bg);
		}
		return bookGenres;
	}
}
