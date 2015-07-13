package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

import com.gcit.lms.domain.*;
import com.gcit.lms.dao.*;

public class LibrarianService {



	public LibraryBranch readOneLibraryBranch(int branchId) throws Exception {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {
			LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
			LibraryBranch libraryBranch = lbdao.readOne(branchId);
			conn.commit();//not sure if needed
			return libraryBranch;
		} catch (Exception e) {//not a valid bookId
			e.printStackTrace();
			conn.rollback();//not sure if needed
			return null;
		} finally {
			conn.close();
		}
	}
	
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


	public void updateLibraryBranch(LibraryBranch libraryBranch) throws Exception,
	SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();

		try {

			if (libraryBranch == null || libraryBranch.getBranchName() == null
					|| libraryBranch.getBranchName().length() == 0
					|| libraryBranch.getBranchName().length() > 45) {
				throw new Exception(
						"Library branch name cannot be empty or more than 45 Chars");
			}else if(libraryBranch == null || libraryBranch.getBranchAddress() == null
					|| libraryBranch.getBranchAddress().length() == 0
					|| libraryBranch.getBranchAddress().length() > 45){
				throw new Exception(
						"Library Branch address cannot be empty or more than 45 Chars");
			}else {

				LibraryBranchDAO lbdao = new LibraryBranchDAO(conn);
				if(lbdao.readOne(libraryBranch.getBranchId()) == null){
					throw new Exception(
							"The Library Branch you are trying to update does not exit");
				}
				lbdao.update(libraryBranch);
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
	public void updateNumbCopies(LibraryBranch lb1, int bookId, int newNumbCopies) throws Exception{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.createConnection();
		BookCopiesDAO bcdao = new BookCopiesDAO(conn);
		BookDAO bookdao = new BookDAO(conn);
		LibraryBranchDAO branchdao = new LibraryBranchDAO(conn);
		
		try{
			if(bookdao.readOne(bookId) == null){
				throw new Exception("The book id you passed as a parameter does not match any book");
			}
			if(branchdao.readOne(lb1.getBranchId()) == null){
				throw new Exception("The branch id you passed as a parameter does not match any library branch");
			}

			BookCopies bc = new BookCopies();
			bc.setBookId(bookId);
			bc.setBranchId(lb1.getBranchId());
			bc.setNoOfCopies(newNumbCopies);

			LibraryBranch lb = branchdao.readOne(lb1.getBranchId());

			boolean flag = false;
			for(Entry<Book, Integer> entry : lb.getBookCopies().entrySet()){
				if(entry.getKey().getBookId() == bookId){
					bcdao.update(bc);
					flag = true;
					break;
				}
			}
			if(!flag){
				bcdao.create(bc);
			}
			conn.commit();
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();//not sure if needed
		} finally {
			conn.close();
		}
	}
	
	
}
