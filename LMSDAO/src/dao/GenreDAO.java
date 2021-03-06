package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Book;
import domain.Genre;

public class GenreDAO extends BaseDAO{
	
	
	public GenreDAO(Connection conn) throws Exception {
		super(conn);
	}

	public void create(Genre genre) throws Exception {
		save("insert into tbl_genre (genre_name) values(?)",
				new Object[] { genre.getGenreName() });
	}

	public void update(Genre genre) throws Exception {
		save("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void delete(Genre genre) throws Exception {
		save("delete from tbl_genre where genreId = ?",
				new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAll() throws Exception{
		return (List<Genre>) read("select * from tbl_genre", null);
		
	}

	public Genre readOne(int genreId) throws Exception {
		List<Genre> genres = (List<Genre>) read("select * from tbl_genre", new Object[] {genreId});
		if(genres!=null && genres.size()>0){
			return genres.get(0);
		}
		return null;
	}


	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<Genre> genres = new ArrayList<Genre>();
		BookDAO bDao = new BookDAO(getConnection());
		
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			@SuppressWarnings("unchecked")
			List<Book> books = (List<Book>) bDao.readFirstLevel("select * from tbl_book where bookId In"
					+ "(select bookId from tbl_book_genres where genre_id=?)", new Object[] {rs.getInt("genre_id")});
			g.setBooks(books);
			
			genres.add(g);
		}
		
		return genres;
	}
	
	@Override
	public List extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Genre> genres =  new ArrayList<Genre>();
		
		while(rs.next()){
			Genre g = new Genre();
			g.setGenreId(rs.getInt("genre_id"));
			g.setGenreName(rs.getString("genre_name"));
			
			genres.add(g);
		}
		return genres;
	}
}
