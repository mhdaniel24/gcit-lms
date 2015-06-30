import java.util.ArrayList;


public class Borrower{
	
	private String cardNo;
	private String name;
	private String address;
	private String phone;
	
	
	public Borrower(String cardNo, String name, String address, String phone) { 
		
		this.cardNo = cardNo;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	
	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("cardNo");
		arr.add("name");
		arr.add("address");
		arr.add("phone");
		return arr;
	}
	
	public static String getTableName(){
		return "tbl_borrower";
	}
	
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	

}
