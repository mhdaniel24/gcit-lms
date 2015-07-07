package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.Book;
import domain.BookLoan;
import domain.Borrower;

public class BorrowerDAO extends BaseDAO{
	public BorrowerDAO(Connection conn) throws Exception {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void create(Borrower borrower) throws Exception {
		save("insert into tbl_borrower (name, address, phone) values(?,?,?)",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void update(Borrower borrower) throws Exception {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
				new Object[] { borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });

	}

	public void delete(Borrower borrower) throws Exception {
		save("delete from tbl_borrower where cardNo = ?",
				new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readAll() throws Exception{
		return (List<Borrower>) read("select * from tbl_borrower", null);
		
	}

	public Borrower readOne(int cardNo) throws Exception {
		List<Borrower> borrowers = (List<Borrower>) read("select * from tbl_borrower where cardNo = ?", new Object[] {cardNo});
		if(borrowers!=null && borrowers.size()>0){
			return borrowers.get(0);
		}
		return null;
	}

	//TODO: missing
	@Override
	public List<Borrower> extractData(ResultSet rs) throws Exception {
		List<Borrower> borrowers =  new ArrayList<Borrower>();
		BookLoanDAO bldao = new BookLoanDAO(getConnection());
		
		while(rs.next()){
			Borrower b = new Borrower();
			b.setAddress(rs.getString("address"));
			b.setName(rs.getString("name"));
			b.setPhone(rs.getString("phone"));
			b.setCardNo(rs.getInt("cardNo"));
			@SuppressWarnings("unchecked")
			List<BookLoan> bookLoans = (List<BookLoan>) bldao.readFirstLevel("select * from tbl_book_loans where cardNo = ?"
					, new Object[] {rs.getInt("cardNo")});
			b.setBookLoans(bookLoans);
			borrowers.add(b);
		}
		return borrowers;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<Borrower> borrowers =  new ArrayList<Borrower>();
		
		while(rs.next()){
			Borrower b = new Borrower();
			b.setCardNo(rs.getInt("cardNo"));
			b.setAddress(rs.getString("address"));
			b.setName(rs.getString("name"));
			b.setPhone(rs.getString("phone"));
			
			borrowers.add(b);
		}
		return borrowers;
	}
}
