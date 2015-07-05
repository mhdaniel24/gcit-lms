package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.html.HTMLDocument.Iterator;

import domain.Author;
import domain.Book;
import domain.BookCopies;
import domain.Genre;
import domain.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO{

	public LibraryBranchDAO(Connection conn) throws Exception {
		super(conn);
	}

	public void update(LibraryBranch libraryBranch) throws Exception {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress(), libraryBranch.getBranchId() });
		
//		for(Entry<Book, Integer> entry : libraryBranch.getBookCopies().entrySet()){
//			BookCopiesDAO bcdao = new BookCopiesDAO(getConnection());
//			BookCopies bcs = new BookCopies();
//			bcs.setBookId(entry.getKey().getBookId());
//			bcs.setBranchId(libraryBranch.getBranchId());
//			bcs.setNoOfCopies(entry.getValue());
//			
//			BookCopies check = bcdao.readOne(bcs.getBookId(), bcs.getBranchId());
//			if(check == null){
//				bcdao.create(bcs);
//			}else{
//				bcdao.update(bcs);
//			}
//			
//		}
	}

	public void delete(LibraryBranch libraryBranch) throws Exception {
		save("delete from tbl_library_branch where branchId = ?",
				new Object[] { libraryBranch.getBranchId() });
	}

	public void create(LibraryBranch libraryBranch) throws Exception {
		int branchId = saveWithID("insert into tbl_library_branch (branchName, branchAddress) values(?,?)",
				new Object[] { libraryBranch.getBranchName(), libraryBranch.getBranchAddress()});
		
		for(Entry<Book, Integer> entry : libraryBranch.getBookCopies().entrySet()){
			BookCopiesDAO bcdao = new BookCopiesDAO(getConnection());
			BookCopies bcs = new BookCopies();
			bcs.setBookId(entry.getKey().getBookId());
			bcs.setBranchId(branchId);
			bcs.setNoOfCopies(entry.getValue());
			bcdao.create(bcs);
		}
	}
	
	public List<LibraryBranch> readAll() throws Exception{
		return (List<LibraryBranch>) read("select * from tbl_library_branch", null);
		
	}
	
	public LibraryBranch readOne(int branchId) throws Exception {
		List<LibraryBranch> libraryBranches = (List<LibraryBranch>) read("select * from tbl_library_branch",
				new Object[] {branchId});
		if(libraryBranches!=null && libraryBranches.size()>0){
			return libraryBranches.get(0);
		}
		return null;
	}
	
	//TODO: Missing
	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws Exception {
		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		BookDAO bdao = new BookDAO(getConnection());
		BookCopiesDAO bcdao = new BookCopiesDAO(getConnection());
		BookLoanDAO bldao = new BookLoanDAO(getConnection());
		
		while(rs.next()){
			HashMap<Book, Integer> bookCopies = new HashMap<Book, Integer>();
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchName(rs.getString("branchName"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			lb.setLoans(bldao.readFirstLevel("select * from tbl_book_loans where branchId = ?", new Object[] {rs.getInt("branchId")}));
			
			@SuppressWarnings("unchecked")
			List<BookCopies> bcs = (List<BookCopies>) bcdao.readFirstLevel("select * from tbl_book_copies where branchId = ?"
					, new Object[] {rs.getInt("branchId")});
			for(BookCopies bc: bcs){
				bookCopies.put(bdao.readOne(bc.getBookId()), bc.getNoOfCopies());
			}
			lb.setBookCopies(bookCopies);
			libraryBranches.add(lb);
		}
		
		return libraryBranches;
	}
	
	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws Exception {
		List<LibraryBranch> libraryBranches = new ArrayList<LibraryBranch>();
		while(rs.next()){
			LibraryBranch lb = new LibraryBranch();
			lb.setBranchId(rs.getInt("branchId"));
			lb.setBranchAddress(rs.getString("branchAddress"));
			lb.setBranchName(rs.getString("branchName"));
			libraryBranches.add(lb);
		}
		return libraryBranches;
	}

}
