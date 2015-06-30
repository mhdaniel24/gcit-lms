import java.util.ArrayList;


public class Book {
	private String bookId;
	private String title;
	private String pubId;
	
	
	
	
	public Book(String bookId, String title, String pubId) {
		this.bookId = bookId;
		this.title = title;
		this.pubId = pubId;
	}

	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("bookId");
		arr.add("title");
		arr.add("pubId");
		return arr;
	}
	
	public static String getTableName(){
		return "tbl_book";
	}
	
	public String getBookId() {
		return bookId;
	}



	public void setBookId(String bookId) {
		this.bookId = bookId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getPubId() {
		return pubId;
	}



	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

}
