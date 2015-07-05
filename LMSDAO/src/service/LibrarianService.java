package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BookCopiesDAO;
import dao.BookDAO;
import dao.LibraryBranchDAO;
import domain.Book;
import domain.BookCopies;
import domain.LibraryBranch;

public class LibrarianService {



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


	public void updateLibraryBranch(LibraryBranch librryBranch) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {

			if (librryBranch == null || librryBranch.getBranchName() == null
					|| librryBranch.getBranchName().length() == 0
					|| librryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library branch name cannot be empty or more than 45 Chars");
			}else if(librryBranch == null || librryBranch.getBranchAddress() == null
					|| librryBranch.getBranchAddress().length() == 0
					|| librryBranch.getBranchAddress().length() > 45){
				throw new Exception(
						"Library Branch address cannot be empty or more than 45 Chars");
			}else {

				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				lbdao.update(librryBranch);
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			conn.close();
		}
	}
	
	public List<Book> readAllBooks() throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			BookDAO bdao = new BookDAO(conn);
			List<Book> allBooks = bdao.readAll();
			conn.commit();//not sure if needed
			return allBooks;
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
	//another method to implement this update is to update the entire branch in the branch update
	public void addCopiesOfBookToTheBranch(LibraryBranch libBranch, Book book, int newNumbCopies) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookCopies bc = new BookCopies();
		bc.setBookId(book.getBookId());
		bc.setBranchId(libBranch.getBranchId());
		bc.setNoOfCopies(newNumbCopies);
		
		if(libBranch.getBookCopies().containsKey(book)){
			//just update
			bcdao.update(bc);
		}else{
			//add
			bcdao.create(bc);
		}
	}
	
	
}
