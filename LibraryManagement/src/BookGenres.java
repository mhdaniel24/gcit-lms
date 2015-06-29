
public class BookGenres {
	private String bookId;
	private String genreId;
	public BookGenres(String bookId, String genreId) {
		this.bookId = bookId;
		this.genreId = genreId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getGenreId() {
		return genreId;
	}
	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
	
	
}
