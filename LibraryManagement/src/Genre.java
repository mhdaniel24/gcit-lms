
public class Genre {
	private String genere_id;
	private String genere_name;
	
	public Genre(String genere_name, String genere_id) {
		this.genere_id = genere_id;
		this.genere_name = genere_name;
	}

	public String getGenere_id() {
		return genere_id;
	}

	public void setGenere_id(String genere_id) {
		this.genere_id = genere_id;
	}

	public String getGenere_name() {
		return genere_name;
	}

	public void setGenere_name(String genere_name) {
		this.genere_name = genere_name;
	}
	
	
	
}
