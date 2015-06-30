//TODO: Modify this class
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Loan {
	private String bookId;
	private String branchId;
	private String cardNo;
	private Date dateOut;
	private Date dueDate;
	private Date dateIn;

	public Loan(String bookId, String branchId, String cardNo) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Calendar cal = Calendar.getInstance();
		dateOut = cal.getTime();
		//System.out.println();
		cal.add(Calendar.DATE, 7);
		dueDate = cal.getTime();
		dateIn = null;
		//dateFormat.format(dateIn);
	}
	public Loan(String bookId, String branchId, String cardNo, String dateOut, String dueDate, String dateIn){
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		dateIn = null;//for now latter we can modify this when dateIn becomes valid
		try {
			this.dateOut = dateFormat.parse(dateOut);
		} catch (Exception e) {
			this.dateOut = null;
		}
		try {
			this.dueDate = dateFormat.parse(dueDate);
		} catch (Exception e) {
			this.dueDate = null;
		}
	}

	public static ArrayList<String> getColumnNames(){
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("bookId");
		arr.add("branchId");
		arr.add("cardNo");
		arr.add("dateOut");
		arr.add("dueDate");
		arr.add("dateIn");
		return arr;
	}

	public static String getTableName(){
		return "tbl_book_loans";
	}
	
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Date getDateIn() {
		return dateIn;
	}

	//------get dates as strings
	public String getDueDateAsString(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(dueDate);
	}
	public String getDateOutAsString()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(dateOut);
	}
	public String getDateInAsString()
	{
		if(dateIn == null){
			return "NULL";
		}else{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return dateFormat.format(dateIn);
		}

	}


}


