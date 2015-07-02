package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import domain.Author;
import domain.BookAuthors;
import domain.BookCopies;

public class BookCopiesDAO extends BaseDAO{
	public void create(BookCopies bookCopies) throws Exception {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values(?,?,?)",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId(), bookCopies.getNoOfCopies() });
	}

	public void update(BookCopies bookCopies) throws Exception {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? AND branchId = ?",
				new Object[] { bookCopies.getNoOfCopies(), bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public void delete(BookCopies bookCopies) throws Exception {
		save("delete from tbl_book_copies where bookId = ? AND branchId = ?",
				new Object[] { bookCopies.getBookId(), bookCopies.getBranchId() });
	}

	public List<BookCopies> readAll() throws Exception{
		return (List<BookCopies>) read("select * from tbl_book_copies", null);
		
	}

	public BookCopies readOne(int bookId, int branchId) throws Exception {
		List<BookCopies> bookCopies = (List<BookCopies>) read("select * from tbl_book_copies", new Object[] {bookId, branchId});
		if(bookCopies!=null && bookCopies.size()>0){
			return bookCopies.get(0);
		}
		return null;
	}

	@Override
	public List extractData(ResultSet rs) throws Exception {
		List<BookCopies> bookCopies =  new ArrayList<BookCopies>();
		
		while(rs.next()){
			BookCopies bc = new BookCopies();
			bc.setBookId(rs.getInt("bookId"));
			bc.setBranchId(rs.getInt("branchId"));
			bc.setNoOfCopies(rs.getInt("noOfCopies"));
			
			bookCopies.add(bc);
		}
		return bookCopies;
	}
}
