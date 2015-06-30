import java.util.ArrayList;


public class Publisher{
	private String publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;

	public Publisher(String publisherId, String publisherName, String publisherAddress, String publisherPhone) {
		this.publisherId = publisherId;
		this.publisherName = publisherName;
		this.publisherAddress = publisherAddress;
		this.publisherPhone = publisherPhone;
	}

	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("publisherId");
		arr.add("publisherName");
		arr.add("publisherAddress");
		arr.add("publisherPhone");
		return arr;
	}
	
	public static String getTableName(){
		return "tbl_publisher";
	}
	
	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getPublisherAddress() {
		return publisherAddress;
	}

	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}

	public String getPublisherPhone() {
		return publisherPhone;
	}

	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	


}
