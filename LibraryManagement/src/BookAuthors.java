
public class BookAuthors {
	String authorId;
	String bookId;
	public BookAuthors(String bookId, String authorId) {
		this.authorId = authorId;
		this.bookId = bookId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	
	
}
