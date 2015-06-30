import java.util.ArrayList;


public class Author {

	private String authorId;
	private String authorName;
	
	public Author(String authorId, String authorName) {
		this.authorId = authorId;
		this.authorName = authorName;
	}
	
	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("authorId");
		arr.add("authorName");
		return arr;
	}
	
	public static String getTableName(){
		return "tbl_author";
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	
}
