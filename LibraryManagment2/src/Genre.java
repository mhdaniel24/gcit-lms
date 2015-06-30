import java.util.ArrayList;


public class Genre {
	private String genre_id;
	private String genre_name;
	
	public Genre(String genre_id, String genre_name) {
		this.genre_id = genre_id;
		this.genre_name = genre_name;
	}

	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("genre_id");
		arr.add("genre_name");
		return arr;
	}
	
	public static String getTableName(){
		return "tbl_genre";
	}

	public String getGenre_id() {
		return genre_id;
	}

	public void setGenre_id(String genre_id) {
		this.genre_id = genre_id;
	}

	public String getGenre_name() {
		return genre_name;
	}

	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
	
	
}
