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
	
	DateFormat dateFormat;
	
	public Loan(String bookId, String branchId, String cardNo) {
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = Calendar.getInstance();
		dateOut = cal.getTime();
		//System.out.println();
		cal.add(Calendar.DATE, 7);
		dueDate = cal.getTime();
		dateIn = null;
		//dateFormat.format(dateIn);
	}
	public Loan(String bookId, String branchId, String cardNo, String dateOut, String dueDate){
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateIn = null;//dont understand the use of dateIn yet
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			
			this.dateOut = dateFormat.parse(dateOut);
			this.dueDate = dateFormat.parse(dueDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return dateFormat.format(dueDate);
	}
	public String getDateOutAsString()
	{
		return dateFormat.format(dateOut);
	}
	public String getDateInAsString()
	{
		if(dateIn == null){
			return "NULL";
		}else{
			return dateFormat.format(dateIn);
		}
			
	}
	
	
}
