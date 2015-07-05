package service;

import java.sql.Connection;
import java.util.List;
import java.util.Map.Entry;

import dao.BookCopiesDAO;
import dao.BookDAO;
import dao.LibraryBranchDAO;
import domain.Book;
import domain.BookCopies;
import domain.LibraryBranch;

public class BorrowerService {
	
	public List<LibraryBranch> readAllLibraryBranchs() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			List<LibraryBranch> allLibraryBranches = lbdao.readAll();
			conn.commit();//not sure if needed
			return allLibraryBranches;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	public void checkOutBook(int branchId, int bookId) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		
		LibraryBranch libBranch = lbdao.readOne(branchId);
		
		//make sure that the branchId maps a branch
		if(libBranch == null){
			throw new Exception(
					"The branch id provided does not match any branch");
		}
		
		Book book = null;
		for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
			if(entry.getKey().getBookId() == bookId){
				book = entry.getKey();
				break;
			}
		}
		
		if(book != null && libBranch.getBookCopies().get(book) > 0){
			BookCopiesDAO bcdao = new BookCopiesDAO(conn);
			BookCopies bc = new BookCopies();
			
			bc.setBookId(book.getBookId());
			bc.setBranchId(libBranch.getBranchId());
			bc.setNoOfCopies(libBranch.getBookCopies().get(book) - 1);
			
			bcdao.update(bc);
		}else{
			throw new Exception(
					"In order for a book to be checkout it must have more than 1 copy in the library branch");
		}
	}
	
	public void returnBook(int branchId, int bookId) throws Exception{
		//make sure that the book id actually maps a book
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		
		LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
		BookDAO bdao = new BookDAO(conn);
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		
		LibraryBranch libBranch = lbdao.readOne(branchId);
		
		if(libBranch == null){
			throw new Exception(
					"The provided branch id does not match any branch");
		}
		
		Book book = bdao.readOne(bookId);
		if(book == null){
			throw new Exception(
					"The provided book id does not match any book");
		}
		
		BookCopies bc = new BookCopies();
		
		bc.setBookId(book.getBookId());
		bc.setBranchId(libBranch.getBranchId());
		
		for(Entry<Book, Integer> entry : libBranch.getBookCopies().entrySet()){
			if(entry.getKey().getBookId() == bookId){
				bc.setNoOfCopies(entry.getValue() + 1);
				bcdao.update(bc);
				return;
			}
		}
		
		bc.setNoOfCopies(1);
		bcdao.create(bc);
	}
	
}
